package visitor.semantic;

import exception.FatalError;
import exception.TypeMismatchException;
import symbol.Sym;

public class TypeChecker {

    //Operazioni da controllare con il checker
    public static final String BINARYOP = "BINARYOP";
    public static final String RELOP = "RELOP";
    public static final String ASSIGNOP = "ASSIGNOP";
    public static final String UMINUSOP = "UMINUSOP";
    public static final String NOTOP = "NOTOP";
    public static final String BOOLEANOP = "BOOLEANOP";
    public static final String CONDITIONAL = "CONDITIONAL";

    public static int typeCheckUnaryOp(String op, int type) throws TypeMismatchException, FatalError {
        if (op.equals(UMINUSOP)) {
            return typeCheckUminusOp(type);

        } else if (op.equals(NOTOP)) {
            //return"";
            //return typeCheckNotOp(type);
            return 0;

        } else if (op.equals(CONDITIONAL)) {
            return typeCheckConditionalOp(type);
        } else {
            throw new FatalError("Operazione " + op + " non verificabile nel checker unario");
        }
    }

    public static int typeCheckBinaryOp(String op, int type1, int type2) throws TypeMismatchException {
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

    private static int typeCheckConditionalOp(int type) throws TypeMismatchException {
        if (type == Sym.BOOL) {
            return Sym.VOID;
        } else {
            throw new TypeMismatchException("L'espressione nella condizione di un costrutto if, elif e while deve essere di tipo bool. Fornita: " + type);
        }
    }

    private static int typeCheckAssignOp(int type1, int type2) throws TypeMismatchException {
        if (type1 == Sym.INTEGER && type2 == Sym.INTEGER) {
            return Sym.VOID;
        } else if (type1 == Sym.REAL && type2 == Sym.REAL) {
            return Sym.VOID;
        } else if (type1 == Sym.STRING && type2 == Sym.STRING) {
            return Sym.VOID;
        } else if (type1 == Sym.BOOL && type2 == Sym.BOOL) {
            return Sym.VOID;
        }
        throw new TypeMismatchException("Non è possibile assegnare " + type2 + " a " + type1);
    }

    //PLUS, TIMES, . . . integer integer integer
    //PLUS, TIMES, . . . integer real real
    //PLUS, TIMES, . . . real integer real
    //PLUS, TIMES, . . . real real real
    private static int typeCheckAritmeticOp(int type1, int type2) throws TypeMismatchException {
        if (type1 == Sym.INTEGER && type2 == Sym.INTEGER) {
            return Sym.INTEGER;
        } else if (type1 == Sym.INTEGER && type2 == Sym.REAL) {
            return Sym.REAL;
        } else if (type1 == Sym.REAL && type2 == Sym.REAL) {
            return Sym.REAL;
        } else if (type1 == Sym.REAL && type2 == Sym.INTEGER) {
            return Sym.REAL;
        }
        throw new TypeMismatchException("Operazioni aritmentiche eseguibili solo tra espressioni di tipo int e real. Valori forniti: " + type1 + " - " + type2);
    }

    //LT, LE, GT, . . . integer integer boolean
    //LT, LE, GT, . . . real integer boolean
    //LT, LE, GT, . . . integer real boolean
    //LT, LE, GT, . . . real real boolean
    private static int typeCheckRelationalOp(int type1, int type2) throws TypeMismatchException {
        if (type1 == Sym.INTEGER && type2 == Sym.INTEGER) {
            return Sym.BOOL;
        } else if (type1 == Sym.INTEGER && type2 == Sym.REAL) {
            return Sym.BOOL;
        } else if (type1 == Sym.REAL && type2 == Sym.REAL) {
            return Sym.BOOL;
        } else if (type1 == Sym.REAL && type2 == Sym.INTEGER) {
            return Sym.BOOL;
        } else if (type1 == Sym.STRING && type2 == Sym.STRING) {
            return Sym.BOOL;
        } else if (type1 == Sym.BOOL && type2 == Sym.BOOL) {
            return Sym.BOOL;
        } else {
            throw new TypeMismatchException("Non possiamo relazionare " + type2 + " e " + type1);
        }
    }

    //STR CONCAT string string string
  /*  private static String typeCheckStringConcat(String type1, String type2) throws TypeMismatchException {
        if (type1.equals(STRING) && type2.equals(STRING)) {
            return STRING;
        } else {
            throw new TypeMismatchException("Non possiamo concatenare " + type2 + " e " + type1);
        }
    }*/

    //AND, OR boolean boolean boolean
    private static int typeCheckOrAndOp(int type1, int type2) throws TypeMismatchException {
        if (type1 == Sym.BOOL && type2 == Sym.BOOL) {
            return Sym.BOOL;
        } else {
            throw new TypeMismatchException("Per l'operazione di and, o or, sono richieste due espressioni di tipo bool. Fornite: " + type1 + " - " + type2);
        }
    }

    //MINUS integer integer
    //MINUS real real
    private static int typeCheckUminusOp(int type) throws TypeMismatchException {
        if (type == Sym.INTEGER) {
            return Sym.INTEGER;
        } else if (type == Sym.REAL) {
            return Sym.INTEGER;
        } else {
            throw new TypeMismatchException("Per l'operazione 'uminus' è richiesta una espressione di tipo int o float. Fornita: " + type);
        }
    }

   /* private static int typeCheckNotOp(String type) throws TypeMismatchException {
        if (type == Sym.BOOL) {
            return Sym.BOOL;
        } else {
            throw new TypeMismatchException("Per l'operazione 'not' è richiesta una espressione di tipo bool. Fornita: " + type);
        }

    }*/


}
