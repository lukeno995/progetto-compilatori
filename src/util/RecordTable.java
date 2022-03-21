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
}
