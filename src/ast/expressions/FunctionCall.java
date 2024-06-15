package ast.expressions;

import ast.Expression;
import ast.Statement;
import visitor.Visitor;

import java.util.List;

public class FunctionCall extends AbstractExpression implements Statement {

    private Variable variable;
    private List<Expression> params;

    public FunctionCall(int line, int column, Variable variable, List<Expression> params) {
        super(line, column);
        this.variable = variable;
        this.params = params;
    }

    public Variable getVariable() {
        return variable;
    }

    public List<Expression> getParams() {
        return params;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "FunctionCall [variable=" + variable + ", params=" + params + ", line=" + line + ", column=" + column
                + "]";
    }

}
