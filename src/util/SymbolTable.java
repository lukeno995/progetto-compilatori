package util;

import nodes.LeafNode;

import java.util.HashMap;

//HashMap per gli identificatori
public class SymbolTable extends HashMap<String, LeafNode> {

    private String name;

    public SymbolTable(String n) {
        super();
        name = n;
    }
}
