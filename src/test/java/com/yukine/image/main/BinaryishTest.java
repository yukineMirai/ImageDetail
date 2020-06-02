package com.yukine.image.main;

import com.yukine.image.detail.BinaryishImageDetail;
import com.yukine.image.detail.GrayImageDetail;
import com.yukine.image.entity.GrayScope;
import com.yukine.image.gray.vo.GrayImage;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BinaryishTest {


    public static void main(String[] args) throws IOException {


       /* File file = new ClassPathResource("/image/test.jpg").getFile();

        System.out.println(file.getParent());
        System.out.println(file.getPath());
        GrayImage grayImage = new GrayImage(file);

        GrayImageDetail grayImageDetail = new GrayImageDetail(file);
        BinaryishImageDetail detail = new BinaryishImageDetail(file);

        System.out.println("本图片灰度值:["+ grayImage.getMinGray() + "," + grayImage.getMaxGray()+"]");


        generalNORMALGray(detail,file);*/
        File file = new ClassPathResource("/image/test.jpg").getFile();
        BinaryishImageDetail binaryishImageDetail = new BinaryishImageDetail(file);
        binaryishImageDetail.getThreshold2();








    }




    /**
     * 正常生成二值化图
     * @param file
     */
    public static void generalNORMALGray(BinaryishImageDetail detail,File file){
        File outputFile = new File(file.getParent() + File.separator +"binary"+File.separator+"custom_"+ file.getName());

        File parent = outputFile.getParentFile();
        if (!parent.exists()){
            parent.mkdirs();
        }
        try {
            ImageIO.write(detail.binaryImage(), "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
