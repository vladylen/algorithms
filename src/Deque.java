import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int N = 0;               // number of elements on queue
    private Node<Item> first = null;    // beginning of queue
    private Node<Item> last = null;     // end of queue

    public Deque()                     // construct an empty deque
    {
    }

    public boolean isEmpty()           // is the deque empty?
    {
        return N == 0;
    }

    public int size()                  // return the number of items on the deque
    {
        return N;
    }

    public void addFirst(Item item)    // insert the item at the front
    {
        if (isEmpty()) {
            addFirstElement(item);
        } else {
            Node<Item> node = new Node<Item>();
            node.item = item;
            node.next = first;
            node.prev = null;
            first.prev = node;
            first = node;
        }

        incCount();
    }

    public void addLast(Item item)     // insert the item at the end
    {
        if (isEmpty()) {
            addFirstElement(item);
        } else {
            Node<Item> node = new Node<Item>();
            node.item = item;
            node.prev = last;
            node.next = null;
            last.next = node;
            last = node;
        }

        incCount();
    }

    public Item removeFirst()          // delete and return the item at the front
    {
        if (size() == 1) {
            removeFirstElement();
        } else {
            first = first.next;
            first.prev = null;
        }

        decCount();

        return first == null ? null : first.item;
    }

    public Item removeLast()           // delete and return the item at the end
    {
        if (size() == 1) {
            removeFirstElement();
        } else {
            last = last.prev;
            last.next = null;
        }

        decCount();

        return last == null ? null : last.item;
    }

    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    // helper linked list class
    private static class Node<Item> {
        private Item item = null;
        private Node<Item> next = null;
        private Node<Item> prev = null;
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

    private void addFirstElement(Item item) {
        Node<Item> node = new Node<Item>();

        node.item = item;
        node.next = null;
        node.prev = null;

        first = node;
        last = node;
    }

    private void removeFirstElement() {
        first = null;
        last = null;
    }

    private void incCount() {
        N++;
    }

    private void decCount() {
        N--;

        if (N < 0) {
            N = 0;
        }
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