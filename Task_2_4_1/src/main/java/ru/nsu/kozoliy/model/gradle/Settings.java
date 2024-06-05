package ru.nsu.kozoliy.model.gradle;

import lombok.Builder;

@Builder
public class Settings {
    @Builder.Default
    private boolean runTest = true;
    @Builder.Default
    private boolean runDocs = true;
    @Builder.Default
    private boolean runCheckstyle = true;
}