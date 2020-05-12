package com.yukine.image.gray.vo.base;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 图片基类
 */
@Data
public abstract class Image {

    protected BufferedImage bufferedImage;
    /**
     * 灰度集合
     */
    protected Integer width;

    protected Integer height;



    public Image(String path) throws IOException {
        File file = new File(path);
        bufferedImage = ImageIO.read(file);
        init();
    };

    public Image(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
        init();
    }

    private void init(){
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
    }


    protected  int colorToRGB(int alpha, int red , int green, int blue){
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }





}
