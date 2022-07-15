package com.pokewith.auth;

import org.springframework.data.repository.CrudRepository;

public interface GitRedisRepository extends CrudRepository<GitToken, String> {

}
