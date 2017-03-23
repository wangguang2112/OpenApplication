package com.annotation.processor;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Created by wangguang.
 * Date:2017/2/8
 * Description:
 */
@SupportedAnnotationTypes("com.annotation.LocalSetter")
@SupportedSourceVersion(value= SourceVersion.RELEASE_7)
public class LocalProcessor extends PrintProcessor {


}
