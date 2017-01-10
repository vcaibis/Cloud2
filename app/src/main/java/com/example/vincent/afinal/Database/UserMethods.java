package com.example.vincent.afinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.vincent.afinal.Objects.User;
import com.example.vincent.afinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 03.11.2016.
 */

public class UserMethods {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private Context context;
    private String allColumns[] = {DBHelper.COLUMN_USER_ID,DBHelper.COLUMN_USERNAME,DBHelper.COLUMN_PASSWORD};

    public UserMethods(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
        open();
    }
    //Ouvre la base de donnée
    public void open(){
        database = dbHelper.getWritableDatabase();
    }

    //Ferme la base de donnée
    public void close(){
        dbHelper.close();
    }

    public void addUser(User user){
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_USERNAME, user.getUserName());
        values.put(dbHelper.COLUMN_PASSWORD, user.getPassword());
        database.insert(dbHelper.TABLE_USERS,null,values);
        database.close();
    }

    //Créer une liste d'utilisateur et parcours toute la table pour les ajouter à la liste et enfin retourne la liste
    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TABLE_USERS, allColumns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToUser(cursor);
                listUsers.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listUsers;
    }



    public  void deleteAllUsers(){
        database.delete(DBHelper.TABLE_USERS,null,null);
        database.close();
    }

    //Methode qui parcours toute la table et compare toutes les paires de valeur utilisateur/mot de passe pour voir s'il y a une correspondance
    public boolean validateUser (String username, String password){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + dbHelper.TABLE_USERS + " WHERE " + dbHelper.COLUMN_USERNAME
                + "='" + username + "' AND " + dbHelper.COLUMN_PASSWORD + "='" + password + "'",null);
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    //Methode qui parcours toute la table et compte le nombre le nombre de username similaire et retourne true si ce nombre est supérieur à 0
    public boolean userExists(String username){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + dbHelper.TABLE_USERS + " WHERE " + dbHelper.COLUMN_USERNAME
                + "='" + username + "'",null);
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setUserName(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        return user;
    }

    //Parcours toutes les lignes de la table User
    public Cursor getAllRows(){
        Cursor cursor = database.query(dbHelper.TABLE_USERS,allColumns,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToNext();
        }
        return cursor;
    }

    //Se positionne sur une ligne précise
    public Cursor getRow(long rowId){
        String where = DBHelper.COLUMN_USER_ID + "=" + rowId;
        Cursor cursor = database.query(true,DBHelper.TABLE_USERS,allColumns,where,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Met à jour une ligne précise dont on précise l'ID
    public boolean updateRow(long rowId, String username, String password){
        String where = DBHelper.COLUMN_USER_ID + "=" + rowId;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_USERNAME,username);
        contentValues.put(DBHelper.COLUMN_PASSWORD,password);
        return database.update(DBHelper.TABLE_USERS,contentValues,where,null) !=0;
    }
}
