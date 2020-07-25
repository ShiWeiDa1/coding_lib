package myjava.design.patterns.factory;

import myjava.design.patterns.entity.Food;
import myjava.design.patterns.entity.HamburgerFood;
import myjava.design.patterns.entity.SandwichFood;

import java.util.Objects;

/**
 *@className: SimpleFactory
 *@description: 简单工厂模式(创建型)
 *@author: weida.shi
 *@date: 2020/5/16 18:32
 *@version: V1.0
 **/
public class SimpleFactory {

    /**
     * 踩坑:包名不能java开头
     * @param name
     * @return
     */

    public static Food makeFood(String name){
        if ("Hamburger".equals(name)) {
            return new HamburgerFood();
        }else if("Sandwich".equals(name)){
            return new SandwichFood();
        }else{
            return null;
        }
    }

}

class App{
    public static void main(String[] args) {
        Food hamburger = SimpleFactory.makeFood("Hamburger");
        if (Objects.nonNull(hamburger)) {
            hamburger.sayName();
        }
    }
}