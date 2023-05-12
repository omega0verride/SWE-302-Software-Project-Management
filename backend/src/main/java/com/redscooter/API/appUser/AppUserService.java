package com.redscooter.API.appUser;

import com.redscooter.API.appUser.registration.VerificationToken;
import com.redscooter.API.appUser.registration.VerificationTokenRepository;
import com.redscooter.API.common.BaseService;
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


    public AppUser addRoleToUser(String username, String roleName) {
        AppUser user = getByUsername(username);
        user.getRoles().add(roleService.getByName(roleName));
        return save(user);
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
