package com.hrfid.acs.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by brentmercader on 06/03/2017.
 */

public class SoundUtils {

    private static SoundUtils soundUtils;

    static {
        soundUtils = new SoundUtils();
    }

    MediaPlayer mediaPlayer;

    public SoundUtils() {
    }

    public static SoundUtils getInstance() {
            return soundUtils;
    }

    public void prepareMediaPlayer(Context context, String soundFilePath) {

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri uri = Uri.parse(soundFilePath);
        try {
            mediaPlayer.setDataSource(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setLooping(false);
        mediaPlayer.setVolume(100, 100);
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void playSound() {
        stopMediaPlayer();
        mediaPlayer.start();
    }

    public void stopMediaPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
}
