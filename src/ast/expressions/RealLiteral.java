package ast.expressions;

import visitor.Visitor;

public class RealLiteral extends AbstractExpression {

    private double value;

    public RealLiteral(int line, int column, double value) {
        super(line, column);
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "RealLiteral [value=" + value + ", line=" + line + ", column=" + column + "]";
    }

}
