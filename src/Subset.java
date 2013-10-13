import java.util.Iterator;

public class Subset {
    public static void main(String[] args)   // test client, described below
    {
        RandomizedQueue queue = new RandomizedQueue();

        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        int k = Integer.parseInt(args[0]);

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }

/*
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(3);
        queue.enqueue(4);

        Iterator it1 = queue.iterator();

        StdOut.println(it1.next());
        StdOut.println(it1.next());
        StdOut.println(it1.next());
        StdOut.println(it1.next());
        StdOut.println(it1.next());

        Iterator it2 = queue.iterator();
        StdOut.println(it2.next());
        StdOut.println(it2.next());
        StdOut.println(it2.next());
        StdOut.println(it2.next());
        StdOut.println(it2.next());
        /*
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(0);
        queue.enqueue(0);
        queue.enqueue(0);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(0);
        queue.dequeue();
        queue.enqueue(0);
        queue.enqueue(0);
        queue.enqueue(0);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(0);
        queue.dequeue();
        queue.dequeue();
        */

        /*
        for (int i = 0; i < 50; i++) {
            queue.enqueue(i);
            StdOut.println(queue.sample());
        }

        StdOut.println("dequeu");
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        /*
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addFirst(7);
        deque.removeFirst();
        deque.addFirst(8);
        deque.addFirst(9);
        deque.addFirst(10);
        deque.removeFirst();
        deque.removeFirst();

        Deque<Integer> deque1 = new Deque<Integer>();
        deque1.addLast(0);
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);
        deque1.addLast(4);
        deque1.addLast(5);
        deque1.addLast(6);
        deque1.addLast(7);
        deque1.addLast(8);
        deque1.removeLast();
        deque1.addLast(9);
        deque1.addLast(10);
        deque1.removeLast();
        deque1.removeLast();
        deque1.addFirst(1);
        deque1.removeFirst();
        deque1.addFirst(1);
        deque1.removeLast();
        */
    }
}
