package com.demetrio.hashdecriptor.domain.services;

import com.demetrio.hashdecriptor.domain.entity.Hash;

public interface HashService {

    Hash decryptHash(String hash, String algorithm);

    void encryptHashes();

}
