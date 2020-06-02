package com.yukine.image.gray.vo;

import com.yukine.image.entity.GrayScope;
import com.yukine.image.gray.vo.base.Image;
import com.yukine.image.utils.ArrayUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper=false)
public class GrayImage extends Image {


    public static final int[] STANDARD = new int[]{0,255};

    //正常灰度化
    public final static int NORMAL_TYPE = 0;

    //标准处理灰度化
    public final static int  STANDARD_TYPE = 1;

    //自定义灰度化
    public final static int  CUSTOMIZE_TYPE = 2;

    /**
     * 灰度集合
     */
    int[][] grayArray;


  /*  //转换后灰度值
    int[][] transGrayArray;*/

    Integer maxGray = 0;

    Integer minGray = 255;

    BufferedImage transGray;


  /*  public int[][] getTransGrayArray() {

        if (transGrayArray == null){
            transGrayArray = ArrayUtils.copy(grayArray,width,height);
        }

        return transGrayArray;
    }*/


    public GrayImage(String path) throws IOException {
        this(new File(path));
    }

    public GrayImage(File file) throws IOException {
        this(ImageIO.read(file));
    }

    public GrayImage(BufferedImage image){
        super(image);
        init();
    }


    public int getGray(int i,int j){
        if (grayArray == null){
            return 0;
        }
        return grayArray[i][j];
    }

    /*public int getTransGray(int i,int j){
        if (transGrayArray == null){
            return 0;
        }
        return transGrayArray[i][j];
    }*/


   /* public int getAutoGray(int i,int j){
        if (transGrayArray != null){
            return getTransGray(i,j);
        }else {
            return getGray(i,j);
        }
    }*/

    /**
     * 初始化灰度化图片
     */
    private void init(){
        if (bufferedImage == null){
            throw new RuntimeException("获取图片流失败");
        }
        grayArray = new int[width][height];
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                final int color = bufferedImage.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray = (int) (0.39 * r + 0.5 * g + 0.11 * b);
                setGray(i,j,gray);
            }
        }
    }
    public void setGray(int i,int j ,int gray){
        grayArray[i][j] = gray;
        if (maxGray<gray){
            maxGray = gray;
        }
        if (minGray > gray){
            minGray = gray;
        }
    }

}
