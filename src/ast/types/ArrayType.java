package ast.types;

import ast.ASTNode;
import ast.Type;
import visitor.Visitor;

public class ArrayType extends AbstractType {

    private int size;
    private Type of;

    public ArrayType(int line, int column, int size, Type of) {
        super(line, column);
        this.size = size;
        this.of = of;
    }

    public int getSize() {
        return size;
    }

    public Type getOf() {
        return of;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public int numberOfBytes() {
        int totalNumberOfBytes = 0;
        for (int i = 0; i < size; i++)
            totalNumberOfBytes += of.numberOfBytes();
        return totalNumberOfBytes;
    }

    @Override
    public Type squareBrackets(Type other, ASTNode ast) {
        if (other instanceof IntType)
            return this.of;
        return super.squareBrackets(other, ast);
    }

    @Override
    public String toString() {
        return "ArrayType[of:" + of + ",size:" + size + "]";
    }

}
