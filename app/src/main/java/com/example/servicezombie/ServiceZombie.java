package com.example.servicezombie;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceZombie extends Service {
    private static int count = 0;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Según el parámetro que el service devuelva en este método es como el sistema lo trata
     * START_STICKY: Siempre lo recreará aunque haya sido cancelado. Service que siempre se inicia
     * START_NO_TICKY: Si el usuario cancela el service no se recrea hasta que no se reciba la
     * llamada startservice.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("ServiceZombie", "Ejecución número: "+ (++count));
        return START_STICKY;
    }

    /**
     * Si el service finaliza se lo comunica al BroadcastReceiver
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ServiceZombie", "Killin' it");
        Intent intent = new Intent(this, ServiceBroadcast.class);
        sendBroadcast(intent);
    }
}
