package ru.nsu.kozoliy.dslFilesPackage;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.kozoliy.GroovyMain;
import ru.nsu.kozoliy.model.configs.ChangesConfig;
import ru.nsu.kozoliy.model.configs.Group;
import ru.nsu.kozoliy.model.configs.MainConfig;
import ru.nsu.kozoliy.model.object.StudentInfo;
import ru.nsu.kozoliy.model.tasks.TaskConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private final GroovyShell sh;

    public Parser() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        this.sh = new GroovyShell(GroovyMain.class.getClassLoader(), new Binding(), cc);
    }

    public DelegatingScript getScript(File file) throws IOException {
        return (DelegatingScript) sh.parse(file);
    }

    public DelegatingScript getScript(String filePath) throws IOException {
        return getScript(new File(filePath));
    }

    public void parseScript(String filePath, Object delegate) throws IOException {
        DelegatingScript script = getScript(filePath);
        script.setDelegate(delegate);
        script.run();
    }


    public MainConfig readGeneral(String scriptPath) {
        MainConfig mainConfig = new MainConfig();
        try {
            parseScript(scriptPath, mainConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return mainConfig;
    }

    public TaskConfig readTasks(MainConfig generalConfig, String scriptPath) {
        TaskConfig taskConfig = new TaskConfig(generalConfig);
        try {
            parseScript(scriptPath, taskConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return taskConfig;

    }

    public Group readGroup(MainConfig mainConfig, String scriptPath) {
        Group groupConfig = new Group(mainConfig);
        try {
            parseScript(scriptPath, groupConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return groupConfig;
    }

    public ChangesConfig readFixes(Map<String, StudentInfo> studentInformation, String scriptPath) {
        ChangesConfig fixConfig = new ChangesConfig(studentInformation);
        try {
            parseScript(scriptPath, fixConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fixConfig;
    }

    public static Map<String, StudentInfo> getStudentInformationMap(Group groupConfig,
                                                                           TaskConfig taskConfig) {
        Map<String, StudentInfo> informationMap = new HashMap<>();
        groupConfig.getStudentConfigs()
                .forEach(studentConfig -> informationMap.put(studentConfig.getGitName(),
                        new StudentInfo(studentConfig, taskConfig)
                ));
        return informationMap;
    }
}