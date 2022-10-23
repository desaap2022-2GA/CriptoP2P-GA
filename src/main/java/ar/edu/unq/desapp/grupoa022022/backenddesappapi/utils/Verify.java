package ar.edu.unq.desapp.grupoa022022.backenddesappapi.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verify {

    public static boolean verifyLong(String word, int long1, int long2){
        return (word.length() >= long1 && word.length() <= long2);
    }
    public static boolean verifyEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        return (mather.find());
    }

    public static boolean verifyPassword(String password){
        int upperCase = 0;
        int lowerCase = 0;
        int specialCharacter = 0;

        for (int k = 0; k < password.length(); k++) {
            if (Character.isUpperCase(password.charAt(k))) upperCase++;
            if (Character.isLowerCase(password.charAt(k))) {
                lowerCase++;
            } else {
                specialCharacter++;
            }
        }

        return (upperCase > 0 && lowerCase > 0 && specialCharacter > 0 && password.length() >= 6);
    }

    public static boolean verifyCVUMercadoPago(String cvu){
        return cvu.length() == 22;
    }

    public static boolean verifyAddressWalletActiveCripto(String addressAC){
        return addressAC.length() == 8;
    }
}
