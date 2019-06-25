package ru.myapp.githubusers.POJO;

import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("id")
    int id;

    @SerializedName("login")
    String login;

    @SerializedName("name")
    String name;

    @SerializedName("location")
    String location;

    @SerializedName("bio")
    String bio;

    @SerializedName("followers")
    int followers;

    @SerializedName("following")
    int following;

    @SerializedName("created_at")
    String createdAt;

    @SerializedName("avatar_url")
    String avatarUrl;

    public UserDetails(int id, String login, String name, String location, String bio, int followers, int following, String createdAt, String avatarUrl) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.location = location;
        this.bio = bio;
        this.followers = followers;
        this.following = following;
        this.createdAt = createdAt;
        this.avatarUrl = avatarUrl;
    }

    public UserDetails() {
    }

    public int getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
