package com.dasha.marathonLogic;
import java.util.*;

public class SpeedRunning {
    private static final Map<Integer, List<Double>> level1 = new HashMap<>();
    private static final Map<Integer, List<Double>> level2 = new HashMap<>();
    private static final Map<Integer, List<Double>> level3 = new HashMap<>();

    public static Map<Integer, List<Double>> getLevel1() {
        return level1;
    }
    public static Map<Integer, List<Double>> getLevel2() {
        return level2;
    }
    public static Map<Integer, List<Double>> getLevel3() {
        return level3;
    }

    static {
        level1.put(1, Arrays.asList(6d, 100d));
        level1.put(2, Arrays.asList(4d, 150d));
        level1.put(3, Arrays.asList(3d, 200d));

        level2.put(1, Arrays.asList(5d, 300d));
        level2.put(2, Arrays.asList(3d, 500d));
        level2.put(3, Arrays.asList(4d, 400d));

        level3.put(1, Arrays.asList(5d, 600d));
        level3.put(2, Arrays.asList(6d, 500d));
        level3.put(3, Arrays.asList(4d, 750d));
    }

    static List<Double> returnIntervalAndDistanceTrain(int level){
        int random = new Random().nextInt(3)+1;
        switch (level){
            case 1:
                return returnTotalSpeedTrain(new ArrayList<>(getLevel1().get(random)));
            case 2:
                return returnTotalSpeedTrain(new ArrayList<>(getLevel2().get(random)));
            default:
                return returnTotalSpeedTrain(new ArrayList<>(getLevel3().get(random)));
        }
    }
    static List<Double> returnTotalSpeedTrain(List<Double> intervalsAndDistances){
        intervalsAndDistances.add((intervalsAndDistances.get(0)*intervalsAndDistances.get(1))/1000);
        return intervalsAndDistances;
    }
}
