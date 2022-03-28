package exception;

public class UndeclaredVariableException extends Throwable {
    private static final long serialVersionUID = 1L;

    public UndeclaredVariableException(String nameId) {
        super("Variabile non dichiarata" + nameId + "' non dichiarata!");
    }
}
