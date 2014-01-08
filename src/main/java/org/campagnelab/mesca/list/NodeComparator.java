package org.campagnelab.mesca.list;

import java.util.Comparator;

/**
 * Interface for comparators to use in {@link org.campagnelab.mesca.list.DoublyLinkedList}.
 */
public interface NodeComparator<E> extends Comparator<Node<E>> {
}
