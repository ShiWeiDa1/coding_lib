package myjava.lg;

/**
 *@className: StaticThreadInit2
 *@description:
 *@author: Regan_alex
 *@date: 2021/8/22 20:35
 *@version: V1.0
 **/
public class StaticThreadInit {

    static {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run start...");
                // website 未初始化完成等待 main线程完成初始化操作 ,lock 死锁现象产生
                website = "www.baidu.com";
                System.out.println("run end...");
            }
        });

        thread.start();
        try {
            // 互相等待
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static String website = "www.bing.com";

    public static void main(String[] args) {
        System.out.println(StaticThreadInit.website);
    }

}