package com.tis.merchant.app.utils;

import android.graphics.Bitmap;
import com.google.zxing.Dimension;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by Nanthakorn on 9/1/2015.
 */
public class QRCodeGenerator {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    public static Bitmap generateQRCode(String toEncode,Dimension imageSize){
        //Log.d("dam",""+imageSize.getWidth()+"/"+imageSize.getHeight());
        Bitmap bitmap = null;
        try {
            bitmap = encodeAsBitmap(toEncode, imageSize);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

     private static Bitmap encodeAsBitmap(String str, Dimension imageSize) throws WriterException {
        BitMatrix result;

        try {

            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, imageSize.getWidth(), imageSize.getHeight(), null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }

    public static Bitmap encodeAsBitmap(BitMatrix result) throws WriterException {
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }
}
