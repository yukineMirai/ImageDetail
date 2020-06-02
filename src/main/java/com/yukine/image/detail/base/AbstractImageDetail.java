package com.yukine.image.detail.base;

import com.yukine.image.detail.ImageDetails;
import com.yukine.image.gray.vo.GrayImage;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Data
public abstract class AbstractImageDetail {

    protected String path;

    protected BufferedImage bufferedImage;

    public AbstractImageDetail(String path) throws IOException {
        this(new File(path));
    }

    public AbstractImageDetail(File file) throws IOException {
        this(ImageIO.read(file));
    }

    public AbstractImageDetail(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        init();
    }

    public AbstractImageDetail(GrayImage grayImage){
        bufferedImage = grayImage.getBufferedImage();
    }



    protected abstract void init();

}
