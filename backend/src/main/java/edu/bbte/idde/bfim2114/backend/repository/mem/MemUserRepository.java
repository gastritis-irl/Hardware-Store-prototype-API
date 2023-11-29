package edu.bbte.idde.bfim2114.backend.repository.mem;

import edu.bbte.idde.bfim2114.backend.model.User;
import edu.bbte.idde.bfim2114.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public final class MemUserRepository implements UserRepository {

    private final Map<Long, User> dataStore = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    private MemUserRepository() {
    }

    private static class Holder {
        private static final MemUserRepository INSTANCE = new MemUserRepository();
    }

    public static MemUserRepository getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public User create(User user) {
        long id = currentId.getAndIncrement();
        user.setId(id);
        dataStore.put(id, user);
        log.info("User with ID {} created.", id);
        return user;
    }

    @Override
    public User findByUserName(String username) {
        return dataStore.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findById(Long id) {
        User user = dataStore.get(id);
        if (user == null) {
            log.warn("User with ID {} not found.", id);
        } else {
            log.info("User with ID {} found.", id);
        }
        return user;
    }

    @Override
    public Collection<User> findAll() {
        log.info("Reading all Users.");
        return new ArrayList<>(dataStore.values());
    }

    @Override
    public User update(User user) {
        if (dataStore.containsKey(user.getId())) {
            dataStore.put(user.getId(), user);
            log.info("User with ID {} updated.", user.getId());
            return user;
        } else {
            log.error("Failed to update. User with ID {} not found.", user.getId());
            throw new IllegalArgumentException("User with the given ID does not exist");
        }
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting User with ID {}.", id);
        dataStore.remove(id);
    }
}