### 为什么需要UncaoughtExceptionHandler?
#### 1、主线程可以轻松发现异常，子线程却不行
#### 2、子线程异常无法用传统方法捕获
#### 3、两种解决方案
##### 3.1 手动在每个run方法里进行try/catch (不推荐)
##### 3.2 利用UncaughtExceptionHandler (推荐)
* UncaughtExceptionHandler接口
* void uncaughtException(Thread t, Throwable e)
#### 4、面试问题
##### 4.1 Java异常体系
##### 4.2 如何处理全局异常？为什么要处理？不处理行不行？
* 如何处理：定义一个UncaughtExceptionHandler，设置为全局的异常处理器。
* 为什么要处理：记录
* 不处理不行
##### 4.3 run方法是否可以抛出异常，如果抛出异常，线程的状态会怎么样？
run方法不能再向外抛出异常，只能自己做异常的处理，线程会终止运行。
##### 4.4 线程中如何处理某个未处理异常？
全局处理器，UncaughtExceptionHandler