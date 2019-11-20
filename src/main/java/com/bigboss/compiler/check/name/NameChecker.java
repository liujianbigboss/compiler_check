package com.bigboss.compiler.check.name;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.regex.Pattern;

/**
 * @ClassName: NameChecker
 * @Description: 名称检查器（具体检查规则实现类）：如果命名检查出现不规范情况，则会输出一个编译器的警告（warning）信息
 * @Description Visitor模式访问语法树中的元素
 * @author: BigBoss
 * @date: 2019年11月19日 21:02
 */
public class NameChecker extends ElementScanner8 {

    /**
     * 类、接口、枚举名称匹配正则
     */
    private static final String TYPE_PATTERN = "^([A-Z]{1}[a-z]{1,}){1,}$";

    /**
     * 变量或者参数名称匹配正则
     */
    private static final String VARIABLE_OR_PARAMETER_PATTERN = "^([a-z]{1,}){1,}([A-Z]{1}[a-z]{1,}){0,}$";

    /**
     * 常量名称匹配正则
     */
    private static final String CONSTANT_PATTERN = "^[A-Z]{1,}(_{1}[A-Z]{1,}){0,}$";

    /**
     * 包路径匹配规则
     */
    private static final String PACKAGE_PATTERN = "^([a-z]{1,}){1,}(\\.{1}[a-z]{1,}){0,}$";

    private final Messager messager;

    public NameChecker(ProcessingEnvironment processingEnv) {
        this.messager = processingEnv.getMessager();
    }

    /**
     * 名称检查入口方法
     * @param element 语法树节点
     */
    public void checkName(Element element){
        super.scan(element);
    }

    /**
     * 检查java类名称
     * 首字母大写，驼峰命名法
     * @param e
     * @param o
     * @return
     */
    @Override
    public Object visitType(TypeElement e, Object o) {
        Name name = e.getSimpleName();
        if(Pattern.matches(TYPE_PATTERN, name)){
            messager.printMessage(Diagnostic.Kind.WARNING,"类、接口、枚举名称"+name+"应符合驼峰式命名法且首字母大写-[正则:"+TYPE_PATTERN+"]",e);
        }
        return super.visitType(e, o);
    }

    /**
     * 检查变量名称
     * 1、常量：所有字符大写，中间下划线连接
     * 2、非常量：首字母小写，驼峰命名法
     * @param e
     * @param o
     * @return
     */
    @Override
    public Object visitVariable(VariableElement e, Object o) {
        Name name = e.getSimpleName();
        //如果为常量则按照大写字母规范检查，否则按照驼峰命名法检查
        if(null != e.getConstantValue()){
            if(Pattern.matches(CONSTANT_PATTERN, name)){
                messager.printMessage(Diagnostic.Kind.WARNING,"常量名称"+name+"应所有字母大写且以下划线连接-[正则:"+CONSTANT_PATTERN+"]",e);
            }
        }else {
            if(Pattern.matches(VARIABLE_OR_PARAMETER_PATTERN, name)){
                messager.printMessage(Diagnostic.Kind.WARNING,"变量名称"+name+"应符合驼峰式命名法且首字母小写-[正则:"+VARIABLE_OR_PARAMETER_PATTERN+"]",e);
            }
        }
        return super.visitVariable(e, o);
    }

    /**
     * 检查方法名称
     * 首字母小写，驼峰命名法
     * @param e
     * @param o
     * @return
     */
    @Override
    public Object visitExecutable(ExecutableElement e, Object o) {
        Name name = e.getSimpleName();
        if(Pattern.matches(VARIABLE_OR_PARAMETER_PATTERN, name)){
            messager.printMessage(Diagnostic.Kind.WARNING,"方法名称"+name+"应符合驼峰式命名法且首字母小写-[正则:"+VARIABLE_OR_PARAMETER_PATTERN+"]",e);
        }
        return super.visitExecutable(e, o);
    }

    /**
     * 检查参数名称
     * 首字母小写，驼峰命名法
     * @param e
     * @param o
     * @return
     */
    @Override
    public Object visitTypeParameter(TypeParameterElement e, Object o) {
        Name name = e.getSimpleName();
        if(Pattern.matches(VARIABLE_OR_PARAMETER_PATTERN, name)){
            messager.printMessage(Diagnostic.Kind.WARNING,"参数名称"+name+"应符合驼峰式命名法且首字母小写-[正则:"+VARIABLE_OR_PARAMETER_PATTERN+"]",e);
        }
        return super.visitTypeParameter(e, o);
    }

    /**
     * 检查包路径
     * 包路径字母全部小写
     * @param e
     * @param o
     * @return
     */
    @Override
    public Object visitPackage(PackageElement e, Object o) {
        Name name = e.getSimpleName();
        if(Pattern.matches(PACKAGE_PATTERN, name)){
            messager.printMessage(Diagnostic.Kind.WARNING,"包路径"+name+"应所有字母小写且以.连接-[正则:"+PACKAGE_PATTERN+"]",e);
        }
        return super.visitPackage(e, o);
    }

}
