package com.example.reservation;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
   // private static final String DATABASE_NAME = "InnerDatabase(SQLIte).db";
    private static final int DATABASE_VERSION = 1;
    public  static SQLiteDatabase mDB;
    private MyDbHelper m_helper;
    private Context mContext;
    private MemberDb memberDb;
   // ArrayAdapter<String> arrayAdapter;
    Button DBbutton;
    Button dataButton;
    Button loadButton;
    Button delete_Button;
    ListView conList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mContext = SettingActivity.this;//context 따라가서 검사해보고,...
        conList = (ListView)findViewById(R.id.reservList);
        conList.setVisibility(View.VISIBLE);
        DBbutton = (Button)findViewById(R.id.buttonBe);
        memberDb = new MemberDb(mContext,"MEMBER",null,1);
        DBbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final EditText editText = new EditText(mContext);
                editText.setHint("예약목록 입력");
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("예약목록 생성 or 접근")
                        .setMessage("목록 이름을 입력하세요")
                        .setView(editText)
                        .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().length()>0){
                                    m_helper = new MyDbHelper(mContext,editText.getText().toString(),null,1);
                                    m_helper.testDB();
                                    m_helper.existedDB(editText.getText().toString());
                                    m_helper.visibleList(conList,mContext);
                                    if(!m_helper.existedTable){
                                        Member member = new Member();
                                        member.setTitle(editText.getText().toString());
                                        member.setPhoneNum("아직 미정");
                                        memberDb.addMember(member);
                                    }
                                }else Toast.makeText(mContext, "입력한 문자가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });
        dataButton = (Button)findViewById(R.id.buttondataAdd);
        dataButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LinearLayout layout = new LinearLayout(mContext);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText editTitle = new EditText(mContext);
                editTitle.setHint("제목 입력");
                editTitle.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editTitle.setInputType(InputType.TYPE_CLASS_TEXT);
                final EditText editName = new EditText(mContext);
                editName.setHint("예약자 이름 입력");
                editName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editName.setInputType(InputType.TYPE_CLASS_TEXT);
                final EditText editFTime = new EditText(mContext);
                editFTime.setHint("시작 시간 입력 (문자 자유형식)");
                editFTime.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editFTime.setInputType(InputType.TYPE_CLASS_TEXT);
                final EditText editLTime = new EditText(mContext);
                editLTime.setHint("종료 시간 입력 (문자 자유형식)");
                editLTime.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                editLTime.setInputType(InputType.TYPE_CLASS_TEXT);
                final EditText editContents = new EditText(mContext);
                editContents.setHint("내용 입력");

                layout.addView(editTitle);
                layout.addView(editName);
                layout.addView(editFTime);
                layout.addView(editLTime);
                layout.addView(editContents);

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("예약목록 추가 입력")
                        .setView(layout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = editTitle.getText().toString();
                                String name = editName.getText().toString();
                                String fTime = editFTime.getText().toString();
                                String lTime = editLTime.getText().toString();
                                String contents = editContents.getText().toString();
                                if( m_helper == null){
                                    m_helper = new MyDbHelper(mContext,"RESERV",null,1);
                                    m_helper.testDB();
                                    m_helper.existedDB("RESERV");
                                    m_helper.visibleList(conList,mContext);
                                    if(!m_helper.existedTable){
                                        Member member = new Member();
                                        member.setTitle("RESERV");
                                        member.setPhoneNum("아직 미정");
                                        memberDb.addMember(member);
                                    }
                                   // Toast.makeText(mContext, "기본 예약목록_\"RESERV\"_TABLE으로 접근", Toast.LENGTH_SHORT).show();
                                }
                                Reser reser = new Reser();
                                reser.setTilte(title);
                                reser.setName(name);
                                reser.setfTime(fTime);
                                reser.setlTime(lTime);
                                reser.setContents(contents);
                                m_helper.addReser(reser);
                                m_helper.visibleList(conList,mContext);
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });
        delete_Button = (Button)findViewById(R.id.buttonDelete);
        delete_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final EditText editText = new EditText(mContext);
                editText.setHint("삭제할 번호 입력");
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("예약 삭제")
                        .setMessage("삭제할 번호를 입력하세요")
                        .setView(editText)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(m_helper == null){
                                    Toast.makeText(mContext, "선택된 예약목록이 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(editText.getText().toString().length()>0) {
                                        m_helper.close();
                                        m_helper.deleteReser(Integer.parseInt(editText.getText().toString()));
                                        m_helper.visibleList(conList,mContext);
                                    }else
                                        Toast.makeText(SettingActivity.this, "번호를 입력하세요", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("완전 삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(m_helper == null){
                            Toast.makeText(mContext, "선택된 목록이 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            m_helper.allDeleteReser();
                            List resers = m_helper.getAllReserData();
                            SQLiteDatabase db = openOrCreateDatabase(m_helper.myName, Context.MODE_PRIVATE, null);
                            String mpath = "/data/data/" + getPackageName() + "/databases/" + m_helper.myName;
                            File mFile = new File(mpath);
                            db.deleteDatabase(mFile);
                            m_helper.close();
                            conList.setAdapter(new ReserListAdapter(resers,mContext,m_helper,conList));
                            Toast.makeText(SettingActivity.this, "'"+m_helper.myName+"' 목록이 완전히 제거되었습니다.", Toast.LENGTH_SHORT).show();
                            memberDb.deleteMember(m_helper.myName);
                            m_helper=null;
                        }
                    }
                })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
        });
        loadButton = (Button)findViewById(R.id.buttonLoad);
        loadButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(m_helper == null){
                    m_helper = new MyDbHelper(mContext,"RESERV",null,1);
                    m_helper.testDB();
                    m_helper.existedDB("RESERV");
                    m_helper.visibleList(conList,mContext);
                    if(!m_helper.existedTable){
                        Member member = new Member();
                        member.setTitle("RESERV");
                        member.setPhoneNum("아직 미정");
                        memberDb.addMember(member);
                    }
                    //Toast.makeText(mContext, "기본 예약목록_\"RESERV\"_TABLE 로드", Toast.LENGTH_SHORT).show();
                }
                m_helper.visibleList(conList,mContext);
            }
        });

    }
    protected void onDestroy() {
        if(m_helper !=null) {
            m_helper.close();
        }
        memberDb.close();
        super.onDestroy();
    }
}
