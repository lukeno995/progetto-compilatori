package visitor.typechecker;

import exception.FatalError;
import exception.TypeMismatchException;

public class TypeChecker {
    //VALORI DI CONTROLLI PER IL CHECKER
    private static final String INT = "INTEGER";
    private static final String BOOL = "BOOL";
    private static final String STRING = "STRING";
    private static final String REAL = "REAL";
    private static final String VOID = "VOID";

    //Operazioni da controllare con il checker
    public static final String BINARYOP = "BINARYOP";
    public static final String RELOP = "RELOP";
    public static final String ASSIGNOP = "ASSIGNOP";
    public static final String UMINUSOP = "UMINUSOP";
    public static final String NOTOP = "NOTOP";
    public static final String BOOLEANOP = "BOOLEANOP";
    public static final String CONDITIONAL = "CONDITIONAL";

    public static String typeCheckUnaryOp(String op, String type) throws TypeMismatchException, FatalError {
        if (op.equals(UMINUSOP)) {
            return typeCheckUminusOp(type);

        } else if (op.equals(NOTOP)) {
            return typeCheckNotOp(type);

        } else if (op.equals(CONDITIONAL)) {
            return typeCheckConditionalOp(type);
        } else {
            throw new FatalError("Operazione " + op + " non verificabile nel checker unario");
        }
    }

    public static String typeCheckBinaryOp(String op, String type1, String type2) throws TypeMismatchException {
        if (op.equals(BINARYOP)) {
            return typeCheckAritmeticOp(type1, type2);

        } else if (op.equals(RELOP)) {
            return typeCheckRelationalOp(type1, type2);

        } else if (op.equals(ASSIGNOP)) {
            return typeCheckAssignOp(type1, type2);

        } else if (op.equals(BOOLEANOP)) {
            return typeCheckOrAndOp(type1, type2);

        } else {
            throw new TypeMismatchException("Operazione " + op + " non verificabile nel checker binario");
        }
    }

    private static String typeCheckConditionalOp(String type) throws TypeMismatchException {
        if (type.equals(BOOL)) {
            return VOID;
        } else {
            throw new TypeMismatchException("L'espressione nella condizione di un costrutto if, elif e while deve essere di tipo bool. Fornita: " + type);
        }
    }

    private static String typeCheckAssignOp(String type1, String type2) throws TypeMismatchException {
        if (type1.equals(INT) && type2.equals(INT)) {
            return VOID;
        } else if(type1.equals(REAL) && type2.equals(REAL)) {
            return VOID;
        } else if(type1.equals(STRING) && type2.equals(STRING)) {
            return VOID;
        } else if(type1.equals(BOOL) && type2.equals(BOOL)) {
            return VOID;
        }
        throw new TypeMismatchException("Non è possibile assegnare " + type2 + " a " + type1);
    }

    //PLUS, TIMES, . . . integer integer integer
    //PLUS, TIMES, . . . integer real real
    //PLUS, TIMES, . . . real integer real
    //PLUS, TIMES, . . . real real real
    private static String typeCheckAritmeticOp(String type1, String type2) throws TypeMismatchException {
        if (type1.equalsIgnoreCase(INT) && type2.equalsIgnoreCase(INT)) {
            return INT;
        } else if (type1.equals(INT) && type2.equals(REAL)) {
            return REAL;
        } else if (type1.equals(REAL) && type2.equals(REAL)) {
            return REAL;
        } else if (type1.equals(REAL) && type2.equals(INT)) {
            return REAL;
        }
        throw new TypeMismatchException("Operazioni aritmentiche eseguibili solo tra espressioni di tipo int e real. Valori forniti: " + type1 + " - " + type2);
    }

    //LT, LE, GT, . . . integer integer boolean
    //LT, LE, GT, . . . real integer boolean
    //LT, LE, GT, . . . integer real boolean
    //LT, LE, GT, . . . real real boolean
    private static String typeCheckRelationalOp(String type1, String type2) throws TypeMismatchException {
        if (type1.equals(INT) && type2.equals(INT)) {
            return BOOL;
        } else if (type1.equals(INT) && type2.equals(REAL)) {
            return BOOL;
        } else if (type1.equals(REAL) && type2.equals(REAL)) {
            return BOOL;
        } else if (type1.equals(REAL) && type2.equals(INT)) {
            return BOOL;
        } else if (type1.equals(STRING) && type2.equals(STRING)) {
            return BOOL;
        } else if (type1.equals(BOOL) && type2.equals(BOOL)) {
            return BOOL;
        } else {
            throw new TypeMismatchException("Non possiamo relazionare " + type2 + " e " + type1);
        }
    }

    //STR CONCAT string string string
    private static String typeCheckStringConcat(String type1, String type2) throws TypeMismatchException {
        if (type1.equals(STRING) && type2.equals(STRING)) {
            return STRING;
        } else {
            throw new TypeMismatchException("Non possiamo concatenare " + type2 + " e " + type1);
        }
    }


    //AND, OR boolean boolean boolean
    private static String typeCheckOrAndOp(String type1, String type2) throws TypeMismatchException {
        if (type1.equals(BOOL) && type2.equals(BOOL)) {
            return BOOL;
        } else {
            throw new TypeMismatchException("Per l'operazione di and, o or, sono richieste due espressioni di tipo bool. Fornite: " + type1 + " - " + type2);
        }
    }

    //MINUS integer integer
    //MINUS real real
    private static String typeCheckUminusOp(String type) throws TypeMismatchException {
        if (type.equals(INT)) {
            return INT;
        } else if (type.equals(REAL)) {
            return REAL;
        } else {
            throw new TypeMismatchException("Per l'operazione 'uminus' è richiesta una espressione di tipo int o float. Fornita: " + type);
        }
    }

    private static String typeCheckNotOp(String type) throws TypeMismatchException {
        if (type.equals(BOOL)) {
            return BOOL;
        } else {
            throw new TypeMismatchException("Per l'operazione 'not' è richiesta una espressione di tipo bool. Fornita: " + type);
        }

    }


}
