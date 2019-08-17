package tw.org.iii.appps.brad25;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.brad);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String act = intent.getStringExtra("act");
        if (act != null){
            if (act.equals("start")){
                mediaPlayer.start();
            }else if (act.equals("pause") && mediaPlayer!=null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }

        super.onDestroy();
    }
}
