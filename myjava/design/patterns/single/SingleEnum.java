package myjava.design.patterns.single;

/**
 * @enumName: SingleEnum
 * @description: SingleEnum
 * @author: regan-alex
 * @date: 2022/3/27 1:12
 * @Version: V1.0
 **/
public enum SingleEnum {
    INSTANCE;

    public void doing() {
	System.out.println("single enum doing something!");
    }

}
