package com.yukine.image.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 灰度范围
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrayScope {

    int start = 0;

    int end = 255;

    public void setStart(int start){
        if (start > 255 || start <0){
            throw new RuntimeException("灰度范围在[0,255]之间");
        }
        this.start = start;
    }


    public void setEnd(int end){
        if (end > 255 || end <0){
            throw new RuntimeException("灰度范围在[0,255]之间");
        }
        this.end = end;
    }

    public String toString(){
        return " [" + start +"," + end +"] ";
    }
}
