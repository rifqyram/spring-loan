package com.enigma.loan_backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static boolean validateImageFile(String ext) {
        String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";

        Pattern imageReg = Pattern.compile(regex);

        if (ext == null) return false;

        Matcher matcher = imageReg.matcher(ext);
        return matcher.matches();
    }

}
