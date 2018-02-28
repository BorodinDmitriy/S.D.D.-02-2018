package com.colorit.backend.views;

import com.colorit.backend.entities.UserEntity;

/**
 * An abstract class that represents output view.
 *
 * @author hustonMavr
 * @version 1.0
 */
@SuppressWarnings("ALL")
public class UserView {
    private String nickname;
    private String email;
    private Double rating;

    public UserView() {

    }

    public UserView(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        this.email = userEntity.getEmail();
        if (userEntity.getCountGames() != 0 && userEntity.getCountWins() != null && userEntity.getCountGames() != null) {
            this.rating = userEntity.getCountWins().doubleValue() / userEntity.getCountGames().doubleValue();
        } else {
            this.rating = 0.0;
        }
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}