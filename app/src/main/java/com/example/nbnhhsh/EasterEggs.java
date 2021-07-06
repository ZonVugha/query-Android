package com.example.nbnhhsh;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class EasterEggs {
    public void play(AudioManager manager, MediaPlayer turnUFKMusicDown,Context context){
        if (manager.isMusicActive() && manager.getStreamVolume(AudioManager.STREAM_MUSIC)>=12){
                manager.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                Toast.makeText(context, "TURN YOU FUCKING MUSIC DOWN!!!", Toast.LENGTH_LONG).show();
            Log.d("TAG", "播放且音量大于12时的音量: "+manager.getStreamVolume(AudioManager.STREAM_MUSIC));
            turnUFKMusicDown.start();
        }else {
            Log.d("TAG", "设备音量: "+manager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    }
}
