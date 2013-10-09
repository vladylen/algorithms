/**
 * Created with IntelliJ IDEA.
 * User: vlsh
 * Date: 09/10/13
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class Runner {
    public static void main(String[] args)   // test client, described below
    {
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
    }
}
