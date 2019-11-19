package com.bigboss.compiler.check.name;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementScanner8;

/**
 * @ClassName: NameChecker
 * @Description: 名称检查器（具体检查规则实现类）：如果命名检查出现不规范情况，则会输出一个编译器的警告（warning）信息
 * @author: BigBoss
 * @date: 2019年11月19日 21:02
 */
public class NameChecker extends ElementScanner8 {

    /**
     * 名称检查入口方法
     * @param element 语法树节点
     */
    public void checkName(Element element){

    }

}
