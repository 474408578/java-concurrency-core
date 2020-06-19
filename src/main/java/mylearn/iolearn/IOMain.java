package mylearn.iolearn;

import java.io.IOException;

/**
 * io是指input/output，即输入输出。以内存为中心，input表示从外部读入数据到内存，output表示把数据从内存输出到外部。
 * io的操作：网络、磁盘IO，网络操作相关的类在java.net包下
 *
 * 流：表示有能力产生数据源或接收数据源的对象。
 * 从数据源或者说是操作对象的角度，IO类可以分为：
 *   节点流：
 *      文件（file）：FileInputStream、FileOutputStream、FileReader、FileWriter
 *
 *      管道操作：PipedInputStream、PipedOutputStream、PipedReader、PipedWriter
 *
 *      数组（[]）：
 *          2.1、字节数组（byte[]）：ByteArrayInputStream、ByteArrayOutputStream
 *          2.2、字符数组（char[]）：CharArrayReader、CharArrayWriter
 *
 *
 *   处理流
 *      基本数据类型：DataInputStream、DataOutputStream
 *
 *      缓冲操作：BufferedInputStream、BufferedOutputStream、BufferedReader、BufferedWriter
 *
 *      打印：PrintStream、PrintWriter
 *
 *      对象序列化反序列化：ObjectInputStream、ObjectOutputStream
 *
 *      转换：InputStreamReader、OutputStreamWriter
 *
 *
 * 根据传输方式分类：
 *      字节流：以字节单位来传输（OutputStream、InputStream）
 *      字符流：以字符单位来传输（Reader、Writer）
 *
 *      字节流读取单个字节，字符流读取单个字符（一个字符根据编码的不同，对应的字节也不同，如 UTF-8 编码是 3 个字节，中文编码是 2 个字节。）
 *      字节流用来处理二进制文件（图片、MP3、视频文件），字符流用来处理文本文件（可以看做是特殊的二进制文件，使用了某种编码，人可以阅读）
 *
 *      https://wenshixin.gitee.io/blog/2018/09/11/Java%E8%BF%9B%E9%98%B6-IO%E6%80%BB%E7%BB%93/%E6%8C%89%E5%AD%97%E8%8A%82%E5%92%8C%E5%AD%97%E7%AC%A6%E5%88%92%E5%88%86.png
 *
 * 常用的IO类及相关方法：
 *
 */
public class IOMain {
    public static void main(String[] args) throws IOException {
        IOConsoleDemo ioConsoleDemo = new IOConsoleDemo();
//        ioConsoleDemo.test01();
//        ioConsoleDemo.test02();
//        ioConsoleDemo.test03();


        IOBinaryFileDemo ioBinaryFileDemo = new IOBinaryFileDemo();
//        ioBinaryFileDemo.test01();
//        ioBinaryFileDemo.test02();


        IOTextFileDemo ioTextFileDemo = new IOTextFileDemo();
        ioTextFileDemo.test01();
        ioTextFileDemo.test02();
    }




}
