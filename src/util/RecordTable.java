package util;

import java.util.ArrayList;
import java.util.List;


public class RecordTable {
    private String symbol;
    private String kind;   // var, fun
    private Integer varType;
    private List<Integer> paramsType = new ArrayList<>();
    private Integer returnType;

    // case var
    public RecordTable(String symbol, String kind, Integer varType) {
        this.symbol = symbol;
        this.kind = kind;
        this.varType = varType;
    }
    // case fun
    public RecordTable(String symbol, String kind, List<Integer> paramsType, Integer returnType) {
        this.symbol = symbol;
        this.kind = kind;
        this.paramsType = paramsType;
        this.returnType = returnType;
    }
    // case fun no return
    public RecordTable(String symbol, String kind, List<Integer> paramsType) {
        this.symbol = symbol;
        this.kind = kind;
        this.paramsType = paramsType;
        this.returnType = null;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getVarType() {
        return varType;
    }

    public void setVarType(Integer varType) {
        this.varType = varType;
    }

    public List<Integer> getParamsType() {
        return paramsType;
    }

    public void setParamsType(List<Integer> paramsType) {
        this.paramsType = paramsType;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        if (kind != null) {
            if (kind.equals("var")) {
                return "SymbolTableRecord{" +
                        "symbol='" + symbol + '\'' +
                        ", kind='" + kind + '\'' +
                        ", varType=" + varType +
                        '}';
            } else if (kind.equals("fun")) {
                String tmpParams = "";
                String tmpReturn = "";
                if (paramsType != null) {
                    for (Integer tmp : paramsType) {
                        tmpParams += tmp + " ";
                    }
                }
                if (returnType != null) {
                    tmpReturn += returnType + " ";
                }

                return "SymbolTableRecord{" +
                        "symbol='" + symbol + '\'' +
                        ", kind='" + kind + '\'' +
                        ", paramsType=" + tmpParams +
                        ", returnType=" + tmpReturn +
                        '}';
            }
        }
        return null;
    }
}
