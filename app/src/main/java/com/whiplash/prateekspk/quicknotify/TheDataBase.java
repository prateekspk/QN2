package com.whiplash.prateekspk.quicknotify;

/**
 * Created by Prateek on 6/2/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Prateek on 5/27/2017.
 */

public class TheDataBase {

    SPKhelper sh1;
    public TheDataBase(Context context) {
        sh1=new SPKhelper(context);
    }

    public long InsertData(String user,String pass){
        SQLiteDatabase db=sh1.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(sh1.TITLES,user);
        contentValues.put(sh1.CONTENTS,pass);
        long id=db.insert(SPKhelper.tbname,null,contentValues);
        return id;
    }
    public String GetOneData(Long uid)
    {
        SQLiteDatabase db=sh1.getWritableDatabase();
        String[] columns={SPKhelper.TITLES,SPKhelper.CONTENTS};
        String[] selectionargs={uid.toString()};
        Cursor cursor=db.query(SPKhelper.tbname,columns,SPKhelper.UID+"=?",selectionargs,null,null,null);
        StringBuffer buffer = new StringBuffer();
        String s1="";
        while(cursor.moveToNext())
        {
            String title=cursor.getString(0);
            String context=cursor.getString(1);
            buffer.append(title+"."+context);

        }
        return buffer.toString();
    }

    public String Getalldata()
    {
        SQLiteDatabase db=sh1.getWritableDatabase();
        String[] columns={SPKhelper.UID,SPKhelper.TITLES,SPKhelper.CONTENTS};
        Cursor cursor=db.query(SPKhelper.tbname,columns,null,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid=cursor.getInt(0);
            String user=cursor.getString(1);
            String pass=cursor.getString(2);
            stringBuffer.append(cid+" . "+user+
                    " . "+pass+"\n");
        }
        return stringBuffer.toString();
    }
    public String Getalldata1()
    {
        SQLiteDatabase db=sh1.getWritableDatabase();
        String[] columns={SPKhelper.TITLES,SPKhelper.CONTENTS};
        Cursor cursor=db.query(SPKhelper.tbname,columns,null,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext())
        {

            String user=cursor.getString(0);
            String pass=cursor.getString(1);
            stringBuffer.append(user+
                    " . "+pass+"\n");
        }
        return stringBuffer.toString();
    }

    public int UpdateData(Long uid,String newTitle,String newContext)
    {
        SQLiteDatabase db=sh1.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(SPKhelper.TITLES,newTitle);
        contentValues.put(SPKhelper.CONTENTS,newContext);
        String wherearg[] = {uid.toString()};
        int count=db.update(SPKhelper.tbname,contentValues,SPKhelper.UID+" =? ",wherearg);
        return count;
    }
    public int deleteall()
    {
        SQLiteDatabase db=sh1.getWritableDatabase();
        int count=db.delete(SPKhelper.tbname,null,null);
        return count;
    }


    static class SPKhelper extends SQLiteOpenHelper {
        private static final String dbname="SPKdatabase";
        private static final String tbname="SPKtable";
        private static final int version=1;
        private static final String UID="_id";
        private static final String TITLES="title";
        private static final String CONTENTS="content";
        private Context context;
        private static final String Create_Table = "CREATE TABLE "+tbname+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+TITLES+" VARCHAR(255),"+CONTENTS+" VARCHAR(255));";
        private static final String Drop_Table = "DROP TABLE IF EXISTS "+tbname;
        public SPKhelper(Context context) {

            super(context, dbname, null, version);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try{ db.execSQL(Create_Table);
                Message_Toast.main(context,"OnCrreate");}
            catch (SQLException e){
                Message_Toast.main(context,"NO DB"+e);}

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try{Message_Toast.main(context,"OnUpgrade");
                db.execSQL(Drop_Table);
                onCreate(db);
            }
            catch (SQLException e){
                Message_Toast.main(context,"NO DB"+e);}
        }

    }
}
