package ru.myapp.githubusers.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.myapp.githubusers.POJO.UserDetails;
import ru.myapp.githubusers.R;
import ru.myapp.githubusers.database.UserDetailsTable;
import ru.myapp.githubusers.loaders.UserDetailsLoader;

public class UsersDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.avatarImg)
    ImageView avatarImg;
    @BindView(R.id.name)
    TextView nameTv;
    @BindView(R.id.login)
    TextView loginTv;
    @BindView(R.id.location)
    TextView locationTv;
    @BindView(R.id.followers)
    TextView followersTv;
    @BindView(R.id.following)
    TextView followingTv;
    @BindView(R.id.createdAt)
    TextView createdAtTv;
    @BindView(R.id.bio)
    TextView bioTv;

    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorTranslucent));
        }

        setContentView(R.layout.activity_users_details);

        ButterKnife.bind(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        login = intent.getStringExtra("login");

        getSupportLoaderManager().initLoader(R.id.user_details_loader, null, this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
        if(id == R.id.user_details_loader)
            return new UserDetailsLoader(this, login);
         else return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        int id = loader.getId();

        if (data == null || !data.moveToFirst()) {
            getSupportLoaderManager().destroyLoader(id);
            return;
        }

        if(id == R.id.user_details_loader) {
            UserDetails userDetails = UserDetailsTable.fromCursor(data);
            initUI(userDetails);
        }
        getSupportLoaderManager().destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private void initUI(UserDetails userDetails){
        nameTv.setText(userDetails.getName());
        loginTv.setText(userDetails.getLogin());
        locationTv.setText(userDetails.getLocation());
        followersTv.setText(String.valueOf(userDetails.getFollowers()));
        followingTv.setText(String.valueOf(userDetails.getFollowing()));
        createdAtTv.setText(userDetails.getCreatedAt());
        bioTv.setText(userDetails.getBio());

        Picasso.get()
                .load(userDetails.getAvatarUrl())
                .into(avatarImg);
    }
}
