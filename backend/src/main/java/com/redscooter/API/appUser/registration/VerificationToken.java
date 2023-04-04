package com.redscooter.API.appUser.registration;

import com.redscooter.API.appUser.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24; // minutes

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    private Date expiryDate;

    public VerificationToken(String token, AppUser user) {
        setToken(token);
        setUser(user);
        setExpiryDate(calculateExpiryDate(EXPIRATION));
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }


}
