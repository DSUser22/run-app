package com.dasha.usersystem.plan;

import org.springframework.stereotype.Service;

@Service
public class MarathonDateValidator {
    public boolean test(int countOfWeeks){
        return countOfWeeks>16;
    }
}
