package imooc.threadcoreknowledge.background;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布逸出
 */
public class MultiThreadError1 {
    private Map<String, String> states;
    public MultiThreadError1() {
        states = new HashMap<>();
        states.put("1", "周1");
        states.put("2", "周2");
        states.put("3", "周3");
        states.put("4", "周4");
        states.put("5", "周5");
    }

    public Map<String, String> getStates() {
        return states;
    }


    public static void main(String[] args) {
        MultiThreadError1 multiThreadError1 = new MultiThreadError1();
        Map<String, String> states = multiThreadError1.getStates();
        System.out.println(states.get("1"));
        states.remove("1");
        System.out.println(states.get("1"));
    }
}
