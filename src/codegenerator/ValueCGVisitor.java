package codegenerator;

import ast.expressions.*;
import ast.types.FunctionType;
import ast.types.IntType;

public class ValueCGVisitor extends AbstractCGVisitor<Void, Void> {

    private AddressCGVisitor address;
    private CodeGenerator cg;

    public ValueCGVisitor(CodeGenerator cg) {
        this.cg = cg;
    }

    public void setAddress(AddressCGVisitor address) {
        this.address = address;
    }

    /*
      value[[CharLiteral: expression -> CHAR_CONSTANT]]() =
      <pushb> CHAR_CONSTANT
     */
    @Override
    public Void visit(CharLiteral ast, Void p) {
        this.cg.push(ast.getValue());
        return null;
    }

    /*
      value[[IntLiteral: expression -> INT_CONSTANT]]() =
      <pushi> INT_CONSTANT
     */
    @Override
    public Void visit(IntLiteral ast, Void p) {
        this.cg.push(ast.getValue());
        return null;
    }

    /*
      value[[RealLiteral: expression -> REAL_CONSTANT]]() =
      <pushf> REAL_CONSTANT
     */
    @Override
    public Void visit(RealLiteral ast, Void p) {
        this.cg.push(ast.getValue());
        return null;
    }

    /*
      value[[Variable: expression -> ID]]() =
      address[[ID]]()
      <load> expression.type.suffix()
     */
    @Override
    public Void visit(Variable ast, Void p) {
        ast.accept(this.address, p);
        this.cg.load(ast.getType());
        return null;
    }

    /*
      value[[ArithmeticOperation: expression1 -> expression2 (+|-|*|/|%) expression3]]() =
      value[[expression2]]()
      cg.convertTo(expression2.type, expression1.type)
      value[[expression3]]()
      cg.convertTo(expression3.type, expression1.type)
      cg.arithmetic(expression1.operator, expression1.type)
     */
    @Override
    public Void visit(ArithmeticOperator ast, Void p) {
        ast.getLeft().accept(this, p);
        this.cg.convertTo(ast.getLeft().getType(), ast.getType());
        ast.getRight().accept(this, p);
        this.cg.convertTo(ast.getRight().getType(), ast.getType());
        this.cg.arithmetic(ast.getOperator(), ast.getType());
        return null;
    }

    /*
      value[[ComparisonOperation: expression1 -> expression2 (==|!=|>=|<=|>|<) expression3]]() =
      value[[expression2]]()
      cg.convertTo(expression2.type, expression1.type)
      value[[expression3]]()
      cg.convertTo(expression3.type, expression1.type)
      cg.comparison(expression1.operator, expression1.type)
     */
    @Override
    public Void visit(ComparisonOperator ast, Void p) {
        ast.getLeft().accept(this, p);
        this.cg.convertTo(ast.getLeft().getType(), ast.getType());
        ast.getRight().accept(this, p);
        this.cg.convertTo(ast.getRight().getType(), ast.getType());
        cg.comparison(ast.getOperator(), ast.getType());
        return null;
    }

    /*
      value[[LogicalOperation: expression1 -> expression2 (&&|||) expression3]]() =
      value[[expression2]]()
      value[[expression3]]()
      cg.logical(expression1.operator)
     */
    @Override
    public Void visit(LogicalOperator ast, Void p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this, p);
        this.cg.logical(ast.getOperator());
        return null;
    }

    /*
      value[[Cast: expression1-> type expression2]]() =
      value[[expression2]]
      cg.convertTo(expression2.type, type)
     */
    @Override
    public Void visit(Cast ast, Void p) {
        ast.getExpression().accept(this, p);
        this.cg.convertTo(ast.getExpression().getType(), ast.getType());
        return null;
    }

    /*
      value[[UnaryMinus: expression1 -> expression2]]() =
      value[[expression2]]()
      cg.convertTo(expression2.type, expression1.type)
      <pushi> -1
      cg.convertTo(IntType, expression1.type)
      <mul> expression1.type.suffix()
     */
    @Override
    public Void visit(UnaryMinus ast, Void p) {
        ast.getExpression().accept(this, p);
        this.cg.convertTo(ast.getExpression().getType(), ast.getType());
        this.cg.push(-1);
        this.cg.convertTo(IntType.getInstance(), ast.getType());
        this.cg.mul(ast.getType());
        return null;
    }

    /*
      value[[NotOperator: expression1 -> expression2]]() =
      value[[expression2]]()
      cg.logical(expression1.operator)
     */
    @Override
    public Void visit(NotOperator ast, Void p) {
        ast.getExpression().accept(this, p);
        this.cg.not();
        return null;
    }

    /*
      value[[FunctionCall: expression1 -> expression2 expression3*]]() =
      for (int i = 0; i < ast.getExpressions().size(); i++) {
        ast.getExpressions().get(i).accept(this, p)
        this.cg.convertTo(ast.getExpressions().get(i).getType(),
          ((FunctionType) ast.getVariable().getDefinition().getType()).getVarDefinitions().get(i).getType())
      }
      expression2.definition.type.varDefinitions
      <call> expression2.name
     */
    @Override
    public Void visit(FunctionCall ast, Void p) {
        for (int i = 0; i < ast.getParams().size(); i++) {
            ast.getParams().get(i).accept(this, p);
            this.cg.convertTo(ast.getParams().get(i).getType(),
                    ((FunctionType) ast.getVariable().getDefinition().getType()).getParameters().get(i).getType());
        }
        this.cg.call(ast.getVariable().getName());
        return null;
    }

    /*
      value[[ArrayAccess: expression1 -> expression2 expression3]]() =
      address[[expression1]]
      <load> expression1.type.suffix()
     */
    @Override
    public Void visit(ArrayAccess ast, Void p) {
        ast.accept(this.address, p);
        this.cg.load(ast.getType());
        return null;
    }

    /*
      value[[StructAccess: expression1 -> expression2 ID]]() =
      address[[expression1]]
      <load> expression1.type.suffix()
     */
    @Override
    public Void visit(StructAccess ast, Void p) {
        ast.accept(this.address, p);
        this.cg.load(ast.getType());
        return null;
    }

}
