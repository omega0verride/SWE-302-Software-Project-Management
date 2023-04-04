//package com.redscooter.API.appUser.registration;
//
//import org.junit.jupiter.api.Assertions;
//import org.testng.annotations.Test;
//
//import java.io.File;
//
//public class TestConfirmUserRegistrationHTMLBuilder {
//
////    @Test
////    @Disabled
////    void TestAndOpenHTMLOnBrowser() {
////       try {
////            FileWriter myWriter = new FileWriter("out.html");
////            myWriter.write(ConfirmUserRegistrationHTMLBuilder.buildConfirmUserRegistrationHTML(DatabaseConfig.admin, "localhost/test", new VerificationToken(), StoreConfig.redscooter));
////            myWriter.close();
////            Desktop.getDesktop().open(new File("out.html"));
////        } catch (IOException e) {
////            System.out.println("An error occurred.");
////            e.printStackTrace();
////        }
////    }
//
//    @Test
//    void TestOrderStatusHTMLTemplateExists() {
//        // this will fail on the static values evaluation, this does still count as a valid test...
//        Assertions.assertTrue(new File(ConfirmUserRegistrationHTMLBuilder.templatePath).exists(), "ConfirmUserRegistrationHTMLBuilder template file could not be found! FlePath: " + ConfirmUserRegistrationHTMLBuilder.templatePath);
//    }
//}
