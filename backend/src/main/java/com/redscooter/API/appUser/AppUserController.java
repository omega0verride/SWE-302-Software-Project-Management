package com.redscooter.API.appUser;

import com.redscooter.API.appUser.DTO.CreateAppUserDTO;
import com.redscooter.API.appUser.DTO.GetAppUserDTO;
import com.redscooter.API.appUser.DTO.RoleToUserDTO;
import com.redscooter.API.appUser.registration.OnRegistrationCompleteEvent;
import com.redscooter.API.appUser.registration.VerificationToken;
import com.redscooter.API.common.responseFactory.ResponseFactory;
import com.redscooter.exceptions.api.ResourceNotFoundException;
import com.redscooter.exceptions.api.UserAccountAlreadyActivatedException;
import com.redscooter.exceptions.api.forbidden.ForbiddenAccessException;
import com.redscooter.security.AuthenticationFacade;
import com.redscooter.security.DTO.BasicCredentialsDTO;
import com.redscooter.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private final JwtUtils jwtUtils;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @GetMapping("/")
    public ResponseEntity<List<GetAppUserDTO>> getUsers() {
        if (!AuthenticationFacade.isAdminOnCurrentSecurityContext())
            return ResponseEntity.status(403).body(null);
        return ResponseEntity.ok().body(appUserService.getUsers().stream().map(u -> u.toGetAppUserDTO()).collect(Collectors.toList()));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Object> deleteUsers(@PathVariable List<Long> ids) {
        if (!AuthenticationFacade.isAdminOnCurrentSecurityContext())
            return ResponseEntity.status(403).body(null);
        List<AbstractMap.SimpleEntry<Long, String>> response = new ArrayList<>();
        for (Long id : ids) {
            AbstractMap.SimpleEntry<Long, String> pair;
            if (appUserService.existsById(id)) {
                appUserService.delete(id);
                response.add(new AbstractMap.SimpleEntry<>(id, "Success!"));
            } else
                response.add(new AbstractMap.SimpleEntry<>(id, new ResourceNotFoundException("User", "Id", id.toString()).getMessage()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetAppUserDTO> getUserByUsername(@PathVariable String username) {
        if (!AuthenticationFacade.isAdminOrCurrentUserOnCurrentSecurityContext(username))
            return ResponseEntity.status(403).body(null);
        AppUser user = appUserService.getByUsername(username);
        return new ResponseEntity<GetAppUserDTO>(user.toGetAppUserDTO(), HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<GetAppUserDTO> updateUserByUsername(@PathVariable String username) {
        if (!AuthenticationFacade.isAdminOrCurrentUserOnCurrentSecurityContext(username))
            return ResponseEntity.status(403).body(null);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
//        AppUser user = appUserService.getUser(username).orElseThrow(() -> {
//            throw new ResourceNotFoundException("User", "username", username);
//        });
//        return new ResponseEntity<AppUser>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        if (!AuthenticationFacade.isAdminOrCurrentUserOnCurrentSecurityContext(username))
            return ResponseEntity.status(403).body(null);
        if (!appUserService.existsByUsername(username))
            throw new ResourceNotFoundException("User", "username", username);
        appUserService.delete(username);
        return ResponseFactory.buildResourceDeletedSuccessfullyResponse();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid CreateAppUserDTO createAppUserDTO, @RequestParam(defaultValue = "false") Boolean skipVerification, HttpServletRequest request) {
        AppUser appUser = appUserService.saveUser(new AppUser(createAppUserDTO));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (skipVerification && auth.getAuthorities().contains(AuthenticationFacade.ADMIN_AUTHORITY)) {
            appUser.setEnabled(true);
        } else {
            VerificationToken verificationToken = appUserService.createVerificationToken(appUser);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(verificationToken));
        }
        return ResponseFactory.buildResourceCreatedSuccessfullyResponse("User", "Id", appUser.getId());
    }

    @PostMapping("/register/resendVerification")
    public ResponseEntity<Object> registerUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestBody(required = true) BasicCredentialsDTO basicCredentialsDTO, HttpServletRequest request) {
        AppUser appUser = appUserService.getByUsername(basicCredentialsDTO.getUsername());
        if (!appUserService.matchesPassword(appUser, basicCredentialsDTO.getPassword()) && !AuthenticationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService))
            throw new ForbiddenAccessException("Unauthorized! Cannot resend verification email without verifying account credentials from a non admin context.");
        if (appUser.isEnabled())
            throw new UserAccountAlreadyActivatedException();
        VerificationToken verificationToken = appUserService.createVerificationToken(appUser);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(verificationToken));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/register/confirm")
    public Object confirmRegistration(@RequestParam(name = "token", required = true) String token, @RequestParam(name = "username", required = false) String username) {
        VerificationToken verificationToken = appUserService.getVerificationToken(token);
        if (verificationToken == null) {
            if (username != null) {
                AppUser appUser = appUserService.getByUsername(username,false);
                if (appUser != null && appUser.isEnabled()) {
                    // return "The user has already been verified.";
                    RedirectView redirectView = new RedirectView();
                    redirectView.setUrl("https://www.redscooter.al");
                    return redirectView;
                }
            }
            return "The verification token is invalid or the user has already been verified!";
        }
        AppUser user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            verificationToken = appUserService.createVerificationToken(user);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(verificationToken));
            return "Verification Token Has Expired! A new token has been sent to your email."; // TODO change response to HTML
        }
        appUserService.enableUser(user);

        // return "Successfully Verified Your Account";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://www.redscooter.al");
        return redirectView;
    }
}

