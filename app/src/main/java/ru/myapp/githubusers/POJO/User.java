package ru.myapp.githubusers.POJO;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    int id;

    @SerializedName("login")
    String login;

    @SerializedName("avatar_url")
    String avatarUrl;

    public User() {
    }

    public User(String login, String avatarUrl, int id) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
