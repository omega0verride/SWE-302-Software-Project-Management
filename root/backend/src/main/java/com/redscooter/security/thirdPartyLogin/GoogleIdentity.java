package com.redscooter.security.thirdPartyLogin;

import com.redscooter.API.appUser.AppUser;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GoogleIdentity extends ThirdPartyIdentityBase {

    // TODO[production] change value
    // TODO[refactor] move to config file
    private static final String TrustedOAuthAppClientID = "952013421089-1gaa2jebsjs14dt2lufhfo4u5v96ooaj.apps.googleusercontent.com";
    private static final String OAuthAppClientIDJsonPath = "aud";

    public GoogleIdentity() {
        super("https://oauth2.googleapis.com/tokeninfo?id_token=", "sub", "GOOGLE_ID_", "invalid_token");
    }

    @Override
    protected void mapExtraUserDetails(AppUser appUser, JsonNode root) {
        String OAuthAppClientID = tryGetValue(root, OAuthAppClientIDJsonPath);
        if (!TrustedOAuthAppClientID.equals(OAuthAppClientID)) {
            throw new ThirdPartyIdentityProviderException("Untrusted Token. The value of $.\"" + OAuthAppClientIDJsonPath + "\" did not match with the defined TrustedOAuthAppClientID.", "UntrustedToken");
        }
        appUser.setEmail(tryGetValue(root, "email", false));
        appUser.setName(tryGetValue(root, "given_name", false));
        appUser.setSurname(tryGetValue(root, "family_name", false));
    }


//    {
//        "iss": "https://accounts.google.com",
//            "nbf": "1673200531",
//            "aud": "952013421089-1gaa2jebsjs14dt2lufhfo4u5v96ooaj.apps.googleusercontent.com",
//            "sub": "112837812943339789449",
//            "email": "indritbreti@gmail.com",
//            "email_verified": "true",
//            "azp": "952013421089-1gaa2jebsjs14dt2lufhfo4u5v96ooaj.apps.googleusercontent.com",
//            "name": "Indrit Breti",
//            "picture": "https://lh3.googleusercontent.com/a/AEdFTp7_IvvWIeFlhF5R7b73c2qoS4xHPn5fTqBQ2t9YMg=s96-c",
//            "given_name": "Indrit",
//            "family_name": "Breti",
//            "iat": "1673200831",
//            "exp": "1673204431",
//            "jti": "b2ad71dd27faec0fde234914efa85afb82e5889a",
//            "alg": "RS256",
//            "kid": "a29abc19be27fb4151aa431e94fa3680ae458da5",
//            "typ": "JWT"
//    }
}
