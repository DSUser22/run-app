package com.dasha.usersystem.plan.planService;

import com.dasha.usersystem.training.TrainingType;
import com.dasha.usersystem.training.type.running.*;
import com.dasha.usersystem.training.Training;
import com.dasha.usersystem.training.TrainingService;
import com.dasha.usersystem.plan.Plan;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.time.DayOfWeek.*;

@AllArgsConstructor
@Service
public class TrainingFactoryService {

    private static final DayOfWeek[] threeDaysAWeek = {TUESDAY, THURSDAY, SUNDAY};
    private static final DayOfWeek[] fourDaysAWeek = {TUESDAY, WEDNESDAY, FRIDAY, SUNDAY};
    private static final DayOfWeek[] fiveDaysAWeek = {TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SUNDAY};

    private final TrainingService trainingService;

    @Transactional
    public void createTrainings(Plan plan){
        int weeks = plan.getCountOfWeeks();
        int timesAWeek = plan.getTimesAWeek();
        int longRun = plan.getLongRun();
        int[] ratio = Ratio.ratio(weeks, longRun);
        switch(timesAWeek){
            case 3:
                createThreeTimesAWeek(weeks, plan, ratio);
                break;
            case 4:
                createFourTimesAWeek(weeks, plan, ratio);
                break;
            case 5:
                createFiveTimesAWeek(weeks, plan, ratio);
                break;
        }
    }

    @Transactional
    public void createThreeTimesAWeek(int weeks, Plan plan, int[] ratio){
        LocalDate startWeek = plan.getFirstTrainingDay();
        int timesAWeek = plan.getTimesAWeek();
        for (int week = 1; week < weeks; week++) {
            int[] dist = distanceForTrainings(ratio[week]);
            LocalDate[] dates = datesForAWeek(startWeek, timesAWeek);
            creatingTrainingOR(dist[0],week*3-2, week, dates[0], plan);
            creatingTrainingOR(dist[1],week*3-1, week, dates[1], plan);
            creatingTrainingLR(dist[2],week*3, week, dates[2], plan);
        }
        int trainingNumber = (weeks-1)*3;
        creatingLastWeek(trainingNumber, weeks, startWeek, plan);
    }
    @Transactional
    public void createFourTimesAWeek(int weeks, Plan plan, int[] ratio){
        LocalDate startWeek = plan.getFirstTrainingDay();
        int timesAWeek = plan.getTimesAWeek();
        int level = 1;
        for (int week = 1; week < weeks; week++) {
            if(week>18){
                level = 3;
            } else if(week>13){
                level = 2;
            }
            int[] dist = distanceForTrainings(ratio[week]);
            LocalDate[] dates = datesForAWeek(startWeek, timesAWeek);
            creatingTrainingOR(dist[0],week*4-3, week, dates[0], plan);
            int speedDistance = creatingTrainingSR(level,week*4-2, week, dates[1], plan);
            creatingTrainingOR(dist[1] - speedDistance,week*4-1, week, dates[2], plan);
            creatingTrainingLR(dist[2],week*4, week, dates[3], plan);
        }
        int trainingNumber = (weeks-1)*4;
        creatingLastWeek(trainingNumber, weeks, startWeek, plan);
    }
    @Transactional
    public void createFiveTimesAWeek(int weeks, Plan plan, int[] ratio){
        LocalDate startWeek = plan.getFirstTrainingDay();
        int timesAWeek = plan.getTimesAWeek();
        int level = 1;
        for (int week = 1; week < weeks; week++) {
            if(week>18){
                level = 3;
            } else if(week>13){
                level = 2;
            }
            int[] dist = distanceForTrainings(ratio[week]);
            LocalDate[] dates = datesForAWeek(startWeek, timesAWeek);

            creatingTrainingOR(dist[0],week*5-4, week, dates[0], plan);
            int speedDistance = creatingTrainingSR(level,week*5-3, week, dates[1], plan);
            creatingTrainingOR(dist[1] - speedDistance,week*5-2, week, dates[2], plan);
            creatingTrainingG(week*5-1, week, dates[3], plan);
            creatingTrainingLR(dist[2],week*5, week, dates[5], plan);
            startWeek = startWeek.with(TemporalAdjusters.nextOrSame(MONDAY));
        }
        int trainingNumber = (weeks-1)*5;
        creatingLastWeek(trainingNumber, weeks, startWeek, plan);
    }

    public void creatingTrainingOR(int distance,
                                         int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        OrdinaryRunning r = new OrdinaryRunning(distance);
        trainingService.saveRunning(r);
        Training t = new Training(trainingNumber, week, startOfWeek, plan, r);
        trainingService.saveTraining(t);
    }
    public void creatingTrainingLR(int distance,
                                         int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        LongRunning r = new LongRunning(distance);
        trainingService.saveRunning(r);
        Training t = new Training(trainingNumber, week, startOfWeek, plan, r);
        trainingService.saveTraining(t);
    }
    public int creatingTrainingSR(int level,
                                         int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        SpeedRunning r = new SpeedRunning(level);
        trainingService.saveRunning(r);
        Training t = new Training(trainingNumber, week, startOfWeek, plan, r);
        trainingService.saveTraining(t);
        return r.getDistance();
    }
    public void creatingTrainingG(int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        Gym g = new Gym();
        trainingService.saveRunning(g);
        Training t = new Training(trainingNumber, week, startOfWeek, plan, g);
        trainingService.saveTraining(t);
    }
    public void creatingLastWeek (int trainingNumber,int week, LocalDate startWeek,
                                 Plan plan){
        LocalDate[] dates = datesForAWeek(startWeek, 3);
        creatingTrainingOR(5,trainingNumber+1,week, dates[0], plan);
        creatingTrainingOR(5,trainingNumber+2,week, dates[1], plan);
        OrdinaryRunning r = new OrdinaryRunning(42);
        r.setType(TrainingType.MARATHON);
        trainingService.saveRunning(r);
        Training t = new Training(trainingNumber+3, week, dates[2], plan, r);
        trainingService.saveTraining(t);
    }

    public int[] distanceForTrainings(int weekDistance){
        int[] dist = new int[3];
        dist[2] = (int)(Math.ceil((double)weekDistance/2));
        dist[1] = weekDistance/4;
        dist[0] = weekDistance-dist[1]-dist[2];
        System.out.println("0: "+dist[0]+", 1: "+dist[1]+", 2: "+dist[2]);
        return dist;
    }

    public LocalDate[] datesForAWeek(LocalDate startWeek, int timesAWeek){
        LocalDate[] dates = new LocalDate[timesAWeek];
        DayOfWeek[] current;
        switch (timesAWeek){
            case 3:
                current = threeDaysAWeek;
                break;
            case 4:
                current = fourDaysAWeek;
                break;
            default:
                current = fiveDaysAWeek;
        }
        for (int i = 0; i < timesAWeek; i++) {
            dates[i] = startWeek.with(TemporalAdjusters.next(current[i]));
        }
        return dates;
    }
}
