package com.annotation.processor;

import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by wangguang.
 * Date:2017/2/8
 * Description:
 */
@SupportedAnnotationTypes("com.annotation.ClassSetter")
@SupportedSourceVersion(value= SourceVersion.RELEASE_7)
public class ClassProcessor extends PrintProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        super.process(set, roundEnvironment);
        Messager messager = processingEnv.getMessager();
        for(TypeElement te:set) {
            Set<? extends Element> s = roundEnvironment.getElementsAnnotatedWith(te);
        }

        return true;
    }
}
