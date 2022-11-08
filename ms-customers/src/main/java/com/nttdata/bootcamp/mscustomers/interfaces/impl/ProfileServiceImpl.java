package com.nttdata.bootcamp.mscustomers.interfaces.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.mscustomers.infraestructure.IProfileRepository;
import com.nttdata.bootcamp.mscustomers.interfaces.IProfileService;
import com.nttdata.bootcamp.mscustomers.model.Profile;

@Service
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    private IProfileRepository repository;

    @Override
    public List<Profile> createFromList(List<Profile> profiles) {
        return repository.insert(profiles);
    }

    @Override
    public List<Profile> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Profile> findByProfile(String profile) {
        return repository.findByProfile(profile);
    }

}
