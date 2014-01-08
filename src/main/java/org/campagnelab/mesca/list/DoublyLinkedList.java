package org.campagnelab.mesca.list;

import java.util.*;

/**
 * A list implemented with a doubly linked list.
 *
 * @author manuele
 */
public class DoublyLinkedList<E> implements Iterable<E> {

    List<Node<E>> internalList = new ArrayList<Node<E>>();

    public boolean add(E element) {
        Node<E> node = new Node<E>(element);
        if (this.internalList.size() > 0) {
            node.prev = internalList.get(internalList.size() - 1);
            internalList.get(internalList.size() - 1).next = node;
        }
        return internalList.add(node);
    }

    public void add(int i, E element) {
        Node<E> node = new Node<E>(element);
        if (this.internalList.size() > 0) {
            node.prev = internalList.get(i - 1);
            internalList.get(i - 1).next = node;
            internalList.get(i + 1).prev = node;
        }
        internalList.add(i, node);
    }

    public boolean addAll(Collection<E> elements) {
        for (E element : elements)
            internalList.add(new Node<E>(element));
        return true;
    }

    public boolean addAll(int i, Collection<? extends Node<E>> nodes) {
        for (Node<E> node : nodes)
            internalList.add(i++, node);
        return true;
    }

    public E get(int i) {
        return  this.internalList.get(i).element;
    }

    public int size() {
        return this.internalList.size();
    }

    public void sort(BaseComparator<E> c) {
        Collections.sort(this.internalList, c);
    }

    /**
     * Reverses the order of the elements in the list.
     */
    public void reverse() {
        Collections.reverse(this.internalList);
    }
    /**
     *  Randomly permutes the list.
     */
    public void shuffle() {
        Collections.shuffle(this.internalList);
    }

    public ListIterator<E> forwardIterator(int position) {
        return new LinkedForwardIterator<E>(internalList.get(position));
    }

    public ListIterator<E> backwardIterator(int position) {
        return new LinkedBackwardIterator<E>(internalList.get(position));
    }

    /**
     * Gets an iterator that navigates the list in the actual order.
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new SequentialForwardIterator<E>(internalList.iterator());
    }

    /**
     * An iterator that navigates the list in the actual order.
     * @param <E>
     */
    private class SequentialForwardIterator<E> implements Iterator<E> {

        private java.util.Iterator<Node<E>> iterator;

        public SequentialForwardIterator(java.util.Iterator<Node<E>> iterator) {
           this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public E next() {
            return this.iterator.next().element;
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }

    }


        /**
     * An iterator to navigate linked elements in the backward direction.
     *
     * @param <E>
     */
    private class LinkedBackwardIterator<E> implements ListIterator<E> {
        /**
         * the node that is returned by previous()
         */
        private Node<E> current;

        protected LinkedBackwardIterator(Node<E> startNode) {
            current = startNode.prev;
        }

        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public E next() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        public boolean hasPrevious() {
            return current != null;
        }

        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            E element = current.element;
            current = current.prev;
            return element;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

    }


    /**
     * An iterator to navigate linked elements in the forward direction.
     *
     * @param <E>
     */
    private class LinkedForwardIterator<E> implements ListIterator<E> {

        /**
         * the node that is returned by next()
         */
        private Node<E> current;

        protected LinkedForwardIterator(Node<E> startNode) {
            current = startNode.next;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E element = current.element;
            current = current.next;
            return element;
        }

        @Override
        public boolean hasPrevious() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");

        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");

        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this SequentialForwardIterator");

        }

    }

}
