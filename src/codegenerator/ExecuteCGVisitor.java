package codegenerator;

import ast.Definition;
import ast.Program;
import ast.definitions.FunctionDefinition;
import ast.definitions.VariableDefinition;
import ast.expressions.FunctionCall;
import ast.statements.*;
import ast.types.BooleanType;
import ast.types.CharType;
import ast.types.FunctionType;
import ast.types.VoidType;

public class ExecuteCGVisitor extends AbstractCGVisitor<FunctionDefinition, Void> {

    private CodeGenerator cg;
    private AddressCGVisitor address;
    private ValueCGVisitor value;

    public ExecuteCGVisitor(CodeGenerator cg) {
        this.cg = cg;
        this.address = new AddressCGVisitor(cg);
        this.value = new ValueCGVisitor(cg);
        this.address.setValue(value);
        this.value.setAddress(address);
    }

    /*
      execute[[Program: program -> definition*]]() =
      for (definition in definition*) {
        if (def instanceof VariableDefinition)
          execute[[definition]]()
      }
      <call main>
      <halt>
      for (definition in definition*) {
        if (def instanceof FunctionDefinition)
          execute[[definition]]()
      }
     */
    @Override
    public Void visit(Program ast, FunctionDefinition p) {
        for (Definition definition : ast.getDefinitions()) {
            if (definition instanceof VariableDefinition)
                definition.accept(this, p);
        }
        this.cg.callMain();
        this.cg.halt();
        for (Definition definition : ast.getDefinitions()) {
            if (definition instanceof FunctionDefinition)
                definition.accept(this, p);
        }
        return null;
    }

    /*
      execute[[Input: statement -> expression]]() =
      address[[expression]]()
      <in> expression.type.suffix()
      <store> expression.type.suffix()
     */
    @Override
    public Void visit(Input ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.comment("Read");
        ast.getExpression().accept(this.address, null);
        this.cg.in(ast.getExpression().getType());
        this.cg.store(ast.getExpression().getType());
        return null;
    }

    /*
      execute[[Print: statement -> expression]]() =
      value[[expression]]()
      <out> expression.type.suffix()
     */
    @Override
    public Void visit(Print ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.comment("Write");
        ast.getExpression().accept(this.value, null);
        if (ast.getExpression() instanceof BooleanType) {
            int labelFalse = this.cg.getLabel();
            int labelEnd = this.cg.getLabel();

            this.cg.jz(labelFalse);
            this.cg.push('t');
            this.cg.out(CharType.getInstance());
            this.cg.push('r');
            this.cg.out(CharType.getInstance());
            this.cg.push('u');
            this.cg.out(CharType.getInstance());
            this.cg.push('e');
            this.cg.out(CharType.getInstance());
            this.cg.jmp(labelEnd);
            this.cg.printLabel(labelFalse);
            this.cg.push('f');
            this.cg.out(CharType.getInstance());
            this.cg.push('a');
            this.cg.out(CharType.getInstance());
            this.cg.push('l');
            this.cg.out(CharType.getInstance());
            this.cg.push('s');
            this.cg.out(CharType.getInstance());
            this.cg.push('e');
            this.cg.out(CharType.getInstance());
            this.cg.printLabel(labelEnd);
        }
        else
            this.cg.out(ast.getExpression().getType());
        return null;
    }

    /*
      execute[[Assignment: statement -> expression1 expression2]]() =
      address[[expression1]]()
      value[[expression2]]()
      cg.convertTo(expression2.type, expression1.type)
      <store> expression1.type.suffix()
     */
    @Override
    public Void visit(Assignment ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.comment("Assignment");
        ast.getLeft().accept(this.address, null);
        ast.getRight().accept(this.value, null);
        this.cg.convertTo(ast.getRight().getType(), ast.getLeft().getType());
        this.cg.store(ast.getLeft().getType());
        return null;
    }

    /*
      execute[[VariableDefinition: definition -> ID type]]() =
      ' * type.toString() ID (offset definition.offset)
     */
    @Override
    public Void visit(VariableDefinition ast, FunctionDefinition p) {
        this.cg.comment(ast.getType().toString() + " " + ast.getName() + " (offset " + ast.getOffset() + ")");
        return null;
    }

