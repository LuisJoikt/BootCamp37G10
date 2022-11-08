package com.nttdata.bootcamp.mscustomers.infraestructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.mscustomers.model.Profile;

@Repository
public interface IProfileRepository extends MongoRepository<Profile, String> {
    public Optional<Profile> findByProfile(String profile);
}
