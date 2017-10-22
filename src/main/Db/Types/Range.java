package Db.Types;

import java.io.Serializable;

public class Range<T> implements Serializable {

    private T left;
    private T right;

    public Range() {}

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

    @Override
    public String toString() {
        return left.toString() + " .. " + right.toString();
    }
}