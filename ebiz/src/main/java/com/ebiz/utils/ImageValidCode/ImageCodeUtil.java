package com.ebiz.utils.ImageValidCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ImageCodeUtil {
    private static final String BASE_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SCALE = 400;
    private static Map<Integer, List<Integer>> COMPOSITE_NUMBER;

    static {
        COMPOSITE_NUMBER = getCompositeNumber(SCALE);
    }

    /**
     * 获取验证码
     * length>2
     *
     * @param length 验证码长度
     * @return 包含数字和英文字母长度为 ${length} 的字符串
     */
    public static String genCharsCode(int length) {
        if (length < 3)
            throw new IllegalArgumentException("验证码长度必须大于2");
        Random random = new Random();
        int numbers = random.nextInt(2) + 1;
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (; i < numbers; i++) {
            sb.append(random.nextInt(10));
        }

        for (; i < length; i++) {
            sb.append(BASE_STRING.charAt(random.nextInt(BASE_STRING.length())));
        }
        return sb.toString();
    }

    /**
     * 获取验证码
     * length>2
     *
     * @param length 验证码长度
     * @return 包含数字和英文字母长度为 ${length} 的字符串
     */
    public static String genNumbersCode(int length) {
        if (length < 3)
            throw new IllegalArgumentException("验证码长度必须大于2");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }


    /**
     * 获取验证码(算术结果)
     *
     * @return 包含数字和英文字母长度为 ${length} 的字符串
     */
    public static int genArithmeticCode(int method) {
        Random random = new Random();
        int x = random.nextInt(SCALE);
        switch (method) {
            case 0://+
                return x;
            case 1://-
                if (x > 500) {
                    return x / 2;
                }
                return x;
            case 2://×
                Integer[] keys = COMPOSITE_NUMBER.keySet().toArray(new Integer[0]);
                return keys[random.nextInt(keys.length)];
            case 3://÷
                return random.nextInt((int) Math.sqrt(SCALE));
        }
        return random.nextInt(SCALE);
    }

    /**
     * 随机从加减乘除中选择一个方法
     */
    public static int genArithmeticMethod() {
        return new Random().nextInt(4);
    }

    /**
     * 获取验证码
     */
    public static String genArithmeticCodeStr(int result, int method) {
        Random random = new Random();
        switch (method) {
            case 0://+
                int add1 = random.nextInt(result - 1) + 1;
                return add1 + "+" + (result - add1) + "=?";
            case 1://-
                int sub1 = random.nextInt(result);
                return (sub1 + result) + "-" + sub1 + "=?";
            case 2://×
                List<Integer> list = COMPOSITE_NUMBER.get(result);
                Integer multi1 = list.get(random.nextInt(list.size()));
                return multi1 + "×" + result / multi1 + "=?";
            case 3://÷
                int divide = random.nextInt((int) Math.sqrt(SCALE)) + 1;
                return result * divide + "÷" + divide + "=?";
        }
        return "";
    }

    /**
     * 获取素数
     *
     * @param scale 范围
     */
    private static Map<Integer, List<Integer>> getCompositeNumber(int scale) {
        Map<Integer, List<Integer>> hs = new HashMap<>();
        for (int i = 4; i <= scale; i++) {
            boolean isHeshu = false;
            List<Integer> list = new ArrayList<>();
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    isHeshu = true;
                    list.add(j);
                }
            }
            if (isHeshu) {
                hs.put(i, list);
            }
        }
        return hs;
    }

    public static BufferedImage genNumberImage(int width,
                                               int height,
                                               int lines,
                                               String code) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        int marginX = 5;
        //设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        width = width - marginX * 2;
        int fontSize = width / 4;
        Font font = new Font("宋体", Font.BOLD, fontSize);
        //设置字体
        g.setFont(font);

        //随机数字
        Random r = new Random(new Date().getTime());
        for (int i = 0; i < code.length(); i++) {
            int y = fontSize + r.nextInt(height - fontSize);//10~30范围内的一个整数，作为y坐标

            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g.setColor(c);

            g.drawString("" + code.charAt(i), marginX + i * width / code.length(), y);
        }

        //干扰线
        int baseColor = 25;
        for (int i = 0; i < lines; i++) {
            int tempRed = r.nextInt(255);
            int tempGreen = r.nextInt(255);
            int tempBlue = r.nextInt(255);
            int red = tempRed < 50 ? tempRed + baseColor : tempRed;
            int green = tempGreen < 50 ? tempGreen + baseColor : tempGreen;
            int blue = tempBlue < 50 ? tempBlue + baseColor : tempBlue;
            Color c = new Color(red, green, blue);
            g.setColor(c);
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }

        g.dispose();//类似于流中的close()带动flush()---把数据刷到img对象当中
        return img;
    }

    public static void main(String[] args) throws IOException {
        int width = 400;
        int height = 100;
        int lines = 10;
        //        for (int i = 0; i < 10; i++) {
        //            BufferedImage bufferedImage = genNumberImage(width, height, lines, genNumbersCode(4));
        //            File file = new File("C:/Users/ZL/Desktop/out" + i + ".jpg");
        //            ImageIO.write(bufferedImage, "JPG", new FileOutputStream(file));
        //        }
        //
        for (int i = 0; i < 5; i++) {
            int method = genArithmeticMethod();
            int code = genArithmeticCode(method);
            System.out.println(code);
            BufferedImage bufferedImage = genNumberImage(width, height, lines, genArithmeticCodeStr(code, method));
            File file = new File("C:/Users/ZL/Desktop/out" + i + ".jpg");
            ImageIO.write(bufferedImage, "JPG", new FileOutputStream(file));
        }
    }
}
