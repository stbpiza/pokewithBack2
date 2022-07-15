package com.pokewith.auth;

import org.springframework.data.repository.CrudRepository;

public interface EmailRedisRepository extends CrudRepository<EmailToken, String> {

}
