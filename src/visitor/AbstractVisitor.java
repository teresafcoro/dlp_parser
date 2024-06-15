package visitor;

import ast.Definition;
import ast.Expression;
import ast.Program;
import ast.Statement;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.*;
import ast.statements.*;
import ast.types.*;

public abstract class AbstractVisitor<TP, TR> implements Visitor<TP, TR> {

    // Program

    public TR visit(Program ast, TP p) {
        for (Definition d : ast.getDefinitions())
            d.accept(this, p);
        return null;
    }

    // Definitions

    public TR visit(FunctionDefinition ast, TP p) {
        ast.getType().accept(this, p);
        for (VariableDefinition v : ast.getVariableDefinitions())
            v.accept(this, p);
        for (Statement s : ast.getStatements())
            s.accept(this, p);
        return null;
    }

    public TR visit(VariableDefinition ast, TP p) {
        ast.getType().accept(this, p);
        return null;
    }

    // Expressions

    public TR visit(ArithmeticOperator ast, TP p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this, p);
        return null;
    }

    public TR visit(ArrayAccess ast, TP p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this, p);
        return null;
    }

    @Override
    public TR visit(Cast ast, TP p) {
        ast.getTypecast().accept(this, p);
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(CharLiteral ast, TP p) {
        return null;
    }

    @Override
    public TR visit(ComparisonOperator ast, TP p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this, p);
        return null;
    }

    @Override
    public TR visit(FunctionCall ast, TP p) {
        ast.getVariable().accept(this, p);
        for (Expression e : ast.getParams())
            e.accept(this, p);
        return null;
    }

    @Override
    public TR visit(IntLiteral ast, TP p) {
        return null;
    }

    @Override
    public TR visit(LogicalOperator ast, TP p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this, p);
        return null;
    }

    @Override
    public TR visit(NotOperator ast, TP p) {
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(RealLiteral ast, TP p) {
        return null;
    }

    @Override
    public TR visit(StructAccess ast, TP p) {
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(UnaryMinus ast, TP p) {
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(Variable ast, TP p) {
        return null;
    }

    // Statements

    @Override
    public TR visit(Assignment ast, TP p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this, p);
        return null;
    }

    @Override
    public TR visit(IfElse ast, TP p) {
        ast.getExpression().accept(this, p);
        for (Statement s : ast.getBody())
            s.accept(this, p);
        for (Statement s : ast.getElseBody())
            s.accept(this, p);
        return null;
    }

    @Override
    public TR visit(Input ast, TP p) {
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(Print ast, TP p) {
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(Return ast, TP p) {
        ast.getExpression().accept(this, p);
        return null;
    }

    @Override
    public TR visit(While ast, TP p) {
        ast.getExpression().accept(this, p);
        for (Statement s : ast.getStatements())
            s.accept(this, p);
        return null;
    }

    // Types

    @Override
    public TR visit(ArrayType ast, TP p) {
        ast.getOf().accept(this, p);
        return null;
    }

    @Override
    public TR visit(CharType ast, TP p) {
        return null;
    }

    @Override
    public TR visit(ErrorType ast, TP p) {
        return null;
    }

    @Override
    public TR visit(Field ast, TP p) {
        ast.getType().accept(this, p);
        return null;
    }

    @Override
    public TR visit(FunctionType ast, TP p) {
        ast.getReturnType().accept(this, p);
        for (Definition d : ast.getParameters())
            d.accept(this, p);
        return null;
    }

    @Override
    public TR visit(IntType ast, TP p) {
        return null;
    }

    @Override
    public TR visit(RealType ast, TP p) {
        return null;
    }

    @Override
    public TR visit(StructType ast, TP p) {
        for (Field f : ast.getFields())
            f.accept(this, p);
        return null;
    }

    @Override
    public TR visit(VoidType ast, TP p) {
        return null;
    }

    @Override
    public TR visit(BooleanType ast, TP p) {
        return null;
    }

}
