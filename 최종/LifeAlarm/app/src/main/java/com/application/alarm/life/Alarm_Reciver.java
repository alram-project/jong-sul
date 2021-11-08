package com.application.alarm.life;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class Alarm_Reciver extends BroadcastReceiver{

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {




        this.context = context;

        String get_yout_string = intent.getExtras().getString("state");
        int get_mode = intent.getExtras().getInt("mode");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("state", get_yout_string);
        service_intent.putExtra("mode", get_mode);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            this.context.startForegroundService(service_intent);
        }else{
            this.context.startService(service_intent);
        }
    }
}

