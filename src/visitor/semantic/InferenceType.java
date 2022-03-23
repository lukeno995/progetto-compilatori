package visitor.semantic;

import symbol.Sym;

public class InferenceType {

    public static int inferenceType(String type) {
        switch (type) {
            case "INTEGER":
            case "INTEGER_CONST":
                return Sym.INTEGER;
            case "REAL":
            case "REAL_CONST":
                return Sym.REAL;
            case "TRUE":
            case "FALSE":
            case "BOOL":
                return Sym.BOOL;
            case "STRING":
            case "STRING_CONST":
                return Sym.STRING;

            default:
                return Sym.error;
        }

    }
}
