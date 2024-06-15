package ast.expressions;

import visitor.Visitor;

public class IntLiteral extends AbstractExpression {

    private int value;

    public IntLiteral(int line, int column, int value) {
        super(line, column);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "IntLiteral [value=" + value + ", line=" + line + ", column=" + column + "]";
    }

}
