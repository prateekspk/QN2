package com.whiplash.prateekspk.quicknotify;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Prateek on 5/27/2017.
 */

public class Message_Toast {
    public static void main(Context context, String msg) {

        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}