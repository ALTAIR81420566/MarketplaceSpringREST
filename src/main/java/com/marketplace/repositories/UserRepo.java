package com.marketplace.repositories;

import com.marketplace.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
     User findByLogin(String login);
}
