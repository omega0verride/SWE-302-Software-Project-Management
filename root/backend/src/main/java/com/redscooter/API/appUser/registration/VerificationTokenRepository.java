package com.redscooter.API.appUser.registration;

import com.redscooter.API.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(AppUser user);

    void deleteAllByUser(AppUser user);
}