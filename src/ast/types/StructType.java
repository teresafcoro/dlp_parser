package ast.types;

import ast.ASTNode;
import ast.Type;
import visitor.Visitor;

import java.util.List;

public class StructType extends AbstractType {

    private final List<Field> fields;

    public StructType(int line, int column, List<Field> fields) {
        super(line, column);
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public Type dot(String id, ASTNode ast) {
        for (Field field : fields) {
            if (field.getName().equals(id))
                return field.getType();
        }
        return super.dot(id, ast);
    }

    @Override
    public int numberOfBytes() {
        int totalNumberOfBytes = 0;
        for (Field field : fields)
            totalNumberOfBytes += field.getType().numberOfBytes();
        return totalNumberOfBytes;
    }

    public Field getField(String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName))
                return field;
        }
        return null;
    }

    @Override
    public String toString() {
        return "StructType[fields:" + fields + "]";
    }

}
