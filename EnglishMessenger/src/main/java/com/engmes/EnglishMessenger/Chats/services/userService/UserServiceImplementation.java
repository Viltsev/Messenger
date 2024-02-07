package com.engmes.EnglishMessenger.Chats.services.userService;

import com.engmes.EnglishMessenger.Chats.model.User;
import com.engmes.EnglishMessenger.Chats.repo.UsersRepository;
import com.engmes.EnglishMessenger.Chats.services.userService.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImplementation implements UserService {
    private final UsersRepository repository;

    @Override
    public Stream<User> findAllUsers(String email) {
        List<User> users = repository.findAll();
        return users.stream().filter(user -> !user.getEmail().equals(email));
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) { return repository.findUserByEmail(email); }
}
