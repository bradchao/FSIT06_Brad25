package tw.org.iii.appps.brad25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
