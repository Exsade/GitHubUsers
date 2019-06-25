package ru.myapp.githubusers.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.myapp.githubusers.POJO.UserDetails;

public class UserDetailsTable {
    public static final Uri URI = SQLiteHelper.BASE_CONTENT_URI.buildUpon().appendPath(Requests.TABLE_NAME).build();

    public static void save(Context context, @NonNull UserDetails userDetails){
        context.getContentResolver().insert(URI, toContentValues(userDetails));
    }

    public static UserDetails fromCursor(Cursor cursor){
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(Columns.ID));
        String login = cursor.getString(cursor.getColumnIndex(Columns.LOGIN));
        String name = cursor.getString(cursor.getColumnIndex(Columns.NAME));
        String location = cursor.getString(cursor.getColumnIndex(Columns.LOCATION));
        String bio = cursor.getString(cursor.getColumnIndex(Columns.BIO));
        int followers = cursor.getInt(cursor.getColumnIndex(Columns.FOLLOWERS));
        int folowing = cursor.getInt(cursor.getColumnIndex(Columns.FOLOWING));
        String creataedAt = cursor.getString(cursor.getColumnIndex(Columns.CREATED_AT));
        String avatarUrl = cursor.getString(cursor.getColumnIndex(Columns.AVATAR_URL));

        return new UserDetails(id,login,name,location,bio,followers,folowing,creataedAt,avatarUrl);
    }

    public static void clear(Context context){
        context.getContentResolver().delete(URI, null, null);
    }

    private static ContentValues toContentValues(@NonNull UserDetails userDetails) {
        ContentValues values = new ContentValues();
        values.put(Columns.ID, userDetails.getId());
        values.put(Columns.LOGIN, userDetails.getLogin());
        values.put(Columns.NAME, userDetails.getName());
        values.put(Columns.LOCATION, userDetails.getLocation());
        values.put(Columns.BIO, userDetails.getBio());
        values.put(Columns.FOLLOWERS, userDetails.getFollowers());
        values.put(Columns.FOLOWING, userDetails.getFollowing());
        values.put(Columns.CREATED_AT, userDetails.getCreatedAt());
        values.put(Columns.AVATAR_URL, userDetails.getAvatarUrl());
        return values;
    }


    public interface Columns {
        String ID = "id";
        String LOGIN = "login";
        String NAME = "name";
        String LOCATION = "location";
        String BIO = "bio";
        String FOLLOWERS = "followers";
        String FOLOWING = "following";
        String CREATED_AT = "created_at";
        String AVATAR_URL = "avatar_url";
    }

    public interface Requests {
        String TABLE_NAME = UserDetailsTable.class.getSimpleName();

        String CREATION_REQUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                Columns.ID + " INT, " +
                Columns.LOGIN + " VARCHAR(200), " +
                Columns.NAME + " VARCHAR(200), " +
                Columns.LOCATION + " VARCHAR(200), " +
                Columns.BIO + " VARCHAR(200), " +
                Columns.FOLLOWERS + " INT, " +
                Columns.FOLOWING + " INT, " +
                Columns.CREATED_AT + " VARCHAR(200), " +
                Columns.AVATAR_URL + " VARCHAR(200)" + ");";

        String DROP_REQUEST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
