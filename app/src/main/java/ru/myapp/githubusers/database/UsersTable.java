package ru.myapp.githubusers.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.myapp.githubusers.POJO.User;

public class UsersTable {
    public static final Uri URI = SQLiteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull User user) {
        context.getContentResolver().insert(URI, toContentValues(user));
    }

    public static void save(Context context, @NonNull List<User> users) {
        ContentValues[] values = new ContentValues[users.size()];
        for (int i = 0; i < users.size(); i++) {
            values[i] = toContentValues(users.get(i));
        }
        context.getContentResolver().bulkInsert(URI, values);
    }

    @NonNull
    public static ContentValues toContentValues(@NonNull User user) {
        ContentValues values = new ContentValues();
        values.put(Columns.ID, user.getId());
        values.put(Columns.LOGIN, user.getLogin());
        values.put(Columns.AVATAR_URL, user.getAvatarUrl());
        return values;
    }

    @NonNull
    public static User fromCursor(@NonNull Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(Columns.ID));
        String login = cursor.getString(cursor.getColumnIndex(Columns.LOGIN));
        String avatarUrl = cursor.getString(cursor.getColumnIndex(Columns.AVATAR_URL));
        return new User(login, avatarUrl, id);
    }

    @NonNull
    public static List<User> listFromCursor(@NonNull Cursor cursor) {
        List<User> users = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return users;
        }
        try {
            do {
                users.add(fromCursor(cursor));
            } while (cursor.moveToNext());
            return users;
        } finally {
            cursor.close();
        }
    }

    public static void clear(Context context) {
        context.getContentResolver().delete(URI, null, null);
    }

    public interface Columns {
        String ID = "id";
        String LOGIN = "login";
        String AVATAR_URL = "avatar_url";
    }

    public interface Requests {

        String TABLE_NAME = UsersTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ID + " INT, " +
                Columns.LOGIN + " VARCHAR(200), " +
                Columns.AVATAR_URL + " VARCHAR(200)" + ");";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
