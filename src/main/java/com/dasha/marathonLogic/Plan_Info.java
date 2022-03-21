package com.dasha.marathonLogic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TreeMap;

import static java.time.temporal.ChronoUnit.DAYS;

public class Plan_Info {
    static Map<String, Object> weeks(int year, int month, int day) {
        Calendar currentDay;
        Calendar marathonDay;
        LocalDate localMarathonDay;
        Calendar startTrain;
        LocalDate localStartTrain;
        int currentDayOfWeek;
        int weeks;

        currentDay = Calendar.getInstance();
        marathonDay = new GregorianCalendar(year, (month - 1), day);

        currentDayOfWeek = currentDay.get(Calendar.DAY_OF_WEEK) - 1;

        startTrain = Calendar.getInstance();
        startTrain.add(Calendar.DAY_OF_MONTH, (7 - currentDayOfWeek + 1) % 7);

        localMarathonDay = marathonDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localStartTrain = startTrain.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        System.out.println(localStartTrain);
        System.out.println(localMarathonDay);

        int days = (int) DAYS.between(localStartTrain, localMarathonDay) + 1;
        weeks = days / 7;
        Map<String, Object> plan_info = new TreeMap<>();
        plan_info.put("currentDay", currentDay);
        plan_info.put("marathonDay", marathonDay);
        plan_info.put("firstTrainingDay", startTrain);
        plan_info.put("weeks", weeks);
        return plan_info;
    }
    static int[] ratio(int weeks, int longRun){
        int hardWeeks=weeks-4;
        double ratio;
        double distance;
        double lastDistance = 60d;
        if (longRun==0){
            distance =5;
        } else distance =longRun;
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
