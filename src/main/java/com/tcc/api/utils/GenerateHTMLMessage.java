package com.tcc.api.utils;

public class GenerateHTMLMessage {
    public static String signup(String message) {
       return "<!DOCTYPE html>" +
                "<html lang = \"pt-br\">" +
                "<head>" +
                "</head>" +
                "<body>" +
                "<p>" + message + "</p>" +
                "</body>" +
                "</html>";
    }

}
