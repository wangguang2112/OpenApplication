package com.annotation.processor;

import com.annotation.ValueSetter;

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
public class AutonSetProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        for (TypeElement te : set) {
            messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + te.toString());
            for (Element e : roundEnvironment.getElementsAnnotatedWith(te)) {

                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getSimpleName());
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getEnclosingElement()
                                                                            .toString());
                Element faelement = e.getEnclosingElement();
                String clsname;
                if (faelement instanceof PackageElement) {
                    clsname = ((PackageElement) faelement).getQualifiedName()
                                                          .toString();
                } else {
                    clsname = ((TypeElement) faelement).getQualifiedName()
                                                       .toString();
                }
                String name=faelement.getSimpleName().toString();
                ValueSetter valueSetter = e.getAnnotation(ValueSetter.class);
                String gpckName=clsname.substring(0,clsname.lastIndexOf("."));
                // 创建Java文件

               /* Writer w = null;
                JavaFileObject f=null;
                try {
                     f= processingEnv.getFiler().createSourceFile(clsname+"_123");
                    // 在控制台输出文件路径
                    messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + f.toUri());
                    w = f.openWriter();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    PrintWriter pw = new PrintWriter(w);
                    pw.println("package " + gpckName + ";");
                    pw.println("\npublic class " + name + "_123 { ");
                    pw.println("    public  void print(){");
                    pw.println("        System.out.println("+valueSetter.value()+");");
                    pw.println("    }");
                    pw.println("}");
                    pw.flush();
                } finally {
                    try {
                        w.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }*/
            }

        }
        return true;
    }
}
