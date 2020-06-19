package mylearn.serializablelearn;

import java.io.*;

public class SerializableDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializableDemo serializableDemo = new SerializableDemo();
        serializableDemo.serializePerson();

        serializableDemo.deserializePerson();
    }

    /**
     * 序列化Person对象
     */

    private static void serializePerson() throws IOException {
        Person person = new Person();
        person.setName("xschen");
        person.setAge(22);
        person.setSex("男");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\work\\data\\Person.txt")));
        oos.writeObject(person);
        System.out.println("Person对象序列化成功");
        oos.close();
    }

    /**
     * 反序列化person对象
     */
    private static void deserializePerson() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\work\\data\\Person.txt")));
        Person person = (Person) ois.readObject();
        System.out.println("Person对象反序列化成功");
        System.out.println(person);
    }



}
