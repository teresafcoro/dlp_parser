package ast.expressions;

import ast.Expression;

public abstract class AbstractSingleExpression extends AbstractExpression {

    protected Expression expression;

    public AbstractSingleExpression(int line, int column, Expression expression) {
        super(line, column);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

}
