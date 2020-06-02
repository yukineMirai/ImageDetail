package com.yukine.image.detail;

import com.yukine.image.detail.base.AbstractImageDetail;
import com.yukine.image.entity.GrayScope;
import com.yukine.image.gray.vo.GrayImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.yukine.image.gray.vo.GrayImage.*;

public class GrayImageDetail extends AbstractImageDetail {

    protected GrayImage grayImage;


    public GrayImageDetail(String path) throws IOException {
        super(path);
    }

    public GrayImageDetail(File file) throws IOException {
        super(file);
    }

    public GrayImageDetail(BufferedImage file) {
        super(file);
    }

    public GrayImageDetail(GrayImage grayImage) {
        super(grayImage);
        this.grayImage = grayImage;
    }

    @Override
    protected void init() {
        grayImage = new GrayImage(getBufferedImage());
    }

    public BufferedImage transGray() {
        return transGray(NORMAL_TYPE);
    }

    public BufferedImage transGray(int type) {
        return transGray(type,null,null);
    }

    public BufferedImage transGray(GrayScope transGrayScope) {
        return transGray(CUSTOMIZE_TYPE,null,transGrayScope);
    }

    public BufferedImage transGray(GrayScope selectGrayScope, GrayScope transGrayScope) {
        return transGray(CUSTOMIZE_TYPE,selectGrayScope,transGrayScope);
    }


    private BufferedImage transGray(int type,GrayScope selectGrayScope,GrayScope transGrayScope){

         linearTrans(type,selectGrayScope,transGrayScope);

         return grayImage();
    }

    public BufferedImage grayImage(){


        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        BufferedImage result = new BufferedImage(width,height,bufferedImage.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int gray = 0;
                gray = grayImage.getGray(i,j);
                int newPixel = grayImage.colorToRGB(255, gray, gray, gray);
                result.setRGB(i, j, newPixel);
            }
        }
        return result;
    }



    /**
     * 灰度线性转换
     * @param type
     * @return
     */
    private boolean linearTrans(int type,GrayScope selectGrayScope,GrayScope transGrayScope){

        boolean isTrans = true;

        if (type==STANDARD_TYPE){
            linearTrans(selectGrayScope,new GrayScope(STANDARD[0],STANDARD[1]));
        }else if (type== CUSTOMIZE_TYPE){
            linearTrans(selectGrayScope,transGrayScope);
        }else {
            isTrans =false;
        }

        return isTrans;


    }


    /**
     * 分段线性转换
     * @param selectGrayScope
     * @param transGrayScope
     */
    private void linearTrans(GrayScope selectGrayScope,GrayScope transGrayScope){

        if (selectGrayScope == null){
            selectGrayScope = new GrayScope(grayImage.getMinGray(),grayImage.getMaxGray());
        }

        //System.out.println("范围：" + selectGrayScope.toString() + "  TO  " + transGrayScope.toString());

        int width = grayImage.getWidth();
        int height = grayImage.getHeight();

        grayImage.setMaxGray(0);
        grayImage.setMinGray(255);

        for(int i=0; i< width;i++){
            for(int j = 0; j< height; j++){
                int gray = grayImage.getGray(i,j);

                int tranGray = 0;
                if (gray < selectGrayScope.getStart()){
                    if (selectGrayScope.getStart() != 0){
                        tranGray = (transGrayScope.getStart()/selectGrayScope.getStart()) *gray;
                    }
                }else if(gray <= selectGrayScope.getEnd()){
                    tranGray = transGrayScope.getStart() + (int)((Double.valueOf((transGrayScope.getEnd() - transGrayScope.getStart())+"")
                            /(selectGrayScope.getEnd()- selectGrayScope.getStart())*(gray - selectGrayScope.getStart())));
                }else {
                    if (grayImage.getMaxGray() == selectGrayScope.getEnd()){
                        tranGray = transGrayScope.getEnd();
                    }else {
                        tranGray = (grayImage.getMaxGray() - transGrayScope.getEnd())/(grayImage.getMaxGray() -selectGrayScope.getEnd()) *
                                (gray - selectGrayScope.getEnd()) + transGrayScope.getEnd();
                    }
                }
                grayImage.setGray(i,j,tranGray);
            }
        }

    }

}
