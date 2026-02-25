package com.example.smratsee;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import org.tensorflow.lite.Interpreter;
import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.util.*;

public class YoloV8Classifier {
    private final Interpreter interpreter;
    private final List<String> labels;
    private final int INPUT_SIZE = 640;
    private final float THRESHOLD = 0.5f;

    public YoloV8Classifier(Context context, String modelPath, String labelPath) throws IOException {
        interpreter = new Interpreter(loadModelFile(context, modelPath));
        labels = loadLabelsFromAssets(context, labelPath); // <-- Metadata yerine burası
    }

    private MappedByteBuffer loadModelFile(Context context, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private List<String> loadLabelsFromAssets(Context context, String labelPath) throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(labelPath)));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    public String recognizeImage(Bitmap bitmap) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
        ByteBuffer inputBuffer = ImageUtils.bitmapToFloat32Buffer(scaledBitmap);

        float[][][] output = new float[1][8][8400];  // YOLOv8 çıkışı: 8 kanal (4 bbox + 4 class score)
        interpreter.run(inputBuffer, output);

        float bestScore = -1f;
        int bestClass = -1;

        for (int i = 0; i < 8400; i++) {
            float maxScore = -1;
            int maxClass = -1;

            for (int c = 4; c < 8; c++) {
                if (output[0][c][i] > maxScore) {
                    maxScore = output[0][c][i];
                    maxClass = c - 4;
                }
            }

            if (maxScore > bestScore) {
                bestScore = maxScore;
                bestClass = maxClass;
            }
        }

        return bestClass != -1 ? labels.get(bestClass) : "Tanımlanamadı";

    }
}
