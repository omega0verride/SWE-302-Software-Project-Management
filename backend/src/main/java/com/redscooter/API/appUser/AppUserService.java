package com.redscooter.API.appUser;

import com.redscooter.API.appUser.registration.VerificationToken;
import com.redscooter.API.appUser.registration.VerificationTokenRepository;
import com.redscooter.API.common.BaseService;
import com.redscooter.exceptions.api.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class AppUserService extends BaseService<AppUser> implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    public AppUserService(AppUserRepository appUserRepository, RoleService roleService, PasswordEncoder passwordEncoder, VerificationTokenRepository tokenRepository) {
        super(appUserRepository, "User");
        this.appUserRepository = appUserRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> {
            throw new ResourceNotFoundException("User", "username", username);
        });
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }


    // this method is used to register users internally, with no need to verify
    public AppUser getOrRegisterUserInternally(AppUser user) {
        AppUser appUser = appUserRepository.getByUsername(user.getUsername());
        if (appUser != null)
            return appUser;
        user.setEnabled(true);
        user.getRoles().clear();
        if (user.isPasswordUpdated())
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    // TODO refactor these methods. why is the return type optional? the service should handle exceptions
    public Optional<AppUser> saveUser(AppUser user) {
        if (appUserRepository.existsByUsername(user.getUsername()))
            return Optional.empty();
        if (user.isPasswordUpdated())
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(appUserRepository.save(user));
    }


    public AppUser addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> {
            throw new ResourceNotFoundException("User", "username", username);
        });
        user.getRoles().add(roleService.getByName(roleName));
        return save(user);
    }


    // TODO [1] refactor not found inside this function
    public Optional<AppUser> getUser(String username) {
        return appUserRepository.findByUsername(username);
    }
//    public Optional<AppUser> getUser(String username, AuthType au) {
//        return appUserRepository.findByUsername(username);
//    }

    @Override
    public void delete(Long id, boolean throwNotFoundEx) {
        if (existsById(id, throwNotFoundEx)) {
            AppUser appUser = getById(id);
            deleteAllVerificationTokens(appUser);
            appUserRepository.deleteById(id);
        }
    }

    public void delete(String username) {
        AppUser appUser = appUserRepository.getByUsername(username);
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

    public void enableUser(AppUser appUser) {
        appUser.setEnabled(true);
        saveUser(appUser);
        deleteAllVerificationTokens(appUser);
    }

    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public VerificationToken createVerificationToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, user);
        deleteAllVerificationTokens(user);
        tokenRepository.save(myToken);
        return myToken;
    }

    public void deleteAllVerificationTokens(AppUser user) {
        tokenRepository.deleteAllByUser(user);
    }

    public boolean matchesPassword(AppUser user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
