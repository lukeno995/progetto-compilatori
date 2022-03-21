package visitor.semantic;

import symbol.Sym;

public class InferenceType {

    public static int inferenceType(String type) {
        switch (type) {
            case "INTEGER":
                return Sym.INTEGER;
            case "REAL":
                return Sym.REAL;
            case "TRUE":
            case "FALSE":
            case "BOOL":
                return Sym.BOOL;
            case "STRING":
                return Sym.STRING;
            case "INTEGER_CONST":
                return Sym.INTEGER_CONST;
            case "REAL_CONST":
                return Sym.REAL_CONST;
            case "STRING_CONST":
                return Sym.STRING_CONST;

            default:
                return Sym.error;
        }

    }
}
