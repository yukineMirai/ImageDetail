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


    private static final int[] STANDARD = new int[]{0,255};

    public final static int NORMAL_TYPE = 0;

    public final static int  STANDARD_TYPE = 1;

    public final static int  CUSTOMIZE_TYPE = 2;

    /**
     * 灰度集合
     */
    int[][] grayArray;

    int[][] transGrayArray;

    Integer maxGray = 0;

    Integer minGray = 255;

    BufferedImage transGray;


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

    /**
     * 转换为灰度图
     * @return
     */
    public BufferedImage transGray(){
        return transGray(NORMAL_TYPE);
    }

    public BufferedImage transGray(int type){
        return transGray(type,null,null);
    }

    public BufferedImage transGray(GrayScope transGrayScope){

        return transGray(CUSTOMIZE_TYPE,null,transGrayScope);
    }

    public BufferedImage transGray(GrayScope selectGrayScope,GrayScope transGrayScope){

        return transGray(CUSTOMIZE_TYPE,selectGrayScope,transGrayScope);
    }

    private BufferedImage transGray(int type,GrayScope selectGrayScope,GrayScope transGrayScope){

        boolean isTrans = linearTrans(type,selectGrayScope,transGrayScope);


        BufferedImage result = new BufferedImage(width,height,bufferedImage.getType());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int gray = 0;
                if(isTrans){
                    gray = getTransGray(i,j);
                }else {
                    gray = getGray(i,j);
                }
                int newPixel = colorToRGB(255, gray, gray, gray);
                result.setRGB(i, j, newPixel);
            }
        }
        return result;
    }

    /*private BufferedImage transGray(int type,int start,int end,)*/

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


    private void linearTrans(GrayScope selectGrayScope,GrayScope transGrayScope){

        if (selectGrayScope == null){
            selectGrayScope = new GrayScope(minGray,maxGray);
        }

        System.out.println("范围：" + selectGrayScope.toString() + "  TO  " + transGrayScope.toString());


        if(transGrayArray == null){
            transGrayArray = ArrayUtils.copy(grayArray,width,height);
        }

        for(int i=0; i< width;i++){
            for(int j = 0; j< height; j++){
                int gray = getGray(i,j);

                int tranGray = 0;
                if (gray < selectGrayScope.getStart()){
                    if (selectGrayScope.getStart() != 0){
                        tranGray = (transGrayScope.getStart()/selectGrayScope.getStart()) *gray;
                    }
                }else if(gray <= selectGrayScope.getEnd()){
                    tranGray = transGrayScope.getStart() + (int)((Double.valueOf((transGrayScope.getEnd() - transGrayScope.getStart())+"")
                            /(selectGrayScope.getEnd()- selectGrayScope.getStart())*(gray - selectGrayScope.getStart())));
                }else {
                    if (maxGray == selectGrayScope.getEnd()){
                        tranGray = transGrayScope.getEnd();
                    }else {
                        tranGray = (maxGray - transGrayScope.getEnd())/(maxGray -selectGrayScope.getEnd()) *
                                (gray - selectGrayScope.getEnd()) + transGrayScope.getEnd();

                    }
                }

                transGrayArray[i][j] = tranGray;

            }
        }

    }

    public int getGray(int i,int j){
        if (grayArray == null){
            return 0;
        }
        return grayArray[i][j];
    }

    public int getTransGray(int i,int j){
        if (transGrayArray == null){
            return 0;
        }
        return transGrayArray[i][j];
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
