package myjava.lg;

/**
 *@className: StaticThreadInit2
 *@description:
 *@author: Regan_alex
 *@date: 2021/8/22 20:35
 *@version: V1.0
 **/
public class StaticThreadInit2 {

    static {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run start...");
                System.out.println(website);
                http://www
                website = "www.baidu.com";
                System.out.println("run end...");
            }
        });

        thread.start();
    }
    static String website = "www.bing.com";

    public static void main(String[] args) {
        String s = null;
        System.out.println(s instanceof String);
        //System.out.println(StaticThreadInit2.website);
    }

}