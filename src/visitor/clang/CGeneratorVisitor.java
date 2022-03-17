package visitor.clang;

import nodes.*;
import visitor.xml.AbstractSyntaxNode;
import visitor.xml.Visitor;

import java.io.*;
import java.util.ArrayList;

public class CGeneratorVisitor implements Visitor {

    private Writer writer;
    private String code = "";

    public CGeneratorVisitor(String fileName) throws IOException {
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.indexOf("."));
        fileName += ".c";
        fileName = "" + fileName;

        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
    }

    public void close() throws IOException {
        writer.close();
    }

    @Override
    public String visit(AbstractSyntaxNode ast) {
        try {
            if (ast instanceof ProgramNode) {
                visit((ProgramNode) ast);
            } else if (ast instanceof VarDeclNode) {
                visit((VarDeclNode) ast);
            } else if (ast instanceof FunNode) {
                visit((FunNode) ast);
            } else if (ast instanceof ParamDeclNode) {
                visit((ParamDeclNode) ast);
            } else if (ast instanceof InitNode) {
                visit((InitNode) ast);
            } else if (ast instanceof MainNode) {
                visit((MainNode) ast);
            } else if (ast instanceof ExprNode) {
                ExprNode nodeExpr = (ExprNode) ast;
                if (nodeExpr instanceof StatNode) {
                    StatNode nodeStat = (StatNode) nodeExpr;
                    if (nodeStat instanceof WhileStatNode) {
                        visit((WhileStatNode) nodeStat);
                    } else if (nodeStat instanceof IfStatNode) {
                        visit((IfStatNode) nodeStat);
                    } else if (nodeStat instanceof ElseNode) {
                        visit((ElseNode) nodeStat);
                    } else if (nodeStat instanceof AssignStatNode) {
                        visit((AssignStatNode) nodeStat);
                    } else if (nodeStat instanceof ReadStatNode) {
                        visit((ReadStatNode) nodeStat);
                    } else if (nodeStat instanceof WriteStatNode) {
                        visit((WriteStatNode) nodeStat);
                    } else if (nodeStat instanceof ReturnNode) {
                        visit((ReturnNode) nodeStat);
                    } else if (nodeStat instanceof CallFunNode) {
                        visit((CallFunNode) nodeStat);
                    }
                }
                if (nodeExpr instanceof BinOpNode) {
                    visit((BinOpNode) nodeExpr);
                } else if (nodeExpr instanceof UnOpNode) {
                    visit((UnOpNode) nodeExpr);
                } else if (nodeExpr instanceof LeafNode) {
                    visit((LeafNode) nodeExpr);
                }
                return "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }


    //PROGRAM NODE
    public void visit(ProgramNode node) throws IOException {
        code += "#include <stdio.h>\n";
        code += "#include <stdlib.h>\n";
        code += "#include <string.h>\n";
        code += "#include <math.h>\n";

        ArrayList<VarDeclNode> varDeclListNode = ((ProgramNode) node).getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                (varDeclNode).accept(this);
                code += "\n";
            }
        }

        ArrayList<FunNode> funListNode = ((ProgramNode) node).getFunListNode();
        for (FunNode funNode : funListNode) {
            if (funNode != null) {
                (funNode).accept(this);
            }
        }
        code += "int main() {\n";
        MainNode mainNode = ((ProgramNode) node).getMainNode();
        if (mainNode != null) {
            (mainNode).accept(this);
        }
        code += "\n";
        code += "    return 0;\n";
        code += "}\n";
        writer.write(code);
        writer.close();
    }

    //VAR DECL NODE
    public void visit(VarDeclNode node) throws IOException {
        VarDeclNode tmp = (VarDeclNode) node;

        if (tmp.getTypeVar() != null) {
            code += typeC(tmp.getTypeVar()) + " ";
        }

        ArrayList<InitNode> initNodes = tmp.getInitNodes();
        for (InitNode initNode : initNodes) {
            if (initNode != null) {
                (initNode).accept(this);
            }
        }
    }

    //FUN NODE
    public void visit(FunNode node) throws IOException {
        code += "\n";
        if (node instanceof FunNode) {
            FunNode tmp = (FunNode) node;
            if (tmp.getTypeVar() != null) {
                code += typeC(tmp.getTypeVar().toString()) + " ";
            } else {
                code += "void ";
            }

            if (node.getLeafNode() != null) {
                node.getLeafNode().accept(this);
            }

            code += "(";
            ArrayList<ParamDeclNode> paramDeclNodes = tmp.getParamDeclListNodes();
            for (int i = 0; i < paramDeclNodes.size(); i++) {
                if (paramDeclNodes.get(i) != null) {
                    if (i == paramDeclNodes.size() - 1) {
                        paramDeclNodes.get(i).accept(this);
                    } else {
                        paramDeclNodes.get(i).accept(this);
                        code += ", ";
                    }
                }
            }

            code += ") {\n";
            ArrayList<VarDeclNode> varDeclListNode = tmp.getVarDeclListNode();
            varDeclListNode.stream().forEach(varDeclElem -> {
                varDeclElem.accept(this);
            });

            ArrayList<StatNode> stmtListNode = tmp.getStatListNode();
            stmtListNode.stream().forEach(stmtElem -> {
                stmtElem.accept(this);
            }); //visit each statement
            code += "\n}";
        }
    }

    //PARAM DECL NODE
    public void visit(ParamDeclNode node) throws IOException {
        ParamDeclNode tmp = (ParamDeclNode) node;
        if (tmp.getTypeParam() != null) {
            code += typeC(tmp.getTypeParam()) + " ";
        }
        if (tmp.getLeafNode() != null) {
            tmp.getLeafNode().accept(this);
        }
    }

    public void visit(InitNode node) throws IOException {
        InitNode tmp = (InitNode) node;
        if (tmp.getLeafNode() != null) {    //if there is a leaf nod
            tmp.getLeafNode().accept(this);
        }
        if (tmp.getExprNode() != null) {
            code += " = ";
            tmp.getExprNode().accept(this);
        }
        code += ";\n";
    }


    //MAIN NODE
    public void visit(MainNode node) throws IOException {
        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        varDeclListNode.stream().forEach(varDeclElem -> {
            varDeclElem.accept(this);
        });
        ArrayList<StatNode> statListNode = node.getStatListNode();
        statListNode.stream().forEach(statElem -> {
            statElem.accept(this);
        });

    }

    //PARENT EXPR NODE
    //STATEMENTS NODE (EXPR)
    public void visit(WhileStatNode node) throws IOException {
        System.out.println("ExprNode");
        WhileStatNode tmp = (WhileStatNode) node;
        code += "while (";
        if (tmp.getExprNode() != null) {
            tmp.getExprNode().accept(this);
        }
        code += ") {\n";

        ArrayList<VarDeclNode> varDeclListNode = tmp.getVarDeclListNode();
        varDeclListNode.stream().forEach(varDeclElem -> {
            varDeclElem.accept(this);
        });
        ArrayList<StatNode> statListNode = tmp.getStatListNode();
        statListNode.stream().forEach(statElem -> {
            statElem.accept(this);
        });

        code += "    }\n";
    }

    public void visit(IfStatNode node) throws IOException {
        System.out.println("IfStatNode");
        code += "if (";
        node.getExprNode().accept(this);
        code += ") {\n";

        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        varDeclListNode.stream().forEach(varDeclElem -> {
            varDeclElem.accept(this);
        });


        ArrayList<StatNode> stmtListNode = node.getStatListNode();
        stmtListNode.stream().forEach(stmtElem -> {
            stmtElem.accept(this);
        });

        code += "}\n";
        if (node.getElseNode() != null) {
            node.getElseNode().accept(this);
        }

    }

    public void visit(ElseNode node) throws IOException {
        System.out.println("ElseNode");
        code += "else {\n";
        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        varDeclListNode.stream().forEach(varDeclElem -> {
            varDeclElem.accept(this);
        });

        ArrayList<StatNode> stmtListNode = node.getStatListNode();
        stmtListNode.stream().forEach(stmtElem -> {
            stmtElem.accept(this);
        }); //visit each statement
        code += "\n}\n";

    }

    public void visit(AssignStatNode node) throws IOException {
        System.out.println("AssignStatNode");
        node.getLeafNode().accept(this);
        code += " = ";
        node.getExprNode().accept(this);
        code += ";\n";

    }

    public void visit(ReadStatNode node) throws IOException {
    }

    public void visit(WriteStatNode node) throws IOException {
        System.out.println("WriteStatNode");
        code += "printf(\"";
        node.getExprNode().accept(this);
        code += "\\n\"); \n";
    }

    public void visit(ReturnNode node) throws IOException {
        System.out.println("ReturnStatNode");
        code += "return ";
        node.getExprNode().accept(this);
        code += ";\n";
    }

    public void visit(CallFunNode node) throws IOException {
        System.out.println("CallFunNode");
        code += node.getLeafNode().accept(this);
        code += "(";
        ArrayList<ExprNode> exprListNode = node.getExprRefsNode();
       for (int i = 0; i < exprListNode.size(); i++) {
           if(i == exprListNode.size() - 1) {
               exprListNode.get(i).accept(this);
           } else {
               exprListNode.get(i).accept(this);
               code += ", ";
           }

           }

        code += ");\n";
    }

    public void visit(BinOpNode node) throws IOException {
        System.out.println("BinOpNode");
        node.getExprNode1().accept(this);
        code += typeOperation(node.getName());
        node.getExprNode2().accept(this);
    }

    public void visit(UnOpNode node) throws IOException {
        System.out.println("UnOpNode");
        code += node.getName();
    }

    public void visit(LeafNode node) throws IOException {
        code += node.getNameId();
    }

    private String typeC(String typeMyfun) {
        switch (typeMyfun) {
            case "INTEGER":
                return "int";
            case "REAL":
                return "float";
            case "STRING":
                return "char*";
        }
        return "";
    }


    private String typeOperation(String typeOperation) {
        switch (typeOperation) {
            case "PLUS":
                return " + ";
            case "MINUS":
                return " - ";
            case "TIMES":
                return " * ";
            case "DIVINT":
            case "DIV":
                return " / ";
            case "GE":
                return " >= ";
            case "GT":
                return " > ";
            case "EQ":
                return " == ";
            case "NE":
                return " != ";
            case "LE":
                return " <= ";
            case "LT":
                return " < ";
            case "AND":
                return " && ";
            case "OR":
                return " || ";
            case "NOT":
                return " ! ";
        }
        return "";
    }

}
