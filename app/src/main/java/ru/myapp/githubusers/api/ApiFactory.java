package ru.myapp.githubusers.api;

import android.support.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myapp.githubusers.BuildConfig;

public class ApiFactory {
    @NonNull
    public static GitHubApi getGitHubApi() {
        return getRetrofit().create(GitHubApi.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
