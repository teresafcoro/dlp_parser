package codegenerator;

import ast.Program;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.*;
import ast.statements.*;
import ast.types.*;
import visitor.Visitor;

public abstract class AbstractCGVisitor<TP, TR> implements Visitor<TP, TR> {

    @Override
    public TR visit(Program ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Program\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    /* Definitions */
    @Override
    public TR visit(FunctionDefinition ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"FunctionDefinition\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(VariableDefinition ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"VariableDefinition\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    /* Expressions */
    @Override
    public TR visit(ArithmeticOperator ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"ArithmeticOperator\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(ArrayAccess ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"ArrayAccess\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(Cast ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Cast\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(CharLiteral ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"CharLiteral\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(ComparisonOperator ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"ComparisonOperator\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(IntLiteral ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"IntLiteral\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(LogicalOperator ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"LogicalOperator\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(RealLiteral ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"RealLiteral\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(StructAccess ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"StructAccess\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(UnaryMinus ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"UnaryMinus\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(NotOperator ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"NotOperator\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(Variable ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Variable\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    /* Expression & Statement */
    @Override
    public TR visit(FunctionCall ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"FunctionCall\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    /* Statements */
    @Override
    public TR visit(Assignment ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Assignment\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(IfElse ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"IfElse\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(Input ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Input\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(Print ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Print\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(Return ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Return\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(While ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"While\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    /* Types */
    @Override
    public TR visit(ArrayType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"ArrayType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(Field ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"Field\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(CharType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"CharType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(ErrorType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"ErrorType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(FunctionType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"FunctionType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(IntType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"IntType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(RealType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"RealType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(StructType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"StructType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(VoidType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"VoidType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

    @Override
    public TR visit(BooleanType ast, TP p) {
        throw new IllegalStateException("ERROR: Plantilla de generacion de codigo \"BooleanType\" no definida para la funcion: " + ast.getClass().getSimpleName());
    }

}
