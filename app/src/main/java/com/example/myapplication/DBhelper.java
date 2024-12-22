package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE="myDB";
    private static final String TABLE_NAME="TODO";


    //columns names
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DBhelper(@Nullable  Context context) {
        super(context,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT,"
                +STARTED+ " TEXT,"
                +FINISHED+" TEXT" +
                ");";
        db.execSQL(TABLE_CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        String DROP_TABLE_QUERY="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(DROP_TABLE_QUERY);
        db.close();
    }

    //save data in the database
    public Boolean save(Todo todo){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(TITLE,todo.getTitle());
        contentValues.put(DESCRIPTION,todo.getDescription());
        contentValues.put(STARTED,todo.getStarted());
        contentValues.put(FINISHED,todo.getFinished());

       long result=db.insert(TABLE_NAME,null,contentValues);
       if (result==-1){
           return false;
       }
        db.close();
        return true;
    }

    //count number of Todos
    public int Count(){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;

        Cursor cursor=db.rawQuery(query,null);
        return  cursor.getCount();
    }

    //Read database
    public List<Todo> read(){
        SQLiteDatabase db=getReadableDatabase();
        List<Todo> TodoList=new ArrayList<>();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Todo todo=new Todo();
                todo.setId(cursor.getInt(0));
                todo.setTitle(cursor.getString(1));
                todo.setDescription(cursor.getString(2));
                todo.setStarted(cursor.getLong(3));
                todo.setFinished(cursor.getLong(4));

                TodoList.add(todo);
            }while (cursor.moveToNext());
        }
        return TodoList;
    }

    //get single todo
    public Todo getsingle(int id){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION,STARTED, FINISHED},
                ID + "= ?",new String[]{String.valueOf(id)}
                ,null,null,null);

        Todo toDo;
        if(cursor != null){
            cursor.moveToFirst();
            toDo = new Todo(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );
            return toDo;
        }
        return null;
    }

    //Update Todo
    public int update(Todo todo){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE,todo.getTitle());
        contentValues.put(DESCRIPTION, todo.getDescription());
        contentValues.put(STARTED,todo.getStarted());
        contentValues.put(FINISHED,todo.getFinished());

        int status = db.update(TABLE_NAME,contentValues,ID +" =?",
                new String[]{String.valueOf(todo.getId())});

        db.close();
        return status;
    }

    //Delete Todo
    public Boolean delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        long result=db.delete(TABLE_NAME,"id =?",new String[]{String.valueOf(id)});
        if(result==-1){
            return false;
        }
        db.close();
        return true;
    }
}
