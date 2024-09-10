package compedia.vn.tickmi.mail.task;

import compedia.vn.tickmi.mail.entity.EventRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Test {
    public static void main(String[] args) {
        System.out.println(String.format("%0" + 3 + "d", 10000));
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        Queue<Integer> queueEventRequest = new ConcurrentLinkedQueue<>();
        queueEventRequest.addAll(a);
        while (!queueEventRequest.isEmpty()) {
            System.out.println(queueEventRequest.poll());
        }
    }
}
