package com.yukine.image.gray.vo;

import com.yukine.image.gray.vo.base.Image;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
public class GrayImage extends Image {


    private static final int[] STANDARD = new int[]{0,255};

    public final static int NORMAL_TYPE = 0;

    public final static int  STANDARD_TYPE = 1;

    public final static int  CUSTOMIZE_TYPE = 2;

    /**
     * 灰度集合
     */
    int[][] grayArray;

    Integer maxGray = 0;

    Integer minGray = 255;

    BufferedImage transGray;


    public GrayImage(String path) throws IOException {
        super(path);
        init();
    }

    public GrayImage(BufferedImage image){
        super(image);
        init();
    }

    /**
     * 转换为灰度图
     * @return
     */
    public BufferedImage transGray(){
        return transGray(NORMAL_TYPE);
    }

    public BufferedImage transGray(int type){
        return transGray(type,minGray,maxGray);
    }

    public BufferedImage transGray(int start ,int end){
        return transGray(CUSTOMIZE_TYPE,start,end);
    }

    private BufferedImage transGray(int type,int start,int end){

        if (type==STANDARD_TYPE){
            linearTrans(STANDARD[0],STANDARD[1]);
        }else if (type== CUSTOMIZE_TYPE){
            linearTrans(start,end);
        }

        BufferedImage result = new BufferedImage(width,height,bufferedImage.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int gray = getGray(i,j);
                int newPixel = colorToRGB(255, gray, gray, gray);
                result.setRGB(i, j, newPixel);
            }
        }

        return result;
    }





    /**
     * 灰度线性转换
     * @param transA 灰度范围
     * @param transB
     */
    public void linearTrans(int transA,int transB){

        if (transA<0 || transB <0){
            throw new RuntimeException("灰度范围不能小于0");
        }

        if (transA>255|| transB > 255){
            throw new RuntimeException("灰度范围不能大于255");
        }

        if (transB < transA){
            throw new RuntimeException("灰度范围B不能大于A");
        }

        for(int i=0; i< width;i++){
            for(int j = 0; j< height; j++){
                int gray = getGray(i,j);
                grayArray[i][j] = transA + (int)((Double.valueOf((transB - transA)+"")
                        /(maxGray- minGray)*(gray - minGray)));
            }
        }

    }

    public int getGray(int i,int j){
        if (grayArray == null){
            return 0;
        }
        return grayArray[i][j];
    }

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
    private void setGray(int i,int j ,int gray){
        grayArray[i][j] = gray;
        if (maxGray<gray){
            maxGray = gray;
        }
        if (minGray > gray){
            minGray = gray;
        }
    }

}
