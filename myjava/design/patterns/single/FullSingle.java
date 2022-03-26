package myjava.design.patterns.single;

/**
 * @className: FullSingle
 * @description:FullSingle
 * @author: regan-alex
 * @date: 2022/3/27 1:03
 * @version: V1.0
 **/
public class FullSingle {
    private static volatile FullSingle instance;

    /**
     * @return
     */
    public FullSingle getInstance() {
	if (null == instance) {
	    synchronized (FullSingle.class) {
		if (null == instance) {
		    instance = new FullSingle();
		}
	    }
	}
	return instance;
    }

}