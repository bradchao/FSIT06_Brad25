package tw.org.iii.appps.brad25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private MyReceiver receiver;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser){
                    Log.v("brad", "fromUser");
                    Intent intent = new Intent(MainActivity.this, MyService.class);
                    intent.putExtra("seekto", i);
                    intent.putExtra("act", "seekto");
                    startService(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACT_LEN");
        filter.addAction("ACT_NOW");
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    public void startPlay(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("act", "start");
        startService(intent);
    }
    public void stopPlay(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    public void pausePlay(View view) {
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("act", "pause");
        startService(intent);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String act = intent.getAction();
            if (act.equals("ACT_LEN")){
                int len = intent.getIntExtra("len", -1);
                Log.v("brad", "len:" + len);
                seekBar.setMax(len);
            }else if (act.equals("ACT_NOW")){
                int now = intent.getIntExtra("now", -1);
                seekBar.setProgress(now);
            }
        }
    }

}
