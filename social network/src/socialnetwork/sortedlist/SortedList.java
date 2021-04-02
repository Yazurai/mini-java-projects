package socialnetwork.sortedlist;

import java.util.ArrayList;
import java.util.List;

public class SortedList<T extends Comparable<T>> {
    //An ordered list which also ensures that every element is only present only once
    //Needs a comparable type to ensure order of elements

    private SortedNode<T> initialNode;
    private int length;

    public SortedList() {
        this.initialNode = null;
        this.length = 0;
    }

    public SortedList(SortedNode<T> initNode) {
        this.initialNode = initNode;
        this.length = 0;
    }

    public int getLength() {
        return length;
    }

    public T getValue(int index) {
        if (-1 < index && index < length) {
            SortedNode<T> currNode = initialNode;
            for (int i = 0; i < index; i++) {
                currNode = currNode.getLink();
            }
            return currNode.getValue();
        } else {
            return null;
        }
    }

    public SortedNode<T> getNode(int index) {
        if (-1 < index && index < length) {
            SortedNode<T> currNode = initialNode;
            for (int i = 0; i < index; i++) {
                currNode = currNode.getLink();
            }
            return currNode;
        } else {
            return null;
        }
    }

    private int findIndex(T value) {
        SortedNode<T> currNode = initialNode;
        int index = 0;
        while (currNode != null) {
            if (value.equals(currNode.getValue())) {
                return index;
            }
            currNode = currNode.getLink();
            index++;
        }
        return index;
    }

    private int findSortedIndex(T value) {
        SortedNode<T> currNode = initialNode;
        int index = 0;
        while (currNode != null) {
            if (value.compareTo(currNode.getValue()) == 1) {
                return index;
            }
            currNode = currNode.getLink();
            index++;
        }
        return index;
    }

    private boolean contains(T value) {
        SortedNode<T> currNode = initialNode;
        while (currNode != null) {
            if (value.equals(currNode.getValue())) {
                return true;
            }
            currNode = currNode.getLink();
        }
        return false;
    }

    public boolean remove(int index) {
        if (-1 < index && index < length) {
            if (index == 0) {
                initialNode = getNode(1);
            } else {
                SortedNode<T> currNode = getNode(index - 1);
                currNode.setLink(currNode.getLink().getLink());
            }
            length--;
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(T value) {
        if (contains(value)) {
            return remove(findIndex(value));
        } else {
            return false;
        }
    }

    private boolean add(T value, int index) {
        if (-1 < index && index <= length) {
            if (index == 0) {
                SortedNode<T> newNode = new SortedNode<>(value);
                newNode.setLink(initialNode);
                initialNode = newNode;
            } else {
                SortedNode<T> prevNode = getNode(index - 1);
                SortedNode<T> newNode = new SortedNode<>(value);
                SortedNode<T> nextNode = null;

                if (prevNode.getLink() != null) {
                    nextNode = prevNode.getLink();
                }

                prevNode.setLink(newNode);
                newNode.setLink(nextNode);
            }
            length++;
            return true;
        } else {
            return false;
        }
    }

    public boolean add(T value) {
        if (!contains(value)) {
            int sortIndex = findSortedIndex(value);
            return add(value, sortIndex);
        } else {
            return false;
        }
    }

    public List<T> toList() {
        List<T> list = new ArrayList<>();
        SortedNode<T> currNode = initialNode;
        while (currNode != null) {
            list.add(currNode.getValue());
            currNode = currNode.getLink();
        }
        return list;
    }
}