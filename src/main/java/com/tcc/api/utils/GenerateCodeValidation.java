package com.tcc.api.utils;

import java.util.Random;

public class GenerateCodeValidation {
    public static String generateCode(){
        Random random = new Random();

        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }

        return sb.toString();
    }
}
