package codegenerator;

import ast.Statement;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.types.Field;
import ast.types.FunctionType;
import ast.types.StructType;
import visitor.AbstractVisitor;

public class OffSetVisitor extends AbstractVisitor<Void, Void> {

    private int totalLocalVariablesSize, totalGlobalVarDefSizes;

    @Override
    public Void visit(VariableDefinition ast, Void p) {
        if (ast.getScope() == 0) {
            ast.setOffset(totalGlobalVarDefSizes);
            this.totalGlobalVarDefSizes += ast.getType().numberOfBytes();
        } else {
            this.totalLocalVariablesSize -= ast.getType().numberOfBytes();
            ast.setOffset(totalLocalVariablesSize);
        }
        ast.getType().accept(this, p);
        return null;
    }

    @Override
    public Void visit(FunctionDefinition ast, Void p) {
        this.totalLocalVariablesSize = 0;
        ast.getType().accept(this, p);
        for (VariableDefinition v : ast.getVariableDefinitions())
            v.accept(this, p);
        for (Statement s : ast.getStatements())
            s.accept(this, p);
        return null;
    }

    @Override
    public Void visit(FunctionType ast, Void p) {
        int offset = 4;
        for (int i = ast.getParameters().size() - 1; i >= 0; i--) {
            ast.getParameters().get(i).setOffset(offset);
            offset += ast.getParameters().get(i).getType().numberOfBytes();
        }
        return null;
    }

    @Override
    public Void visit(StructType ast, Void p) {
        int offset = 0;
        for (Field field : ast.getFields()) {
            field.setOffset(offset);
            offset += field.getType().numberOfBytes();
            field.getType().accept(this, p);
        }
        return null;
    }

}
