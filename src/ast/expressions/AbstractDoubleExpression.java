package ast.expressions;

import ast.Expression;

public abstract class AbstractDoubleExpression extends AbstractExpression {

    protected Expression left, right;

    public AbstractDoubleExpression(int line, int column, Expression left, Expression right) {
        super(line, column);
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

}
