package ast.statements;

import ast.Expression;

public abstract class AbstractSingleExpressionStatement extends AbstractStatement {

    protected Expression expression;

    public AbstractSingleExpressionStatement(int line, int column, Expression expression) {
        super(line, column);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

}
