package com.ebiz.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class QRCodeUtils {
    public static final int DEFAULT_SIZE = 100;

    /**
     * 生成图片二维码 大小100*100
     *
     * @param content 待生成的内容
     * @return BufferedImage
     * @throws WriterException 生成失败
     */
    public static BufferedImage genQRCode(String content) throws WriterException {
        return genQRCode(content, null);
    }

    /**
     * 生成图片二维码
     *
     * @param content 待生成的内容
     * @param size    图片大小 默认正方形 大小100
     * @return BufferedImage
     * @throws WriterException 生成失败
     */
    public static BufferedImage genQRCode(String content, Integer size) throws WriterException {
        if (size == null || size <= 10) {
            size = DEFAULT_SIZE;
        }
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.MARGIN, 1); //设置二维码空白边框的大小 1-4，1是最小 4是默认的国标
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return image;
    }
}
