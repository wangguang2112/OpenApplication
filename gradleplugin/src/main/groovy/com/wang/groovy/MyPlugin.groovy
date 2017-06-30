package com.wang.groovy

import org.gradle.api.Plugin
import org.gradle.api.Project;

public class MyPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.tasks.withType("javaCompile"){it->
            println it;
        }
    }
}