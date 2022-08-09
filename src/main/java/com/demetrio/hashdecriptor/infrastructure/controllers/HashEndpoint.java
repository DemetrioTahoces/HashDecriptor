package com.demetrio.hashdecriptor.infrastructure.controllers;

import com.demetrio.hashdecriptor.domain.services.HashService;
import com.demetrio.hashdecriptor.domain.entity.Hash;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hash")
@Slf4j
public class HashEndpoint {

    @Autowired
    private HashService hashService;

    @GetMapping()
    public ResponseEntity<Hash> decryptHash(@RequestParam String hash, @RequestParam String algorithm) {
        Hash hashModel = hashService.decryptHash(hash, algorithm);
        log.info("Decrypted: {}", hashModel);
        return hashModel != null ? ResponseEntity.ok().body(hashModel) : ResponseEntity.badRequest().body(null);
    }

    @PostMapping()
    public ResponseEntity<String> encryptAll() {
        hashService.encryptHashes();
        return ResponseEntity.ok().body("OK");
    }

}
