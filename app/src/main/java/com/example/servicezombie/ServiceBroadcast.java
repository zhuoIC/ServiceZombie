package com.example.servicezombie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ServiceBroadcast", "Service Stop!!");
        // YA NO FUNCIONA EN API > 26 cuando la app no es visible
        //context.startService(new Intent(context, ServiceZombie.class));
        context.startForegroundService(new Intent(context, ServiceZombie.class));
    }
}
