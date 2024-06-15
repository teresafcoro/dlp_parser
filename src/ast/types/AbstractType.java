package ast.types;

import ast.ASTNode;
import ast.AbstractASTNode;
import ast.Type;

import java.util.List;

public abstract class AbstractType extends AbstractASTNode implements Type {

    public AbstractType(int line, int column) {
        super(line, column);
    }

    @Override
    public Type arithmetic(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Una operación aritmética no pudo realizarse por sus tipos: " + other + " y " + this);
    }

    @Override
    public Type arithmetic(ASTNode ast) {
        return new ErrorType(ast.getLine(), ast.getColumn(), "Se esperaba un tipo aritmético");
    }

    @Override
    public Type asBuiltInType(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        else if (other.isBuiltInType(ast))
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Se esperaba un tipo built-in, pero se recibió: " + this);
    }

    @Override
    public Type asLogical(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        else if (other.isLogical(ast))
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Se esperaba un tipo lógico, pero se recibió: " + this);
    }

    @Override
    public Type canBeCastTo(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Una operación de casting no pudo realizarse, se esperaba: " + other + ", pero se recibió: " + this);
    }

    @Override
    public Type comparison(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Una operación de comparación no pudo realizarse por sus tipos: " + this + " y " + other);
    }

    @Override
    public Type dot(String id, ASTNode ast) {
        return new ErrorType(ast.getLine(), ast.getColumn(), "El campo " + id + " no se definió en el struct");
    }

    @Override
    public boolean isBuiltInType(ASTNode ast) {
        return false;
    }

    @Override
    public boolean isLogical(ASTNode ast) {
        return false;
    }

    @Override
    public Type logical(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Una operación lógica no pudo realizarse por sus tipos: " + other + " y " + this);
    }

    @Override
    public Type logical(ASTNode ast) {
        return new ErrorType(ast.getLine(), ast.getColumn(), "Se esperaba un tipo lógico, pero se recibió: " + this);
    }

    @Override
    public int numberOfBytes() {
        return 0;
    }

    @Override
    public Type parenthesis(List<Type> others, ASTNode ast) {
        StringBuilder paramTypes = new StringBuilder();
        for (Type other : others) {
            if (other instanceof ErrorType)
                return other;
            else
                paramTypes.append(other.toString()).append(" ");
        }
        if (paramTypes.isEmpty())
            paramTypes.append("none");
        return new ErrorType(ast.getLine(), ast.getColumn(), "Parámetros erróneos al llamar a la función, se recibió: " + paramTypes);
    }

    @Override
    public Type promotesTo(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Se esperaba: " + other + ", pero se recibió: " + this);
    }

    @Override
    public Type squareBrackets(Type other, ASTNode ast) {
        if (other instanceof ErrorType)
            return other;
        return new ErrorType(ast.getLine(), ast.getColumn(), "Se esperaba un tipo array, pero se recibió: " + this);
    }

    @Override
    public char suffix() {
        return '\0';
    }

}
