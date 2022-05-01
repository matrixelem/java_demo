package com.example.java_demo.image;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ImageUtil {

    public static byte[] resize(byte[] srcFileData, int width, int height) {
        ImageInputStream input = null;
        ImageReader reader = null;
        try {
            input = ImageIO.createImageInputStream(new ByteArrayInputStream(srcFileData));
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            reader = readers.next();
            reader.setInput(input);
            //原始图像的长和宽
            int srcWidth = reader.getWidth(0);
            int srcHeight = reader.getHeight(0);

            ImageReadParam param = reader.getDefaultReadParam();
            int sampling = Math.min(srcWidth/width, srcHeight/height);
            //采样压缩, 其中sampling的值表示每隔多少个像素点取一个像素
            param.setSourceSubsampling(sampling, sampling, 0, 0);
            // 裁剪
            param.setSourceRegion(new Rectangle(0, 0, srcWidth, srcHeight));
            BufferedImage bufferedImage = reader.read(0, param);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                if (reader != null){
                    reader.dispose();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new byte[]{};

    }
}
