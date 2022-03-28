package visitor.clang;

import nodes.*;
import symbol.Sym;
import visitor.xml.AbstractSyntaxNode;
import visitor.xml.Visitor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CGeneratorVisitor implements Visitor {
    private int indexString = 0;
    private final Writer writer;
    private String code = "";

    public CGeneratorVisitor(String fileName) throws IOException {
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.indexOf("."));
        fileName += ".c";
        fileName = "test_files/c_out/" + fileName;

        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
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
                } else if (nodeExpr instanceof ConcatNode) {
                    visit((ConcatNode) nodeExpr);
                } else if (nodeExpr instanceof UnOpNode) {
                    visit((UnOpNode) nodeExpr);
                } else if (nodeExpr instanceof LeafNode) {
                    visit((LeafNode) nodeExpr);
                } else if (nodeExpr instanceof ConstNode) {
                    visit((ConstNode) nodeExpr);
                } else if (nodeExpr instanceof RelOpNode) {
                    visit((RelOpNode) nodeExpr);
                } else if (nodeExpr instanceof OrAndOpNode) {
                    visit((OrAndOpNode) nodeExpr);
                } else if (nodeExpr instanceof NotOpNode) {
                    visit((NotOpNode) nodeExpr);
                } else if (nodeExpr instanceof PowOpNode) {
                    visit((PowOpNode) nodeExpr);
                } else if (nodeExpr instanceof OutParNode) {
                    visit((OutParNode) nodeExpr);
                } else if (nodeExpr instanceof ConcatNode) {
                    visit((ConcatNode) nodeExpr);
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
        code += "#include <stdbool.h>\n";

        ArrayList<VarDeclNode> varDeclListNode = node.getVarDeclListNode();
        for (VarDeclNode varDeclNode : varDeclListNode) {
            if (varDeclNode != null) {
                (varDeclNode).accept(this);
                code += "\n";
            }
        }

        ArrayList<FunNode> funListNode = node.getFunListNode();
        for (FunNode funNode : funListNode) {
            if (funNode != null) {
                (funNode).accept(this);
            }
        }
        code += "int main() {\n";
        MainNode mainNode = node.getMainNode();
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
        VarDeclNode tmp = node;

        ArrayList<InitNode> initNodes = tmp.getInitNodes();
        for (InitNode initNode : initNodes) {
            if (initNode != null) {
                (initNode).accept(this);
            }
        }
    }

    //FUN NODE
    public void visit(FunNode node) throws IOException {

        if (node instanceof FunNode) {
            FunNode tmp = node;
            if (tmp.getTypeVar() != null) {
                code += typeC(tmp.getType()) + " ";
            } else {
                code += "void ";
            }

            if (node.getLeafNode() != null) {
                node.getLeafNode().accept(this);
            }

            code += "(";
            ArrayList<ParamDeclNode> paramDeclNodes = tmp.getParamDeclListNodes();
            if (paramDeclNodes != null) {
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
            code += "}\n";
        }
        indexString = code.length();

    }

    //PARAM DECL NODE
    public void visit(ParamDeclNode node) throws IOException {
        ParamDeclNode tmp = node;
        if (tmp.getTypeParam() != null) {
            code += typeC(tmp.getType());
        }
        if (node.getMode() == "OUT") {
            code += "*";
        }
        if (tmp.getLeafNode() != null) {
            tmp.getLeafNode().accept(this);
        }
    }


    public void visit(InitNode node) throws IOException {
        InitNode tmp = node;
        if (tmp.getType() != null) {
            code += typeC(tmp.getType());
        }
        if (tmp.getLeafNode() != null) {    //if there is a leaf nod
            tmp.getLeafNode().accept(this);
        }
        if (tmp.getExprNode() != null) {
            code += " = ";
            tmp.getExprNode().accept(this);
        } else if (tmp.getConstant() != null) {
            code += " = ";
            tmp.getConstant().accept(this);
        }
        code += ";\n";
    }

    //OUTPAR
    public void visit(OutParNode node) throws IOException {
        OutParNode tmp = node;
        if (tmp.getLeafNode() != null) {    //if there is a leaf nod
            tmp.getLeafNode().accept(this);
        }
    }
    //OUTPAR


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
        //System.out.println("ExprNode");
        WhileStatNode tmp = node;
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
        //System.out.println("IfStatNode");
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
        //System.out.println("ElseNode");
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
        //System.out.println("AssignStatNode");
        node.getLeafNode().accept(this);
        code += " = ";
        if (node.getExprNode() instanceof CallFunNode) {
            node.getExprNode().accept(this);
        } else {
            node.getExprNode().accept(this);
            code += ";\n";
        }

    }

    public void visit(ReadStatNode node) throws IOException {

        ArrayList<LeafNode> idListNode = node.getIdListNode();
        int index = code.length();
        String concatPrintf = "";
        if (node.getExprNode() instanceof ConstNode) {
            concatPrintf = "printf(\"";
            concatPrintf += ((ConstNode) node.getExprNode()).getValue();
            concatPrintf += "\");\n";
        } else if (node.getExprNode() instanceof LeafNode) {
            concatPrintf = "printf(\"";
            LeafNode leafNode = ((LeafNode) node.getExprNode());
            concatPrintf += formatSpecifier(leafNode.getType());
            concatPrintf += "\", &" + leafNode.getNameId();
            concatPrintf += "\");\n";
        }


        code += concatPrintf;
        code += "\nscanf(\"";
        ArrayList<LeafNode> idList = node.getIdListNode();
        for (LeafNode id : idListNode) {
            code += formatSpecifier(id.getType());
        }
        code += "\", ";
        for (int i = 0; i < idList.size(); i++) {
            if (i <= idList.size() - 1) {
                if (idList.get(i).getType() == Sym.STRING) {
                    String firstPart = code.substring(0, index);
                    String lastPart = code.substring(index);
                    String nameMalloc = idList.get(i).getNameId() + " = malloc(256);\n";
                    String newSubstring = firstPart + nameMalloc + lastPart;
                    code = newSubstring;

                    code += idList.get(i).getNameId();
                } else {
                    code += "&" + idList.get(i).getNameId();
                }
            } else {

                if (idList.get(i).getType() == Sym.STRING) {
                    code += idList.get(i).getNameId() + ", ";
                } else {
                    code += "&" + idList.get(i).getNameId() + ", ";
                }
            }
            code = code + ");\n";
        }
    }

    public void visit(WriteStatNode node) throws IOException {
        code += "printf(\"";
        //System.out.println("WriteStatNode");
        if (node.getExprNode() instanceof ConstNode) {
            code += ((ConstNode) node.getExprNode()).getValue();
            code += "\\n";
            code += "\"";


        } else if (node.getExprNode() instanceof LeafNode) {
            LeafNode leafNode = ((LeafNode) node.getExprNode());
            code += formatSpecifier(leafNode.getType());
            code += "\", " + leafNode.getNameId();
        } else if (node.getExprNode() instanceof ConcatNode) {
            code += "%s\\n\", ";
            node.getExprNode().accept(this);
        }
        code += ");\n";
    }

    public void visit(ReturnNode node) throws IOException {
        //System.out.println("ReturnStatNode");
        code += "return ";
        node.getExprNode().accept(this);
        code += ";\n";
    }

    public void visit(CallFunNode node) throws IOException {
        callFunNodeInvoke(node, true);
    }

    public void visit(ConstNode node) throws IOException {
        //System.out.println("ConstNode");
        if (node.getType() != null) {
            typeConst(node.getType(), node.getValue());
        }
    }

    public void visit(BinOpNode node) throws IOException {
        //System.out.println("BinOpNode");
        node.getExprNode1().accept(this);
        code += typeOperation(node.getName());
        node.getExprNode2().accept(this);
    }

    public void visit(RelOpNode node) throws IOException {
        //System.out.println("RelOpNode");
        if (node.getExprNode1().getType() == Sym.STRING) {
            code += "strcmp(";
            node.getExprNode1().accept(this);
            code += ",";
            node.getExprNode2().accept(this);
            code += ")";
            if (node.getName().equalsIgnoreCase("EQ")) {
                code += " == 0";
            } else {
                code += " != 0";
            }
        } else {
            node.getExprNode1().accept(this);
            code += typeOperation(node.getName());
            node.getExprNode2().accept(this);
        }

    }

    public void visit(UnOpNode node) throws IOException {
        //System.out.println("UnaryOpNode");
        code += typeOperation(node.getName());
        node.getExprNode1().accept(this);
    }

    public void visit(OrAndOpNode node) throws IOException {
        //System.out.println("OrAndOpNode");
        node.getExprNode1().accept(this);
        code += typeOperation(node.getName());
        node.getExprNode2().accept(this);
    }

    public void visit(ConcatNode node) throws IOException {
        //System.out.println("ConcatNode");
        String concatFunction = "";
        if (node.getExprNode2().getType() == Sym.STRING) {
            if (!code.contains("concatStringToString")) {
                concatFunction += "char* concatStringToString(char *s1, char* i) {\n" +
                        "    char* s = malloc(256);\n" +
                        "    sprintf(s, \"%s%s\", s1, i);\n" +
                        "    return s;\n" +
                        "}\n\n";
            }
            String firstPart = code.substring(0, indexString);
            String lastPart = code.substring(indexString);
            String newSubstring = firstPart + concatFunction + lastPart;
            newSubstring += "concatStringToString(";
            code = newSubstring;
            if (node.getExprNode1() instanceof CallFunNode) {
                callFunNodeInvoke((CallFunNode) node.getExprNode1(), false);
            } else {
                node.getExprNode1().accept(this);
            }
            code += ",";
            if (node.getExprNode2() instanceof CallFunNode) {
                callFunNodeInvoke((CallFunNode) node.getExprNode2(), false);
            } else {
                node.getExprNode2().accept(this);
            }
            code += ")";
        } else if (node.getExprNode2().getType() == Sym.REAL) {
            if (!code.contains("concatRealToString")) {
                concatFunction += "char* concatRealToString(char *s1, float i) {\n" +
                        "    char* s = malloc(256);\n" +
                        "    sprintf(s, \"%s%.2f\", s1, i);\n" +
                        "    return s;\n" +
                        "}\n\n";
            }
            String firstPart = code.substring(0, indexString);
            String lastPart = code.substring(indexString);
            String newSubstring = firstPart + concatFunction + lastPart;
            newSubstring += "concatRealToString(";
            code = newSubstring;
            if (node.getExprNode1() instanceof CallFunNode) {
                callFunNodeInvoke((CallFunNode) node.getExprNode1(), false);
            } else {
                node.getExprNode1().accept(this);
            }
            code += ",";
            if (node.getExprNode2() instanceof CallFunNode) {
                callFunNodeInvoke((CallFunNode) node.getExprNode2(), false);
            } else {
                node.getExprNode2().accept(this);
            }
            code += ")";
        } else if (node.getExprNode2().getType() == Sym.INTEGER) {
            if (!code.contains("concatIntegerToString")) {
                concatFunction += "char* concatIntegerToString(char *s1, int i) {\n" +
                        "    char* s = malloc(256);\n" +
                        "    sprintf(s, \"%s%d\", s1, i);\n" +
                        "    return s;\n" +
                        "}\n\n";
            }
            String firstPart = code.substring(0, indexString);
            String lastPart = code.substring(indexString);
            String newSubstring = firstPart + concatFunction + lastPart;
            newSubstring += "concatIntegerToString(";
            code = newSubstring;
            if (node.getExprNode1() instanceof CallFunNode) {
                callFunNodeInvoke((CallFunNode) node.getExprNode1(), false);
            } else {
                node.getExprNode1().accept(this);
            }
            code += ",";
            if (node.getExprNode2() instanceof CallFunNode) {
                callFunNodeInvoke((CallFunNode) node.getExprNode2(), false);
            } else {
                node.getExprNode2().accept(this);
            }
            code += ")";
        }

    }

    public void visit(NotOpNode node) throws IOException {
        //System.out.println("NotOpNode");
        code = code + "!";
        node.getExprNode1().accept(this);
    }

    public void visit(PowOpNode node) throws IOException {
        //System.out.println("PowOpNode");
        code = code + "pow(";
        node.getExprNode1().accept(this);
        code = code + " , ";
        node.getExprNode2().accept(this);
        code = code + ")";
    }


    public void visit(LeafNode node) throws IOException {
        code += node.getNameId();
    }

    public void callFunNodeInvoke(CallFunNode node, boolean comma) throws IOException {
        node.getLeafNode().accept(this);
        code += "(";
        ArrayList<ExprNode> exprListNode = node.getExprRefsNode();
        for (int i = 0; i < exprListNode.size(); i++) {
            if (i == exprListNode.size() - 1) {
                if (exprListNode.get(i) instanceof OutParNode) {
                    code += "&";
                    exprListNode.get(i).accept(this);
                } else {
                    exprListNode.get(i).accept(this);
                }

            } else {
                if (exprListNode.get(i) instanceof OutParNode) {
                    code += "&";
                    exprListNode.get(i).accept(this);
                    code += ", ";
                } else {
                    exprListNode.get(i).accept(this);
                    code += ", ";
                }
            }

        }
        if (comma) {
            code += ");";
        } else {
            code += ")";
        }

    }

    private String typeC(int typeMyfun) {
        switch (typeMyfun) {
            case Sym.INTEGER:
                return "int ";
            case Sym.REAL:
                return "float ";
            case Sym.STRING:
                return "char *";
            case Sym.BOOL:
                return "bool ";
        }
        return "";
    }

    private String formatSpecifier(int type) {
        switch (type) {
            case Sym.INTEGER:
                return "%d";
            case Sym.REAL:
                return "%f";
            case Sym.STRING:
                return "%s";
            default:
                return "";
        }
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

    private void typeConst(int typeConst, Object value) {
        switch (typeConst) {
            case Sym.INTEGER:
            case Sym.REAL:

            case Sym.BOOL:
            case Sym.INTEGER_CONST:
            case Sym.REAL_CONST:
                code += value;
                break;
            case Sym.STRING:
            case Sym.STRING_CONST:
                code += "\"" + value + "\"";
        }

    }
}

