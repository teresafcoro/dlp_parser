package ast.types;

import ast.ASTNode;
import ast.Type;
import visitor.Visitor;

public class BooleanType extends AbstractType {

    private static BooleanType instance;

    public BooleanType() {
        super(0, 0);
    }

    public static BooleanType getInstance() {
        if (instance == null)
            instance = new BooleanType();
        return instance;
    }


    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public Type comparison(Type other, ASTNode ast) {
        if (other.isBuiltInType(ast))
            return this;
        return super.comparison(other, ast);
    }

    @Override
    public boolean isBuiltInType(ASTNode ast) {
        return true;
    }

    @Override
    public Type logical(Type other, ASTNode ast) {
        if (other instanceof BooleanType)
            return other;
        return super.logical(other, ast);
    }

    @Override
    public Type logical(ASTNode ast) {
        return this;
    }

    @Override
    public boolean isLogical(ASTNode ast) {
        return true;
    }

    @Override
    public char suffix() {
        return 'i';
    }

    @Override
    public String toString() {
        return "BooleanType";
    }

}