    /*
      execute[[FunctionDefinition: definition -> ID type variabledefinition* statement*]]() =
      ID:
      ' * Parameters:
      execute[[type]]()
      ' * Local variables:
      variabledefinition*.forEach(varDef -> execute[[varDef]]())
      definition.bytesLocals = variabledefinition*.isEmpty() ? 0 : -1 * variabledefinition*.get(variabledefinition*.size() - 1).offset
      <enter> definition.bytesLocals
      definition.type.bytesParams = varDefinitions.stream().mapToInt(varDef -> varDef.getType().numberOfBytes()).sum();
      definition.type.bytesReturns = type.returnType.numberOfBytes();
      statement*.forEach(stmnt -> execute[[stmnt]](functionDefinition))
      if (definition.bytesReturn === 0)
        <ret> definition.getBytesReturn(), definition.getBytesLocals(), definition.type.getBytesParams()
     */
    @Override
    public Void visit(FunctionDefinition ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.printFunction(ast.getName());
        this.cg.comment("Parameters");
        ast.getType().accept(this, p);
        this.cg.comment("Local variables");
        ast.getVariableDefinitions().forEach(varDef -> varDef.accept(this, p));
        ast.setBytesLocals(ast.getVariableDefinitions().isEmpty() ? 0 : -1 * ast.getVariableDefinitions().get(ast.getVariableDefinitions().size() - 1).getOffset());
        this.cg.enter(ast.getBytesLocals());
        ((FunctionType) ast.getType()).setBytesParams(((FunctionType) ast.getType()).getParameters().stream().mapToInt(varDef -> varDef.getType().numberOfBytes()).sum());
        ((FunctionType) ast.getType()).setBytesReturn(((FunctionType) ast.getType()).getReturnType().numberOfBytes());
        ast.getStatements().forEach(statement -> statement.accept(this, ast));
        if (((FunctionType) ast.getType()).getBytesReturn() == 0)
            this.cg.ret(((FunctionType) ast.getType()).getBytesReturn(), ast.getBytesLocals(), ((FunctionType) ast.getType()).getBytesParams());
        return null;
    }

    /*
      execute[[FunctionType: type -> variabledefinition* type]]() =
      variabledefinition*.forEach(varDef -> execute[[varDef]]())
     */
    @Override
    public Void visit(FunctionType ast, FunctionDefinition p) {
        ast.getParameters().forEach(varDef -> varDef.accept(this, p));
        return null;
    }

    /*
      execute[[Return: statement -> expression]](Definition functionDefinition) =
      value[[expression]]()
      <ret> ((FunctionType) functionDefinition.type).getBytesReturn(),
      functionDefinition.getBytesLocals(),
      ((FunctionType) functionDefinition.type).getBytesReturn()
     */
    @Override
    public Void visit(Return ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.comment("Return");
        ast.getExpression().accept(this.value, null);
        this.cg.ret(((FunctionType) p.getType()).getBytesReturn(), p.getBytesLocals(), ((FunctionType) p.getType()).getBytesParams());
        return null;
    }

    /*
      execute[[FunctionCall: statement -> expression1 expression2*]]() =
      value[[(Expression) statement]]()
      if (!((Expression) statement).type instanceof VoidType)
        <pop> ((Expression) statement).type.suffix()
     */
    @Override
    public Void visit(FunctionCall ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        ast.accept(this.value, null);
        if (!(ast.getType() instanceof VoidType))
            this.cg.pop(ast.getType());
        return null;
    }

    /*
      execute[[IfElse: statement1 -> expression statement2* statement3*]]() =
      int elseBody = cg.getLabel();
      int end = cg.getLabel();
      value[[expression]]()
      <jz> elseBody
      statement2*.forEach(stmnt -> execute[[stmnt]])
      <jmp> end
      <label> elseBody <:>
      statement3*.forEach(stmnt -> execute[[stmnt]])
      <label> end <:>
     */
    @Override
    public Void visit(IfElse ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.comment("If");
        int elseBody = this.cg.getLabel();
        int end = this.cg.getLabel();
        this.cg.commentLine(ast.getLine());
        ast.getExpression().accept(this.value, null);
        this.cg.jz(elseBody);
        this.cg.comment("if body");
        ast.getBody().forEach(statement -> statement.accept(this, p));
        this.cg.jmp(end);
        this.cg.printLabel(elseBody);
        this.cg.comment("else body");
        ast.getElseBody().forEach(statement -> statement.accept(this, p));
        this.cg.printLabel(end);
        return null;
    }

    /*
      execute[[While: statement1 -> expression statement2*]]() =
      int condition = cg.getLabel
      int end = cg.getLabel()
      <label> condition <:>
      value[[expression]]()
      <jz> end
      statement2*.forEach(stmnt -> execute[[stmnt]])
      <jmp label> condition
      <label> end <:>
     */
    @Override
    public Void visit(While ast, FunctionDefinition p) {
        this.cg.commentLine(ast.getLine());
        this.cg.comment("While");
        int condition = this.cg.getLabel();
        int end = this.cg.getLabel();
        this.cg.commentLine(ast.getLine());
        this.cg.printLabel(condition);
        ast.getExpression().accept(this.value, null);
        this.cg.jz(end);
        this.cg.comment("While body");
        ast.getStatements().forEach(statement -> statement.accept(this, p));
        this.cg.jmp(condition);
        this.cg.printLabel(end);
        return null;
    }

}
