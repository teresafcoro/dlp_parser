package ast.types;

import ast.ASTNode;
import ast.Type;
import visitor.Visitor;

public class IntType extends AbstractType {

    private static IntType instance;

    private IntType() {
        super(0, 0);
    }

    public static IntType getInstance() {
        if (instance == null)
            instance = new IntType();
        return instance;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public Type arithmetic(Type other, ASTNode ast) {
        if (other instanceof IntType || other instanceof RealType)
            return this;
        return super.arithmetic(other, ast);
    }

    @Override
    public Type arithmetic(ASTNode ast) {
        return this;
    }

    @Override
    public Type canBeCastTo(Type other, ASTNode ast) {
        if (other.isBuiltInType(ast))
            return other;
        return super.canBeCastTo(other, ast);
    }

    @Override
    public Type comparison(Type other, ASTNode ast) {
        if (other.isBuiltInType(ast))
            return BooleanType.getInstance();
        return super.comparison(other, ast);
    }

    @Override
    public boolean isBuiltInType(ASTNode ast) {
        return true;
    }

    @Override
    public int numberOfBytes() {
        return 2;
    }

    @Override
    public Type promotesTo(Type other, ASTNode ast) {
        if (other instanceof IntType || other instanceof RealType)
            return other;
        return super.promotesTo(other, ast);
    }

    @Override
    public char suffix() {
        return 'i';
    }

    @Override
    public String toString() {
        return "IntType";
    }

}
