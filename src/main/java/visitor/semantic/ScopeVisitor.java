package visitor.semantic;

import nodes.*;
import symbol.Sym;
import util.RecordTable;
import util.SymbolTable;
import visitor.xml.AbstractSyntaxNode;
import visitor.xml.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ScopeVisitor implements Visitor {

    private final Stack<SymbolTable> stackScope;
    private SymbolTable symbolTable;

    public ScopeVisitor() {
        stackScope = new Stack<SymbolTable>();
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
        } else if (ast instanceof WhileStatNode) {
            visitWhileStatNode((WhileStatNode) ast);
        } else if (ast instanceof IfStatNode) {
            visitIfStatNode((IfStatNode) ast);
        } else if (ast instanceof ElseNode) {
            visitElseStatNode((ElseNode) ast);
        }
        return null;
    }

    // ProgramNode abbiamo definito la tabella globale e la tabella di funzioni
    // e le variabili globali, per quanto riguarda il tipo della program node abbiamo deciso che e' un void
    void visitProgramNode(ProgramNode node) {
        symbolTable = new SymbolTable();
        symbolTable.put(node.getClass().getName(), new RecordTable("Global"));
        stackScope.add(symbolTable);
        node.setSymbolTable(symbolTable);
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
        node.setType(Sym.VOID);
    }

    // VarDeclNode abbiamo definito il tipo della variabile e il nome della variabile
    // se essa risulta diversa da null allora abbiamo gia' definito il tipo della
    // variabile altrimenti dobbiamo verificare il tipo della costante per capire
    // come trasformare la nostra variabile var
    void visitVarDeclNode(VarDeclNode node) {
        int type = Sym.error;
        if (node.getTypeVar() != null) {
            String typeVar = node.getTypeVar();
            type = InferenceType.inferenceType(typeVar);
        } else {
            String typeVar = node.getInitNodes().get(0).getConstant().getNameID();
            type = InferenceType.inferenceType(typeVar);
        }

        ArrayList<InitNode> initNodeArrayList = node.getInitNodes();
        for (InitNode tmp : initNodeArrayList) {
            //prendo l'id della variabile
            String varSymbol = tmp.getLeafNode().getNameId();
            //controllo se è già presente
            if (stackScope.peek().containsKey(varSymbol)) {
                throw new Error("Identificatore già dichiarato all'interno dello scope: " + varSymbol);
            }
            //aggiungo la variabile alla tabella
            RecordTable symbolTableRecord = new RecordTable(varSymbol, "var", type);
            stackScope.peek().put(varSymbol, symbolTableRecord);
        }

        node.setType(type);
    }


    // FunNode abbiamo definito il tipo della funzione e il nome della funzione
    // se essa risulta diversa da null allora abbiamo gia' definito il tipo della funzione
    // altrimente risulta essere void

    void visitFunNode(FunNode node) {
        int type = Sym.VOID;
        SymbolTable symbolTableTemp = stackScope.peek();
        node.setType(Sym.VOID);
        if (node.getTypeVar() != null) {
            type = InferenceType.inferenceType(node.getTypeVar());
        }
        FunNode tmp = node;

        String funName = node.getLeafNode().getNameId();
        if (!symbolTableTemp.containsKey(funName)) {

            symbolTable = new SymbolTable();
            symbolTable.put(node.getClass().getName(), new RecordTable(funName));
            stackScope.add(symbolTable);

            ArrayList<ParamDeclNode> paramDeclListNode = node.getParamDeclListNodes();
            List<Integer> paramsType = new ArrayList<>();
            if (paramDeclListNode != null) {


                for (ParamDeclNode paramDeclNode : paramDeclListNode) {
                    if (paramDeclNode != null) {
                        paramDeclNode.accept(this);
                        paramsType.add(paramDeclNode.getType());
                    }
                }
            }

            RecordTable symbolTableRecord = new RecordTable(funName, "fun", paramsType, type);
            symbolTableTemp.put(funName, symbolTableRecord);

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
            node.setSymbolTable(stackScope.peek());
            node.setType(type);
            stackScope.pop();
        }
    }

    void visitParamDeclNode(ParamDeclNode node) {
        int type = Sym.error;
        if (node.getTypeParam() != null) {
            type = InferenceType.inferenceType(node.getTypeParam());
        }
        String paramSymbol = node.getLeafNode().getNameId();
        if (stackScope.peek().containsKey(paramSymbol)) {
            throw new Error("Identificatore già dichiarato all'interno dello scope: " + paramSymbol);
        }
        RecordTable symbolTableRecord = new RecordTable(paramSymbol, "param", type);
        stackScope.peek().put(paramSymbol, symbolTableRecord);
        node.setType(type);
    }

    void visitMain(MainNode node) {
        symbolTable = new SymbolTable();
        symbolTable.put(node.getClass().getName(), new RecordTable("main"));
        stackScope.add(symbolTable);
        node.setSymbolTable(symbolTable);
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

    void visitWhileStatNode(WhileStatNode node) {
        symbolTable = new SymbolTable();
        symbolTable.put(node.getClass().getName(), new RecordTable("while"));
        stackScope.add(symbolTable);
        node.setSymbolTable(symbolTable);
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

    void visitIfStatNode(IfStatNode node) {
        symbolTable = new SymbolTable();
        symbolTable.put(node.getClass().getName(), new RecordTable("if"));
        stackScope.add(symbolTable);
        node.setSymbolTable(symbolTable);
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
        if (node.getElseNode() != null) {
            node.getElseNode().accept(this);
        }
    }

    void visitElseStatNode(ElseNode node) {
        symbolTable = new SymbolTable();
        symbolTable.put(node.getClass().getName(), new RecordTable("else"));
        stackScope.add(symbolTable);
        node.setSymbolTable(symbolTable);
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
}
