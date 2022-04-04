package com.company;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int index;
    private boolean key;
    private final int row;
    private final int col;

    private final List<Node> adj = new ArrayList<>();

    public Node(int index, boolean key, int row, int col) {
        this.index = index;
        this.key = key;
        this.row = row;
        this.col = col;
    }

    public int getIndex() {
        return index;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public List<Node> getAdj() {
        return adj;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public void add(Node node) {
        adj.add(node);
    }
}
