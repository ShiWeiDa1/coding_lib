package myjava.design.patterns.single;

/**
 * @className: StaticInnerSingle
 * @description:StaticInnerSingle
 * @author: regan-alex
 * @date: 2022/3/27 1:07
 * @version: V1.0
 **/
public class StaticInnerSingle {
    private static class Holder {
	private static final StaticInnerSingle instance = new StaticInnerSingle();
    }

    public static StaticInnerSingle getInstance() {
	return Holder.instance;
    }

}