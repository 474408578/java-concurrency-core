package atguigu.juc;

/**
 * 基本类型：传值
 * 引用类型：传引用
 */

public class _16_TestTransferValue {
    public void changeValue1(int age) {
        age = 30;
    }

    public void changeValue2(Person person) {
        person.setName("xxx");
    }
    public void changeValue3(String str) {
        str = "xxx";
    }

    public static void main(String[] args) {
        _16_TestTransferValue test = new _16_TestTransferValue();
        int age = 20;
        test.changeValue1(age);
        // 20, 方法的 作用域， 基本类型，传值？传引用？
        System.out.println("age: " + age);

        Person person = new Person("abc");
        test.changeValue2(person);
        // xxx
        System.out.println("person name: " + person.getName());

        String str = "abc";
        test.changeValue3(str);
        //abc
        System.out.println("str: " + str);

    }


}



class Person {
    private Integer id;

    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}