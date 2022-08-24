package com.enigma.loan_backend.utils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static boolean validateContentTypeImage(String contentType) {
        List<String> contentTypes = Arrays.asList("image/jpeg", "image/png");
        return contentTypes.contains(contentType);
    }

    public static boolean validateContentTypeDocument(String contentType) {
        List<String> contentTypes = Arrays.asList("image/jpeg", "image/png", "application/pdf");
        return contentTypes.contains(contentType);
    }

}
