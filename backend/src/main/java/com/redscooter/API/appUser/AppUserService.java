package com.redscooter.API.appUser;

import com.redscooter.API.appUser.passwordReset.PasswordResetToken;
import com.redscooter.API.appUser.passwordReset.PasswordResetTokenRepository;
import com.redscooter.API.appUser.registration.VerificationToken;
import com.redscooter.API.appUser.registration.VerificationTokenRepository;
import com.redscooter.API.common.BaseService;
import com.redscooter.exceptions.api.verificationTokens.VerificationTokenException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@Slf4j
public class AppUserService extends BaseService<AppUser> implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public AppUserService(AppUserRepository appUserRepository, RoleService roleService, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        super(appUserRepository, "User");
        this.appUserRepository = appUserRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = getByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }


    // this method is used to register users internally, with no need to verify
    public AppUser getOrRegisterUserInternally(AppUser user) {
        AppUser appUser = getByUsername(user.getUsername());
        if (appUser != null)
            return appUser;
        user.setEnabled(true);
        user.getRoles().clear();
        if (user.isPasswordUpdated())
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    // used to register only
    public AppUser registerUser(AppUser user) {
        AppUser existingUserWithUsername = getByUsername(user.getUsername(), false);
        if (existingUserWithUsername != null)
            throw buildResourceAlreadyExistsException("username", user.getUsername());
        if (user.isPasswordUpdated())
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    // used to persist, do not use for register
    public AppUser saveUser(AppUser user) {
        AppUser existingUserWithUsername = getByUsername(user.getUsername(), false);
        if (existingUserWithUsername != null) {
            if (user.getId() != null && !user.getId().equals(existingUserWithUsername.getId())) {
                throw buildResourceAlreadyExistsException("username", user.getUsername());
            }
            user.setId(existingUserWithUsername.getId());
        }
        if (user.isPasswordUpdated())
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }


    public void addRoleToUser(AppUser appUser, String roleName) {
        appUser.getRoles().add(roleService.getByName(roleName));
    }
    public void addRoleToUser(String username, String roleName) {
        addRoleToUser(getByUsername(username), roleName);
    }

    @Override
    public void delete(Long id, boolean throwNotFoundEx) {
        if (existsById(id, throwNotFoundEx)) {
            AppUser appUser = getById(id);
            deleteAllVerificationTokens(appUser);
            appUserRepository.deleteById(id);
        }
    }

    public void delete(String username) {
        AppUser appUser = getByUsername(username);
        if (appUser != null) {
            deleteAllVerificationTokens(appUser);
            appUserRepository.delete(appUser);
        }
    }

    public void deleteAllUsersById(List<Long> ids) {
        appUserRepository.deleteAllById(ids);
    }

    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }


    public AppUser getByUsername(String username) {
        return getByUsername(username, true);
    }

    public AppUser getByUsername(String username, boolean throwNotFoundEx) {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        if (appUser.isPresent())
            return appUser.get();
        if (throwNotFoundEx)
            throw buildResourceNotFoundException("username", username);
        return null;
    }

    public void enableUser(AppUser appUser) {
        appUser.setEnabled(true);
        saveUser(appUser);
        deleteAllVerificationTokens(appUser);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    public VerificationToken createVerificationToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, user);
        deleteAllVerificationTokens(user);
        verificationTokenRepository.flush();
        verificationTokenRepository.save(myToken);
        return myToken;
    }

    public PasswordResetToken createPasswordResetToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.flush();
        passwordResetTokenRepository.save(myToken);
        return myToken;
    }

    public void deleteAllVerificationTokens(AppUser user) {
        verificationTokenRepository.deleteAllByUser(user);
    }

    public boolean matchesPassword(AppUser user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        if (passToken == null)
            throw new VerificationTokenException();
        if (passToken.getExpiryDate().before(Calendar.getInstance().getTime()))
            throw new VerificationTokenException(passToken.getExpiryDate().getTime());
        return true;
    }

    public void deleteAllPasswordResetTokesByUser(AppUser appUser) {
        passwordResetTokenRepository.deleteAllByUser(appUser);
    }
}
