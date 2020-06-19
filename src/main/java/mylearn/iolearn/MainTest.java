package mylearn.iolearn;

import java.io.*;

public class MainTest {
    public static void main(String[] args) {
        File f = new File("D:\\work\\data\\test.txt");
        System.out.println(f);
        System.out.println(f.isFile());
        MainTest.readFile();
        MainTest.writeFile();
        MainTest.readFile();
    }


    public static void readFile() {

        try (InputStream inputStream = new FileInputStream("D:\\work\\data\\test.txt")) {
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                System.out.println(n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile() {
        try (OutputStream outputStream = new FileOutputStream("D:\\work\\data\\test.txt")) {
            outputStream.write("hello".getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
