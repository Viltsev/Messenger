package com.engmes.EnglishMessenger.friends.controllers;
import com.engmes.EnglishMessenger.friends.services.FriendRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @GetMapping("/getSentRequests/{email}")
    public ResponseEntity getSentFriendRequests(@PathVariable String email) {
        return friendRequestService.getSentFriendRequests(email);
    }

    @GetMapping("/getReceivedRequests/{email}")
    public ResponseEntity getReceivedFriendRequests(@PathVariable String email) {
        return friendRequestService.getReceivedFriendRequests(email);
    }

    @GetMapping("/addingFriend/{email}")
    public ResponseEntity addingFriend(@PathVariable String email, @RequestBody String requestedEmail) {
        return friendRequestService.addingFriend(email, requestedEmail);
    }

    @PostMapping("/addingFriend/accepted/{email}")
    public ResponseEntity acceptedFriending(@PathVariable String email, @RequestBody String sentEmail) {
        return friendRequestService.acceptedFriending(email, sentEmail);
    }

    @PostMapping("/addingFriend/rejected/{email}")
    public ResponseEntity rejectedFriending(@PathVariable String email, @RequestBody String sentEmail) {
        return friendRequestService.rejectedFriending(email, sentEmail);
    }
}
