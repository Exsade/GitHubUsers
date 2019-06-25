package ru.myapp.githubusers.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.io.IOException;

import retrofit2.Call;
import ru.myapp.githubusers.POJO.UserDetails;
import ru.myapp.githubusers.api.ApiFactory;
import ru.myapp.githubusers.api.GitHubApi;
import ru.myapp.githubusers.database.UserDetailsTable;

public class UserDetailsLoader extends BaseLoader {

    String login;

    public UserDetailsLoader(Context context, @NonNull String login) {
        super(context);
        this.login = login;
    }

    @Override
    Cursor apiCall() throws IOException {
        GitHubApi gitHubApi = ApiFactory.getGitHubApi();
        Call<UserDetails> call = gitHubApi.getUserByLogin(login);
        UserDetails userDetails = call.execute().body();
        UserDetailsTable.clear(getContext());
        UserDetailsTable.save(getContext(), userDetails);
        return getContext().getContentResolver().query(UserDetailsTable.URI, null, null, null, null);
    }
}
