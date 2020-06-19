package mylearn.iolearn;

import java.io.*;

public class IOBinaryFileDemo {
    public void test01() throws IOException {
        byte[] bytes = {12,21,34,11,21};

        OutputStream fs = new FileOutputStream("D:\\work\\data\\test.txt");
        fs.write(bytes);
        fs.close();
    }

    public void test02() throws IOException {
        InputStream is = new FileInputStream("D:\\work\\data\\test.txt");
        int c;
        while ((c = is.read()) != -1) {
            System.out.println(c);
        }
    }
}
