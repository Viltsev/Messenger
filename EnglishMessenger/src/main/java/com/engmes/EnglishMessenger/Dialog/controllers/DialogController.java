package com.engmes.EnglishMessenger.Dialog.controllers;


import com.engmes.EnglishMessenger.Dialog.services.DialogService;
import com.engmes.EnglishMessenger.Profile.model.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/generateDialogTopic/{email}")
    public ResponseEntity generateDialogTopic(@PathVariable String email) {
        return dialogService.generateDialogTopic(email);
    }
}
