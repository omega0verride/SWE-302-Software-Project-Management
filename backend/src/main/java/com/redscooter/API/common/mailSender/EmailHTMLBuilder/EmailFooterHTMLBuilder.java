package com.redscooter.API.common.mailSender.EmailHTMLBuilder;

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
public class EmailFooterHTMLBuilder {
    static PebbleEngine engine = new PebbleEngine.Builder().build();
    public static String templatePath = Utilities.joinPathsAsString(Utilities.HTML_TEMPLATES_FOLDER_DIR, "EmailFooter.html");
    static PebbleTemplate compiledTemplate = engine.getTemplate(templatePath);

    public static String buildEmailFooterHTML() {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Map<String, Object> context = new HashMap<>();
        context.put("storeName", "RedScooter");
        context.put("storeURL", "https://redscooter.al/");
        context.put("currencySymbol", "ALL ");
        context.put("storeAddress", "https://goo.gl/maps/mLVCsQMNgtJ8bwH99");
        context.put("storePhoneNumber", "+355696867903");
        context.put("storeEmail", "redscooter.al@gmail.com");
        context.put("instagramURL", "https://instagram.com/motorra.elektrik");
        context.put("facebookURL", "https://www.facebook.com/motorraelektrikred/");

        Writer writer = new StringWriter();
        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException ignored) {
        }

        return writer.toString();
    }
}
