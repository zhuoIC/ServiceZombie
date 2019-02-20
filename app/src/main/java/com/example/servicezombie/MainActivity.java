package com.example.servicezombie;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_SERVICE = 1001;
    private ServiceZombie serviceZombie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. Programamos la alarma
        setAlarmManager();
        // 2. Comprobamos que el servicio esté ejecutándose
        serviceZombie = new ServiceZombie();
        Intent intent = new Intent(MainActivity.this, ServiceZombie.class);
        if(!isRunning(serviceZombie.getClass())){
            startService(intent);
        }
    }

    /**
     * Comprueba si existe en ActivityManager un servicio concreto
     * @param serviceClass
     * @return
     */
    private boolean isRunning(Class<?> serviceClass){
        boolean flag = false;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        // Consume muchos recursos
        for(ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(serviceInfo.service.getClassName())){
                Log.d("MainActivity","Servicio corriendo");
                flag = true;
                break;
            }
        }
        if(!flag)
            Log.d("MainActivity","Servicio no iniciado");
        return flag;
    }

    /**
     * método que planifica la ejecución del servicio
     */
    private void setAlarmManager() {
        Intent intent = new Intent(MainActivity.this, ServiceZombie.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, REQUEST_SERVICE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // IMPORTANTE
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pendingIntent);

    }
}
