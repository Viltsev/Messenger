package com.engmes.EnglishMessenger.Dialog.services;
import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Profile.model.User;
import org.springframework.http.ResponseEntity;

public interface DialogService {
    User generateDialog(String email);

    ResponseEntity generateDialogTopic(String email);
}
