package com.developments.ar.accept;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sql_db extends SQLiteOpenHelper {
    public Sql_db(Context context) {
        super(context, "Accept", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Details = "Create table A_tab (x_id INTEGER DEFAULT 1, s_in INTEGER DEFAULT 1, s_hold1 INTEGER DEFAULT 1, s_out INTEGER DEFAULT 1, s_hold2 INTEGER DEFAULT 1, opt1 INTEGER DEFAULT 1, opt2 INTEGER DEFAULT 2, opt3 INTEGER DEFAULT 1, status INTEGER DEFAULT 0, splash INTEGER DEFAULT 0 )";
        db.execSQL(Details);

        String Band_details="Create table Band_table(b_id INTEGER DEFAULT 1, b_name TEXT, b_addr TEXT)";
        db.execSQL(Band_details);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Accept");
        onCreate(db);
    }

    public void insert_db(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "insert into A_tab (x_id,s_in,s_hold1,s_out,s_hold2,opt1,opt2,opt3,status,splash) values(1,4,2,4,2,1,2,1,0,0)";
        db.execSQL(sql);
        String sql2 = "insert into Band_table (b_id,b_name,b_addr) values(1,Null,Null)";
        db.execSQL(sql2);
    }

    public void update_band_feature(){
        SQLiteDatabase db = this.getWritableDatabase();
        String Band_details="Create TABLE IF NOT EXISTS Band_table(b_id INTEGER DEFAULT 1, b_name TEXT, b_addr TEXT)";
        db.execSQL(Band_details);

        String  selectQuery = "select * from Band_table where b_id=1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

        }
        else{
            String sql = "insert into Band_table (b_id,b_name,b_addr) values(1,'','')";
            db.execSQL(sql);
        }

    }

    public String[] get_device(){
        SQLiteDatabase db = this.getWritableDatabase();
        String  selectQuery = "select * from Band_table where b_id=1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[] content={"",""};
        if (cursor.moveToFirst()) {
            do {
                content[0]=cursor.getString(1);
                content[1]=cursor.getString(2);
            }
            while (cursor.moveToNext());
        }
        return content;
    }

    public void update_new_device(String b_name,String b_addr) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE Band_table set b_name ='"+b_name+"',b_addr ='"+b_addr+"' where b_id=1";
        db.execSQL(sql);
    }


    public int[] get_value() {
        int[] content={1,0,1,0,1,1,1,0};
        String  selectQuery = "select * from A_tab where x_id=1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                content[0]=cursor.getInt(1);
                content[1]=cursor.getInt(2);
                content[2]=cursor.getInt(3);
                content[3]=cursor.getInt(4);
                content[4]=cursor.getInt(5);
                content[5]=cursor.getInt(6);
                content[6]=cursor.getInt(7);
                content[7]=cursor.getInt(8);
            }
            while (cursor.moveToNext());
        }
        else{
            insert_db();
        }
        return content;
    }

    public int get_splash(){
        int ans=0;
        String  selectQuery = "select splash from A_tab where x_id=1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ans=cursor.getInt(0);
            }
            while (cursor.moveToNext());
        }
        else{
            insert_db();
        }
        return ans;
    }

    public void update_db(int s_in,int s_hold1,int s_out,int s_hold2,int opt1,int opt2,int opt3,int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE A_tab set s_in ="+s_in+", s_hold1 ="+s_hold1+", s_out ="+s_out+", s_hold2 ="+s_hold2+", opt1 ="+opt1+", opt2 ="+opt2+", opt3 ="+opt3+", status ="+status+" where x_id=1";
        db.execSQL(sql);
    }

    public void update_splash(int s_no) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE A_tab set splash ="+s_no+" where x_id=1";
        db.execSQL(sql);
    }
}