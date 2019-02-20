package com.example.reservation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyDbHelper extends SQLiteOpenHelper {
    Context context;
    boolean existedTable;
    String myName;
    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.context = context;
        this.myName = name;
        existedTable = true;
    }
   /* public void deleteTable(SQLiteDatabase db){
        db =getWritableDatabase();
        db.execSQL("DROP TABLE RESERV_TABLE;");
       // onCreate(db);
    }
    public void createTable(SQLiteDatabase db){
        db=getWritableDatabase();
        db.execSQL(MyDbConstru.CreateDB._CREATE0);
    }*/
    public  void existedDB(String s){
        if(existedTable){
            Toast.makeText(context, "기존 목록_" +s+"_TABLE 접근.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "새로운 예약목록_"+s+"_생성", Toast.LENGTH_SHORT).show();
        }
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(MyDbConstru.CreateDB._CREATE0);
        existedTable = false;
    }
    //"create table if not exists DailyDiary (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+" user TEXT not null, contents TEXT not null);"
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        // db.execSQL("DROP TABLE IF EXISTS person" + MyDbConstru.CreateDB._CREATE0);
        //onCreate(db);
        Toast.makeText(context, "버젼업!", Toast.LENGTH_SHORT).show();
    }
    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }
    public void addReser(Reser reser){
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO RESERV_TABLE ( ");    ///DB 테이블 이름이 가변이 아니고 고정인데 오류 뜨면 확인해
        sb.append(" TITLE, NAME, FTIME, LTIME, CONTENTS, RES ) ");
        sb.append(" VALUES ( ?, ?, ?, ?, ?, ?) ");  //// ㅇ요요거랑
        db.execSQL(sb.toString(),new Object[]{
                reser.getTilte(),reser.getName(),reser.getfTime(),reser.getlTime(),reser.getContents(),reser.getRes()
        });
        Toast.makeText(context, "입력 완료!", Toast.LENGTH_SHORT).show();
    }
    public void updateDB(int _id,String str){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME",str);
        values.put("RES",1);
        StringBuffer tb = new StringBuffer();
        tb.append(" SELECT _ID, TITLE, NAME, FTIME, LTIME, CONTENTS, RES FROM RESERV_TABLE");
        Cursor cursor = db.rawQuery(tb.toString(),null);
        cursor.moveToPosition(_id);
        int num = cursor.getInt(0);
        db.update("RESERV_TABLE",values,"_ID ="+num,null);
        //db.execSQL(s);
        db.close();
    }
    public boolean resClose(int pos){
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer tb = new StringBuffer();
        tb.append(" SELECT _ID, TITLE, NAME, FTIME, LTIME, CONTENTS, RES FROM RESERV_TABLE");
        Cursor cursor = db.rawQuery(tb.toString(),null);
        cursor.moveToPosition(pos);
        int num = cursor.getInt(6);
        db.close();
        if(num == 0) return false;
        else return true;
    }
    public void deleteReser(int _id){
        String sb = String.format("DELETE FROM RESERV_TABLE WHERE _ID = '%d'",_id);
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer tb = new StringBuffer();
        tb.append(" SELECT _ID, TITLE, NAME, FTIME, LTIME, CONTENTS FROM RESERV_TABLE");
        Cursor cursor = db.rawQuery(tb.toString(),null);
        cursor.moveToFirst();
        int a;
        while(true) {
            a=cursor.getInt(0);
            if(a == _id) {
                db.execSQL(sb);
                Toast.makeText(context, _id + "번 항목이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            }
            else if(a< _id){if(!cursor.moveToNext()){Toast.makeText(context, _id + "번 항목이 없습니다.", Toast.LENGTH_SHORT).show(); break;}}
            else{
                Toast.makeText(context, _id + "번 항목이 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        db.close();
    }
    public void allDeleteReser(){
        String sb = String.format("DELETE FROM RESERV_TABLE ");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sb);
    }
    public List getAllReserData(){
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, TITLE, NAME, FTIME, LTIME, CONTENTS, RES FROM RESERV_TABLE");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(),null);
        List resers = new ArrayList();
        Reser reser  = null;
        while(cursor.moveToNext()){
            reser = new Reser();
            reser.set_id(cursor.getInt(0));
            reser.setTilte(cursor.getString(1));
            reser.setName(cursor.getString(2));
            reser.setfTime(cursor.getString(3));
            reser.setlTime(cursor.getString(4));
            reser.setContents(cursor.getString(5));
            reser.setRes(cursor.getInt(6));
            resers.add(reser);
        }
        return resers;
    }
    public void visibleList(ListView listView,Context context){
       // listView.setVisibility(View.VISIBLE);
        List resers = this.getAllReserData();
        listView.setAdapter(new ReserListAdapter(resers, context,this,listView));
    }
}

