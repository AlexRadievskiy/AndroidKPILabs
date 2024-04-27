package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        TextView textViewPlaceholder = findViewById(R.id.textViewPlaceholder);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String fileUriString = intent.getStringExtra("fileUri");
        Log.d("MainActivity", "Received URI: " + fileUriString);

        if (fileUriString != null) {
            Uri fileUri = Uri.parse(fileUriString);
            if (fileUriString.endsWith(".mp3")) {
                textViewPlaceholder.setText("Аудіо");
                setupMediaPlayer(fileUri);
            } else {
                textViewPlaceholder.setVisibility(View.GONE);
                setupVideoPlayer(fileUri);
            }
        } else {
            textViewPlaceholder.setText("Виберіть файл");
        }

        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            } else {
                videoView.start();
            }
            Log.d("MainActivity", "Play button clicked");
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                videoView.pause();
            }
            Log.d("MainActivity", "Pause button clicked");
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(0);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error resetting MediaPlayer", e);
                }
            } else {
                videoView.stopPlayback();
                videoView.setVideoURI(Uri.parse(fileUriString));
            }
            Log.d("MainActivity", "Stop button clicked");
        });

        btnBack.setOnClickListener(v -> {
            Log.d("MainActivity", "Back button clicked");
            Intent backIntent = new Intent(this, FileChooserActivity.class);
            backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(backIntent);
        });
    }

    private void setupVideoPlayer(Uri fileUri) {
        videoView.setVideoURI(fileUri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        Log.d("MainActivity", "Video URI set");
    }

    private void setupMediaPlayer(Uri fileUri) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, fileUri);
            mediaPlayer.prepare();
            Log.d("MainActivity", "Audio URI set");
        } catch (Exception e) {
            Log.e("MainActivity", "Error setting up MediaPlayer", e);
        }
    }
}
