package ru.myapp.githubusers.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ru.myapp.githubusers.POJO.User;
import ru.myapp.githubusers.POJO.UserDetails;

public interface GitHubApi {
    @GET("/users")
    Call<List<User>> getUsers(@Query("since") int since, @Query("per_page") int perPageCount);

    @GET("/users/{login}")
    Call<UserDetails> getUserByLogin(@Path("login") String login);
}
