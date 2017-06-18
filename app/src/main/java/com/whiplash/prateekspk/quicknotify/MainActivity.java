package com.whiplash.prateekspk.quicknotify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import static com.whiplash.prateekspk.quicknotify.R.id.action_help;
import static com.whiplash.prateekspk.quicknotify.R.id.action_history;

public class MainActivity extends AppCompatActivity {
    TheDataBase theDataBase;
    TextView tv1,tv2;
    String s1,s2,ss1,ss2;
    CheckBox checkBox,checkBox2;
    long id= 0;
    boolean cb;
    int notid;
    NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        --------------------------------------------

        theDataBase=new TheDataBase(this);
        tv1= (TextView) findViewById(R.id.editText);
        tv1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        tv2= (TextView) findViewById(R.id.editText2);
        tv2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        checkBox.setChecked(true);
        checkBox2= (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setChecked(true);

        ss1=null;
        Intent intent=getIntent();
        ss1 = intent.getStringExtra("first");
        ss2 = intent.getStringExtra("second");

        if(ss1 != null) {

            notificationMaker(ss1, ss2);
            ss1=null;
            ss2=null;
            moveTaskToBack(true);
        }
    }

    public void sendNotification(View view) {
        s1=tv1.getText().toString();
        s2=tv2.getText().toString();

        notificationMaker(s1,s2);
        tv1.setText("");
        tv2.setText("");
        if(checkBox.isChecked())
            finish();
    }
    public void notificationMaker(String s1,String s2)
    {
        if(s1=="")
            s1="Blank Notification";
        id = theDataBase.InsertData(s1,s2);
        cb=checkBox2.isChecked();
        if(id>0)
        Message_Toast.main(this,"Notification Created!");
        Intent myintent2=new Intent(this,MainActivity.class);

        Intent myintent=new Intent(this,InnerActivity.class);
        myintent.putExtra("key",id);
        myintent.putExtra("msg1",s1);
        myintent.putExtra("msg2",s2);
        myintent.setAction(Long.toString(System.currentTimeMillis()));

        notid = (int)System.currentTimeMillis();
        myintent.putExtra("NotificationID",notid);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, s1+" "+s2);
        sendIntent.setType("text/plain");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), notid, myintent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntent2=PendingIntent.getActivity(getApplicationContext(),notid,myintent2,PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent sharePI=PendingIntent.getActivity(getApplicationContext(),notid,sendIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] v = {100,300};
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_add_alert_black_24dp);
        mBuilder.setContentTitle(s1);
        mBuilder.setContentText(s2);
        mBuilder.setAutoCancel(false);
        mBuilder.setVibrate(v);
        mBuilder.addAction(R.drawable.ic_add,"New",pendingIntent2);
        mBuilder.addAction(R.drawable.ic_edit,"Edit",pendingIntent);
        mBuilder.addAction(R.drawable.ic_share_black_24dp,"Share",sharePI);

        if(cb)
            mBuilder.setOngoing(true);


        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notid, mBuilder.build());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clearall) {
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();
            return true;
        }
        if(id==action_history)
        {
            Intent intent = new Intent(this, History.class);
            startActivity(intent);
            return true;
        }
        if (id == action_help) {
            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "Type a quick message in the editor and tap the round button. Your notification will be displayed.", Snackbar.LENGTH_LONG);

            snackbar.show();
            return true;
        }
        if (id == R.id.action_rateus) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName())));
            }catch (ActivityNotFoundException e){}
            return true;
        }
        if (id == R.id.action_write) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","spk.prateek@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions for Quick Notify");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body goes here");
            startActivity(emailIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
