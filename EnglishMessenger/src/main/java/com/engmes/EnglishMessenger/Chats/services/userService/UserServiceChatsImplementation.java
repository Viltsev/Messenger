package com.engmes.EnglishMessenger.Chats.services.userService;

import com.engmes.EnglishMessenger.Chats.model.UserChats;
import com.engmes.EnglishMessenger.Chats.repo.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Primary
public class UserServiceChatsImplementation implements UserServiceChats {
    private final UsersRepository repository;

    @Override
    public Stream<UserChats> findAllUsers(String email) {
        List<UserChats> userChats = repository.findAll();
        return userChats.stream().filter(user -> !user.getEmail().equals(email));
    }

    @Override
    public UserChats saveUser(UserChats userChats) {
        return repository.save(userChats);
    }

    @Override
    public UserChats findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public UserChats updateUser(UserChats userChats) {
        return repository.save(userChats);
    }

    @Override
    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public UserChats getUserByEmail(String email) { return repository.findUserByEmail(email); }
}
