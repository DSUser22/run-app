package com.dasha.marathonLogic;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarathonLogicService {
    // TODO:: переписать логику с листа и массивов на классы
    static List<double[]> returnTrainingList(String marathonDay, int longRun, int timesAWeek){
        int[] date = scanData(marathonDay);
        int weeks = (int) Plan_Info.weeks(date[2], date[1],date[0]).get("weeks");
        int[] kmPerWeeks = Plan_Info.ratio(weeks, longRun);
        List<double[]> distances = PlanRunning.returnTrainingList(kmPerWeeks, timesAWeek);
        /*
        for (double[] day: distances){
            System.out.printf("№ тр-ки: %d; № недели: %d; этап: %d; тип: %d ; км: %.2f; зал: %d; подходы: %d; длина: %d \n",
                    (int) day[0], (int) day[1], (int)day[2], (int)day[3], day[4], (int)day[5], (int)day[6], (int)day[7]);
        }*/
        return distances;
    }
    static int[] scanData(String marathonDay){
        Matcher matcher = Pattern.compile("^(([0-9]{1,2})[./:]([0-9]{1,2})[./:]([0-9]{4}))$").matcher(marathonDay);
        int[] data = new int[3];
        try{
            matcher.find();
            for (int i = 0; i < 3; i++) {
                data[i] = Integer.parseInt(matcher.group(i+2));
            }
            System.out.println(Arrays.toString(data));
        } catch(IllegalStateException e){
            System.out.println("неверные данные");
        }
        return data;
    }
}
