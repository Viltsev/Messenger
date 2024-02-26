package com.engmes.EnglishMessenger.Dialog.services;
import com.engmes.EnglishMessenger.Profile.model.User;

public interface DialogService {
    User generateDialog(String email);
}
