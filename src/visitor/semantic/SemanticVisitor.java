package visitor.semantic;

import exception.FatalError;
import exception.TypeMismatchException;
import exception.UndeclaredVariableException;
import nodes.*;
import symbol.Sym;
import util.RecordTable;
import util.SymbolTable;
import visitor.xml.AbstractSyntaxNode;
import visitor.xml.Visitor;

import java.util.ArrayList;
import java.util.Stack;

public class SemanticVisitor implements Visitor {
    private final Stack<SymbolTable> stackScope;
    private SymbolTable symbolTable;


    public SemanticVisitor() {
        stackScope = new Stack<>();
    }

    @Override
    public String visit(AbstractSyntaxNode ast) {
        if (ast instanceof ProgramNode) {
            visitProgramNode((ProgramNode) ast);
        } else if (ast instanceof VarDeclNode) {
            visitVarDeclNode((VarDeclNode) ast);
        } else if (ast instanceof FunNode) {
            visitFunNode((FunNode) ast);
        } else if (ast instanceof ParamDeclNode) {
            visitParamDeclNode((ParamDeclNode) ast);
        } else if (ast instanceof MainNode) {
            visitMain((MainNode) ast);
        } else if (ast instanceof InitNode) {
            visitInitNode((InitNode) ast);
        } else if (ast instanceof AssignStatNode) {
            try {
                visitAssignStatNode((AssignStatNode) ast);
            } catch (TypeMismatchException | UndeclaredVariableException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof ReadStatNode) {
            visitReadStatNode((ReadStatNode) ast);
        } else if (ast instanceof WriteStatNode) {
            visitWriteStatNode((WriteStatNode) ast);
        } else if (ast instanceof ReturnNode) {
            visitReturnNode((ReturnNode) ast);
        } else if (ast instanceof CallFunNode) {
            visitCallFunNode((CallFunNode) ast);
        } else if (ast instanceof BinOpNode) {
            try {
                visitBinOpNode((BinOpNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof RelOpNode) {
            try {
                visitRelopNode((RelOpNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof ConcatNode) {
            try {
                visitConcatNode((ConcatNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof OrAndOpNode) {
            try {
                visitOrOpNode((OrAndOpNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof UnOpNode) {
            try {
                visitUnOpNode((UnOpNode) ast);
            } catch (FatalError | TypeMismatchException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof NotOpNode) {
            try {
                visitNotOpNode((NotOpNode) ast);
            } catch (FatalError | TypeMismatchException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof LeafNode) {
            try {
                visitLeafNode((LeafNode) ast);
            } catch (UndeclaredVariableException e) {
                e.printStackTrace();
            }
        } else if (ast instanceof ConstNode) {
            visitConstNode((ConstNode) ast);
        } else if (ast instanceof WhileStatNode) {
            try {
                visitWhileStatNode((WhileStatNode) ast);
            } catch (TypeMismatchException | FatalError e) {
                e.printStackTrace();
            }
        } else if (ast instanceof IfStatNode) {
            try {
                visitIfStatNode((IfStatNode) ast);
            } catch (TypeMismatchException | FatalError e) {
                e.printStackTrace();
            }
        } else if (ast instanceof ElseNode) {
            try {
                visitElseStatNode((ElseNode) ast);
            } catch (TypeMismatchException | FatalError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    void visitConstNode(ConstNode ast) {
        int type = InferenceType.inferenceType(ast.getNameID());
        ast.setType(type);
    }

    void visitConcatNode(ConcatNode node) throws TypeMismatchException {
        ExprNode left = node.getExprNode1();
        left.accept(this);
        ExprNode right = node.getExprNode2();
        right.accept(this);
        int resultType = TypeChecker.typeCheckBinaryOp(TypeChecker.STRINGCONCAT, left.getType(), right.getType());
        node.setType(resultType);
    }

    void visitRelopNode(RelOpNode ast) throws TypeMismatchException {
        ExprNode left = ast.getExprNode1();
        left.accept(this);
        ExprNode right = ast.getExprNode2();
        right.accept(this);
        int resultType = TypeChecker.typeCheckBinaryOp(TypeChecker.RELOP, left.getType(), right.getType());
        ast.setType(resultType);
    }

    void visitOrOpNode(OrAndOpNode ast) throws TypeMismatchException {
        ExprNode left = ast.getExprNode1();
        left.accept(this);
        ExprNode right = ast.getExprNode2();
        right.accept(this);
        int resultType = TypeChecker.typeCheckBinaryOp(TypeChecker.BOOLEANOP, left.getType(), right.getType());
        ast.setType(resultType);
    }

    void visitNotOpNode(NotOpNode ast) throws TypeMismatchException, FatalError {
        ExprNode left = ast.getExprNode1();
        left.accept(this);
        int resultType = TypeChecker.typeCheckUnaryOp(TypeChecker.NOTOP, left.getType());
        ast.setType(resultType);
    }

    void visitLeafNode(LeafNode ast) throws UndeclaredVariableException {
        String idName = ast.getNameId();
        int type = lookup(idName);
        ast.setType(type);
    }

    void visitUnOpNode(UnOpNode ast) throws FatalError, TypeMismatchException {
        UnOpNode expr = ast;
        ExprNode exprNode1 = ast.getExprNode1();
        exprNode1.accept(this);
        int resultType = TypeChecker.typeCheckUnaryOp(TypeChecker.UMINUSOP, exprNode1.getType());
        ast.setType(resultType);
    }

    void visitBinOpNode(BinOpNode ast) throws TypeMismatchException {
        ExprNode left = ast.getExprNode1();
        left.accept(this);
        int typeLeft = left.getType();
        ExprNode right = ast.getExprNode2();
        right.accept(this);
        int resultType = TypeChecker.typeCheckBinaryOp(TypeChecker.BINARYOP, left.getType(), right.getType());
        ast.setType(resultType);
    }


    void visitCallFunNode(CallFunNode ast) {
        LeafNode nodeLeaf = ast.getLeafNode();
        nodeLeaf.accept(this);
        ast.setType(nodeLeaf.getType());
        for (ExprNode exprNode : ast.getExprRefsNode()) {
            if (exprNode != null) {
                exprNode.accept(this);
            }
        }
        //ast.setType(type);
    }

    void visitReturnNode(ReturnNode ast) {
        ExprNode exprNode = ast.getExprNode();
        exprNode.accept(this);
        ast.setType(exprNode.getType());
    }

    void visitWriteStatNode(WriteStatNode ast) {
    }

    void visitReadStatNode(ReadStatNode ast) {
        for (LeafNode leafNode : ast.getIdListNode()) {
            leafNode.accept(this);
        }
        ast.setType(Sym.VOID);
    }

    void visitAssignStatNode(AssignStatNode node) throws TypeMismatchException, UndeclaredVariableException {

        LeafNode leafNode = node.getLeafNode();
        leafNode.accept(this);
        int typeLeaf = leafNode.getType();

        ExprNode exprNode = node.getExprNode();
        exprNode.accept(this);
        int typeExpr = exprNode.getType();

        int typeNode = TypeChecker.typeCheckBinaryOp(TypeChecker.ASSIGNOP, typeLeaf, typeExpr);
        node.setType(typeNode);
    }


    private int lookup(String idName) throws UndeclaredVariableException {
        Stack<SymbolTable> stack = (Stack) stackScope.clone();
        while (!stack.isEmpty()) {
            SymbolTable symbolTable = stack.pop();
            if (symbolTable.containsKey(idName)) {
                return symbolTable.get(idName).getType();
            }
        }
        throw new UndeclaredVariableException(idName);
    }

    void visitInitNode(InitNode node) {

    }

    void visitProgramNode(ProgramNode node) {
        stackScope.push(node.getSymbolTable());
        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                varDeclNode.accept(this);
            }
        }

        ArrayList<FunNode> funListNode = node.getFunListNode();
        for (FunNode funNode : funListNode) {
            if (funNode != null) {
                funNode.accept(this);
            }
        }

        if (node.getMainNode() != null) {
            node.getMainNode().accept(this);
        } else {
            throw new Error("MAIN non dichiarato");
        }
        stackScope.pop();
    }


    //problam
    void visitVarDeclNode(VarDeclNode node) {
        //controllo se è già presente

        for (InitNode idInitOp : node.getInitNodes()) {
            if (idInitOp != null) {
                String idName = idInitOp.getLeafNode().getNameId();

                idInitOp.getLeafNode().setType(node.getType());
                idInitOp.accept(this);
                if (!stackScope.peek().containsKey(idName)) {
                    throw new Error("Identificatore già dichiarato all'interno dello scope: " + idName);
                }
                RecordTable symbolTableRecord = stackScope.peek().get(idName);
                node.setType(symbolTableRecord.getType());
            }
        }


    }

    void visitFunNode(FunNode node) {
        SymbolTable symbolTableRecord = stackScope.peek();
        String funName = node.getLeafNode().getNameId();
        if (!symbolTableRecord.containsKey(funName)) {
            throw new Error(funName + " non è stata dichiarata");
        }
        stackScope.push(node.getSymbolTable());
        ArrayList<ParamDeclNode> paramDeclListNode = node.getParamDeclListNodes();
        for (ParamDeclNode paramDeclNode : paramDeclListNode) {
            if (paramDeclNode != null) {
                paramDeclNode.accept(this);
            }
        }
        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                varDeclNode.accept(this);
            }
        }
        ArrayList<StatNode> statListNode = node.getStatListNode();
        for (StatNode statNode : statListNode) {
            if (statNode != null) {
                statNode.accept(this);
            }
        }
        stackScope.pop();
    }

    void visitParamDeclNode(ParamDeclNode node) {
        String paramSymbol = node.getLeafNode().getNameId();
        if (!stackScope.peek().containsKey(paramSymbol)) {
            throw new Error("Parametro non presente nella tabella dei simboli: " + paramSymbol);
        }
        RecordTable symbolTableRecord = stackScope.peek().get(paramSymbol);
        node.setType(symbolTableRecord.getType());
    }

    void visitMain(MainNode node) {
        stackScope.push(node.getSymbolTable());
        //VAR list
        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                varDeclNode.accept(this);
            }
        }
        //STAT list
        ArrayList<StatNode> statNodes = node.getStatListNode();
        for (StatNode statNode : statNodes) {
            if (statNode != null) {
                statNode.accept(this);
            }
        }
        stackScope.pop();
    }

    void visitWhileStatNode(WhileStatNode node) throws TypeMismatchException, FatalError {
        stackScope.push(node.getSymbolTable());

        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                varDeclNode.accept(this);
            }
        }
        //STAT list
        ArrayList<StatNode> statNodes = node.getStatListNode();
        for (StatNode statNode : statNodes) {
            if (statNode != null) {
                statNode.accept(this);
            }
        }
        ExprNode exprNode = node.getExprNode();
        exprNode.accept(this);

        TypeChecker.typeCheckUnaryOp(TypeChecker.CONDITIONAL, exprNode.getType());
        node.setType(Sym.VOID);
        stackScope.pop();
    }

    void visitIfStatNode(IfStatNode node) throws TypeMismatchException, FatalError {
        stackScope.push(node.getSymbolTable());
        ExprNode exprNode = node.getExprNode();
        exprNode.accept(this);
        TypeChecker.typeCheckUnaryOp(TypeChecker.CONDITIONAL, exprNode.getType());

        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                varDeclNode.accept(this);
            }
        }
        //STAT list
        ArrayList<StatNode> statNodes = node.getStatListNode();
        for (StatNode statNode : statNodes) {
            if (statNode != null) {
                statNode.accept(this);
            }
        }
        node.setType(Sym.VOID);
        stackScope.pop();

        if (node.getElseNode() != null) {
            node.getElseNode().accept(this);
        }


    }

    void visitElseStatNode(ElseNode node) throws TypeMismatchException, FatalError {
        stackScope.push(node.getSymbolTable());
        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                varDeclNode.accept(this);
            }
        }
        //STAT list
        ArrayList<StatNode> statNodes = node.getStatListNode();
        for (StatNode statNode : statNodes) {
            if (statNode != null) {
                statNode.accept(this);
            }
        }
        node.setType(Sym.VOID);
        stackScope.pop();
    }
}
