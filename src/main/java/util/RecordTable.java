package util;

import symbol.Sym;

import java.util.List;


public class RecordTable {
    private String symbolName; // name of the symbol
    private String kind;   // var, fun
    private int type;   // int, bool, string, etc. --> valore del sym
    private String scope;  // global, local, etc.

    private List<Integer> params;
    private int returnType;

    public RecordTable(String scope) {
        this.scope = scope;
    }

    // case var
    public RecordTable(String symbol, String kind, int type) {
        this.symbolName = symbol;
        this.kind = kind;
        this.type = type;
    }

    // case fun
    public RecordTable(String symbol, String kind, List<Integer> paramsType, int returnType) {
        this.symbolName = symbol;
        this.kind = kind;
        this.params = paramsType;
        this.returnType = returnType;
    }

    // case fun no return
    public RecordTable(String symbol, String kind, List<Integer> paramsType) {
        this.symbolName = symbol;
        this.kind = kind;
        this.params = paramsType;
        this.returnType = Sym.VOID;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<Integer> getParams() {
        return params;
    }

    public void setParams(List<Integer> params) {
        this.params = params;
    }

    public int getReturnType() {
        return returnType;
    }

    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }
}
