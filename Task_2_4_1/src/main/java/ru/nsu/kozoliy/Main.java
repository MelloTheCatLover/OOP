package ru.nsu.kozoliy;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.kozoliy.model.object.StudentClass;
import ru.nsu.kozoliy.model.object.StudentGroupClass;

import java.io.File;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello CATS!");
        testGroup();
    }

    private static void testStudent() {


        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = null;


        try {
            script = (DelegatingScript) sh.parse(new File("scripts/ts.groovy"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        StudentClass config = new StudentClass();
        script.setDelegate(config);
        script.run();
        System.out.println(config);
    }

    private static void testGroup() {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = null;
        try {
            script = (DelegatingScript) sh.parse(new File("scripts/group.groovy"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StudentGroupClass config = new StudentGroupClass();
        script.setDelegate(config);
        script.run();
        System.out.println(config);

    }
}