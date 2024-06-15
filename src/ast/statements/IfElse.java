package ast.statements;

import ast.Expression;
import ast.Statement;
import visitor.Visitor;

import java.util.List;

public class IfElse extends AbstractSingleExpressionStatement {

    private List<Statement> body, elseBody;

    public IfElse(int line, int column, Expression condition, List<Statement> ifBody, List<Statement> elseBody) {
        super(line, column, condition);
        this.body = ifBody;
        this.elseBody = elseBody;
    }

    public List<Statement> getBody() {
        return body;
    }

    public List<Statement> getElseBody() {
        return elseBody;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "IfElse [body=" + body + ", elseBody=" + elseBody + ", expression=" + expression + ", line=" + line
                + ", column=" + column + "]";
    }

}
