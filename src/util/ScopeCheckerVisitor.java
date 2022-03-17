package util;

import nodes.*;
import visitor.xml.AbstractSyntaxNode;
import visitor.xml.Visitor;
import java.util.ArrayList;
import java.util.Stack;


//DREAM TEAM
public class ScopeCheckerVisitor implements Visitor {
    SymbolTable<String, RecordTable> rootSymbolTable;
    Stack<SymbolTable<String, RecordTable>> symbolTableStack;

    public ScopeCheckerVisitor(SymbolTable<String, RecordTable> rootSymbolTable, Stack<SymbolTable<String, RecordTable>> symbolTableStack) {
        this.rootSymbolTable = rootSymbolTable;
        this.symbolTableStack = symbolTableStack;
    }

    @Override
    public String visit(AbstractSyntaxNode node) {


        //Scope ProgramNode START
        if(node instanceof ProgramNode){
            // creation new scope
            symbolTableStack.push(rootSymbolTable);
            rootSymbolTable.setScopingNode(node);
            ((ProgramNode) node).setSymbolTable(rootSymbolTable);
            //VAR list
            ArrayList<VarDeclNode> varDeclListNode = ((ProgramNode) node).getVarDeclListNode();
            for (VarDeclNode varDeclNode : varDeclListNode) {
                if (varDeclNode != null) {
                    varDeclNode.accept(this);
                }
            }

            //FUN list
            ArrayList<FunNode> funListNode = ((ProgramNode) node).getFunListNode();
            for (FunNode funNode : funListNode) {
                if (funNode != null) {
                    funNode.accept(this);
                }
            }

            //check MAIN
            if (((ProgramNode) node).getMainNode() != null) {
                ((ProgramNode) node).getMainNode().accept(this);
            } else {
                throw new Error("MAIN non dichiarato");
            }
            symbolTableStack.pop();
        }
        //Scope ProgramNode END


        //Scope MainNode START
        if(node instanceof MainNode){
            //VAR list
            ArrayList<VarDeclNode> varDeclListNode = ((MainNode) node).getVarDeclListNode();
            for (VarDeclNode varDeclNode : varDeclListNode) {
                if (varDeclNode != null) {
                    varDeclNode.accept(this);
                }
            }

            //STAT list
            ArrayList<StatNode> statListNode = ((MainNode) node).getStatListNode();
            for (StatNode statNode : statListNode) {
                if (statNode != null) {
                    statNode.accept(this);
                }
            }
        }
        //Scope MainNode END


        //Scope IfStatNode START
        if (node instanceof IfStatNode) {
            SymbolTable<String, RecordTable> symbolTable = new SymbolTable<String, RecordTable>(symbolTableStack.peek());
            symbolTableStack.push(symbolTable);
            symbolTable.setScopingNode(node);
            ((IfStatNode) node).setSymbolTable(symbolTable);
            /*if (((IfStatNode) node).get != null) {
                tmp.getBodyOp().accept(this);
            }
            symbolTableStack.pop();
            if (tmp.getElseNode() != null) {
                tmp.getElseNode().accept(this);
            }*/
        }
        //Scope IfStatNode END



        return null;
    }
}
