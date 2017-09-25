package Db.Types;

public class Range<T> {
    T left;
    T right;
    public Range(T l, T r) {
        left = l;
        right = r;
    }
    public T getLeft() {
        return left;
    }
    public T getRight() {
        return right;
    }
    public void setLeft(T l) {
        left = l;
    }
    public void setRight(T r) {
        right = r;
    }
}