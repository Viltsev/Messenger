package com.engmes.EnglishMessenger.friends.controllers;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.friends.services.FriendRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @GetMapping("/getFriends/{email}")
    public ResponseEntity getFriends(@PathVariable String email) {
        return friendRequestService.getFriends(email);
    }

    @GetMapping("/getSentRequests/{email}")
    public ResponseEntity getSentFriendRequests(@PathVariable String email) {
        return friendRequestService.getSentFriendRequests(email);
    }

    @GetMapping("/getReceivedRequests/{email}")
    public ResponseEntity getReceivedFriendRequests(@PathVariable String email) {
        return friendRequestService.getReceivedFriendRequests(email);
    }

    @GetMapping("/get_friends_requests/{email}")
    public ResponseEntity getFriendsRequests(@PathVariable String email) {
        return friendRequestService.getUsersFriendRequests(email);
    }


    @GetMapping("/addingFriend/{email}")
    public ResponseEntity addingFriend(@PathVariable String email, @RequestParam String requestedEmail) {
        return friendRequestService.addingFriend(email, requestedEmail);
    }

    @PostMapping("/addingFriend/accepted/{email}")
    public ResponseEntity acceptedFriending(@PathVariable String email, @RequestParam String sentEmail) {
        return friendRequestService.acceptedFriending(email, sentEmail);
    }

    @PostMapping("/addingFriend/rejected/{email}")
    public ResponseEntity rejectedFriending(@PathVariable String email, @RequestParam String sentEmail) {
        return friendRequestService.rejectedFriending(email, sentEmail);
    }
}
