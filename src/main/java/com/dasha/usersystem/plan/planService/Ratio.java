package com.dasha.usersystem.plan.planService;

public class Ratio {
    public static int[] ratio(int weeks, int longRun){
        int hardWeeks=weeks-4;
        double ratio;
        double distance;
        double lastDistance = 60d;
        if (longRun==0){
            distance =5;
        } else distance =longRun*2;
        if(hardWeeks>17){
            lastDistance = 70;
        } else if(hardWeeks>13){
            lastDistance = 64;
        }
        double pow = hardWeeks-1;
        double res = lastDistance/distance;
        ratio = Math.pow(res, 1/pow);
        int[] run = new int[weeks];
        run[0] = (int)distance;
        for (int i = 1; i < hardWeeks; i++) {
            distance = distance*ratio;
            run[i]=(int)Math.round(distance);
        }
        double ratioToLow = 0.68d;
        for (int i = hardWeeks; i < weeks-1; i++) {
            run[i] = (int)Math.ceil(run[i-1]*ratioToLow);
        }
        run[weeks-1]=52;
        return run;
    }
}
