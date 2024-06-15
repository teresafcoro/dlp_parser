package semantic;

import ast.Definition;
import ast.Statement;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.Variable;
import ast.types.ErrorType;
import symboltable.SymbolTable;
import visitor.AbstractVisitor;

public class IdentificationVisitor extends AbstractVisitor<Void, Void> {

    private SymbolTable table = new SymbolTable();

    // Definitions

    @Override
    public Void visit(FunctionDefinition ast, Void p) {
        if (!table.insert(ast))
            new ErrorType(ast.getLine(), ast.getColumn(), "La función '" + ast.getName() + "' definida tiene el mismo nombre que otra ya declarada");
        table.set();
        ast.getType().accept(this, p);
        for (VariableDefinition v : ast.getVariableDefinitions())
            v.accept(this, p);
        for (Statement s : ast.getStatements())
            s.accept(this, p);
        table.reset();
        return null;
    }

    @Override
    public Void visit(VariableDefinition ast, Void p) {
        if (!table.insert(ast))
            new ErrorType(ast.getLine(), ast.getColumn(), "La variable '" + ast.getName() + "' definida tiene el mismo nombre que otra ya declarada en el ámbito");
        ast.getType().accept(this, p);
        return null;
    }

    // Expressions

    @Override
    public Void visit(Variable ast, Void p) {
        Definition d = table.find(ast.getName());
        if (d == null)
            d = new VariableDefinition(0, 0, new ErrorType(ast.getLine(), ast.getColumn(), "La variable '" + ast.getName() + "' no se declaró"), ast.getName());
        ast.setDefinition(d);
        return null;
    }

}
