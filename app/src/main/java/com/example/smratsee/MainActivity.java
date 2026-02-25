package com.example.smratsee;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int REQUEST_PERMISSION = 100;

    private ImageView imageView;
    private TextView txtNsn;
    private YoloV8Classifier classifier;
    private TextToSpeech tts;

    private void speakResult(String text) {
        if (tts == null) return;
        if (text == null || text.trim().isEmpty()) return;


        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SMARTSEE_TTS");
    }

    private String displayLabelTr(String label) {
        if (label == null) return "Tanımlanamadı";

        switch (label) {
            case "black_mouse": return "Siyah Mouse";
            case "white_mouse": return "Beyaz Mouse";
            case "black_keyboard": return "Siyah Klavye";
            case "white_keyboard": return "Beyaz Klavye";
            default:
                return label.replace("_", " "); // bilmediği label gelirse en azından düzgün görünür
        }
    }
    // Etiketi konuşmaya uygun hale getir (ekranda label aynı kalsa bile ses düzgün çıkar)
    private String speakableLabel(String label) {
        if (label == null) return "Tanımlanamadı";

        switch (label) {
            case "black_mouse":
                return "Siyah mouse";
            case "white_mouse":
                return "Beyaz mouse";
            case "black_keyboard":
                return "Siyah klavye";
            case "white_keyboard":
                return "Beyaz klavye";
            default:
                return label.replace("_", " ");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TTS başlat
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int res = tts.setLanguage(new Locale("tr", "TR"));
                if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "Türkçe TTS desteklenmiyor", Toast.LENGTH_SHORT).show();
                }
                tts.setSpeechRate(0.9f); // hız
                tts.setPitch(1.0f);      // ton
            } else {
                Toast.makeText(this, "Ses motoru başlatılamadı", Toast.LENGTH_SHORT).show();
            }
        });

        imageView = findViewById(R.id.imageView);
        txtNsn = findViewById(R.id.txtNsn);
        Button btnCam = findViewById(R.id.btnCam);
        Button btnGlr = findViewById(R.id.btnGlr);

        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION
            );
        }

        try {
            classifier = new YoloV8Classifier(this, "best_float32.tflite", "labels.txt");
        } catch (IOException e) {
            Toast.makeText(this, "Model yüklenemedi", Toast.LENGTH_SHORT).show();
            classifier = null;
        }

        btnCam.setOnClickListener(v -> openCamera());
        btnGlr.setOnClickListener(v -> openGallery());
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void openCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            Bitmap imageBitmap = null;

            if (requestCode == REQUEST_IMAGE_CAPTURE && data.getExtras() != null) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                Uri imageUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (imageBitmap != null) {
                imageView.setImageBitmap(imageBitmap);

                // Model yüklenmediyse crash olmasın
                if (classifier == null) {
                    Toast.makeText(this, "Model hazır değil", Toast.LENGTH_SHORT).show();
                    return;
                }

                String result = classifier.recognizeImage(imageBitmap);
                txtNsn.setText(displayLabelTr(result));

                // Sesli okuma (etiketi daha düzgün Türkçe oku)
                speakResult(speakableLabel(result));
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}