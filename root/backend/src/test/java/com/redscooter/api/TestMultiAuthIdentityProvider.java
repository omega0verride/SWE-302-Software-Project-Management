package com.redscooter.api;

import com.redscooter.security.thirdPartyLogin.FacebookIdentity;
import com.redscooter.security.thirdPartyLogin.GoogleIdentity;
import com.redscooter.security.thirdPartyLogin.ThirdPartyIdentityProviderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestMultiAuthIdentityProvider {
    static GoogleIdentity googleIdentity;
    static FacebookIdentity facebookIdentity;

    @BeforeAll
    static void setup() {
        googleIdentity = new GoogleIdentity();
        facebookIdentity = new FacebookIdentity();
    }

    @Test
    void testFacebookIdentityInvalidToken() {
        ThirdPartyIdentityProviderException ex = Assertions.assertThrows(ThirdPartyIdentityProviderException.class, () -> facebookIdentity.getAppUserFromToken("eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyOWFiYzE5YmUyN2ZiNDE1MWFhNDMxZTk0ZmEzNjgwYWU0NThkYTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NzMyMDkzMTYsImF1ZCI6Ijk1MjAxMzQyMTA4OS0xZ2FhMmplYnNqczE0ZHQybHVmaGZvNHU1djk2b29hai5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwNTEwMTIzMzk1MDg3ODczNzcxNCIsImhkIjoiZXBva2EuZWR1LmFsIiwiZW1haWwiOiJpYnJldGkyMEBlcG9rYS5lZHUuYWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXpwIjoiOTUyMDEzNDIxMDg5LTFnYWEyamVic2pzMTRkdDJsdWZoZm80dTV2OTZvb2FqLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwibmFtZSI6IkluZHJpdCBCcmV0aSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BRWRGVHA2UmxlVXFUYlNqaUFQN29xOGZQUGc1a3FKSXp2TlFNSDRpVDFhOT1zOTYtYyIsImdpdmVuX25hbWUiOiJJbmRyaXQiLCJmYW1pbHlfbmFtZSI6IkJyZXRpIiwiaWF0IjoxNjczMjA5NjE2LCJleHAiOjE2NzMyMTMyMTYsImp0aSI6IjhlOTBlYzU3YmYxZjY3NDZlMjRjMzY1MGZjZDZhNGVmY2UxOTQ1ZDgifQ.NYzzhJ4bgLgH26DsVT96zcn37onbpfMbgFTPJO12iNBhMZJRwhlNotpRzEYpFhBLQfB9XLcemKJ4EOBTM3RekvXZi594SnTZ3WOTbbhbQ696SKZaNseYGprn6h-OPR7SOg3wKQe3L5J8HrjF8PCTRVyx0tAQZE_acOznO4mrNjqIFF1pudsanhR2FNPpoNCY8YfAqgNnSO6t6SC72bxL6jjO3u-OP9ILZB25z8I15sCp0l8Ea47BLZcV_gwsl14_Vf-cZNQn5vhgY8wnWYvH266KN6WYyfRBapQ452tLjlNTohTaBpBT10-8nrefIgTu8sWV9HU3nJixxANb_GOPzw"));
        Assertions.assertTrue(ex.getMessage().contains("Invalid Token"));
    }

    @Test
    void testGoogleIdentityInvalidToken() {
        ThirdPartyIdentityProviderException ex = Assertions.assertThrows(ThirdPartyIdentityProviderException.class, () -> googleIdentity.getAppUserFromToken("eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyOWFiYzE5YmUyN2ZiNDE1MWFhNDMxZTk0ZmEzNjgwYWU0NThkYTUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NzMyMDkzMTYsImF1ZCI6Ijk1MjAxMzQyMTA4OS0xZ2FhMmplYnNqczE0ZHQybHVmaGZvNHU1djk2b29hai5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwNTEwMTIzMzk1MDg3ODczNzcxNCIsImhkIjoiZXBva2EuZWR1LmFsIiwiZW1haWwiOiJpYnJldGkyMEBlcG9rYS5lZHUuYWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXpwIjoiOTUyMDEzNDIxMDg5LTFnYWEyamVic2pzMTRkdDJsdWZoZm80dTV2OTZvb2FqLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwibmFtZSI6IkluZHJpdCBCcmV0aSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BRWRGVHA2UmxlVXFUYlNqaUFQN29xOGZQUGc1a3FKSXp2TlFNSDRpVDFhOT1zOTYtYyIsImdpdmVuX25hbWUiOiJJbmRyaXQiLCJmYW1pbHlfbmFtZSI6IkJyZXRpIiwiaWF0IjoxNjczMjA5NjE2LCJleHAiOjE2NzMyMTMyMTYsImp0aSI6IjhlOTBlYzU3YmYxZjY3NDZlMjRjMzY1MGZjZDZhNGVmY2UxOTQ1ZDgifQ.NYzzhJ4bgLgH26DsVT96zcn37onbpfMbgFTPJO12iNBhMZJRwhlNotpRzEYpFhBLQfB9XLcemKJ4EOBTM3RekvXZi594SnTZ3WOTbbhbQ696SKZaNseYGprn6h-OPR7SOg3wKQe3L5J8HrjF8PCTRVyx0tAQZE_acOznO4mrNjqIFF1pudsanhR2FNPpoNCY8YfAqgNnSO6t6SC72bxL6jjO3u-OP9ILZB25z8I15sCp0l8Ea47BLZcV_gwsl14_Vf-cZNQn5vhgY8wnWYvH266KN6WYyfRBapQ452tLjlNTohTaBpBT10-8nrefIgTu8sWV9HU3nJixxANb_GOPzw"));
        Assertions.assertTrue(ex.getMessage().contains("Invalid Token"));
    }
}
