package com.yukine.image.entity;

import lombok.Data;

/**
 * 计算阈值
 */
@Data
public class PointInfo implements Comparable<PointInfo>{

    Integer index;

    Integer height;

    Float weight;

    Float value;



    public void valueData(int index){
        if (this.index == index){
            index = -255;
        }
        int dis = Math.abs(this.index - index);
        int augmentingFactor = dis;

        this.value = dis * dis* 1/200  + weight;
    }


    @Override
    public int compareTo(PointInfo pointInfo) {

        return Float.compare(pointInfo.getValue(),value);
    }
}
