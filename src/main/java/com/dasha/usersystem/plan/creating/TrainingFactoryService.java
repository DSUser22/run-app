package com.dasha.usersystem.plan.creating;

import com.dasha.usersystem.plan.Plan;
import com.dasha.usersystem.training.Training;
import com.dasha.usersystem.training.TrainingType;
import com.dasha.usersystem.training.type.running.Gym;
import com.dasha.usersystem.training.type.running.LongRunning;
import com.dasha.usersystem.training.type.running.OrdinaryRunning;
import com.dasha.usersystem.training.type.running.SpeedRunning;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.*;

@AllArgsConstructor
@Service
public class TrainingFactoryService {

    private static final DayOfWeek[] threeDaysAWeek = {TUESDAY, THURSDAY, SUNDAY};
    private static final DayOfWeek[] fourDaysAWeek = {TUESDAY, WEDNESDAY, FRIDAY, SUNDAY};
    private static final DayOfWeek[] fiveDaysAWeek = {TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SUNDAY};

    @Transactional
    public List<Training> createTrainings(Plan plan){
        int weeks = plan.getCountOfWeeks();
        int timesAWeek = plan.getTimesAWeek();
        int longRun = plan.getLongRun();
        int[] ratio = Ratio.ratio(weeks, longRun);
        switch(timesAWeek){
            case 3:
                return createThreeTimesAWeek(weeks, plan, ratio);
            case 4:
                return createFourTimesAWeek(weeks, plan, ratio);
            default:
                return createFiveTimesAWeek(weeks, plan, ratio);
        }
    }

    @Transactional
    public List<Training> createThreeTimesAWeek(int weeks, Plan plan, int[] ratio){
        List<Training> trainings = new ArrayList<>();

        LocalDate startWeek = plan.getFirstTrainingDay();
        int timesAWeek = plan.getTimesAWeek();
        for (int week = 1; week < weeks; week++) {
            int[] dist = distanceForTrainings(ratio[week]);
            LocalDate[] dates = datesForAWeek(startWeek, timesAWeek);
            trainings.add(creatingTrainingOR(dist[0],week*3-2, week, dates[0], plan));
            trainings.add(creatingTrainingOR(dist[1],week*3-1, week, dates[1], plan));
            trainings.add(creatingTrainingLR(dist[2],week*3, week, dates[2], plan));
            startWeek = startWeek.with(TemporalAdjusters.next(MONDAY));
        }
        int trainingNumber = (weeks-1)*3;
        trainings.addAll(creatingLastWeek(trainingNumber, weeks, startWeek, plan));
        return trainings;
    }
    @Transactional
    public List<Training> createFourTimesAWeek(int weeks, Plan plan, int[] ratio){
        List<Training> trainings = new ArrayList<>();
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
            trainings.add(creatingTrainingOR(dist[0],week*4-3, week, dates[0], plan));
            Training t = creatingTrainingSR(level,week*4-2, week, dates[1], plan);
            SpeedRunning sr = (SpeedRunning) t.getRunning();
            trainings.add(t);
            trainings.add(creatingTrainingOR(dist[1] - sr.getDistance(),week*4-1, week, dates[2], plan));
            trainings.add(creatingTrainingLR(dist[2],week*4, week, dates[3], plan));
            startWeek = startWeek.with(TemporalAdjusters.next(MONDAY));
        }
        int trainingNumber = (weeks-1)*4;
        trainings.addAll(creatingLastWeek(trainingNumber, weeks, startWeek, plan));
        return trainings;
    }
    @Transactional
    public List<Training> createFiveTimesAWeek(int weeks, Plan plan, int[] ratio){
        List<Training> trainings = new ArrayList<>();
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

            trainings.add(creatingTrainingOR(dist[0],week*5-4, week, dates[0], plan));
            Training t = creatingTrainingSR(level,week*5-3, week, dates[1], plan);
            SpeedRunning sr = (SpeedRunning) t.getRunning();
            trainings.add(t);
            trainings.add(creatingTrainingOR(dist[1] - sr.getDistance(),week*5-2, week, dates[2], plan));
            trainings.add(creatingTrainingG(week*5-1, week, dates[3], plan));
            trainings.add(creatingTrainingLR(dist[2],week*5, week, dates[4], plan));
            startWeek = startWeek.with(TemporalAdjusters.next(MONDAY));
        }
        int trainingNumber = (weeks-1)*5;
        trainings.addAll(creatingLastWeek(trainingNumber, weeks, startWeek, plan));
        return trainings;
    }

    public Training creatingTrainingOR(int distance,
                                         int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        OrdinaryRunning r = new OrdinaryRunning(distance);
        return new Training(trainingNumber, week, startOfWeek, plan, r);
    }
    public Training creatingTrainingLR(int distance,
                                         int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        LongRunning r = new LongRunning(distance);
        return new Training(trainingNumber, week, startOfWeek, plan, r);
    }
    public Training creatingTrainingSR(int level,
                                         int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        SpeedRunning r = new SpeedRunning(level);
        return new Training(trainingNumber, week, startOfWeek, plan, r);
    }
    public Training creatingTrainingG(int trainingNumber,
                                         int week,
                                         LocalDate startOfWeek,
                                         Plan plan){
        return new Training(trainingNumber, week, startOfWeek, plan, new Gym());
    }
    public List<Training> creatingLastWeek (int trainingNumber,int week, LocalDate startWeek,
                                 Plan plan){
        List<Training> lastWeek = new ArrayList<>();
        LocalDate[] dates = datesForAWeek(startWeek, 3);
        lastWeek.add(creatingTrainingOR(5,trainingNumber+1,week, dates[0], plan));
        lastWeek.add(creatingTrainingOR(5,trainingNumber+2,week, dates[1], plan));
        OrdinaryRunning r = new OrdinaryRunning(42);
        r.setType(TrainingType.MARATHON);
        lastWeek.add(new Training(trainingNumber+3, week, dates[2], plan, r));
        return lastWeek;
    }

    public int[] distanceForTrainings(int weekDistance){
        int[] dist = new int[3];
        dist[2] = (int)(Math.ceil((double)weekDistance/2));
        dist[1] = weekDistance/4;
        dist[0] = weekDistance-dist[1]-dist[2];
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
