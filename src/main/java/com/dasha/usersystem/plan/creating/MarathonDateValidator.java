package com.dasha.usersystem.plan.creating;

import org.springframework.stereotype.Service;

@Service
public class MarathonDateValidator {
    public boolean test(int countOfWeeks){
        return countOfWeeks>16;
    }
}
