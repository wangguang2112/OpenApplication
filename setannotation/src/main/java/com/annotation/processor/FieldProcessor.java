package com.annotation.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by wangguang.
 * Date:2017/2/8
 * Description:
 */
@SupportedAnnotationTypes("com.annotation.FieldSetter")
@SupportedSourceVersion(value = SourceVersion.RELEASE_7)
public class FieldProcessor extends PrintProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        super.process(set, roundEnvironment);
        Messager messager = processingEnv.getMessager();
        for (TypeElement te : set) {
            MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                                              .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                              .returns(void.class)
                                              .addParameter(String[].class, "args")
                                              .addStatement("$T.out.println($S)", System.class, "Hello")
                                              .build();
            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                                          .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                          .addMethod(methodSpec)
                                          .build();
            try {
                JavaFile javaFile = JavaFile.builder("com.guang.wang.openapplication", helloWorld)
                                            .addFileComment("aaaaaaaa")
                                            .build();
                Filer f=processingEnv.getFiler();
                javaFile.writeTo(f);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

        return true;
    }
}
