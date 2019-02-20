package com.example.reservation;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class ReserListAdapter extends BaseAdapter {
    private List<Reser> resers;
    private Context context;
    MyDbHelper m_helper;
    ListView conList;
    public ReserListAdapter(List<Reser> m, Context context,MyDbHelper helper,ListView listView){
        this.context = context;
        this.resers = m;
        m_helper = helper;
        conList = listView;
    }
    public void setResers(List r){
        //this.resers.clear();
        this.resers=r;
        //notifyDataSetChanged();
    }
    public void clearResers() {
        this.resers.clear();
    }
    @Override
    public int getCount() {
        return this.resers.size();
    }

    @Override
    public Object getItem(int position) {
        return this.resers.get(position);
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
        Holder holder = null;
        if(convertView == null) {
            convertView = new LinearLayout(context);
            LinearLayout tempL = new LinearLayout(context);
            tempL.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout tempL2 = new LinearLayout(context);
            tempL2.setOrientation(LinearLayout.HORIZONTAL);
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
            tvTitle.setTextSize(15);
            tvTitle.setWidth(size.x/3);
            tvTitle.setHeight(size.y/15);
            tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            TextView tvName = new TextView(context);
            tvName.setTextColor(Color.BLACK);
            tvName.setTextSize(15);
            tvName.setWidth(size.x/3);
            tvName.setHeight(size.y/17);
            tvName.setTypeface(Typeface.DEFAULT_BOLD);
            tvName.setGravity(Gravity.CENTER);
            TextView tvFTime = new TextView(context);
            //tvFTime.setPadding(20, 0, 15, 0);
            tvFTime.setTextColor(Color.BLACK);
            tvFTime.setTextSize(15);
            tvFTime.setWidth(size.x/3);
            tvFTime.setHeight(size.y/17);
            tvFTime.setTypeface(Typeface.DEFAULT_BOLD);
            TextView tvLTime = new TextView(context);
           // tvLTime.setPadding(20, 0, 15, 0);
            tvLTime.setTextColor(Color.BLACK);
            tvLTime.setTextSize(15);
            tvLTime.setWidth(size.x/3);
            tvLTime.setHeight(size.y/17);
            tvLTime.setTypeface(Typeface.DEFAULT_BOLD);
            TextView tvContents = new TextView(context);
            //tvContents.setPadding(20, 0, 20, 0);
            tvContents.setTextColor(Color.BLACK);
            tvContents.setTextSize(15);
            //tvContents.setWidth(size.x);
            TextView tvRes = new TextView(context);
            tvRes.setPadding(30,0,0,0);
            tvRes.setHeight(size.y/40);
            tvRes.setWidth(size.x/15);
            tempL.addView(tvId);
            tempL.addView(tvTitle);
            tempL3.addView(tvName);
            tempL3.addView(tvRes);
            LinearLayout.LayoutParams p1Control = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            p1Control.rightMargin = 200;
            tvName.setLayoutParams(p1Control);
            tempL2.addView(tvFTime);
            tempL2.addView(tvLTime);
            tempL3.setGravity(Gravity.CENTER);
            ((LinearLayout)convertView).addView(tempL);
            ((LinearLayout)convertView).addView(tempL3);
            ((LinearLayout)convertView).addView(tempL2);
            ((LinearLayout) convertView).addView(tvContents);
            /*((LinearLayout) convertView).addView(tvId);
            ((LinearLayout) convertView).addView(tvTitle);
            ((LinearLayout) convertView).addView(tvName);
            ((LinearLayout) convertView).addView(tvFTime);
            ((LinearLayout) convertView).addView(tvContents);*/
            holder = new Holder();
            holder.viewId = tvId;
            holder.viewTitle = tvTitle;
            holder.viewName = tvName;
            holder.viewFTime = tvFTime;
            holder.viewLTime =tvLTime;
            holder.viewContents = tvContents;
            holder.viewRes = tvRes;

            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }
                Reser reser = (Reser) getItem(position);
        holder.viewId.setText("No."+reser.get_id()+"");
        holder.viewTitle.setText("제목: "+reser.getTilte());
        holder.viewName.setText("예약자: "+reser.getName());
        holder.viewFTime.setText("Start_T: "+reser.getfTime());
        holder.viewLTime.setText("finish_T: "+reser.getlTime());
        holder.viewContents.setText("--내용--\n"+reser.getContents());
        if(reser.getRes() == 0){
            holder.viewRes.setBackgroundColor(Color.GREEN);
        }else holder.viewRes.setBackgroundColor(Color.RED);
        return convertView;
    }

    public class Holder{
        public TextView viewId;
        public TextView viewTitle;
        public TextView viewName;
        public TextView viewFTime;
        public TextView viewLTime;
        public TextView viewContents;
        public TextView viewRes;
    }
}

