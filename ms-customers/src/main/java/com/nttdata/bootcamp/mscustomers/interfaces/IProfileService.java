package com.nttdata.bootcamp.mscustomers.interfaces;

import java.util.List;
import java.util.Optional;

import com.nttdata.bootcamp.mscustomers.model.Profile;

public interface IProfileService {
    public List<Profile> createFromList(List<Profile> profiles);

    public List<Profile> findAll();

    public Optional<Profile> findByProfile(String profile);
}
