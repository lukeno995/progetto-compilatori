package visitor.semantic;

import exception.FatalError;
import exception.TypeMismatchException;
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
        }
        else if (ast instanceof VarDeclNode) {
            visitVarDeclNode((VarDeclNode) ast);
        }
        else if (ast instanceof FunNode) {
            visitFunNode((FunNode) ast);
        }
        else if (ast instanceof ParamDeclNode) {
            visitParamDeclNode((ParamDeclNode) ast);
        }
        else if (ast instanceof MainNode) {
            visitMain((MainNode) ast);
        }
        else if (ast instanceof InitNode) {
            visitInitNode((InitNode) ast);
        }
        else if (ast instanceof StatNode) {
            visitStatNode((StatNode) ast);
        }
        else if (ast instanceof AssignStatNode) {
            visitAssignStatNode((AssignStatNode) ast);
        }
        else if (ast instanceof ReadStatNode) {
            visitReadStatNode((ReadStatNode) ast);
        }
        else if (ast instanceof WriteStatNode) {
            visitWriteStatNode((WriteStatNode) ast);
        }
        else if (ast instanceof ReturnNode) {
            visitReturnNode((ReturnNode) ast);
        }
        else if (ast instanceof CallFunNode) {
            visitCallFunNode((CallFunNode) ast);
        }
        else if (ast instanceof BinOpNode) {
            visitBinOpNode((BinOpNode) ast);
        }
        else if (ast instanceof UnOpNode) {
            visitUnOpNode((UnOpNode) ast);
        }
        else if (ast instanceof LeafNode) {
            visitLeafNode((LeafNode) ast);
        }
        else if (ast instanceof ConstNode) {
            visitConstNode((ConstNode) ast);
        }
        else if (ast instanceof WhileStatNode) {
            try {
                visitWhileStatNode((WhileStatNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            } catch (FatalError e) {
                e.printStackTrace();
            }
        }
        else if (ast instanceof IfStatNode) {
            try {
                visitIfStatNode((IfStatNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            } catch (FatalError e) {
                e.printStackTrace();
            }
        }
        else if (ast instanceof ElseNode) {
            try {
                visitElseStatNode((ElseNode) ast);
            } catch (TypeMismatchException e) {
                e.printStackTrace();
            } catch (FatalError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    void visitConstNode(ConstNode ast) {
    }

    void visitLeafNode(LeafNode ast) {
    }

    void visitUnOpNode(UnOpNode ast) {
    }

    void visitBinOpNode(BinOpNode ast) {
    }

    void visitCallFunNode(CallFunNode ast) {
    }

    void visitReturnNode(ReturnNode ast) {
    }

    void visitWriteStatNode(WriteStatNode ast) {
    }

    void visitReadStatNode(ReadStatNode ast) {
    }

    void visitAssignStatNode(AssignStatNode node) {
    }

    void visitStatNode(StatNode node) {
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

    void visitVarDeclNode(VarDeclNode node) {
        String varName = node.getNameNode();
        //controllo se è già presente
        if (!stackScope.peek().containsKey(varName)) {
            throw new Error("Identificatore già dichiarato all'interno dello scope: " + varName);
        }
        RecordTable symbolTableRecord = stackScope.peek().get(varName);
        node.setType(symbolTableRecord.getType());
    }

    void visitFunNode(FunNode node) {
        SymbolTable symbolTableRecord = stackScope.peek();
        String funName = node.getLeafNode().getNameId();
        if(!symbolTableRecord.containsKey(funName)){
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
        TypeChecker.typeCheckUnaryOp(TypeChecker.CONDITIONAL,exprNode.getType());
        node.setType(Sym.VOID);
        stackScope.pop();
    }

    void visitIfStatNode(IfStatNode node) throws TypeMismatchException,FatalError{
        stackScope.push(node.getSymbolTable());
        ExprNode exprNode = node.getExprNode();
        exprNode.accept(this);
        TypeChecker.typeCheckUnaryOp(TypeChecker.CONDITIONAL,exprNode.getType());

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
        node.accept(this);



    }

    void visitElseStatNode(ElseNode node) throws TypeMismatchException,FatalError{
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
