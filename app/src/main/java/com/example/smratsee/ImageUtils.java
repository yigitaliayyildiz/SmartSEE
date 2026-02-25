package com.example.smratsee;

import android.graphics.Bitmap;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageUtils {
    public static ByteBuffer bitmapToFloat32Buffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1 * 640 * 640 * 3 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] pixels = new int[640 * 640];
        bitmap.getPixels(pixels, 0, 640, 0, 0, 640, 640);
        for (int i = 0; i < pixels.length; ++i) {
            int pixel = pixels[i];
            byteBuffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f);
            byteBuffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);
            byteBuffer.putFloat((pixel & 0xFF) / 255.0f);
        }
        return byteBuffer;
    }
}
