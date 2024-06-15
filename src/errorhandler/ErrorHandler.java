package errorhandler;

import ast.types.ErrorType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

    private static ErrorHandler instance;
    private List<ErrorType> errors;

    public ErrorHandler() {
        errors = new ArrayList<ErrorType>();
    }

    public static ErrorHandler getInstance() {
        if (instance == null)
            instance = new ErrorHandler();
        return instance;
    }

    public boolean anyError() {
        return !this.errors.isEmpty();
    }

    public void addError(ErrorType error) {
        this.errors.add(error);
    }

    public void showErrors(PrintStream ps) {
        for (ErrorType et : errors)
            ps.println("\t(" + et.getLine() + "," + et.getColumn() + ") " + et.getMessage());
    }

}
