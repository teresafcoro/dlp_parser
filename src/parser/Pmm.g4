grammar Pmm;

@header {
    import ast.*;
    import ast.definitions.*;
    import ast.expressions.*;
    import ast.statements.*;
    import ast.types.*;
}

program returns [Program ast] locals [List<Definition> definitions = new ArrayList<Definition>()]:
    (vd=variableDefinition ';' { $definitions.addAll($vd.ast); }
    | fd=functionDefinition { $definitions.add($fd.ast); })* m=main { $definitions.add($m.ast); } EOF { $ast = new Program(0, 0, $definitions); }
    ;

main returns [FunctionDefinition ast] locals [List<VariableDefinition> varDefinitions = new ArrayList<VariableDefinition>(), List<Statement> statements = new ArrayList<Statement>()]:
    T='def' 'main' '(' ')' ':' '{' (vd=variableDefinition ';' { $varDefinitions.addAll($vd.ast); })* (st=statement { $statements.addAll($st.ast); })* '}' { $ast = new FunctionDefinition($T.getLine(), $T.getCharPositionInLine()+1, $varDefinitions, $statements); }
    ;

statement returns [List<Statement> ast = new ArrayList<Statement>()] locals [List<Statement> elseBody = new ArrayList<Statement>()]:
    T='print' ex=expressions ';' { for (Expression exp : $ex.ast) { $ast.add(new Print($T.getLine(), $T.getCharPositionInLine()+1, exp)); } }
    | T='input' ex=expressions ';' { for (Expression exp : $ex.ast) { $ast.add(new Input($T.getLine(), $T.getCharPositionInLine()+1, exp)); } }
    | e1=expression '=' e2=expression ';' { $ast.add(new Assignment($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $e2.ast)); }
    | T='if' e1=expression ':' b1=bodyStatements ('else' ':' b2=bodyStatements { $elseBody = $b2.ast; } )? { $ast.add(new IfElse($T.getLine(), $T.getCharPositionInLine()+1, $e1.ast, $b1.ast, $elseBody)); }
    | T='while' e1=expression ':' b1=bodyStatements { $ast.add(new While($T.getLine(), $T.getCharPositionInLine()+1, $e1.ast, $b1.ast)); }
    | 'return' e1=expression ';' { $ast.add(new Return($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast)); }
    | fc=functionCall ';' { $ast.add($fc.ast); }
    ;

expression returns [Expression ast]:
    INT_CONSTANT { $ast = new IntLiteral($INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToInt($INT_CONSTANT.text)); }
    | REAL_CONSTANT { $ast = new RealLiteral($REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToReal($REAL_CONSTANT.text)); }
    | CHAR_CONSTANT { $ast = new CharLiteral($CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine()+1, LexerHelper.lexemeToChar($CHAR_CONSTANT.text)); }
    | ID { $ast = new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text); }
    | '(' e1=expression ')' { $ast = $e1.ast; }
    | fc=functionCall { $ast = $fc.ast; }
    | l=expression '[' r=expression ']' { $ast = new ArrayAccess($l.ast.getLine(), $l.ast.getColumn(), $l.ast, $r.ast); }
    | e1=expression '.' ID { $ast = new StructAccess($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast, $ID.text); }
    | T='(' tp=type ')' e1=expression { $ast = new Cast($T.getLine(), $T.getCharPositionInLine()+1, $e1.ast, $tp.ast); }
    | '-' e1=expression { $ast = new UnaryMinus($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast); }
    | '!' e1=expression { $ast = new NotOperator($e1.ast.getLine(), $e1.ast.getColumn(), $e1.ast); }
    | l=expression OP=('*'|'/'|'%') r=expression { $ast = new ArithmeticOperator($l.ast.getLine(), $l.ast.getColumn(), $l.ast, $r.ast, $OP.text); }
    | l=expression OP=('+'|'-') r=expression { $ast = new ArithmeticOperator($l.ast.getLine(), $l.ast.getColumn(), $l.ast, $r.ast, $OP.text); }
    | l=expression OP=('>'|'>='|'<'|'<='|'!='|'==') r=expression { $ast = new ComparisonOperator($l.ast.getLine(), $l.ast.getColumn(), $l.ast, $r.ast, $OP.text); }
    | l=expression OP=('&&'|'||') r=expression { $ast = new LogicalOperator($l.ast.getLine(), $l.ast.getColumn(), $l.ast, $r.ast, $OP.text); }
    ;

