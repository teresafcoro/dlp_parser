package codegenerator;

import ast.definitions.VariableDefinition;
import ast.expressions.ArrayAccess;
import ast.expressions.StructAccess;
import ast.expressions.Variable;
import ast.types.IntType;
import ast.types.StructType;

public class AddressCGVisitor extends AbstractCGVisitor<Void, Void> {

    private ValueCGVisitor value;
    private CodeGenerator cg;

    public AddressCGVisitor(CodeGenerator cg) {
        this.cg = cg;
    }

    public ValueCGVisitor getValue() {
        return value;
    }

    public void setValue(ValueCGVisitor value) {
        this.value = value;
    }

    /*
      address[[Variable: expression -> ID]]() =
      if (expression.definition.scope == 0)
          <pusha> expression.definition.offset
      else {
          <push bp>
          <pushi> expression.definition.offset
          <addi>
      }
     */
    @Override
    public Void visit(Variable ast, Void p) {
        if (ast.getDefinition().getScope() == 0) {
            this.cg.pusha(((VariableDefinition) ast.getDefinition()).getOffset());
        } else {
            this.cg.pushBP();
            this.cg.push(((VariableDefinition) ast.getDefinition()).getOffset());
            this.cg.add(IntType.getInstance());
        }
        return null;
    }

    /*
      address[[ArrayAccess: expression1 -> expression2 expression3]]() =
      address[[expression2]]
      value[[expression3]]
      <pushi> expression1.type.numberOfBytes
      <muli>
      <addi>
     */
    @Override
    public Void visit(ArrayAccess ast, Void p) {
        ast.getLeft().accept(this, p);
        ast.getRight().accept(this.value, p);
        this.cg.push(ast.getType().numberOfBytes());
        this.cg.mul(IntType.getInstance());
        this.cg.add(IntType.getInstance());
        return null;
    }

    /*
      address[[StructAccess: expression1 -> expression2 ID]]() =
      address[[expression2]]
      <pushi> expression2.type.getField(expression1.getName()).getOffset()
      <addi>
     */
    @Override
    public Void visit(StructAccess ast, Void p) {
        ast.getExpression().accept(this, p);
        this.cg.push(((StructType) ast.getExpression().getType()).getField(ast.getField()).getOffset());
        this.cg.add(IntType.getInstance());
        return null;
    }

}
