package com.whiplash.prateekspk.quicknotify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class History extends AppCompatActivity {
    TheDataBase theDataBase;
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        theDataBase=new TheDataBase(this);
        l1= (ListView) findViewById(R.id.listview);
        display();
    }

    public void display()
    {
        String data= theDataBase.Getalldata1();
        String[] dummy=data.split("\n");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dummy);
        l1.setAdapter(adapter);
    }
    public void deleteall(View view)
    {
       int n= theDataBase.deleteall();
        Message_Toast.main(this,"Deleted "+n+" entries from history.");
        display();
    }
}
