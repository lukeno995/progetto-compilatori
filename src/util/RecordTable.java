package util;

import java.util.List;


public class RecordTable {
    private String symbolName; // name of the symbol
    private String kind;   // var, fun
    private int type;   // int, bool, string, etc. --> valore del sym
    private String scope;  // global, local, etc.

    private List<String> params;
    private String returnType;

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
    public RecordTable(String symbol, String kind, List<String> paramsType, String returnType) {
        this.symbolName = symbol;
        this.kind = kind;
        this.params = paramsType;
        this.returnType = returnType;
    }

    // case fun no return
    public RecordTable(String symbol, String kind, List<String> paramsType) {
        this.symbolName = symbol;
        this.kind = kind;
        this.params = paramsType;
        this.returnType = "VOID";
    }

    @Override
    public String toString() {
        if (kind != null) {
            if (kind.equalsIgnoreCase("var")) {
                return "SymbolTableRecord{" +
                        "symbol='" + symbolName + '\'' +
                        ", kind='" + kind + '\'' +
                        ", varType=" + type +
                        '}';
            } else if (kind.equalsIgnoreCase("fun")) {
                String tmpParams = "";
                String tmpReturn = "";
                if (params != null) {
                    for (String tmp : params) {
                        tmpParams += tmp;
                    }
                }
                if (returnType != null) {
                    tmpReturn += returnType + " ";
                }

                return "SymbolTableRecord{" +
                        "symbol='" + symbolName + '\'' +
                        ", kind='" + kind + '\'' +
                        ", paramsType=" + tmpParams +
                        ", returnType=" + tmpReturn +
                        '}';
            }
        }
        return null;
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

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
