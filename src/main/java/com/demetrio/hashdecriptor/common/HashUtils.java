package com.demetrio.hashdecriptor.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Component
public class HashUtils {

    @Value("${hash.letter.min}")
    private String lowerLetter;

    @Value("${hash.letter.max}")
    private Integer higherLetter;

    public String getHash(String txt, MessageDigest md) {
        byte[] array = md.digest(txt.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString();
    }

    public List<Character> getAlphabet() {
        List<Character> alphabet = new ArrayList<>();
        for (char letter = lowerLetter.toCharArray()[0]; letter <= higherLetter; letter++) {
            alphabet.add(letter);
        }
        return alphabet;
    }

}
