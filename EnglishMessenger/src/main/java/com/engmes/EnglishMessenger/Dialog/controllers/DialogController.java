package com.engmes.EnglishMessenger.Dialog.controllers;


import com.engmes.EnglishMessenger.Dialog.services.DialogService;
import com.engmes.EnglishMessenger.Profile.model.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dialog")
@AllArgsConstructor
public class DialogController {
    private static final Logger logger = LoggerFactory.getLogger(DialogController.class);

    @Autowired
    private final DialogService dialogService;

    @PostMapping("/generateDialog/{email}")
    public User generateDialog(@PathVariable String email) {
        return dialogService.generateDialog(email);
    }
}
