package com.engmes.EnglishMessenger.Profile.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardLists;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public String saveCard(Card card) {
        String email = card.getUserEmail();
        User userFromDB = userRepository.findByEmail(email);

        if (userFromDB != null) {
            List<Card> currentCardList = userFromDB.getCardList();
            currentCardList.add(card);
            userFromDB.setCardList(currentCardList);
            userRepository.save(userFromDB);
            return "Card successfully created";
        } else {
            logger.info("There is not such user in database");
            return "Card has not been created!";
        }
    }

    public String saveCardTo(CardLists cardLists, Card card) {
        String email = card.getUserEmail();
        User userFromDB = userRepository.findByEmail(email);

        if (userFromDB != null) {
            List<Card> currentRepeatedCards = userFromDB.getToRepeatCards();
            List<Card> currentLearnedCards = userFromDB.getLearnedCard();

            switch (cardLists) {
                case SAVE_TO_REPEAT -> {
                    currentRepeatedCards.add(card);
                    currentLearnedCards.remove(card);
                }
                case SAVE_TO_LEARNED -> {
                    currentLearnedCards.add(card);
                    currentRepeatedCards.remove(card);
                }
            }
            userFromDB.setToRepeatCards(currentRepeatedCards);
            userFromDB.setLearnedCard(currentLearnedCards);

            userRepository.save(userFromDB);

            switch (cardLists) {
                case SAVE_TO_LEARNED -> logger.info("Card successfully saved to learned list");
                case SAVE_TO_REPEAT -> logger.info("Card successfully saved to repeat list");
            }

            return "Card successfully saved";
        } else {
            logger.info("There is not such user in database");
            return "Card has not been saved!";
        }
    }
}
