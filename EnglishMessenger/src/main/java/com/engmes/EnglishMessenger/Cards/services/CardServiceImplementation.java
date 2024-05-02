package com.engmes.EnglishMessenger.Cards.services;

import com.engmes.EnglishMessenger.Cards.models.Card;
import com.engmes.EnglishMessenger.Cards.models.CardSet;

import com.engmes.EnglishMessenger.Cards.repo.CardRepository;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Primary
public class CardServiceImplementation implements CardService {
    private final CardRepository repository;
    private final UserService userService;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(CardServiceImplementation.class);

    @Override
    public CardSet findCardSet(Long id, String email) {
        User user = userRepository.findByEmail(email);
        return user.getCardSets()
                .stream()
                .filter(cardSet -> cardSet.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String createCardSet(CardSet cardSet) {
        String email = cardSet.getUserEmail();
        User userFromDB = userRepository.findByEmail(email);

        if (userFromDB != null) {
            List<CardSet> currentCards = userFromDB.getCardSets();
            currentCards.add(cardSet);

            userFromDB.setCardSets(currentCards);
            userRepository.save(userFromDB);
            logger.info("New card set successfully saved");
            return "New card set successfully saved";
        } else {
            logger.info("There is not such user in database");
            return "There is not such user in database";
        }
    }

    @Override
    public void updateCardSet(CardSet cardSet) {
        User user = userRepository.findByEmail(cardSet.getUserEmail());
        if (user != null) {
            // get all cards set for user
            List<CardSet> cardSets = user.getCardSets();

            // find card set that will be replaced
            CardSet cardSetToUpdate = findCardSet(cardSet.getId(), cardSet.getUserEmail());

            // replace current card set to the new card set
            cardSets.remove(cardSetToUpdate);
            cardSets.add(cardSet);

            // update card set for user
            user.setCardSets(cardSets);

            // update user
            userRepository.save(user);
        } else {
            logger.info("There is not such user in database!");
        }
    }

    @Override
    public void updateTotalCards(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            List<CardSet> cardSets = user.getCardSets();
            List<Card> cardsToLearn = new ArrayList<>();
            List<Card> cardsLearned = new ArrayList<>();

            cardSets.forEach(cardSet -> {
                List<Card> toLearn = cardSet.getToLearn();
                List<Card> learned = cardSet.getLearned();
                cardsToLearn.addAll(toLearn);
                cardsLearned.addAll(learned);
            });

            user.setCardsToLearn(cardsToLearn);
            user.setCardsLearned(cardsLearned);

            userRepository.save(user);
        } else {
            logger.info("There is not such user in database!");
        }
    }

    @Override
    public String createCard(Card card) {
        // find card set which will save this card
        CardSet cardSet = findCardSet(card.getSetId(), card.getUserEmail());

        if (cardSet != null) {
            // get card list from this set
            List<Card> cardList = cardSet.getCardList();
            // get to_learn list from this set
            List<Card> toLearnList = cardSet.getToLearn();

            // update this lists
            cardList.add(card);
            toLearnList.add(card);

            // update this set
            cardSet.setCardList(cardList);
            cardSet.setToLearn(toLearnList);

            // update set in database
            updateCardSet(cardSet);
            updateTotalCards(card.getUserEmail());

            logger.info("New card successfully saved!");
            return "New card successfully saved!";
        } else {
            logger.info("There is not such card set in database");
            return "There is not such card set in database";
        }
    }

    @Override
    public String saveToLearnedCards(Card card) {
        CardSet currentCardsSet = findCardSet(card.getSetId(), card.getUserEmail());
        if (currentCardsSet != null) {
            // get list of cards to learn
            List<Card> currentCardsToLearn = currentCardsSet.getToLearn();

            // get list of learned cards
            List<Card> currentCardsLearned = currentCardsSet.getLearned();

            // update this lists
            currentCardsToLearn.remove(card);
            currentCardsLearned.add(card);

            // update current set
            currentCardsSet.setToLearn(currentCardsToLearn);
            currentCardsSet.setLearned(currentCardsLearned);

            // update set in database
            updateCardSet(currentCardsSet);
            updateTotalCards(card.getUserEmail());

            logger.info("New card set successfully saved");
            return "New card set successfully saved";
        } else {
            logger.info("There is not such card set in database");
            return "There is not such card set in database";
        }
    }

    @Override
    public String refreshCards(Long id, String email) {
        CardSet cardSet = findCardSet(id, email);
        if (cardSet != null) {
            List<Card> currentCardsToLearn = cardSet.getToLearn();
            List<Card> currentCardsLearned = cardSet.getLearned();

            // push all cards from learned list to to_learn list
            currentCardsToLearn.addAll(currentCardsLearned);
            // delete all cards from learned list
            currentCardsLearned.clear();

            // update card set
            cardSet.setToLearn(currentCardsToLearn);
            cardSet.setLearned(currentCardsLearned);

            // update card set in database
            updateCardSet(cardSet);
            updateTotalCards(email);
            return "Cards have been refreshed!";
        } else {
            logger.info("There is not such card set in database");
            return "There is not such card set in database";
        }
    }

    @Override
    public String deleteAllTotal(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setCardsToLearn(null);
            user.setCardsLearned(null);
            userRepository.save(user);
            return "All total cards have been deleted!";
        } else {
            logger.info("There is not such user in database!");
            return "There is not such user in database!";
        }
    }

    @Override
    public String deleteCard(Card card) {
        User user = userRepository.findByEmail(card.getUserEmail());

        if (user != null) {
            List<CardSet> cardSets = user.getCardSets();
            cardSets.forEach(cardSet -> {
                cardSet.getCardList().remove(card);
                cardSet.getToLearn().remove(card);
                cardSet.getLearned().remove(card);
            });
            user.setCardSets(cardSets);
            userRepository.save(user);

            updateTotalCards(card.getUserEmail());
            return "Card has been deleted!";
        } else {
            logger.info("There is not such user in database!");
            return "There is not such user in database!";
        }
    }

    @Override
    public String deleteSet(String email, Long id) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            List<CardSet> cardSets = user.getCardSets();
            cardSets.removeIf(cardSet -> Objects.equals(cardSet.getId(), id));
            user.setCardSets(cardSets);
            userRepository.save(user);

            updateTotalCards(email);
            return "Set has been deleted!";
        } else {
            logger.info("There is not such user in database!");
            return "There is not such user in database!";
        }
    }

    @Override
    public List<Card> getTotalToLearn(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user.getCardsToLearn();
        } else {
            logger.info("There is not such user in database!");
            return null;
        }
    }

    @Override
    public List<Card> getTotalLearned(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user.getCardsLearned();
        } else {
            logger.info("There is not such user in database!");
            return null;
        }
    }

    @Override
    public List<CardSet> getCardSets(String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user.getCardSets();
        } else {
            logger.info("There is not such user in database!");
            return null;
        }
    }

    @Override
    public CardSet getCardSet(String email, Long id) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            List<CardSet> cardSets = user.getCardSets();

            for (CardSet cardSet : cardSets) {
                if (cardSet.getId().equals(id)) {
                    return cardSet;
                }
            }

            logger.info("CardSet with id " + id + " not found for user " + email);
            return null;
        } else {
            logger.info("There is not such user in database!");
            return null;
        }
    }
}
