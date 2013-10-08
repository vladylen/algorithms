import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int N;               // number of elements on queue
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue

    public Deque()                     // construct an empty deque
    {
    }

    public boolean isEmpty()           // is the deque empty?
    {
        return true;
    }

    public int size()                  // return the number of items on the deque
    {
        return N;
    }

    public void addFirst(Item item)    // insert the item at the front
    {
    }

    public void addLast(Item item)     // insert the item at the end
    {
    }

    public Item removeFirst()          // delete and return the item at the front
    {
        return first.item;
    }

    public Item removeLast()           // delete and return the item at the end
    {
        return last.item;
    }

    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }
    /*
    public class Stack<Item>
    {
        private Node first = null;
        private class Node
        {
            Item item;
            Node next;
        }

        public boolean isEmpty()
        { return first == null; }
        public void push(Item item)
        {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
        }
        public Item pop()
        {
            Item item = first.item;
            first = first.next;
            return item;
        }
    }
    */

}