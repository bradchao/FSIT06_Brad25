package tw.org.iii.appps.brad25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            }else if (act.equals("ACT_NOW")){

            }
        }
    }

}
