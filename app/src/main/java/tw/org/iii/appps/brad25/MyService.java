package tw.org.iii.appps.brad25;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private File sdroot;

    public MyService(){
        sdroot = Environment.getExternalStorageDirectory();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();



        timer = new Timer();
        timer.schedule(new MyTask(), 0, 200);

        //mediaPlayer = MediaPlayer.create(this, R.raw.brad);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(sdroot.getAbsolutePath() + "/faded.mp3");
            mediaPlayer.prepare();
        }catch (Exception e){
            Log.v("brad", e.toString());
        }


        int len = mediaPlayer.getDuration();
        sendBroadcast(new Intent("ACT_LEN").putExtra("len",len));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String act = intent.getStringExtra("act");
        if (act != null){
            if (act.equals("start")){
                mediaPlayer.start();
            }else if (act.equals("pause") && mediaPlayer!=null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }else if (act.equals("seekto") && mediaPlayer != null){
                int seekto = intent.getIntExtra("seekto", -1);
                if (seekto >= 0) {
                    mediaPlayer.seekTo(seekto);
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                int now = mediaPlayer.getCurrentPosition();
                sendBroadcast(new Intent("ACT_NOW").putExtra("now",now));
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }

        super.onDestroy();
    }
}
