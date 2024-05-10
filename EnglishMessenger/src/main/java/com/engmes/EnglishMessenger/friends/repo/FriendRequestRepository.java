package com.engmes.EnglishMessenger.friends.repo;
import com.engmes.EnglishMessenger.friends.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);

    void deleteBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);

    List<FriendRequest> findBySenderEmail(String senderEmail);

    List<FriendRequest> findByReceiverEmail(String receiverEmail);
}

