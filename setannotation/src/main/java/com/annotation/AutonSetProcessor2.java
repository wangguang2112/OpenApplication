package com.annotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by wangguang.
 * Date:2017/2/7
 * Description:
 */
@SupportedAnnotationTypes("com.annotation.ValueSetter")
@SupportedSourceVersion(value= SourceVersion.RELEASE_7)
public class AutonSetProcessor2 extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement te : set) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + te.toString());
            for (Element e : roundEnvironment.getElementsAnnotatedWith(te)) {
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + te.toString());
                ValueSetter valueSetter=e.getAnnotation(ValueSetter.class);

            }

        }
        return true;
    }
}
