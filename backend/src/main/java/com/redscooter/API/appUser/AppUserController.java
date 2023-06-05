package com.redscooter.API.appUser;

import com.redscooter.API.appUser.DTO.CreateAppUserDTO;
import com.redscooter.API.appUser.DTO.GetAppUserDTO;
import com.redscooter.API.appUser.DTO.UpdateAppUserDTO;
import com.redscooter.API.appUser.passwordReset.OnResetPasswordEvent;
import com.redscooter.API.appUser.passwordReset.PasswordDto;
import com.redscooter.API.appUser.passwordReset.PasswordResetToken;
import com.redscooter.API.appUser.registration.OnRegistrationCompleteEvent;
import com.redscooter.API.appUser.registration.VerificationToken;
import com.redscooter.API.common.responseFactory.PageResponse;
import com.redscooter.API.common.responseFactory.ResponseFactory;
import com.redscooter.exceptions.api.ResourceNotFoundException;
import com.redscooter.exceptions.api.UserAccountAlreadyActivatedException;
import com.redscooter.exceptions.api.badRequest.BadRequestBodyException;
import com.redscooter.exceptions.api.forbidden.ForbiddenAccessException;
import com.redscooter.exceptions.api.forbidden.ForbiddenException;
import com.redscooter.exceptions.api.unauthorized.InvalidCredentialsException;
import com.redscooter.exceptions.api.unauthorized.UserAccountNotActivatedException;
import com.redscooter.security.AuthorizationFacade;
import com.redscooter.security.DTO.BasicCredentialsDTO;
import com.redscooter.security.JwtUtils;
import com.redscooter.util.Utilities;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.restprocessors.DynamicRESTController.CriteriaParameters;
import org.restprocessors.DynamicRestMapping;
import org.restprocessors.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private final JwtUtils jwtUtils;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @DynamicRestMapping(path = "", entity = AppUser.class, requestMethod = RequestMethod.GET)
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PageResponse<GetAppUserDTO>> getUsers(CriteriaParameters cp) {
        AuthorizationFacade.ensureAdmin();
        return ResponseFactory.buildPageResponse(appUserService.getAllByCriteria(cp), GetAppUserDTO::new);
    }

    @DeleteMapping("/batchDelete/{ids}")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> deleteUsers(@PathVariable List<Long> ids) {
        AuthorizationFacade.ensureAdmin();
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
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<GetAppUserDTO> getUserByUsername(@PathVariable String username) {
        AuthorizationFacade.ensureAdminOrCurrentUserOnCurrentSecurityContext(username);
        AppUser user = appUserService.getByUsername(username);
        return new ResponseEntity<GetAppUserDTO>(user.toGetAppUserDTO(), HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<GetAppUserDTO> updateUserByUsername(@PathVariable String username, @RequestBody UpdateAppUserDTO updateAppUserDTO) {
        AuthorizationFacade.ensureAdminOrCurrentUserOnCurrentSecurityContext(username);
        return new ResponseEntity<GetAppUserDTO>(appUserService.updateUser(username, updateAppUserDTO).toGetAppUserDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        AuthorizationFacade.ensureAdminOrCurrentUserOnCurrentSecurityContext(username);
        if (!appUserService.existsByUsername(username))
            throw new ResourceNotFoundException("User", "username", username);
        appUserService.delete(username);
        return ResponseFactory.buildResourceDeletedSuccessfullyResponse();
    }

    @PostMapping("")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid CreateAppUserDTO createAppUserDTO, @RequestParam(defaultValue = "false") Boolean skipVerification, @RequestParam(defaultValue = "false") Boolean isAdmin, HttpServletRequest httpServletRequest) {
        AppUser appUser = appUserService.registerUser(new AppUser(createAppUserDTO));
        if (isAdmin) {
            if (!AuthorizationFacade.isAdminOnCurrentSecurityContext())
                throw new ForbiddenException("Only admin user can register admin accounts! Set isAdmin=false.");
            appUserService.addRoleToUser(appUser, AuthorizationFacade.ADMIN_AUTHORITY.getAuthority());
        }
        if (skipVerification) {
            if (!AuthorizationFacade.isAdminOnCurrentSecurityContext())
                throw new ForbiddenException("Only admin users can skip email verification! Set skipVerification=false.");
            appUserService.enableUser(appUser);
        } else {
            VerificationToken verificationToken = appUserService.createVerificationToken(appUser);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(httpServletRequest, verificationToken));
        }
        appUserService.saveUser(appUser);
        return ResponseFactory.buildResourceCreatedSuccessfullyResponse("User", "Id", appUser.getId());
    }

    @PostMapping("/register/resendVerification")
    @SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Object> registerUser(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader, @RequestBody(required = true) BasicCredentialsDTO basicCredentialsDTO, HttpServletRequest httpServletRequest) {
        AppUser appUser = appUserService.getByUsername(basicCredentialsDTO.getUsername());
        if (!appUserService.matchesPassword(appUser, basicCredentialsDTO.getPassword()) && !AuthorizationFacade.isAdminAuthorization(authorizationHeader, jwtUtils, appUserService))
            throw new ForbiddenAccessException("Unauthorized! Cannot resend verification email without verifying account credentials from a non admin context.");
        if (appUser.isEnabled())
            throw new UserAccountAlreadyActivatedException();
        VerificationToken verificationToken = appUserService.createVerificationToken(appUser);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(httpServletRequest, verificationToken));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/register/confirm")
    public Object confirmRegistration(@RequestParam(name = "token") String token, @RequestParam(name = "username", required = false) String username, HttpServletRequest httpServletRequest) {
        VerificationToken verificationToken = appUserService.getVerificationToken(token);
        if (verificationToken == null) {
            if (username != null) {
                AppUser appUser = appUserService.getByUsername(username, false);
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
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(httpServletRequest, verificationToken));
            return "Verification Token Has Expired! A new token has been sent to your email."; // TODO change response to HTML or redirect to frontend endpoint that states this message
        }
        appUserService.enableUser(user);
        // return "Successfully Verified Your Account";
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://www.redscooter.al");
        return redirectView;
    }


    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@NotEmpty @NotNull @RequestParam(name = "username") String username, HttpServletRequest httpServletRequest) {
        AppUser appUser = appUserService.getByUsername(username);
        if (!appUser.isEnabled())
            throw new UserAccountNotActivatedException();
        PasswordResetToken passwordResetToken = appUserService.createPasswordResetToken(appUser);
        eventPublisher.publishEvent(new OnResetPasswordEvent(httpServletRequest, passwordResetToken));
        return ResponseFactory.buildGenericSuccessfulResponse("Email successfully sent to [" + appUser.getEmail() + "]", appUser.getEmail());
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody PasswordDto passwordDto) {
        AppUser appUser = appUserService.getByUsername(passwordDto.getUsername());
        if (Utilities.notNullOrEmpty(passwordDto.getToken())) {
            if (appUserService.validatePasswordResetToken(passwordDto.getToken())) {
                appUser.setPassword(passwordDto.getNewPassword());
                appUserService.saveUser(appUser);
                appUserService.deleteAllPasswordResetTokesByUser(appUser);
                return ResponseFactory.buildGenericSuccessfulResponse("Successfully changed password!");
            }
        } else if (Utilities.notNullOrEmpty(passwordDto.getOldPassword())) {
            if (appUserService.matchesPassword(appUser, passwordDto.getOldPassword())) {
                appUser.setPassword(passwordDto.getNewPassword());
                appUserService.saveUser(appUser);
                return ResponseFactory.buildGenericSuccessfulResponse("Successfully changed password!");
            } else
                throw new InvalidCredentialsException("Old Password and Username do not match!");
        }
        throw new BadRequestBodyException("Either PasswordResetToken or OldPassword must be specified!");
    }
}

