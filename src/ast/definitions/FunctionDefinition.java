package ast.definitions;

import ast.Statement;
import ast.Type;
import ast.types.FunctionType;
import ast.types.VoidType;
import visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class FunctionDefinition extends AbstractDefinition {

    private List<VariableDefinition> variableDefinitions;
    private List<Statement> statements;
    private int bytesLocals;

    public FunctionDefinition(int line, int column, String name, Type type, List<VariableDefinition> variableDefinitions, List<Statement> statements) {
        super(line, column, type, name);
        this.variableDefinitions = variableDefinitions;
        this.statements = statements;
    }

    public FunctionDefinition(int line, int column, List<VariableDefinition> variableDefinitions, List<Statement> statements) {
        super(line, column, new FunctionType(line, column, new ArrayList<VariableDefinition>(), VoidType.getInstance()), "main");
        this.variableDefinitions = variableDefinitions;
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<VariableDefinition> getVariableDefinitions() {
        return variableDefinitions;
    }

    public int getBytesLocals() {
        return bytesLocals;
    }

    public void setBytesLocals(int bytesLocals) {
        this.bytesLocals = bytesLocals;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "FunctionDefinition [" +
                ", statements=" + statements +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", bytesLocals=" + bytesLocals +
                ']';
    }
}
