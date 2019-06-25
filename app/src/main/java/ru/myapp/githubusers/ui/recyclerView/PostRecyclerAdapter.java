package ru.myapp.githubusers.ui.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.myapp.githubusers.POJO.User;
import ru.myapp.githubusers.POJO.UserDetails;
import ru.myapp.githubusers.R;
import ru.myapp.githubusers.utils.CircularTransformation;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private List<User> users;

    public PostRecyclerAdapter(List<User> users) {
        this.users = users;
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == users.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void add(User response) {
        users.add(response);
        notifyItemInserted(users.size() - 1);
    }

    public void addAll(List<User> users) {
        for (User response : users) {
            add(response);
        }
    }


    private void remove(User userToDel) {
        int position = users.indexOf(userToDel);
        if (position > -1) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new User());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = users.size() - 1;
        User item = getItem(position);
        if (item != null) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private User getItem(int position) {
        return users.get(position);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.textViewLogin)
        TextView textViewLogin;
        @BindView(R.id.avatarImg)
        ImageView avatarImg;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            User item = users.get(position);

            textViewLogin.setText(firstUpperCase(item.getLogin()));

            Picasso.get()
                    .load(item.getAvatarUrl())
                    .transform(new CircularTransformation(10))
                    .into(avatarImg);
        }
    }

    public class FooterHolder extends BaseViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;

        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }
    }

    public String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return ""; //или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
