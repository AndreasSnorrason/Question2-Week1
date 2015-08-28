/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asnorrason
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Avilable Processors: " + Runtime.getRuntime().availableProcessors());
        MyThread t1 = new MyThread("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/fronter_big-logo.png");
        MyThread t2 = new MyThread("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/folder-image-transp.png");
        MyThread t3 = new MyThread("https://fronter.com/volY12-cache/cache/img/design_images/Classic/other_images/button_bg.png");


        ExecutorService exe = Executors.newFixedThreadPool(3);
        
        long start = System.nanoTime();

                exe.execute(t1);
        exe.execute(t2);
        exe.execute(t3);

        exe.shutdown();
        exe.awaitTermination(10, TimeUnit.SECONDS);
        long end = System.nanoTime();
        System.out.println("Time Parallel: " + (end - start));


        int i = t1.getSum() + t2.getSum() + t3.getSum();
        System.out.println("t1 : " + i);
        
        long start1 = System.nanoTime();
        t1.run();
        t2.run();
        t3.run();
        long end1 = System.nanoTime();
        System.out.println("Time Sequental: " + (end1 - start1));
    }

    public static class MyThread implements Runnable {

        private String url;
        private int sum;
        public int hello;
        byte[] ba;

        public MyThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            ba = getBytesFromUrl(url);
            for (int i = 0; i < ba.length; i++) {
                sum = sum + ba[i];

            }
        }

        public int getSum() {

            return sum;
        }

    }

    protected static byte[] getBytesFromUrl(String url) {
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        try {

            InputStream is = new URL(url).openStream();
            byte[] bytebuff = new byte[4096];
            int read;
            int result = 0;
            while ((read = is.read(bytebuff)) > 0) {
                bis.write(bytebuff, 0, read);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return bis.toByteArray();
    }

}