type returns [Type ast]:
    T='int' { $ast = IntType.getInstance(); }
    | T='double' { $ast = RealType.getInstance(); }
    | T='char' { $ast = CharType.getInstance(); }
    | T='[' INT_CONSTANT ']' of=type { $ast = new ArrayType($T.getLine(), $T.getCharPositionInLine()+1, LexerHelper.lexemeToInt($INT_CONSTANT.text), $of.ast); }
    | T='struct' '{' sf=structFields '}' { $ast = new StructType($T.getLine(), $T.getCharPositionInLine()+1, $sf.ast); }
    ;

structFields returns [List<Field> ast = new ArrayList<Field>()]:
    (idList=ids ':' tp=type ';' {
        for (Variable v : $idList.ast) {
            Field f = new Field(v.getLine(), v.getColumn(), $tp.ast, v.getName());
            if ($ast.contains(f)) new ErrorType(v.getLine(), v.getColumn(), "El struct ya posee un field con nombre: " + v.getName());
            else $ast.add(f);
        }
    })+
    ;

variableDefinition returns [List<VariableDefinition> ast = new ArrayList<VariableDefinition>()]:
    idList=ids ':' tp=type {
        for (Variable v : $idList.ast) {
            VariableDefinition vd = new VariableDefinition(v.getLine(), v.getColumn(), $tp.ast, v.getName());
            if ($ast.contains(vd)) new ErrorType(v.getLine(), v.getColumn(), "Ya se definió una variable con nombre: " + v.getName());
            else $ast.add(vd);
        }
    }
    ;

functionDefinition returns [FunctionDefinition ast] locals [Type returnType = VoidType.getInstance(), List<VariableDefinition> parameters = new ArrayList<VariableDefinition>(), List<VariableDefinition> varDefinitions = new ArrayList<VariableDefinition>(), List<Statement> statements = new ArrayList<Statement>()]:
    T='def' ID '(' (p=params { $parameters = $p.ast; })? ')' ':' (tp=type { $returnType = $tp.ast; })? '{' (vd=variableDefinition ';' { $varDefinitions.addAll($vd.ast); })* (st=statement { $statements.addAll($st.ast); })* '}' { $ast = new FunctionDefinition($T.getLine(), $T.getCharPositionInLine()+1, $ID.text, new FunctionType($T.getLine(), $T.getCharPositionInLine()+1, $parameters, $returnType), $varDefinitions, $statements); }
    ;

functionCall returns [FunctionCall ast] locals [List<Expression> parameters = new ArrayList<Expression>()]:
    ID '(' (ex=expressions { $parameters = $ex.ast; })? ')' { $ast = new FunctionCall($ID.getLine(), $ID.getCharPositionInLine()+1, new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text), $parameters); }
    ;

bodyStatements returns [List<Statement> ast = new ArrayList<Statement>()]:
    s1=statement { $ast.addAll($s1.ast); }
    | '{' (s2=statement { $ast.addAll($s2.ast); })+ '}'
    ;

expressions returns [List<Expression> ast = new ArrayList<Expression>()]:
    e1=expression { $ast.add($e1.ast); } (',' e2=expression { $ast.add($e2.ast); })*
    ;

params returns [List<VariableDefinition> ast = new ArrayList<VariableDefinition>()]:
    vd1=variableDefinition { $ast.addAll($vd1.ast); } (',' vd2=variableDefinition { $ast.addAll($vd2.ast); })*
    ;

ids returns [List<Variable> ast = new ArrayList<Variable>()]:
    id1=ID { $ast.add(new Variable($id1.getLine(), $id1.getCharPositionInLine()+1, $id1.text)); } (',' id2=ID { $ast.add(new Variable($id2.getLine(), $id2.getCharPositionInLine()+1, $id2.text)); })*
    ;

/* rule returns [interfaz_implASTNode ast (=initialization)?] locals [Type variable (=initialization)?]:
    nt=nonterminal T=token {$ast = $nt.ast $T.text $variable}
   Siempre que hay una regla recursiva, renombrar.
*/

/****************************** LEXICO ******************************/

TRASH: [ \n\r\t]+ -> skip
     ;

COMMENT: ('#'.*?('\n'|EOF) | '"""'.*?'"""') -> skip
       ;

ID: [A-Za-z_][A-Za-z0-9_]*
  ;

INT_CONSTANT: [1-9][0-9]* | '0'
            ;

REAL_CONSTANT: (INT_CONSTANT'.'? | '.') INT_CONSTANT? ([eE][+-]?[1-9][0-9]*)?
             ;

CHAR_CONSTANT: '\'' (. | '\\'([0-9]+ | [nt])) '\''
             ;
