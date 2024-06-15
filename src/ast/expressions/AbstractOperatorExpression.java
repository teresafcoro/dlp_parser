package ast.expressions;

import ast.Expression;

public abstract class AbstractOperatorExpression extends AbstractDoubleExpression {

    protected String operator;

    public AbstractOperatorExpression(int line, int column, Expression left, Expression right, String operator) {
        super(line, column, left, right);
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

}
