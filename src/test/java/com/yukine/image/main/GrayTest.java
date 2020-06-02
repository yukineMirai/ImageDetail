package com.yukine.image.main;

import com.yukine.image.detail.GrayImageDetail;
import com.yukine.image.detail.ImageDetails;
import com.yukine.image.entity.GrayScope;
import com.yukine.image.gray.vo.GrayImage;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GrayTest {


    public static void main(String[] args) throws IOException {


        ImageDetails.of("C:\\Users\\001\\Pictures\\Saved Pictures\\2.jpg")
                .toBinaryImage("C:\\Users\\001\\Pictures\\Saved Pictures\\2_binary.jpg");


    }


    private void test() throws IOException {
        File file = new ClassPathResource("/image/test.jpg").getFile();


        String normalPath = file.getParent() + File.separator +"normal_"+ file.getName();
        String custom100_200_Path = file.getParent() + File.separator +"custom_100_200_"+ file.getName();
        String customPath = file.getParent() + File.separator +"custom_"+ file.getName();
        String custom50_100_Path = file.getParent() + File.separator +"custom_50_100"+ file.getName();

        String binary_normalPath = file.getParent() + File.separator +"binary_normal_"+ file.getName();
        String binary_custom_100_200Path = file.getParent() + File.separator +"binary_custom_100_200"+ file.getName();
        String binary_custom_Path = file.getParent() + File.separator +"binary_custom_"+ file.getName();

        //灰度化
        ImageDetails.of(file).toGrayImage(normalPath);
        ImageDetails.of(file)
                .grayTran(new GrayScope(100,200))
                .toGrayImage(custom100_200_Path);
        ImageDetails.of(file)
                .grayTran(new GrayScope(100,200),new GrayScope(0,255))
                .toGrayImage(customPath);

        ImageDetails.of(file)
                .grayTran(new GrayScope(50,100),new GrayScope(0,255))
                .toGrayImage(custom50_100_Path);

        //二值化
        ImageDetails.of(file)
                .toBinaryImage(binary_normalPath);
        ImageDetails.of(file)
                .grayTran(new GrayScope(100,200))
                .toBinaryImage(binary_custom_100_200Path);
        ImageDetails.of(file)
                .grayTran(new GrayScope(100,200),new GrayScope(0,255))
                .toBinaryImage(binary_custom_Path);

        ImageDetails.of(file)
                .grayTran(new GrayScope(50,100),new GrayScope(0,255))
                .toBinaryImage(file.getParent() + File.separator +"binary_custom_0_100"+ file.getName());


       /* System.out.println(file.getParent());
        System.out.println(file.getPath());
        //GrayImage grayImage = new GrayImage(file);

        //System.out.println("本图片灰度值:["+ grayImage.getMinGray() + "," + grayImage.getMaxGray()+"]");

        GrayImageDetail detail = new GrayImageDetail(file);

        generalNORMALGray(detail,file);
        generalStandardGray(detail,file);
        generalCustomizeGray(detail,file,new GrayScope(100,200));
        generalCustomizeGray(detail,file,new GrayScope(100,200),new GrayScope(0,255));*/

    }

    /**
     * 正常生成灰度图
     * @param grayImage
     * @param file
     */
    public static void generalNORMALGray(GrayImageDetail grayImage,File file){
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
    public static void generalStandardGray(GrayImageDetail grayImage,File file){
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
    public static void generalCustomizeGray(GrayImageDetail grayImage,File file,GrayScope grayScope){
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
    public static void generalCustomizeGray(GrayImageDetail grayImage,File file,GrayScope selectGrayScope,GrayScope transGrayScope){
        File outputFile = new File(file.getParent() + File.separator +"customize_2_"+ file.getName());
        try {
            ImageIO.write(grayImage.transGray(selectGrayScope,transGrayScope), "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void main2() throws IOException {

        Thumbnails.of("images/test.jpg").size(200, 300).toFile("C:/image_200x300.jpg");
        Thumbnails.of("images/test.jpg").size(200, 500).scale(0.5).toFile("C:/image_200x300.jpg");
        Thumbnails.Builder<File>  fileBuilder = Thumbnails.of("images/test.jpg");
        fileBuilder.asBufferedImage();

    }
}
