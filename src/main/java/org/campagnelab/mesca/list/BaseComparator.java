package org.campagnelab.mesca.list;

/**
 * Base comparator for sorting elements of {@link org.campagnelab.mesca.list.DoublyLinkedList}
 *
 * @author manuele
 */
public abstract  class BaseComparator<E> implements NodeComparator<E> {
    @Override
    public int compare(Node<E> node1, Node<E> node2) {
        return this.compareElement(node1.element, node2.element);
    }

    protected abstract int compareElement(E element1, E element2);
}
