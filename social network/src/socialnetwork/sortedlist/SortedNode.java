package socialnetwork.sortedlist;

public class SortedNode<T> {
    private T value;
    private SortedNode<T> link;

    public SortedNode() {
        this.value = null;
        this.link = null;
    }

    public SortedNode(T value) {
        this.value = value;
        this.link = null;
    }

    public T getValue() {
        return value;
    }

    public void setLink(SortedNode<T> node) {
        link = node;
    }

    public SortedNode<T> getLink() {
        return link;
    }
}