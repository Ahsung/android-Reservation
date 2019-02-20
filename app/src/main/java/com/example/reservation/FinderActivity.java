package com.example.reservation;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;

public class FinderActivity extends AppCompatActivity{
   ListView conList;
   MyDbHelper m_helper;
   String searchName;
   Button searchButton;
   EditText searchEdit;
   SQLiteDatabase db;
   Context mContext;
   List<Reser> resers;
   ReserListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        mContext =FinderActivity.this;
        conList = (ListView)findViewById(R.id.reservList2);
        conList.setVisibility(View.VISIBLE);
       // resers = null;
       // m_helper = null;
        searchEdit = (EditText)findViewById(R.id.ed1);
        searchEdit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        visiList();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "기본", Toast.LENGTH_LONG).show();
                        return false;
                }
                return true;
            }
        });

        searchButton = (Button)findViewById(R.id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visiList();
            }
        });
        conList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(m_helper.resClose(position)){
                    Toast.makeText(mContext, "예약이 마감되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                final EditText editText = new EditText(mContext);
                editText.setHint("정보 입력");
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("예약자 입력")
                        .setMessage("이름을 입력해주세요")
                        .setView(editText)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().length()>0){
                                    m_helper.updateDB(position,editText.getText().toString());
                                    //m_helper.visibleList(conList,mContext);
                                    FinderActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.clearResers();
                                            resers.clear();
                                            resers.addAll(m_helper.getAllReserData());
                                            //conList.setVisibility(View.VISIBLE);
                                            adapter.setResers(resers);
                                            adapter.notifyDataSetChanged();
                                            //conList.invalidateViews();
                                        }
                                    });
                                   // conList.setAdapter(adapter);
                                    Toast.makeText(FinderActivity.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
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
        });

    }
    public  void visiList(){
        if (searchEdit.getText().toString().length() != 0) {
            m_helper = new MyDbHelper(mContext, searchEdit.getText().toString(), null, 1);
            m_helper.testDB();
           /* resers = m_helper.getAllReserData();
            adapter=new ReserListAdapter(resers, mContext,m_helper,conList);
            conList.setAdapter(adapter);*/
            if (!m_helper.existedTable) {
                m_helper.close();
                db = openOrCreateDatabase(searchEdit.getText().toString(), Context.MODE_PRIVATE, null);
                String mpath = "/data/data/" + getPackageName() + "/databases/" + searchEdit.getText().toString();
                File mFile = new File(mpath);
                db.deleteDatabase(mFile);
                adapter =null;
                conList.setAdapter(adapter);
                Toast.makeText(mContext, "목록이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            } else {
                if (m_helper == null) {
                    m_helper = new MyDbHelper(mContext, "RESERV", null, 1);
                    Toast.makeText(mContext, "기본 예약목록_\"RESERV\"_TABLE 로드", Toast.LENGTH_SHORT).show();
                }
                resers = m_helper.getAllReserData();
                adapter=new ReserListAdapter(resers, mContext,m_helper,conList);
                conList.setAdapter(adapter);
            }
        }else Toast.makeText(mContext, "입력한 문자가 없습니다.", Toast.LENGTH_SHORT).show();
    }
    protected void onDestroy() {
        if(m_helper !=null) {
            m_helper.close();
        }
        super.onDestroy();
    }
}
