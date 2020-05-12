package com.yukine.image.main;

import com.yukine.image.gray.vo.GrayImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GrayTest {


    public static void main(String[] args) throws IOException {

        String path = "H:\\test\\";
        String name = "test_1.jpg";
        name = "还原_test_1.jpg";
        GrayImage grayImage = new GrayImage(path+name);


        System.out.println("["+ grayImage.getMinGray() + "," + grayImage.getMaxGray()+"]");

       /* File outputFile = new File(path+"test_"+name);
        File outputFile2 = new File(path+"还原_"+name);
        try {

            ImageIO.write(grayImage.transGray(0,255), "jpg", outputFile2);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
