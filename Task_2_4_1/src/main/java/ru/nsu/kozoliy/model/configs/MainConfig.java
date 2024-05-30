package ru.nsu.kozoliy.model.configs;

import lombok.Data;
import ru.nsu.kozoliy.model.object.Achievement;

import java.util.List;

@Data
public class MainConfig {
    private List<Achievement> enabledAchievements;

    public List<Achievement> getEnabledAchievements() {
        return enabledAchievements;
    }

    public void setEnabledAchievements(List<Achievement> enabledAchievements) {
        this.enabledAchievements = enabledAchievements;
    }

    @Override
    public String toString() {
        return "MainConfig{" +
                "enabledAchievements=" + enabledAchievements +
                '}';
    }
}
