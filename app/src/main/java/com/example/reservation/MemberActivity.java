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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends AppCompatActivity {
    ListView memberlist;
    MemberDb memberDb;
    Context mContext;
    MemberListAdapter adapter;
    List<Member> members;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        //recyclerView = (RecyclerView)findViewById(R.id.re);
        mContext = MemberActivity.this;
        members = new ArrayList<Member>();
        memberlist = (ListView)findViewById(R.id.memberList);
        memberlist.setVisibility(View.VISIBLE);
        memberDb = new MemberDb(mContext,"MEMBER",null,1);
       // memberDb.testmDB();
        adapter = new MemberListAdapter(memberDb.getAllMemberData(),mContext);
        memberlist.setAdapter(adapter);
    }
    public void buttonDelete(View view){
        final EditText editText = new EditText(mContext);
        editText.setHint("서비스명 입력");
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("이름 입력")
                .setMessage("이름을 입력해주세요")
                .setView(editText)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().length()>0){
                            //m_helper.visibleList(conList,mContext);
                            SQLiteDatabase db = openOrCreateDatabase(editText.getText().toString(), Context.MODE_PRIVATE, null);
                            String mpath = "/data/data/" + getPackageName() + "/databases/"+editText.getText().toString();
                            File mFile = new File(mpath);
                            db.deleteDatabase(mFile);
                            memberDb.deleteMember(editText.getText().toString());
                            MemberActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.clearMembers();
                                    members.clear();
                                    members.addAll(memberDb.getAllMemberData());
                                    adapter.setMembers(members);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            // conList.setAdapter(adapter);
                            //Toast.makeText(mContext, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(mContext, "입력한 문자가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }
}
