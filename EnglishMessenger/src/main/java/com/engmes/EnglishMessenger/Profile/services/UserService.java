package com.engmes.EnglishMessenger.Profile.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.enums.CardLists;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    public Optional<User> findByEmail(String email){
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не был найден", email)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("EmptyRole"))
        );
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByEmail(user.getEmail());

        if (userFromDB != null) {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void updateUser(User user) { userRepository.save(user); }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public int getLanguageLevel(User user) {
        if (user.getLanguageLevel() == null) {
            return 0;
        }
        else {
            String strLanguageLevel = user.getLanguageLevel();

            return switch (strLanguageLevel) {
                case "A1" -> 1;
                case "A2" -> 2;
                case "B1" -> 3;
                case "B2" -> 4;
                case "C1" -> 5;
                case "C2" -> 6;
                default -> 0;
            };
        }
    }

    public void setFields(String email, String username,Date dateOfBirth, byte[] photo, List<Long> interests) {
        Optional<User> optionalUser = findByEmail(email);
        User user = optionalUser.get();
        user.setUsername(username);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoto(photo);
        user.setIdInterests(interests);
        updateUser(user);
    }
}
