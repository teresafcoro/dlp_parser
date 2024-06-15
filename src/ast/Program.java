package ast;

import visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Program extends AbstractASTNode {

    private List<Definition> definitions;

    public Program(int line, int column, List<Definition> definitions) {
        super(line, column);
        this.definitions = definitions;
    }

    public List<Definition> getDefinitions() {
        return new ArrayList<>(definitions);
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> visitor, TP p) {
        return visitor.visit(this, p);
    }

    @Override
    public String toString() {
        return "Program [definitions=" + definitions + ", line=" + line + ", column=" + column + "]";
    }
}
