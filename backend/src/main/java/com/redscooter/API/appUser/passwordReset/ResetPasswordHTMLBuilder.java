package com.redscooter.API.appUser.passwordReset;

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
public class ResetPasswordHTMLBuilder {
    static PebbleEngine engine = new PebbleEngine.Builder().build();
    static String templatePath = Utilities.joinPathsAsString(Utilities.HTML_TEMPLATES_FOLDER_DIR, "PasswordResetTemplate.html");
    static PebbleTemplate compiledTemplate = engine.getTemplate(templatePath);

    public static String buildResetPasswordHTML(AppUser user, String confirmationURL, PasswordResetToken passwordResetToken) {

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
        context.put("confirmationToken", passwordResetToken.getToken());


        Writer writer = new StringWriter();
        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException ignored) {
        }

        return writer.toString();
    }
}
