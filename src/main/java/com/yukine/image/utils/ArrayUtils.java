package com.yukine.image.utils;

public class ArrayUtils {


    public static int[][] copy(int[][] source,int w, int h){
        int[][] result = new int[w][h];

        for (int i =0;i < w;i++){
            for (int j = 0; j < h;j++){
                result[i][j] = source[i][j];
            }
        }

        return result;
    }
}
