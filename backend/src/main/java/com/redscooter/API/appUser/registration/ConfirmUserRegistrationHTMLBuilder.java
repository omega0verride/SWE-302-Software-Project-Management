package com.redscooter.API.appUser.registration;

import com.redscooter.API.appUser.AppUser;
import com.redscooter.API.common.mailSender.EmailHTMLBuilder.EmailFooterHTMLBuilder;
import com.redscooter.util.Utilities;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import lombok.Getter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfirmUserRegistrationHTMLBuilder {
    static PebbleEngine engine = new PebbleEngine.Builder().build();
    static String templatePath = Utilities.joinPathsAsString(Utilities.HTML_TEMPLATES_FOLDER_DIR, "UserRegistrationTemplate.html");
    static PebbleTemplate compiledTemplate = engine.getTemplate(templatePath);

    public static String buildConfirmUserRegistrationHTML(AppUser user, String confirmationURL, VerificationToken verificationToken) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Map<String, Object> context = new HashMap<>();
        context.put("storeName", "RedScooter");
        context.put("storeURL", "https://redscooter.al/");
        context.put("instagramURL", "https://instagram.com/motorra.elektrik");
        context.put("facebookURL", "https://www.facebook.com/motorraelektrikred/");
        context.put("emailFooter", EmailFooterHTMLBuilder.buildEmailFooterHTML());

        context.put("userName", Utilities.capitalizeFirstLetter(user.getName()));
        context.put("username", user.getUsername());
        context.put("confirmationURL", confirmationURL);
        context.put("confirmationToken", verificationToken.getToken());


        Writer writer = new StringWriter();
        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException ignored) {
        }

        return writer.toString();
    }
}
