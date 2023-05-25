package com.redscooter.API.appUser.passwordReset;

import com.redscooter.API.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(AppUser user);

    void deleteAllByUser(AppUser user);
}