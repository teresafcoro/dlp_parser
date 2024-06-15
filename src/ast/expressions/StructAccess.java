package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class StructAccess extends AbstractSingleExpression {

    private String field;

    public StructAccess(int line, int column, Expression expression, String field) {
        super(line, column, expression);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "StructAccess [field=" + field + ", expression=" + expression + ", line=" + line + ", column=" + column
                + "]";
    }

}
