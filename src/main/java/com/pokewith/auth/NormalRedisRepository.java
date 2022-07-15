package com.pokewith.auth;

import org.springframework.data.repository.CrudRepository;

public interface NormalRedisRepository extends CrudRepository<NormalToken, String> {

}
