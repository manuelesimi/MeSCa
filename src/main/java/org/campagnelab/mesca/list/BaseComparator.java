package org.campagnelab.mesca.list;

/**
 * Created by manuelesimi on 1/7/14.
 */
public abstract  class BaseComparator<E> implements NodeComparator<E> {
    @Override
    public int compare(Node<E> node1, Node<E> node2) {
        return this.compareElement(node1.element, node2.element);
    }

    protected abstract int compareElement(E element1, E element2);
}
