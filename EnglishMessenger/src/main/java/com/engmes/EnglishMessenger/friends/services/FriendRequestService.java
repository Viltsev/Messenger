package com.engmes.EnglishMessenger.friends.services;

import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import com.engmes.EnglishMessenger.friends.model.FriendRequest;
import com.engmes.EnglishMessenger.friends.repo.FriendRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity getFriends(String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getEmailFriends());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }

    public ResponseEntity getSentFriendRequests(String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            List<FriendRequest> friendsRequests = friendRequestRepository.findBySenderEmail(email);
            if (!friendsRequests.isEmpty()) {
                return ResponseEntity.ok(friendsRequests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет отправленных запросов.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }

    public ResponseEntity getReceivedFriendRequests(String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            List<FriendRequest> friendsRequests = friendRequestRepository.findByReceiverEmail(email);
            if (!friendsRequests.isEmpty()) {
                return ResponseEntity.ok(friendsRequests);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет полученных запросов.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }

    public ResponseEntity getUsersFriendRequests(String email) {
        List<User> friendsRequests = new ArrayList<>();
        ResponseEntity receivedFriendRequests = getReceivedFriendRequests(email);
        if (receivedFriendRequests.getStatusCode() == HttpStatusCode.valueOf(200)) {
            ResponseEntity<List<FriendRequest>> requests = receivedFriendRequests;
            requests.getBody().forEach(friendRequest -> {
                User currentRequestUser = userService.findUserByEmail(friendRequest.getSenderEmail());
                friendsRequests.add(currentRequestUser);
            });
            return ResponseEntity.ok(friendsRequests);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет полученных запросов.");
        }
    }

    public ResponseEntity addingFriend(String email, String requestedEmail) {
        Optional<User> user = userService.findByEmail(email);
        Optional<User> requestedUser = userService.findByEmail(requestedEmail);

        if (user.isPresent() && requestedUser.isPresent()) {

            if (user.get().getEmailFriends().contains(requestedEmail) && requestedUser.get().getEmailFriends().contains(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователи уже являются друзьями.");
            }

            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSenderEmail(user.get().getEmail());
            friendRequest.setReceiverEmail(requestedUser.get().getEmail());

            boolean existsFriendRequest = friendRequestRepository.existsBySenderEmailAndReceiverEmail(user.get().getEmail(), requestedUser.get().getEmail());
            if (!existsFriendRequest) {
                friendRequestRepository.save(friendRequest);
                return ResponseEntity.ok("Запрос на добавление в друзья отправлен.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Запрос на добавление в друзья уже существует.");
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }

    @Transactional
    public ResponseEntity acceptedFriending(String email, String sentEmail) {
        Optional<User> user = userService.findByEmail(email);
        Optional<User> sentUser = userService.findByEmail(sentEmail);

        if (user.isPresent() && sentUser.isPresent()) {
            boolean existsFriendRequest = friendRequestRepository.existsBySenderEmailAndReceiverEmail(sentEmail, email);

            if (existsFriendRequest) {
                friendRequestRepository.deleteBySenderEmailAndReceiverEmail(sentEmail, email);
                user.get().getEmailFriends().add(sentEmail);
                sentUser.get().getEmailFriends().add(email);
                return ResponseEntity.ok("Запрос на добавление в друзья принят.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Запроса на добавление в друзья не существует.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }

    @Transactional
    public ResponseEntity rejectedFriending(String email, String sentEmail) {
        Optional<User> user = userService.findByEmail(email);
        Optional<User> sentUser = userService.findByEmail(sentEmail);

        if (user.isPresent() && sentUser.isPresent()) {
            boolean existsFriendRequest = friendRequestRepository.existsBySenderEmailAndReceiverEmail(sentEmail, email);

            if (existsFriendRequest) {
                friendRequestRepository.deleteBySenderEmailAndReceiverEmail(sentEmail, email);
                return ResponseEntity.ok("Запрос на добавление в друзья отклонен.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Запроса на добавление в друзья не существует.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует.");
        }
    }
}
