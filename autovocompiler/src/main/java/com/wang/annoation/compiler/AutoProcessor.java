package com.wang.annoation.compiler;


import com.google.auto.service.AutoService;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.wang.annoation.compiler.model.ClassModel;
import com.wang.autovo.AutoVo;
import com.wang.autovo.BooleanField;
import com.wang.autovo.FloatField;
import com.wang.autovo.IntField;
import com.wang.autovo.ListField;
import com.wang.autovo.LongField;
import com.wang.autovo.ShortField;
import com.wang.autovo.StringField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AutoProcessor extends AbstractProcessor {

    Map<String, ClassModel> mClassList;

    Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mClassList = new HashMap<>();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, ":start");
        for (TypeElement annoe : set) {
            if ("AutoVo".equals(annoe.getSimpleName().toString())) {
                for (Element e : roundEnvironment.getElementsAnnotatedWith(annoe)) {
                    if (e.getKind() == ElementKind.FIELD) {
                        String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                        ClassModel mClassModel = mClassList.get(key);
                        if (mClassModel == null) {
                            mClassModel = new ClassModel();
                            mClassModel.clear();
                        }
                        AutoVo a = e.getAnnotation(AutoVo.class);
                        if(a!=null) {
                            mClassModel.count = a.count();
                            mClassModel.className = a.name();
                            mClassModel.open = true;
                            mClassModel.pack=a.pack().equals("")?mClassModel.pack:a.pack();
                            mClassList.put(key, mClassModel);
                        }
                    }
                }
            } else {
               findAllAnnotation(roundEnvironment);
            }
        }

        for (ClassModel model : mClassList.values()) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, ":create class " + model.className);
            makeJava(model);
        }
//        mMessager.printMessage(Diagnostic.Kind.NOTE, ":"+mClassModel.toString());
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add("com.wang.autovo.AutoVo");
        set.add("com.wang.autovo.BooleanField");
        set.add("com.wang.autovo.FloatField");
        set.add("com.wang.autovo.IntField");
        set.add("com.wang.autovo.LongField");
        set.add("com.wang.autovo.ShortField");
        set.add("com.wang.autovo.StringField");
        return set;
    }

    /**
     * 解析剩下的标注
     */
    public void findAllAnnotation(RoundEnvironment env) {
        for (Element e : env.getElementsAnnotatedWith(IntField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                IntField field = e.getAnnotation(IntField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    String[] names=field.name();
                    for (String n :names) {
                        mClassModel.field.put(n, TypeName.INT);
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }

        for (Element e : env.getElementsAnnotatedWith(BooleanField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                BooleanField field = e.getAnnotation(BooleanField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    for (String n : field.name()) {
                        mClassModel.field.put(n, TypeName.BOOLEAN);
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }
        for (Element e : env.getElementsAnnotatedWith(FloatField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                FloatField field = e.getAnnotation(FloatField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    for (String n : field.name()) {
                        mClassModel.field.put(n, TypeName.FLOAT);
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }
        for (Element e : env.getElementsAnnotatedWith(LongField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                LongField field = e.getAnnotation(LongField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    for (String n : field.name()) {
                        mClassModel.field.put(n,TypeName.LONG);
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }
        for (Element e : env.getElementsAnnotatedWith(ShortField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                ShortField field = e.getAnnotation(ShortField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    for (String n : field.name()) {
                        mClassModel.field.put(n,TypeName.SHORT);
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }
        for (Element e : env.getElementsAnnotatedWith(StringField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                StringField field = e.getAnnotation(StringField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    for (String n : field.name()) {
                        mClassModel.field.put(n, TypeName.get(String.class));
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }
        for (Element e : env.getElementsAnnotatedWith(ListField.class)) {
            if (e.getKind() == ElementKind.FIELD) {
                ListField field = e.getAnnotation(ListField.class);
                String key = ((TypeElement) e.getEnclosingElement()).getQualifiedName() + "#" + e.getSimpleName();
                if (field != null) {
                    ClassModel mClassModel = mClassList.get(key);
                    if (mClassModel == null) {
                        mClassModel = new ClassModel();
                    }
                    for (String n : field.name()) {
                        mClassModel.field.put(n, TypeName.get(List.class));
                    }
                    mClassList.put(key, mClassModel);
                }
            }
        }
    }

    /**
     * 构建Java文件
     */
    private void makeJava(ClassModel model) {
        if (model.open == false) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, ":create class " + model.className + " error ,make sure having used @AutoVo");
            return;
        }
        if(model.build){
            mMessager.printMessage(Diagnostic.Kind.NOTE, ":create class " + model.className + " error ,hava create");
            return;
        }
        TypeSpec cls = getClassType(model);
        try {
            JavaFile javaFile = JavaFile.builder(model.pack, cls)
                                        .addFileComment("AutoVo auto make ,please don't change!")
                                        .build();
            Filer f = processingEnv.getFiler();
            javaFile.writeTo(f);
            model.build=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依据model 构建TypeSpec
     */
    private TypeSpec getClassType(ClassModel model) {
        TypeSpec.Builder clsbuilder = TypeSpec.classBuilder(model.className)
                                              .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        /**
         * 构造
         */

        /**
         * 默认构造函数
         */
        MethodSpec defaultCons=MethodSpec
                .constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .build();
        clsbuilder.addMethod(defaultCons);

        /**
         * 带参构造函数
         */
        MethodSpec.Builder paramConsBuilder=MethodSpec
                .constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        for (String name : model.field.keySet()) {
            paramConsBuilder.addParameter(model.field.get(name),name);
            paramConsBuilder.addStatement("this.$N=$N;",name,name);
            TypeName type = model.field.get(name);
            FieldSpec field = FieldSpec.builder(type, name, Modifier.PUBLIC)
                                       .build();

            clsbuilder.addField(field);

            /**
             * set函数
             */
            if (model.hasSetter) {
                MethodSpec setMethod = MethodSpec.methodBuilder("set" + name.substring(0, 1)
                                                                            .toUpperCase() + name.substring(1))
                                                 .returns(void.class)
                                                 .addStatement("this.$N=value", name)
                                                 .addParameter(type, "value")
                                                 .addModifiers(Modifier.PUBLIC)
                                                 .build();
                clsbuilder.addMethod(setMethod);
            }
            /**
             * get函数
             */
            if (model.hasGetter) {
                MethodSpec getMethod = MethodSpec.methodBuilder("get" + name.substring(0, 1)
                                                                            .toUpperCase() + name.substring(1))
                                                 .returns(type)
                                                 .addStatement("return $N", name)
                                                 .addModifiers(Modifier.PUBLIC)
                                                 .build();

                clsbuilder.addMethod(getMethod);
            }
        }
        clsbuilder.addMethod(paramConsBuilder.build());
        /**
         * 构造toString
         */
        if (model.hasToString) {
            /**
             * 构建toString
             */
            StringBuilder stateBuilder = new StringBuilder();
            List<String> args = new ArrayList<>();
            args.add(model.className);

            stateBuilder.append("return \"$N{\"");
            for (String name : model.field.keySet()) {
                stateBuilder.append("+\"$N =\"+" + name + "+\",\"");
                args.add(name);
            }
            stateBuilder.append("+\"}\"");
            MethodSpec toStringMethod = MethodSpec.methodBuilder("toString")
                                                  .addStatement(stateBuilder.toString(), args.toArray())
                                                  .returns(String.class)
                                                  .addModifiers(Modifier.PUBLIC)
                                                  .build();
            clsbuilder.addMethod(toStringMethod);
        }
        return clsbuilder.build();
    }
}
