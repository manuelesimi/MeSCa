package org.campagnelab.mesca.list;

import java.util.*;

/**
 * A list implemented with a doubly linked list.
 *
 * @author manuele
 */
public class DoublyLinkedList<E> {

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

    public void sort(BaseComparator<E> c) {
        Collections.sort(this.internalList, c);
    }


    /**
     *  Randomly permutes the list.
     */
    public void shuffle() {
        Collections.shuffle(this.internalList);
    }

    /**
     * Gets an iterator that navigate the list in the actual order.
     * @return
     */
    public ListIterator<E> iterator() {
        return new Iterator<E>(internalList.iterator());
    }

    public ListIterator<E> forwardIterator(int position) {
        return new LinkedForwardIterator<E>(internalList.get(position));
    }

    public ListIterator<E> backwardIterator(int position) {
        return new LinkedBackwardIterator<E>(internalList.get(position));
    }

    public int size() {
        return this.internalList.size();
    }

    /**
     * An iterator that navigates the list in the actual order.
     * @param <E>
     */
    private class Iterator<E> implements ListIterator<E> {

        private java.util.Iterator<Node<E>> iterator;

        public Iterator(java.util.Iterator<Node<E>> iterator) {
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
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

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
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public E next() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
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
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
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
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public E previous() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");

        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");

        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("The operation is not supported by this Iterator");

        }

    }

}
