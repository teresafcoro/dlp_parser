package ast.expressions;

import ast.Definition;
import visitor.Visitor;

public class Variable extends AbstractExpression {

    private String name;
    private Definition definition;

    public Variable(int line, int column, String name) {
        super(line, column);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Definition getDefinition() {
        return definition;
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "Variable [name=" + name + ", line=" + line + ", column=" + column + "]";
    }

}
