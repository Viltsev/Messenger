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

import java.util.List;

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
    public void createCardSet(CardSet cardSet) {
        String email = cardSet.getUserEmail();
        User userFromDB = userRepository.findByEmail(email);

        if (userFromDB != null) {
            List<CardSet> currentCards = userFromDB.getCardSets();
            currentCards.add(cardSet);

            userFromDB.setCardSets(currentCards);
            userRepository.save(userFromDB);
            logger.info("New card set successfully saved");
        } else {
            logger.info("There is not such user in database");
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
    public void createCard(Card card) {
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

            logger.info("New card successfully saved!");
        } else {
            logger.info("There is not such card set in database");
        }

    }

    @Override
    public void saveToLearnedCards(Card card) {
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

            logger.info("New card set successfully saved");
        } else {
            logger.info("There is not such card set in database");
        }
    }

    @Override
    public void refreshCards(Long id, String email) {
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
        } else {
            logger.info("There is not such card set in database");
        }
    }
}
