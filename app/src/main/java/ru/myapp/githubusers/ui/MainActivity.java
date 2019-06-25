package ru.myapp.githubusers.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.myapp.githubusers.POJO.User;
import ru.myapp.githubusers.R;
import ru.myapp.githubusers.database.UsersTable;
import ru.myapp.githubusers.loaders.UsersLoader;
import ru.myapp.githubusers.ui.recyclerView.PaginationScrollListener;
import ru.myapp.githubusers.ui.recyclerView.PostRecyclerAdapter;
import ru.myapp.githubusers.ui.recyclerView.RecyclerItemClickListener;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.preLoadingProgressBar)
    ProgressBar preLoadingProgressBar;
    @BindView(R.id.noInternetConnectionView)
    TextView noInternetConnectionView;

    private PostRecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLoading = false;
    private int since;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
            getWindow().setEnterTransition(new Fade());
        }
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("GitHub Users");

        checkInternetConnection();

        fetchNextUsersListData();

        swipeRefresh.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PostRecyclerAdapter(new ArrayList<User>());
        mRecyclerView.setAdapter(mAdapter);
        setOnClickListnerRV();
        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                fetchNextUsersListData();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
        if(id == R.id.users_loader)
            return new UsersLoader(this, since, 20);
        else return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();

        if (data == null || !data.moveToFirst()) {
            getLoaderManager().destroyLoader(id);
            return;
        }
        if(id == R.id.users_loader) {
                List<User> users = UsersTable.listFromCursor(data);
                updateSinceParam(users);
                preparedListItem(users);
        }
        getSupportLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    private void preparedListItem(final List<User> users) {
        final ArrayList<User> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                items.addAll(users);
                if (currentPage != PAGE_START) mAdapter.removeLoading();
                mAdapter.addAll(items);
                swipeRefresh.setRefreshing(false);
                mAdapter.addLoading();
                isLoading = false;
                preLoadingProgressBar.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }

    @Override
    public void onRefresh() {
        currentPage = PAGE_START;
        since = 0;
        mAdapter.clear();
        fetchNextUsersListData();
    }


    private void updateSinceParam(List<User> users) {
        since = users.get(users.size()-1).getId();
    }

    public void fetchNextUsersListData() {
        getSupportLoaderManager().restartLoader(R.id.users_loader, null, this);
    }

    public void setOnClickListnerRV() {
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        TextView tv = (TextView) view.findViewById(R.id.textViewLogin);
                        openUsersDetails(tv.getText().toString().toLowerCase());
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected())
            fetchNextUsersListData();
        else
            noInternetConnection();
    }

    private void noInternetConnection() {
        noInternetConnectionView.setText(R.string.no_internet);
        preLoadingProgressBar.setVisibility(View.INVISIBLE);
        noInternetConnectionView.setVisibility(View.VISIBLE);
    }

    private void openUsersDetails(String login){
        Intent intent = new Intent(this, UsersDetailsActivity.class);
        intent.putExtra("login", login);

        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }
}
