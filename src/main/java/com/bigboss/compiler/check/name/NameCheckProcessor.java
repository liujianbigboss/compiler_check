package com.bigboss.compiler.check.name;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @ClassName: NameCheckProcessor
 * @Description: java名称检查处理类（包括类名称、接口名称、方法名称、字段名称、实例变量名称、常量名称）
 * @author: BigBoss
 * @date: 2019年11月19日 20:59
 */
@SupportedAnnotationTypes("com.bigboss.compiler.check.name.CheckName")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NameCheckProcessor extends AbstractProcessor {

    /**
     * 名称检查器
     */
    private NameChecker nameChecker;

    /**
     * 通过初始化方法实例化名称检查器
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    /**
     * 循环输入的语法树节点进行名称检查
     * @param annotations 注解处理器所要处理的注解集合
     * @param roundEnv 语法树节点类访问类
     * @return true:代码发生改变，需要重新编译  false:代码未发生变化，不需要重新编码
     * @Description 此处只是进行命名格式检查，并没有改变代码，因此固定返回false
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //如果当前轮次不是最后一轮（processingOver返回false），请尽量生成尽可能多的输出
        //如果当前回合是最后一个（processingOver返回true），查看是否仍有未处理的注释。如果是这样，编译失败（仅在上一轮！）
        //详细查看javac的多轮编译概念
        if(!roundEnv.processingOver()){
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkName(element);
            }
        }
        return false;
    }

}
