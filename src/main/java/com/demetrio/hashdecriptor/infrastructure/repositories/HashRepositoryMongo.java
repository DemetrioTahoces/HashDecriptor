package com.demetrio.hashdecriptor.infrastructure.repositories;

import com.demetrio.hashdecriptor.domain.entity.Hash;
import com.demetrio.hashdecriptor.domain.repositories.HashRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
@ConditionalOnProperty(prefix = "hash", name = "repository", havingValue = "mongo")
public interface HashRepositoryMongo extends MongoRepository<Hash, String>, HashRepository {

    @Query("{hash:'?0', algorithm:'?1'}")
    Hash findByHashAndAlgorithm(String hash, String algorithm);

}
