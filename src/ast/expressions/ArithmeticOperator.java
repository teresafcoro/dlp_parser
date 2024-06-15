package ast.expressions;

import ast.Expression;
import visitor.Visitor;

public class ArithmeticOperator extends AbstractOperatorExpression {

    public ArithmeticOperator(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right, operator);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "ArithmeticOperator [operator=" + operator + ", left=" + left + ", right=" + right + ", line=" + line
                + ", column=" + column + "]";
    }

}
