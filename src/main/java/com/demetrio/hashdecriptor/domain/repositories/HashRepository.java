package com.demetrio.hashdecriptor.domain.repositories;

import com.demetrio.hashdecriptor.domain.entity.Hash;

public interface HashRepository {

    Hash findByHashAndAlgorithm(String hash, String algorithm);

    Hash save(Hash hashModel);

}
