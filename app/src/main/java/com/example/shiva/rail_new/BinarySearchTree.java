package com.example.shiva.rail_new;

/**
 * Created by shiva on 20-08-2017.
 */

public class BinarySearchTree {
    String r=null;
    String s= null;
    public Node root;

    public void insert(int value,int ind){
        Node node = new Node<>(value,ind);

        if ( root == null ) {
            root = node;
            return;
        }

        insertRec(root, node);

    }
    void printInOrderRec(Node currRoot){
        if ( currRoot == null ){
            return;
        }
        printInOrderRec(currRoot.left);


        MainActivity.indd.add(currRoot.ind);

        printInOrderRec(currRoot.right);
    }

    private void insertRec(Node latestRoot, Node node){

        if ( latestRoot.value > node.value){

            if ( latestRoot.left == null ){
                latestRoot.left = node;
                return;
            }
            else{
                insertRec(latestRoot.left, node);
            }
        }
        else{
            if (latestRoot.right == null){
                latestRoot.right = node;
                return;
            }
            else{
                insertRec(latestRoot.right, node);
            }
        }
    }
}