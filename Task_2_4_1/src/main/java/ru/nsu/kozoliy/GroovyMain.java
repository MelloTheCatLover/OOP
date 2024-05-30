package ru.nsu.kozoliy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.kozoliy.model.configs.ChangesConfig;
import ru.nsu.kozoliy.model.configs.Group;
import ru.nsu.kozoliy.model.configs.MainConfig;
import ru.nsu.kozoliy.model.object.StudentInfo;
import ru.nsu.kozoliy.model.tasks.TaskConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GroovyMain {
    private final GroovyShell shell;

    public GroovyMain() {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(DelegatingScript.class.getName());
        this.shell = new GroovyShell(GroovyMain.class.getClassLoader(), new Binding(), compilerConfiguration);
    }

    public DelegatingScript getScript(String filePath) throws IOException {
        return getScript(new File(filePath));
    }
    public void parseScript(String filePath, Object delegate) throws IOException {
        DelegatingScript script = getScript(filePath);
        script.setDelegate(delegate);
        script.run();
    }

    public DelegatingScript getScript(File file) throws IOException {
        return (DelegatingScript) shell.parse(file);
    }






    private MainConfig readGeneral() {
        MainConfig mainConfig = new MainConfig();
        try {
            parseScript("scripts/mainScript.groovy", mainConfig);
        } catch (IOException e) {
            e.printStackTrace(); // Печать исключения для отладки
            throw new RuntimeException(e);
        }
        return mainConfig;
    }

    private TaskConfig readTasks(MainConfig generalConfig) {
        TaskConfig taskConfig = new TaskConfig(generalConfig);
        try {
            parseScript("scripts/task.groovy", taskConfig);
        } catch (IOException e) {
            e.printStackTrace(); // Печать исключения для отладки
            throw new RuntimeException(e);
        }
        return taskConfig;
    }

    private Group readGroup(MainConfig generalConfig) {
        Group group = new Group(generalConfig);
        try {
            parseScript("scripts/22215.groovy", group);
        } catch (IOException e) {
            e.printStackTrace(); // Печать исключения для отладки
            throw new RuntimeException(e);
        }
        return group;
    }

    private ChangesConfig readFixes(Map<String, StudentInfo> studentInformation) {
        ChangesConfig changesConfig = new ChangesConfig(studentInformation);
        try {
            parseScript("scripts/changes.groovy", changesConfig);
        } catch (IOException e) {
            e.printStackTrace(); // Печать исключения для отладки
            throw new RuntimeException(e);
        }
        return changesConfig;
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

    public static void main(String[] args) {
        System.out.println("---CAAATS MEOOOW---!");
        GroovyMain groovy = new GroovyMain();
        MainConfig generalConfig = groovy.readGeneral();
        System.out.println(generalConfig);
        Group group = groovy.readGroup(generalConfig);
        System.out.println(group);
        TaskConfig taskConfig = groovy.readTasks(generalConfig);
        System.out.println(taskConfig);

        Map<String, StudentInfo> studentInformationMap =
                getStudentInformationMap(group, taskConfig);

        ChangesConfig changes = groovy.readFixes(studentInformationMap);

        System.out.println(changes.getInformationMap().get("MelloTheCatLover"));
    }
}