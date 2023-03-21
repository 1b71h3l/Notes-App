package com.example.tp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class NoteActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech textToSpeech;
    private int currentPosition = 0;
    private String noteContent;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        textToSpeech = new TextToSpeech(this, this);

        ImageView imageView = findViewById(R.id.note_image);
        TextView titleTextView = findViewById(R.id.note_title);
        TextView contentTextView = findViewById(R.id.note_content);
        TextView dateTextView = findViewById(R.id.note_date);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        noteContent = intent.getStringExtra("content");
        date = intent.getStringExtra("date");

        imageView.setImageResource(R.drawable.note);
        titleTextView.setText(title);
        contentTextView.setText(noteContent);
        dateTextView.setText(date);

        Button textToSpeechButton = findViewById(R.id.text_to_speech_button);
        textToSpeechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),noteContent,Toast.LENGTH_LONG).show();
                textToSpeech.speak("your note contains: "+noteContent+"it was created at:"+date, TextToSpeech.QUEUE_FLUSH, null);
                currentPosition=0;
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language not supported");
            }
        } else {
            Log.e("TTS", "Initialization failed");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentPosition > 0) {
            // resume from the saved position
            textToSpeech.speak("you note contains: "+noteContent+"it was created at:"+ date, TextToSpeech.QUEUE_FLUSH, null, Integer.toString(currentPosition));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            currentPosition = textToSpeech.stop(); // save the current position of the TextToSpeech instance
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}