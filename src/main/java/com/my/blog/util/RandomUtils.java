package com.my.blog.util;

import cn.hutool.core.util.RandomUtil;

import java.util.Random;

public class RandomUtils {


    public static int randomInt(int min,int deltaUp){

        return new Random().nextInt(deltaUp) + min;
    }
    public static int randomInt2(int min,int max){

        //注意是范围的除法!!
        return new Random().nextInt(max) % (max-min+1) + min;
    }

    public static void main(String[] args) {


        //直接用hutool的随机范围生成
        System.out.println(RandomUtil.randomInt(3000000, 3600000));
    }
}
