package ast.types;

import ast.ASTNode;
import ast.Type;
import ast.definitions.VariableDefinition;
import visitor.Visitor;

import java.util.List;

public class FunctionType extends AbstractType {

    private List<VariableDefinition> parameters;
    private Type returnType;
    private int bytesParams, bytesReturn;

    public FunctionType(int line, int column, List<VariableDefinition> parameters, Type returnType) {
        super(line, column);
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<VariableDefinition> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public int getBytesParams() {
        return bytesParams;
    }

    public void setBytesParams(int bytesParams) {
        this.bytesParams = bytesParams;
    }

    public int getBytesReturn() {
        return bytesReturn;
    }

    public void setBytesReturn(int bytesReturn) {
        this.bytesReturn = bytesReturn;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public Type parenthesis(List<Type> types, ASTNode ast) {
        if (types.size() != parameters.size())
            return super.parenthesis(types, ast);
        for (int i = 0; i < parameters.size(); i++)
            types.get(i).promotesTo(parameters.get(i).getType(), ast);
        return returnType;
    }

    @Override
    public String toString() {
        return "FunctionType [" +
                "parameters=" + parameters +
                ", returnType=" + returnType +
                ", bytesParams=" + bytesParams +
                ", bytesReturn=" + bytesReturn +
                ']';
    }
}
