package ast.expressions;

import ast.Expression;
import ast.Type;
import visitor.Visitor;

public class Cast extends AbstractSingleExpression {

    private Type typecast;

    public Cast(int line, int column, Expression expression, Type typecast) {
        super(line, column, expression);
        this.typecast = typecast;
    }

    public Type getTypecast() {
        return typecast;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "Cast [typecast=" + typecast + ", expression=" + expression + ", line=" + line + ", column=" + column
                + "]";
    }

}
