package ru.myapp.githubusers.loaders;

import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import ru.myapp.githubusers.POJO.User;
import ru.myapp.githubusers.api.ApiFactory;
import ru.myapp.githubusers.api.GitHubApi;
import ru.myapp.githubusers.database.UsersTable;

public class UsersLoader extends BaseLoader {

    int since, perPageCount;

    public UsersLoader(Context context, int since, int perPageCount) {
        super(context);
        this.since = since;
        this.perPageCount = perPageCount;
    }

    @Override
    Cursor apiCall() throws IOException {
        GitHubApi gitHubApi = ApiFactory.getGitHubApi();
        Call<List<User>> call = gitHubApi.getUsers(since, perPageCount);
        List<User> users = call.execute().body();
        UsersTable.clear(getContext());
        UsersTable.save(getContext(), users);
        return getContext().getContentResolver().query(UsersTable.URI, null, null, null, null);
    }

}
