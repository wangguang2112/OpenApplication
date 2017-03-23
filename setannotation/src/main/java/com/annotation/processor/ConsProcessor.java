package com.annotation.processor;

import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by wangguang.
 * Date:2017/2/8
 * Description:
 */
@SupportedAnnotationTypes("com.annotation.ConsProcessor")
@SupportedSourceVersion(value= SourceVersion.RELEASE_7)
public class ConsProcessor extends PrintProcessor {


}
