package myjava.design.patterns.factory;

import myjava.design.patterns.entity.Food;

/**
 *@className: NormalFactory
 *@description:
 *@author: weida.shi
 *@date: 2020/5/16 18:55
 *@version: V1.0
 **/
public interface FoodFactory {

    /**
     *
     * @param name
     * @return
     */
    Food makeFood(String name);

}

class ChineseFoodFactory implements FoodFactory {

    @Override
    public Food makeFood(String name) {
        if ("A".equals(name)) {
            return new ChineseFoodA();
        } else if ("B".equals(name)) {
            return new ChineseFoodB();
        } else {
            return null;
        }
    }

    public static class ChineseFoodA extends Food {
    }

    public static class ChineseFoodB extends Food {
    }
}

class AmericaFoodFactory implements FoodFactory {

    @Override
    public Food makeFood(String name) {
        if ("A".equals(name)) {
            return new AmericaFoodA();
        } else if ("B".equals(name)) {
            return new AmericaFoodB();
        } else {
            return null;
        }
    }

    public static class AmericaFoodA extends Food {
    }

    public static class AmericaFoodB extends Food {
    }
}

class NormalFactoryApp{
    public static void main(String[] args) {
        ChineseFoodFactory chineseFoodFactory = new ChineseFoodFactory();
        Food chineseFoodA = chineseFoodFactory.makeFood("A");
        AmericaFoodFactory americaFoodFactory = new AmericaFoodFactory();
        Food americaFoodB = americaFoodFactory.makeFood("B");
        americaFoodB.sayName();


    }
}

