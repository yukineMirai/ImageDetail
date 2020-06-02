package com.yukine.image.detail;

import com.yukine.image.entity.GrayScope;
import com.yukine.image.gray.vo.GrayImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public final class ImageDetails {

    private ImageDetails(){}


    public static Builder of(String file){
        if (file == null){
            throw new NullPointerException("文件路径不能为空");
        }

        return Builder.ofString(file);

    }

    public static Builder of(File file){
        if (file == null){
            throw new NullPointerException("文件路径不能为空");
        }

        return Builder.ofFile(file);

    }


    public static class Builder{

        GrayImage grayImage;

        public Builder(GrayImage grayImage) {
            this.grayImage = grayImage;
        }


        public static Builder ofString(String file) {

            GrayImage image;
            try {
                image = new GrayImage(file);
            } catch (IOException e) {
                throw new RuntimeException("读取文件失败");
            }

            return new Builder(image);
        }

        public static Builder ofFile(File file) {

            GrayImage image;
            try {
                image = new GrayImage(file);
            } catch (IOException e) {
                throw new RuntimeException("读取文件失败");
            }

            return new Builder(image);
        }


        public  Builder grayTran(GrayScope startGrayScope){
            GrayImageDetail grayImageDetail = new GrayImageDetail(grayImage);
            grayImageDetail.transGray(startGrayScope);
            this.grayImage = grayImageDetail.grayImage;

            return this;
        }


        public  Builder grayTran(GrayScope startGrayScope,GrayScope endGrayScope){

            GrayImageDetail grayImageDetail = new GrayImageDetail(grayImage);
            grayImageDetail.transGray(startGrayScope,endGrayScope);
            this.grayImage = grayImageDetail.grayImage;

            return this;
        }

        public void toGrayImage(String outPath){

            File file = new File(outPath);
            GrayImageDetail grayImageDetail = new GrayImageDetail(grayImage);
            try {
                ImageIO.write(grayImageDetail.grayImage(), "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void toBinaryImage(String outPath){

            File file = new File(outPath);
            BinaryishImageDetail binaryishImageDetail = new BinaryishImageDetail(grayImage);
            try {
                ImageIO.write(binaryishImageDetail.binaryImage(), "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
