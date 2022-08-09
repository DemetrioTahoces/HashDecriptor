package com.demetrio.hashdecriptor.application;

import com.demetrio.hashdecriptor.common.HashUtils;
import com.demetrio.hashdecriptor.domain.entity.Algorithm;
import com.demetrio.hashdecriptor.domain.entity.Hash;
import com.demetrio.hashdecriptor.domain.repositories.HashRepository;
import com.demetrio.hashdecriptor.domain.services.HashService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "hash", name = "repository")
public class HashServiceDatabase implements HashService {

    @Value("${hash.characters.max}")
    private Integer maxChars;

    private final ThreadPoolTaskExecutor taskExecutor;

    private final HashRepository hashRepository;

    private final HashUtils hashUtils;

    @Override
    public Hash decryptHash(String hash, String algorithm) {
        return hashRepository.findByHashAndAlgorithm(hash, algorithm);
    }

    @Override
    public void encryptHashes() {
        log.info("Encrypting hashes");
        Arrays.stream(Algorithm.values()).forEach(algorithm -> taskExecutor.execute(hashesTask(algorithm.name())));
    }

    private Runnable hashesTask(String algorithm) {
        return () -> {
            try {
                MessageDigest md = MessageDigest.getInstance(algorithm);
                for (int i = 1; i <= maxChars; i++) {
                    hashAllCombinations(algorithm, md, i, hashUtils.getAlphabet(), "");
                }
            } catch (NoSuchAlgorithmException e) {
                log.error("Can't find algorithm {}", algorithm);
            }
        };
    }

    private void hashAllCombinations(String algorithm, MessageDigest md, int maxLength, List<Character> alphabet, String current) {
        if (current.length() == maxLength) {
            Hash hashModel = saveHash(algorithm, md, current);
            if (hashModel == null) {
                log.error("Can't save hash {} -> {}", algorithm, current);
            }
        } else {
            StringBuilder currentBuilder = new StringBuilder(current);
            for (int i = 0; i < alphabet.size(); i++) {
                String oldCurr = currentBuilder.toString();
                currentBuilder.append(alphabet.get(i));
                hashAllCombinations(algorithm, md, maxLength, alphabet, currentBuilder.toString());
                currentBuilder = new StringBuilder(oldCurr);
            }
        }
    }

    private Hash saveHash(String algorithm, MessageDigest md, String current) {
        return hashRepository.save(Hash.builder()
                .id(algorithm + "-" + current)
                .algorithm(algorithm)
                .text(current)
                .hash(hashUtils.getHash(current, md))
                .build());
    }

}
