package ast.statements;

import ast.Expression;
import visitor.Visitor;

public class Print extends AbstractSingleExpressionStatement {

    public Print(int line, int column, Expression expression) {
        super(line, column, expression);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "Print [expression=" + expression + ", line=" + line + ", column=" + column + "]";
    }

}
