package com.example.bankcards.repository;


import com.example.bankcards.base.BaseRepository;
import com.example.bankcards.entity.AuthUser;

import java.util.Optional;

public interface UserRepository extends BaseRepository<AuthUser> {
   Optional<AuthUser> findByUsername(String username);
}