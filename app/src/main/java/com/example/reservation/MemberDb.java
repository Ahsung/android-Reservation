package com.example.reservation;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MemberDb extends SQLiteOpenHelper {
    Context context;
    public MemberDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.context = context;
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE MEMBER_TABLE ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, NUM TEXT)");
        Toast.makeText(context, "생성됌", Toast.LENGTH_SHORT).show();
    }
    //"create table if not exists DailyDiary (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+" user TEXT not null, contents TEXT not null);"
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        // db.execSQL("DROP TABLE IF EXISTS person" + MyDbConstru.CreateDB._CREATE0);
        //onCreate(db);
        Toast.makeText(context, "버젼업!", Toast.LENGTH_SHORT).show();
    }
    public void testmDB(){
        SQLiteDatabase db = getReadableDatabase();
        Toast.makeText(context, "테스트", Toast.LENGTH_SHORT).show();
    }
    public void addMember(Member member){
        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO MEMBER_TABLE ( ");    ///DB 테이블 이름이 가변이 아니고 고정인데 오류 뜨면 확인해
        sb.append(" TITLE, NUM ) ");
        sb.append(" VALUES ( ?, ? ) ");  //// ㅇ요요거랑
        db.execSQL(sb.toString(),new Object[]{
                member.getTitle(),member.getPhoneNum()
        });
        Toast.makeText(context, "서비스 등록!", Toast.LENGTH_SHORT).show();
    }
    public void deleteMember(String str){
        String sb = String.format("DELETE FROM MEMBER_TABLE WHERE TITLE = '%s'",str);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sb);
        db.close();
        Toast.makeText(context, "서비스에서 "+str+"_삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }
    public List getAllMemberData(){
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, TITLE, NUM FROM MEMBER_TABLE");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(),null);
        List<Member> members = new ArrayList();
        Member member  = null;
        while(cursor.moveToNext()){
            member = new Member();
            member.set_id(cursor.getInt(0));
            member.setTitle(cursor.getString(1));
            member.setPhoneNum(cursor.getString(2));
            members.add(member);
        }
        return members;
    }
    public void visibleList(ListView listView, Context context){
        // listView.setVisibility(View.VISIBLE);
        List<Member> members = this.getAllMemberData();
        listView.setAdapter(new MemberListAdapter(members, context));
    }
}
