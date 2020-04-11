package ru.otus;

import java.util.*;

public class DIYArrayList<E> implements List<E> {

    private static final int INITIAL_SIZE = 5;
    private static final int GROW_STEP = 5;

    private int lastIndex = -1;
    private Object[] objectArray = new Object[INITIAL_SIZE];

    // mandatory methods
    @Override
    public boolean add(E e) {
        if (lastIndex == objectArray.length - 1) {
            expand();
        }
        objectArray[++lastIndex] = e;
        return true;
    }

    @Override
    public int size() {
        return lastIndex + 1;
    }

    @Override
    public boolean isEmpty() {
        return lastIndex == -1;
    }

    @Override
    public void clear() {
        lastIndex = -1;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i <= lastIndex; i++) {
                if (objectArray[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i <= lastIndex; i++) {
                if (o.equals(objectArray[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = lastIndex; i >= 0; i--) {
                if (objectArray[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = lastIndex; i >= 0; i--) {
                if (o.equals(objectArray[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[lastIndex + 1];
        System.arraycopy(objectArray, 0, result, 0, lastIndex + 1);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) objectArray, 0, lastIndex + 1, c);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkIndex(index);
        E oldElement = (E) objectArray[index];
        objectArray[index] = element;
        return oldElement;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) objectArray[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E obj = (E) objectArray[index];
        System.arraycopy(objectArray, index + 1, objectArray, index, lastIndex - index);
        lastIndex--;
        return obj;
    }

    @Override
    public Iterator<E> iterator() {
        return new DIYIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new DIYListIterator(0);
    }

    // not implemented =============================

    @Override
    public boolean remove(Object o) {
        notImplemented();
        return false;
    }

    @Override
    public void add(int index, E element) {
        notImplemented();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        notImplemented();
        return false;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        notImplemented();
        return false;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        notImplemented();
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        notImplemented();
        return null;
    }

    @Override
    public boolean retainAll(Collection c) {
        notImplemented();
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        notImplemented();
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        notImplemented();
        return false;
    }

    // service methods =====================

    private void expand() {
        objectArray = Arrays.copyOf(objectArray, objectArray.length + GROW_STEP);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
    }

    private void notImplemented() {
        throw new UnsupportedOperationException("Method not implemented (yet)");
    }

    // iterator classes ================

    private class DIYIterator implements Iterator<E> {
        int currentPos;
        int lastRet = -1;

        DIYIterator() {}

        public boolean hasNext() {
            return currentPos <= lastIndex;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (currentPos > lastIndex) {
                throw new NoSuchElementException();
            }
            return (E) objectArray[lastRet = currentPos++];
        }

        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            DIYArrayList.this.remove(lastRet);
            currentPos = lastRet;
            lastRet = -1;
        }
    }

    private class DIYListIterator extends DIYIterator implements ListIterator<E> {

        DIYListIterator(int index) {
            super();
            currentPos = index;
        }

        public boolean hasPrevious() {
            return currentPos > 0;
        }

        public int nextIndex() {
            return currentPos;
        }

        public int previousIndex() {
            return currentPos - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            if (currentPos <= 0) {
                throw new NoSuchElementException();
            }
            return (E) objectArray[lastRet = --currentPos];
        }

        public void set(E e) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            DIYArrayList.this.set(lastRet, e);
        }

        public void add(E e) {
            DIYArrayList.this.add(currentPos++, e);
            lastRet = -1;
        }
    }
}
