package com.olympus.utils;

import java.util.Random;

public class AppUtils {
    public static String generateRandomOTP() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }
}
