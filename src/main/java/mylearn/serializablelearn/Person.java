package mylearn.serializablelearn;

import java.io.Serializable;

public class Person implements Serializable {
    /**
     * 序列化ID
     */
    private static final long serivalVersionUid = -5809782578272943999L;

    private String name;
    private int age;
    private String sex;

    public static long getSerivalVersionUid() {
        return serivalVersionUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
