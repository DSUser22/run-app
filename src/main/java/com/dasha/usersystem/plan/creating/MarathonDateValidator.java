package com.dasha.usersystem.plan.creating;

import org.springframework.stereotype.Service;

@Service
public class MarathonDateValidator {
    public boolean isBiggerThan16(int countOfWeeks){
        return countOfWeeks>16;
    }
}
