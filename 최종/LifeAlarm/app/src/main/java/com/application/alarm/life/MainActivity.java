package com.application.alarm.life;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    Context context;
    PendingIntent pendingIntent;
    int mode=0;

//    final Intent my_intent = new Intent(this, Alarm_Reciver.class);
    Intent my_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarm_timepicker = findViewById(R.id.time_picker);

        final Calendar calendar = Calendar.getInstance();

        my_intent = new Intent(this, Alarm_Reciver.class);

        Button alarm_on = findViewById(R.id.btn_start);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();
                Toast.makeText(MainActivity.this,"알람이 " + hour + "시 " + minute + "분으로 설정되었습니다.",Toast.LENGTH_SHORT).show();

                my_intent.putExtra("state","alarm on");
//                my_intent.putExtra("mode", mode);

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);
            }
        });

        Button alarm_off = findViewById(R.id.btn_finish);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent math_intent = new Intent(MainActivity.this,MathActivity.class);

                math_intent.putExtra("mode",mode);

                startActivityForResult(math_intent,0);
            }
        });

        findViewById(R.id.btn_mission_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu1){
                            Toast.makeText(MainActivity.this, "난이도: 쉬움을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                            mode = 0;
                        }else if (menuItem.getItemId() == R.id.action_menu2){
                            Toast.makeText(MainActivity.this, "난이도: 보통을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                            mode = 1;
                        }else {
                            Toast.makeText(MainActivity.this, "난이도: 어려움을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                            mode = 2;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==0){
            if (resultCode==RESULT_OK) {
                Toast.makeText(MainActivity.this,"알람 해제 완료.",Toast.LENGTH_SHORT).show();

                alarm_manager.cancel(pendingIntent);

                my_intent.putExtra("state","alarm off");

                // 알람취소
                sendBroadcast(my_intent);
            }else{
                Toast.makeText(MainActivity.this, "취소.", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==1){

        }
    }

}
