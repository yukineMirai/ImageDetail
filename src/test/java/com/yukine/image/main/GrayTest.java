package com.yukine.image.main;

import com.yukine.image.entity.GrayScope;
import com.yukine.image.gray.vo.GrayImage;
import org.junit.Before;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GrayTest {


    private File testFile;

    @Before
    public void getFile(){
        try {
            testFile = new ClassPathResource("/image/test.jpg").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {


        File file = new ClassPathResource("/image/test.jpg").getFile();

        System.out.println(file.getParent());
        System.out.println(file.getPath());
        GrayImage grayImage = new GrayImage(file);

        System.out.println("本图片灰度值:["+ grayImage.getMinGray() + "," + grayImage.getMaxGray()+"]");

        generalNORMALGray(grayImage,file);
        generalStandardGray(grayImage,file);
        generalCustomizeGray(grayImage,file,new GrayScope(100,200));
        generalCustomizeGray(grayImage,file,new GrayScope(100,200),new GrayScope(0,255));

    }

    /**
     * 正常生成灰度图
     * @param grayImage
     * @param file
     */
    public static void generalNORMALGray(GrayImage grayImage,File file){
        File outputFile = new File(file.getParent() + File.separator +"normal_"+ file.getName());
        try {
            ImageIO.write(grayImage.transGray(), "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 线性变换
     * 灰度值调整为[0,255]
     * @param grayImage
     * @param file
     */
    public static void generalStandardGray(GrayImage grayImage,File file){
        File outputFile = new File(file.getParent() + File.separator +"standard_"+ file.getName());
        try {
            ImageIO.write(grayImage.transGray(GrayImage.STANDARD_TYPE), "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 线性变换
     * 灰度值调整为指定范围
     * @param grayImage
     * @param file
     */
    public static void generalCustomizeGray(GrayImage grayImage,File file,GrayScope grayScope){
        File outputFile = new File(file.getParent() + File.separator +"customize_1_"+ file.getName());
        try {
            ImageIO.write(grayImage.transGray(grayScope), "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分段线性变换
     * 将指定范围灰度值[a,b] 线性变换为 [c,d]
     * @param grayImage
     * @param file
     */
    public static void generalCustomizeGray(GrayImage grayImage,File file,GrayScope selectGrayScope,GrayScope transGrayScope){
        File outputFile = new File(file.getParent() + File.separator +"customize_2_"+ file.getName());
        try {
            ImageIO.write(grayImage.transGray(selectGrayScope,transGrayScope), "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
