package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class ArrayAccess extends AbstractDoubleExpression {

    public ArrayAccess(int line, int column, Expression left, Expression right) {
        super(line, column, left, right);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "ArrayAccess [left=" + left + ", right=" + right + ", line=" + line + ", column=" + column + "]";
    }

}
