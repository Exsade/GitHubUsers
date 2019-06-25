package ru.myapp.githubusers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

class SQLiteHelper extends SQLiteOpenHelper {

    public static final String CONTENT_AUTHORITY = "ru.test.github.users";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String DATABASE_NAME = "ru.sample.database.db";

    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsersTable.Requests.CREATION_REQUEST);
        db.execSQL(UserDetailsTable.Requests.CREATION_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsersTable.Requests.DROP_REQUEST);
        db.execSQL(UserDetailsTable.Requests.DROP_REQUEST);
        onCreate(db);
    }
}
