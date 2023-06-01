package com.redscooter.API.order.OrderVerifcication;

import com.redscooter.API.common.mailSender.EmailHTMLBuilder.EmailFooterHTMLBuilder;
import com.redscooter.API.order.Order;
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
public class OrderConfirmationHTMLBuilder {
    static PebbleEngine engine = new PebbleEngine.Builder().build();
    public static String templatePath = Utilities.joinPathsAsString(Utilities.HTML_TEMPLATES_FOLDER_DIR, "OrderConfirmationTemplate.html");
    static PebbleTemplate compiledTemplate = engine.getTemplate(templatePath);

    public static String buildOrderConfirmationHTML(Order order) {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Map<String, Object> context = new HashMap<>();
        context.put("storeName", "RedScooter");
        context.put("storeURL", "https://redscooter.al/");
        context.put("currencySymbol", "ALL ");
        context.put("instagramURL", "https://instagram.com/motorra.elektrik");
        context.put("facebookURL", "https://www.facebook.com/motorraelektrikred/");
        context.put("emailFooter", EmailFooterHTMLBuilder.buildEmailFooterHTML());

//        Utilities.capitalizeFirstLetter(recipientName)
        context.put("orderId", order.getId());
        context.put("orderLines", order.getOrderLines());
        context.put("orderTotal", order.calculateTotal());
        context.put("orderAddress", order.getOrderBilling().getClientAddressLine1());
        context.put("orderNotes", order.getOrderBilling().getClientNotes());

        Writer writer = new StringWriter();
        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException ignored) {
        }

        return writer.toString();
    }
}
