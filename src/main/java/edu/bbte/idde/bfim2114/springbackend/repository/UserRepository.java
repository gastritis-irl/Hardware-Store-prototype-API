package edu.bbte.idde.bfim2114.springbackend.repository;

import edu.bbte.idde.bfim2114.springbackend.model.User;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("User")
public interface UserRepository extends BaseRepository<User> {

    User findByEmail(String username);
}
