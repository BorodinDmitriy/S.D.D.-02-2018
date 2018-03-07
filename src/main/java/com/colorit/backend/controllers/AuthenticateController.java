package com.colorit.backend.controllers;

import com.colorit.backend.common.AuthenticateResponseMaker;
import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.views.output.ResponseView;
import com.colorit.backend.views.input.SignInView;
import com.colorit.backend.views.input.SignUpView;
import com.colorit.backend.views.ViewStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
public class AuthenticateController extends AbstractController {
    public AuthenticateController(@NotNull IUserService userService,
                                  @NotNull AuthenticateResponseMaker authenticateResponseMaker) {
        super(userService, authenticateResponseMaker);
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signInUser(@RequestBody SignInView signInView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signInView.checkValid();
        if (check.isNotValid()) {
            return getResponseMaker().makeResponse(check, locale);
        }

        final UserEntity userEntity = signInView.toEntity();
        final UserServiceResponse userServiceResponse = getUserService().authenticateUser(userEntity);
        if (userServiceResponse.isValid()) {
            httpSession.setAttribute(getSessionKey(), userEntity.getNickname());
        }
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }

    @RequestMapping(value = "/signout", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signOut(HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        httpSession.invalidate();
        return ResponseEntity.ok(new ResponseView());
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signUpUser(@RequestBody SignUpView signUpView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signUpView.checkValid();
        if (check.isNotValid()) {
            return getResponseMaker().makeResponse(check, locale);
        }

        final UserEntity userEntity = signUpView.toEntity();
        final UserServiceResponse userServiceResponse = getUserService().createUser(userEntity);
        if (userServiceResponse.isValid()) {
            httpSession.setAttribute(getSessionKey(), userEntity.getNickname());
        }
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }
}
