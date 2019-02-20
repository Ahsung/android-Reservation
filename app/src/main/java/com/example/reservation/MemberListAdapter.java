package com.example.reservation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MemberListAdapter extends BaseAdapter {
    private List<Member> members;
    private Context context;
    public MemberListAdapter(List<Member> m, Context context){
        this.context = context;
        this.members = m;
    }
    public void setMembers(List r){
        //this.resers.clear();
        this.members=r;
        //notifyDataSetChanged();
    }
    public void clearMembers() {
        this.members.clear();
    }
    @Override
    public int getCount() {
        return this.members.size();
    }

    @Override
    public Object getItem(int position) {
        return this.members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Mholder mholder = null;
        if(convertView == null) {
            convertView = new LinearLayout(context);
            LinearLayout tempL = new LinearLayout(context);
            tempL.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout tempL3 = new LinearLayout(context);
            tempL3.setOrientation(LinearLayout.HORIZONTAL);
            ((LinearLayout) convertView).setOrientation(LinearLayout.VERTICAL);

            TextView tvId = new TextView(context);
            //tvId.setPadding(10, 0, 0, 0);
            tvId.setTextColor(Color.BLACK);
            tvId.setTextSize(15);
            tvId.setWidth(size.x/5);
            tvId.setTypeface(Typeface.DEFAULT_BOLD);
            TextView tvTitle = new TextView(context);
            //tvTitle.setPadding(10, 0, 5, 0);
            tvTitle.setTextColor(Color.BLACK);
            tvTitle.setTextSize(20);
            tvTitle.setWidth(size.x/2);
            tvTitle.setHeight(size.y/15);
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            TextView tvName = new TextView(context);
            tvName.setTextColor(Color.BLACK);
            tvName.setTextSize(15);
            tvName.setWidth(size.x/2);
            tvName.setHeight(size.y/17);
            tvName.setTypeface(Typeface.DEFAULT_BOLD);
            tvName.setGravity(Gravity.CENTER);
            tempL.addView(tvId);
            tempL.addView(tvTitle);
            tempL3.addView(tvName);
            LinearLayout.LayoutParams p1Control = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            p1Control.rightMargin = 100;
            tvId.setLayoutParams(p1Control);
            tempL3.setGravity(Gravity.CENTER);
            ((LinearLayout)convertView).addView(tempL);
            ((LinearLayout)convertView).addView(tempL3);

            mholder = new Mholder();
            mholder.viewId = tvId;
            mholder.viewTitle = tvTitle;
            mholder.viewName = tvName;

            convertView.setTag(mholder);
        }else{
            mholder = (Mholder)convertView.getTag();
        }
        Member member = (Member) getItem(position);
        mholder.viewId.setText("No."+member.get_id()+"");
        mholder.viewTitle.setText("서비스명: "+member.getTitle());
        mholder.viewName.setText("번호: "+member.getPhoneNum());
        return convertView;
    }

    public class Mholder {
        public TextView viewId;
        public TextView viewTitle;
        public TextView viewName;
    }
}
