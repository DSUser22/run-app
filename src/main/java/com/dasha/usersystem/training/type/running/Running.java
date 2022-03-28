package com.dasha.usersystem.training.type.running;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "RUN_TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@Getter
@Setter
public class Running implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
