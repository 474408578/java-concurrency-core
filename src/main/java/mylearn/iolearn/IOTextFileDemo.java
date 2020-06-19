package mylearn.iolearn;

import java.io.*;

public class IOTextFileDemo {
    public void test01() throws IOException {
        Writer fileWriter = new FileWriter(new File("D:\\work\\data\\test.txt"), false);
        fileWriter.write("Hello World! \n欢迎你");
        fileWriter.write("\n再来一行");

        fileWriter.close();
    }

    public void test02() throws IOException {
        Reader fileReader = new FileReader(new File("D:\\work\\data\\test.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
        fileReader.close();
        bufferedReader.close();
    }
}
