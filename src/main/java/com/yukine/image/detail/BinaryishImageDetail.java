package com.yukine.image.detail;


import com.yukine.image.entity.PointInfo;
import com.yukine.image.gray.vo.GrayImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BinaryishImageDetail extends GrayImageDetail {


    public BinaryishImageDetail(String path) throws IOException {
        super(path);
    }

    public BinaryishImageDetail(File file) throws IOException {
        super(file);
    }

    public BinaryishImageDetail(BufferedImage bufferedImage){
        super(bufferedImage);
    }

    public BinaryishImageDetail(GrayImage grayImage){
        super(grayImage);

    }

    /*public BufferedImage binaryImage(){
        int threshold = getThreshold();
        System.out.println("二值化："  + threshold);
        for (int i = 0; i < grayImage.getWidth(); i++) {
            for (int j = 0; j < grayImage.getHeight(); j++) {
                if(grayImage.getGray(i,j)>threshold){

                    int black=new Color(255,255,255).getRGB();
                    bufferedImage.setRGB(i, j, black);
                }else{
                    int white=new Color(0,0,0).getRGB();
                    bufferedImage.setRGB(i, j, white);
                }
            }
        }

        return bufferedImage;
    }*/


    public int getThreshold2(){
        List<PointInfo> pointInfos = new ArrayList<>();
        int[] array = getHistogram();
        int[] diffArray = new int[array.length-1];
        int temp = 0;



        for (int i = 1; i< array.length; i++){
            temp =(array[i] - array[i-1]);
            if (temp> 0){
                diffArray[i-1] = 1;
            }else if (temp == 0){
                diffArray[i-1] = 0;
            }else {
                diffArray[i-1] = -1;
            }

        }

        for (int i = diffArray.length -1; i >=0;i--){

            if (diffArray[i]==0 && i== diffArray.length-1){
                diffArray[i] = 1;
            }else if (diffArray[i]==0){
                if (diffArray[i+1] >= 0){
                    diffArray[i] =1;
                }else {
                    diffArray[i]=-1;
                }
            }
        }


        int index = 0;
        int height = array[0];

        //计算
        float density = grayImage.getWidth() * grayImage.getHeight() / (256F * 100F);
        PointInfo info;
        for (int i = 1; i< diffArray.length; i++){
            temp =(diffArray[i] - diffArray[i-1]);

            if (temp==2){
                info = new PointInfo();
                info.setIndex(i+1);
                info.setHeight(array[i+1]);
                info.setWeight(array[i+1]/density);
                pointInfos.add(info);
            }

            diffArray[i-1] = temp;
        }
        //for ()

        Collections.sort(pointInfos);

        System.out.println("index：" + index);
        System.out.println("index A：" + pointInfos.get(0).getIndex());
        System.out.println("index B：" + pointInfos.get(1).getIndex());



        return 0;
    }

    public int[] getHistogram(){
        int[] histogram = new int[256];

        for (int t = grayImage.getMinGray(); t< grayImage.getMaxGray();t++){
            for (int i = 0;i< grayImage.getWidth();i++){
                for (int j=0; j < grayImage.getHeight();j++){
                    if (grayImage.getGray(i,j) == t){
                        histogram[t]++;
                    }
                }
            }
        }
        return histogram;
    }

    /**
     * 自动获取阀点
     * @return
     */
    private int getThreshold(){

        //获取直方图
        int[] histogram = getHistogram();

        int threshold = 0;
        int middleThreshold = (grayImage.getMinGray() + grayImage.getMaxGray())/2;

        System.out.println("二值化初始阀门："  + middleThreshold);

        int count1 = 0,count2=0,sum1=0,sum2=0;
        while (threshold != middleThreshold){
            for (int i =0; i< threshold;i++){
                count1 += histogram[i];
                sum1 += histogram[i] * i;
            }
            int bp = (count1==0) ? 0 : (sum1/count1);
            for (int j = 0;j<histogram.length;j++){
                count2 += histogram[j];
                sum2 += histogram[j] *j;
            }

            int fp = (count2==0) ? 0: (sum2/count2);
            threshold = middleThreshold;
            middleThreshold = (bp + fp)/2;
        }

        return middleThreshold;
    }

    public BufferedImage binaryImage(){

        int t = 15;
        int s = grayImage.getWidth() >> 3;
        int S = 9;
        //加速因子
        int power2S = 1 << S;
        int factor = power2S * (100 - t) / (100 * s);

        int gn = 127 * s;
        int q = power2S - power2S / s;
        int pn, hn;
        int[] prev_gn = new int[grayImage.getHeight()];
        for (int i = 0; i< grayImage.getHeight(); i++){
            prev_gn[i] = gn;
        }

        int[] scanLine = null;


        for (int i =0; i<grayImage.getWidth(); i++){
            scanLine = grayImage.getGrayArray()[i];
            for (int j =0; j<grayImage.getHeight(); j++){
                pn = scanLine[j];
                gn = ((gn * q) >> S) + pn;
                hn = (gn + prev_gn[j]) >> 1;
                prev_gn[j] = gn;


                if (pn < (hn * factor) >> S){

                    int white=new Color(0,0,0).getRGB();
                    bufferedImage.setRGB(i, j, white);
                }else {

                    int black=new Color(255,255,255).getRGB();
                    bufferedImage.setRGB(i, j, black);
                }

            }
        }

        return bufferedImage;

    }

    @Override
    protected void init() {
        grayImage = new GrayImage(getBufferedImage());
    }

}
