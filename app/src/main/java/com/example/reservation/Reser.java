package com.example.reservation;

public class Reser {
    private int _id;
    private String title;
    private String name;
    private String fTime;
    private String lTime;
    private String contents;
    private int res;
    public Reser(){this.res = 0;}
    public int getRes(){return this.res;}
    public void setRes(int i){ res = i;}

    public int get_id() {
        return this._id ;
    }
    public void set_id(int i) {
        this._id =i;
    }

    public void setTilte(String s) {
        this.title = s;
    }
    public String getTilte() {
        return this.title;
    }

    public void setName(String s) {
        this.name =s;
    }
    public String getName() {
        return this.name;
    }

    public void setfTime(String s) {
        this.fTime = s;
    }
    public String getfTime() {
        return this.fTime ;
    }
    public void setlTime(String s) {
        this.lTime = s;
    }
    public String getlTime() {
        return this.lTime ;
    }
    public void setContents(String s){this.contents = s;
    }
    public String getContents(){ return  this.contents ;
    }
}
