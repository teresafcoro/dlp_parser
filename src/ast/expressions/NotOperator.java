package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class NotOperator extends AbstractSingleExpression {

    public NotOperator(int line, int column, Expression expression) {
        super(line, column, expression);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "NotOperator [expression=" + expression + ", line=" + line + ", column=" + column + "]";
    }

}
