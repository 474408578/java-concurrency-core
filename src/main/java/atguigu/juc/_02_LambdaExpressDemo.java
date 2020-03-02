package atguigu.juc;


/**
 * 1、Lambda Express: 针对接口内有且仅有一个方法(除default修饰的方法外)
 * 1.1 口诀：拷贝小括号，写死右箭头，落地大括号
 * 1.2 @FunctionalInterface（函数式接口）: 只允许接口内有且仅有一个方法，除default、static方法除外
 * 1.3 default:方法的默认实现
 * 1.4 静态方法实现
 */




@FunctionalInterface
interface Foo {
    // 接口内有且仅有一个方法
//    public void sayHello();


    int add(int x, int y);

    default int div(int x, int y) {
        System.out.println("\ndefault方法");
        return x/y;
    }

    static int mul(int x, int y) {
        System.out.println("\nstatic方法");
        return x *y;
    }
}

public class _02_LambdaExpressDemo {
    public static void main(String[] args) {
        /*Foo foo = new Foo() {
            public void sayHello(){
                System.out.println("hello world");
            }
        };

        foo.sayHello();*/

        /*Foo foo = () -> {
            System.out.println("hello world");
        };
        foo.sayHello();*/

        Foo foo = (x, y) -> { return x + y; };
        System.out.println(foo.add(5, 10));

        System.out.println(foo.div(10, 2));

        System.out.println(Foo.mul(2, 30));

    }
}