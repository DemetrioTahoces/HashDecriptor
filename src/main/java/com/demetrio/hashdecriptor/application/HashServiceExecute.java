package com.demetrio.hashdecriptor.application;

import com.demetrio.hashdecriptor.common.HashUtils;
import com.demetrio.hashdecriptor.domain.entity.Hash;
import com.demetrio.hashdecriptor.domain.services.HashService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnMissingBean(HashServiceDatabase.class)
public class HashServiceExecute implements HashService {

    @Value("${hash.characters.max}")
    private Integer maxChars;

    private final HashUtils hashUtils;

    @Override
    public Hash decryptHash(String hash, String algorithm) {
        try {
            MessageDigest md = java.security.MessageDigest.getInstance(algorithm);
            String password = findPassword(hash, md);
            return getHashModel(hash, password, algorithm);
        } catch (NoSuchAlgorithmException e) {
            log.error("Invalid Algorithm", e);
            return null;
        }
    }

    private Hash getHashModel(String hash, String text, String algorithm) {
        return Hash.builder()
                .algorithm(algorithm)
                .text(text)
                .hash(hash)
                .build();
    }

    private String findPassword(String hash, MessageDigest md) {
        for (int i = 1; i <= maxChars; i++) {
            String password = testAllCombinations(md, i, hashUtils.getAlphabet(), hash, "");
            if (password != null) {
                return password;
            }
        }
        return null;
    }

    private String testAllCombinations(MessageDigest md, int maxLength, List<Character> alphabet, String hash, String current) {
        if (current.length() == maxLength) {
            String hashTest = hashUtils.getHash(current, md);
            return Objects.equals(hashTest, hash) ? current : null;
        } else {
            StringBuilder currentBuilder = new StringBuilder(current);
            for (int i = 0; i < alphabet.size(); i++) {
                String oldCurr = currentBuilder.toString();
                currentBuilder.append(alphabet.get(i));
                String found = testAllCombinations(md, maxLength, alphabet, hash, currentBuilder.toString());
                if (found != null) {
                    return found;
                }
                currentBuilder = new StringBuilder(oldCurr);
            }
        }
        return null;
    }

    @Override
    public void encryptHashes() {
        log.info("Not Encrypting hashes");
    }

}
