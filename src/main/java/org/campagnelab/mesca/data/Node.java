package org.campagnelab.mesca.data;

import java.io.File;

/**
 * A node in the list.
 *
 * @author manuele
 */
class Node<T> {
    Node prev = null;
    Node next = null;
    T element;

    protected Node() { }

    protected Node(T element) {
        this.element = element;
    }

}