package com.annotation.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by wangguang.
 * Date:2017/2/8
 * Description:
 */
@SupportedSourceVersion(value= SourceVersion.RELEASE_7)
public abstract class PrintProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, ":****************"+this.getClass().getSimpleName()+"**************:");
        for (TypeElement te : set) {
            messager.printMessage(Diagnostic.Kind.NOTE, "===: " + te.toString()+" :===");
            messager.printMessage(Diagnostic.Kind.NOTE, "type: " + te.getKind());
            messager.printMessage(Diagnostic.Kind.NOTE, "name: " + te.getSimpleName());
            messager.printMessage(Diagnostic.Kind.NOTE, "fana: " + te.getEnclosingElement());
            messager.printMessage(Diagnostic.Kind.NOTE, " ");
            Set<? extends Element> sourceSet=roundEnvironment.getElementsAnnotatedWith(te);
            for(Element e:sourceSet) {
                messager.printMessage(Diagnostic.Kind.NOTE, "###: " + e.toString() + " :###");
                messager.printMessage(Diagnostic.Kind.NOTE, "type: " + e.getKind());
                messager.printMessage(Diagnostic.Kind.NOTE, "name: " + e.getSimpleName());
                messager.printMessage(Diagnostic.Kind.NOTE, "fana: " + e.getEnclosingElement());
                messager.printMessage(Diagnostic.Kind.NOTE, " ");
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, " ");
        return true;
    }
}
