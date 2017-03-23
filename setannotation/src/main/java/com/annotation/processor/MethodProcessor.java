package com.annotation.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

/**
 * Created by wangguang.
 * Date:2017/2/8
 * Description:
 */
@SupportedAnnotationTypes("com.annotation.MethodSetter")
@SupportedSourceVersion(value = SourceVersion.RELEASE_7)
public class MethodProcessor extends PrintProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        super.process(set, roundEnvironment);
        Messager messager = processingEnv.getMessager();
        for (TypeElement te : set) {
            Set<? extends Element> s = roundEnvironment.getElementsAnnotatedWith(te);
            for (Element e : s) {
                if (e.getKind() == ElementKind.METHOD) {
                    if (e instanceof ExecutableElement) {
                        for (VariableElement ve : ((ExecutableElement) e).getParameters()) {
                            messager.printMessage(Diagnostic.Kind.NOTE, " value=" + ve.toString());
                        }
                    }
                }
            }
        }
        return true;
    }
}
