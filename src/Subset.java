public class Subset {
    public static void main(String[] args)   // test client, described below
    {
        RandomizedQueue queue = new RandomizedQueue();
        Integer k = StdIn.readInt();
        Integer N = StdIn.readInt();

        for (int i = 0; i < N; i++) {
            String str = StdIn.readString();
            queue.enqueue(str);
        }

        for (int i = 0; i < k; i++) {
            StdOut.print(queue.dequeue());
            //StdOut.println(queue.dequeue());
        }
        /*
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.addFirst(1);
        deque.removeFirst();
        deque.addFirst(1);
        deque.removeLast();
        */
    }
}
