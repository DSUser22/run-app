package com.dasha.marathonLogic;

import java.util.ArrayList;
import java.util.List;

public class PlanRunning {
    static double[] distanceOfWeek2(int total, byte timesAWeek, double speedTrain){
        double[] distanceOfTheDay = new double[timesAWeek];
        double longRun = (Math.ceil((double)total/2));
        double rec2 = (total-longRun)/2;
        double rec1 = total-longRun-rec2-speedTrain;
        distanceOfTheDay[0] = rec1;
        distanceOfTheDay[1] = rec2;
        distanceOfTheDay[2] = longRun;
        if(timesAWeek ==4 || timesAWeek ==5){
            distanceOfTheDay[3] = speedTrain;
        }
        if(timesAWeek ==5){
            distanceOfTheDay[4]=0;
        }
        return distanceOfTheDay;
    }
    static List<double[]> lastWeek(int countOfDay, int weeks){
        List<double[]> lastWeek = new ArrayList<>();
        lastWeek.add(new double[]{countOfDay++, weeks, 4, 2, 5, 0, 0, 0});
        lastWeek.add(new double[]{countOfDay++, weeks, 4, 2, 5, 0, 0, 0});
        lastWeek.add(new double[]{countOfDay, weeks, 4, 6, 42.2, 0, 0, 0});
        return lastWeek;
    }
    static double[] returnTrain(int digit, int timesAWeek, int level){
        double[] train = new double[8];
        train[2] = level;
        // "№ тр-ки, № недели, этап, --тип, километраж, зал, инт(подходы), инт(км)--"
        switch(digit){
            case 0:
                train[3] = 0; // 1ая восст
                break;
            case 1:
                train[3] = 1; // 2ая восст
                break;
            case 2:
                if(timesAWeek==3){
                    train[3] = 2;
                } else if(timesAWeek==4){
                    train[3] = 5; // инт зал
                    train[5] = 1; // зал
                } else if(timesAWeek==5){
                    train[3] = 4; // зал
                    train[5] = 1; // зал(boolean)
                }
                break;
            case 3:
                if(timesAWeek==4){
                    train[3] = 2;
                } else{
                    train[3] = 3;
                }
                break;
            case 4:
                train[3] = 2;
        }
        return train;
    }
    static List<double[]> returnTrainingList(int[] weeks, int timesAWeek){
        List<double[]> trainingList = new ArrayList<>();
        int type;
        int level = 1;

        // по неделям
        for (int i = 0; i < weeks.length-1; i++) {

            // проверка на уровень
            if(i>17){
                level = 3;
            } else if(i>13){
                level = 2;
            }
            // для интервала
            List<Double> speedTrain = SpeedRunning.returnIntervalAndDistanceTrain(level);

            double[] weekTrainings = distanceOfWeek2(weeks[i], (byte)timesAWeek, speedTrain.get(2));

            // по дням
            for (int j = 0; j < timesAWeek; j++) {
                double[] day = returnTrain(j, timesAWeek, level);
                day[0] = i*timesAWeek+j+1;
                day[1] = i+1;
                day[2] = level;
                type = (int) day[3];
                switch (type){
                    // 3-7 заполнить
                    case 0:
                        // 1В
                        day[4] = weekTrainings[0];
                        break;
                    case 1:
                        // 2В
                        day[4] = weekTrainings[1];
                        break;
                    case 2:
                        // длинная
                        day[4] = weekTrainings[2];
                        break;
                    case 3:
                        // интервал
                        day[6] = speedTrain.get(0);
                        day[7] = speedTrain.get(1);
                        day[4] = speedTrain.get(2);
                        break;
                    case 4:
                        // зал
                        day[5] = 1;
                        break;
                    case 5:
                        // инт+зал
                        day[6] = speedTrain.get(0);
                        day[7] = speedTrain.get(1);
                        day[4] = speedTrain.get(2);
                        day[5] = 1;
                }
                trainingList.add(day);
            }
        }
        trainingList.addAll(lastWeek(trainingList.size()+1, weeks.length));
        return trainingList;
    }
}
