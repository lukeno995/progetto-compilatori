package util;

import nodes.*;
import symbol.Sym;
import visitor.xml.AbstractSyntaxNode;
import visitor.xml.Visitor;
import java.util.ArrayList;
import java.util.Stack;



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
        if (node instanceof ProgramNode) {
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


        //Scope FunNode START
        if (node instanceof FunNode) {
            //Creazione nuovo scope
            SymbolTable<String, RecordTable> symbolTable = new SymbolTable<String, RecordTable>(symbolTableStack.peek());
            symbolTableStack.push(symbolTable);
            symbolTable.setScopingNode(node);
            FunNode tmp = ((FunNode) node);
            tmp.setSymbolTable(symbolTable);
            if (tmp.getParamDeclListNodes() != null) {
                //prendo la lista dei parametri della firma
                ArrayList<ParamDeclNode> paramDeclNodes = tmp.getParamDeclListNodes();
                for (ParamDeclNode paramDeclNode : paramDeclNodes) {
                    String typeNode = paramDeclNode.getTypeParam();
                    //prendo l'id del parametro
                    String idNode = paramDeclNode.getLeafNode().toString();
                    //ricavo il tipo del parametro
                    int varType = -1;
                    if (typeNode == "INTEGER") {
                        varType = Sym.INTEGER;
                    } else if (typeNode == "REAL") {
                        varType = Sym.REAL;
                    } else if (typeNode == "STRING") {
                        varType = Sym.STRING;
                    } else if (typeNode == "BOOL") {
                        varType = Sym.BOOL;
                    }
                    //controllo se è già presente nella tabella
                    if (symbolTableStack.peek().containsKey(idNode)) {
                        throw new Error("Identificatore già dichiarato all'interno dello scope: " + idNode);
                    }
                    //lo aggiungo nel caso
                    RecordTable symbolTableRecord = new RecordTable(idNode, "var", varType);
                    symbolTableStack.peek().put(idNode, symbolTableRecord);
                }
            }
            if (((FunNode) node).getLeafNode() != null) {
                ((FunNode) node).getLeafNode().accept(this);
            }
            ArrayList<VarDeclNode> varDeclListNode = ((FunNode) node).getVarDeclListNode();
            for (VarDeclNode varDeclNode : varDeclListNode) {
                if (varDeclNode != null) {
                    varDeclNode.accept(this);
                }
            }
            ArrayList<StatNode> statListNode = ((FunNode) node).getStatListNode();
            for (StatNode statNode : statListNode) {
                if (statNode != null) {
                    statNode.accept(this);
                }
            }
            symbolTableStack.pop();
        }
        //Scope FunNode END


        //Scope VarDeclNode START
        if (node instanceof VarDeclNode) {
            String typeVar = ((VarDeclNode) node).getTypeVar();
            //se è null vuol dire che è un var
            int varType = Sym.error;
            if (((VarDeclNode) node).getTypeVar() != null) {
                if (typeVar == "INTEGER") {
                    varType = Sym.INTEGER;
                } else if (typeVar == "BOOL") {
                    varType = Sym.BOOL;
                } else if (typeVar == "REAL") {
                    varType = Sym.REAL;
                } else if (typeVar == "STRING") {
                    varType = Sym.STRING;
                }
                //prendo la lista delle variabili
                ArrayList<InitNode> initNodeArrayList = ((VarDeclNode) node).getInitNodes();
                for (InitNode tmp : initNodeArrayList) {
                    //prendo l'id della variabile
                    String varSymbol = ((InitNode) tmp).getLeafNode().getNameId();
                    //controllo se è già presente
                    if (symbolTableStack.peek().containsKey(varSymbol)) {
                        throw new Error("Identificatore già dichiarato all'interno dello scope: " + varSymbol);
                    }
                    //aggiungo la variabile alla tabella
                    RecordTable symbolTableRecord = new RecordTable(varSymbol, "var", varType);
                    symbolTableStack.peek().put(varSymbol, symbolTableRecord);
                }

            } else {
                String typeVar2 = ((InitNode) node).getConstant().getNameID();
                int varType2 = Sym.error;
                if (((InitNode) node).getConstant() != null) {
                    if (typeVar2 == "INTEGER_CONST") {
                        varType2 = Sym.INTEGER_CONST;
                    } else if (typeVar2 == "BOOL") {
                        varType2 = Sym.BOOL;
                    } else if (typeVar2 == "REAL_CONST") {
                        varType2 = Sym.REAL_CONST;
                    } else if (typeVar2 == "STRING_CONST") {
                        varType2 = Sym.STRING_CONST;
                    }
                    //prendo la lista delle variabili
                    ArrayList<InitNode> initNodeArrayList = ((VarDeclNode) node).getInitNodes();
                    for (InitNode tmp : initNodeArrayList) {
                        //prendo l'id della variabile
                        String varSymbol = tmp.getLeafNode().getNameId();
                        //controllo se è già presente
                        if (symbolTableStack.peek().containsKey(varSymbol)) {
                            throw new Error("Identificatore già dichiarato all'interno dello scope: " + varSymbol);
                        }
                        //aggiungo la variabile alla tabella
                        RecordTable symbolTableRecord = new RecordTable(varSymbol, "var", varType2);
                        symbolTableStack.peek().put(varSymbol, symbolTableRecord);
                    }
                }
            }
        }


        //Scope IfStatNode START
        if (node instanceof IfStatNode) {
                // creation new scope
                symbolTableStack.push(rootSymbolTable);
                rootSymbolTable.setScopingNode(node);
                ((IfStatNode) node).setSymbolTable(rootSymbolTable);
                //VAR list
                ArrayList<VarDeclNode> varDeclListNode = ((IfStatNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        varDeclNode.accept(this);
                    }
                }

                //STAT list
                ArrayList<StatNode> statNodes = ((IfStatNode) node).getStatListNode();
                for (StatNode statNode : statNodes) {
                    if (statNode != null) {
                        statNode.accept(this);
                    }
                }
                //check ELSE
                if (((IfStatNode) node).getElseNode() != null) {
                    ((IfStatNode) node).getElseNode().accept(this);
                }
                symbolTableStack.pop();
            }
        //Scope IfStatNode END


        //Scope WhileStatNode START
        if (node instanceof WhileStatNode) {
                // creation new scope
                symbolTableStack.push(rootSymbolTable);
                rootSymbolTable.setScopingNode(node);
                ((WhileStatNode) node).setSymbolTable(rootSymbolTable);
                //VAR list
                ArrayList<VarDeclNode> varDeclListNode = ((WhileStatNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        varDeclNode.accept(this);
                    }
                }
                //STAT list
                ArrayList<StatNode> statNodes = ((WhileStatNode) node).getStatListNode();
                for (StatNode statNode : statNodes) {
                    if (statNode != null) {
                        statNode.accept(this);
                    }
                }
                symbolTableStack.pop();
            }
        //Scope WhileStatNode END


        //Scope ElseNode START
        if (node instanceof ElseNode) {
                // creation new scope
                symbolTableStack.push(rootSymbolTable);
                rootSymbolTable.setScopingNode(node);
                ((ElseNode) node).setSymbolTable(rootSymbolTable);
                //VAR list
                ArrayList<VarDeclNode> varDeclListNode = ((ElseNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        varDeclNode.accept(this);
                    }
                }
                //STAT list
                ArrayList<StatNode> statNodes = ((ElseNode) node).getStatListNode();
                for (StatNode statNode : statNodes) {
                    if (statNode != null) {
                        statNode.accept(this);
                    }
                }
                symbolTableStack.pop();
            }
        //Scope ElseNode END


        //Scope MainNode START
        if (node instanceof MainNode) {
                // creation new scope
                symbolTableStack.push(rootSymbolTable);
                rootSymbolTable.setScopingNode(node);
                ((MainNode) node).setSymbolTable(rootSymbolTable);
                //VAR list
                ArrayList<VarDeclNode> varDeclListNode = ((MainNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        varDeclNode.accept(this);
                    }
                }
                //STAT list
                ArrayList<StatNode> statNodes = ((MainNode) node).getStatListNode();
                for (StatNode statNode : statNodes) {
                    if (statNode != null) {
                        statNode.accept(this);
                    }
                }
                symbolTableStack.pop();
            }
        //Scope MainNode END

        return rootSymbolTable.toString();
    }

}


