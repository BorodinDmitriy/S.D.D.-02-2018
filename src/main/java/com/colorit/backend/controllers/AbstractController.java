package com.colorit.backend.controllers;

import com.colorit.backend.common.AbstractResponseMaker;
import com.colorit.backend.services.UserService;
import com.colorit.backend.views.ResponseView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
public class AbstractController {
    @SuppressWarnings("CheckStyle")
    @NotNull
    protected final MessageSource messageSource;
    @SuppressWarnings("CheckStyle")
    @NotNull
    protected final UserService userService;
    @SuppressWarnings("CheckStyle")
    protected AbstractResponseMaker responseMaker;
    protected static final String SESSION_KEY = "nickname";

    protected ResponseEntity<ResponseView> unauthorizedResponse(Locale locale) {
        final ResponseView responseView = new ResponseView();
        responseView.addError("general", messageSource.getMessage("unauthorized", null, locale));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseView);
    }

    public AbstractController(@NotNull MessageSource messageSource, @NotNull UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }
}