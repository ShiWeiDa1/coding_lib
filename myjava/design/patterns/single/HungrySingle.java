package myjava.design.patterns.single;

/**
 * @className: HungrySingle
 * @description:HungrySingle
 * @author: regan-alex
 * @date: 2022/3/27 0:59
 * @version: V1.0
 **/
public class HungrySingle {
    /**
     * 1.线程安全
     * 2.占用资源
     */
    private static final HungrySingle instance = new HungrySingle();

    private HungrySingle getInstance() {
	return instance;
    }
}