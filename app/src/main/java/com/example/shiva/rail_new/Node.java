package com.example.shiva.rail_new;

/**
 * Created by shiva on 20-08-2017.
 */
public class Node<T> {
    public int value;
    public int ind;
    public Node left;
    public Node right;

    public Node(int value,int ind) {
        this.value = value;
        this.ind=ind;

    }
}