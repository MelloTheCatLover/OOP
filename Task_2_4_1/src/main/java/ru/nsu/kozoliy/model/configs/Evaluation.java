package ru.nsu.kozoliy.model.configs;

import lombok.Data;

@Data
public class Evaluation {
    Double taskScore = 1.0;
    Double firstDeadLinePenalty = 0.5;
    Double secondDeadLinePenalty = 0.5;
    Double jacocoPercentage = 80.0;
    Double jacocoScore = 1.0;
    Double checkStyleScore = 1.0;
}