package visitor.xml;

import nodes.*;

import java.util.ArrayList;

public class XMLVisitor implements Visitor {
    @Override
    public String visit(AbstractSyntaxNode node) {
        String xmlString = "";

        //ProgramNode START
        if (node instanceof ProgramNode) {
            if (node != null) {
                xmlString = "<ProgramNode> ";
                ArrayList<VarDeclNode> varDeclListNode = ((ProgramNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        xmlString += (varDeclNode).accept(this) + " ";
                    }
                }
                ArrayList<FunNode> funListNode = ((ProgramNode) node).getFunListNode();
                for (FunNode funNode : funListNode) {
                    if (funNode != null) {
                        xmlString += (funNode).accept(this) + " ";
                    }
                }
                xmlString += ((ProgramNode) node).getMainNode().accept(this) +
                        " </ProgramNode>";
            }
            return xmlString;
        }
        //ProgramNode END

        //MainNode START
        if (node instanceof MainNode) {
            if (node != null) {
                xmlString = "<MainNode> ";
                ArrayList<VarDeclNode> varDeclListNode = ((MainNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        xmlString += (varDeclNode).accept(this) + " ";
                    }
                }
                ArrayList<StatNode> statListNode = ((MainNode) node).getStatListNode();
                for (StatNode statNode : statListNode) {
                    if (statNode != null) {
                        xmlString += (statNode).accept(this) + " ";
                    }
                }
                xmlString += " </MainNode>";
            }
            return xmlString;
        }
        //MainNode END


        //FunNode START
        if (node instanceof FunNode) {
            if (node != null) {
                xmlString = "<FunNode> ";
                xmlString += ((FunNode) node).getLeafNode().accept(this);
                ArrayList<ParamDeclNode> paramDeclNodes = ((FunNode) node).getParamDeclListNodes();
                for (ParamDeclNode paramDeclNode : paramDeclNodes) {
                    if (paramDeclNode != null) {
                        xmlString += (paramDeclNode).accept(this) + " ";
                    }
                }
                ArrayList<VarDeclNode> varDeclListNode = ((FunNode) node).getVarDeclListNode();
                for (VarDeclNode varDeclNode : varDeclListNode) {
                    if (varDeclNode != null) {
                        xmlString += (varDeclNode).accept(this) + " ";
                    }
                }
                ArrayList<StatNode> statNode = ((FunNode) node).getStatListNode();
                for (StatNode statN : statNode) {
                    if (statN != null) {
                        xmlString += (statN).accept(this) + " ";
                    }
                }
                xmlString += " </FunNode>";
            }
            return xmlString;
        }
        //FunNode END


        //VarDeclNode START
        if (node instanceof VarDeclNode) {
            if (node != null) {
                xmlString = "<VarDeclNode> ";
                if (((VarDeclNode) node).getTypeVar() != null) {
                    xmlString += "<Type>" + ((VarDeclNode) node).getTypeVar() + "</Type>\n";
                }
                ArrayList<InitNode> initNode = ((VarDeclNode) node).getInitNodes();
                for (InitNode initN : initNode) {
                    if (initN != null) {
                        xmlString += (initN).accept(this) + "\n";
                    }
                }
                xmlString += "</VarDeclNode>";
            }
            return xmlString;
        }
        //VarDeclNode END


        //InitNode START
        if (node instanceof InitNode) {
            if (node != null) {
                xmlString = "<InitNode> ";
                xmlString += ((InitNode) node).getLeafNode().accept(this);

                if (((InitNode) node).getExprNode() != null) {
                    xmlString += ((InitNode) node).getExprNode().accept(this);
                }
                xmlString += ((InitNode) node).getConstant();
                xmlString += " </InitNode>";
            }
            return xmlString;
        }
        //InitNode END

        //ParamDeclNode START
        if (node instanceof ParamDeclNode) {
            if (node != null) {
                xmlString = "<ParamDeclNode> ";
                xmlString += "<Type>" + ((ParamDeclNode) node).getTypeParam() + "</Type>\n";
                xmlString += ((ParamDeclNode) node).getLeafNode().accept(this);
                xmlString += " </ParamDeclNode>";
            }
            return xmlString;
        }
        //ParamDeclNode END


        //ExprNode START
        if (node instanceof ExprNode) {

            if (node != null) {
                if (node instanceof WhileStatNode) {
                    xmlString += "<WhileStatNode>";
                    xmlString += ((WhileStatNode) node).getExprNode().accept(this);
                    ArrayList<StatNode> statListNode = ((WhileStatNode) node).getStatListNode();
                    for (StatNode statElem : statListNode) {
                        if (statElem != null) {
                            xmlString += statElem.accept(this) + "\n";
                        }
                    }
                    ArrayList<VarDeclNode> varDeclListNode = ((WhileStatNode) node).getVarDeclListNode();
                    for (VarDeclNode varDeclElem : varDeclListNode) {
                        if (varDeclElem != null) {
                            xmlString += varDeclElem.accept(this);
                        }
                    }

                    xmlString += "</WhileStatNode>";
                }

                if (node instanceof IfStatNode) {
                    xmlString += "<IfStatNode>";
                    xmlString += ((IfStatNode) node).getExprNode().accept(this);
                    ArrayList<VarDeclNode> varDeclListNode = ((IfStatNode) node).getVarDeclListNode();
                    for (VarDeclNode varDeclElem : varDeclListNode) {
                        if (varDeclElem != null) {
                            xmlString += varDeclElem.accept(this);
                        }
                    }
                    ArrayList<StatNode> statListNode = ((IfStatNode) node).getStatListNode();
                    for (StatNode statElem : statListNode) {
                        if (statElem != null) {
                            xmlString += statElem.accept(this) + "\n";
                        }
                    }

                    if (((IfStatNode) node).getElseNode() != null) {
                        xmlString += ((IfStatNode) node).getElseNode().accept(this);

                    }

                    xmlString += "</IfStatNode>";
                }

                if (node instanceof ElseNode) {
                    xmlString += "<ElseNode>";
                    ArrayList<StatNode> statListNode = ((ElseNode) node).getStatListNode();
                    for (StatNode statElem : statListNode) {
                        if (statElem != null) {
                            xmlString += statElem.accept(this) + "\n";
                        }
                    }
                    ArrayList<VarDeclNode> varDeclListNode = ((ElseNode) node).getVarDeclListNode();
                    for (VarDeclNode varDeclElem : varDeclListNode) {
                        if (varDeclElem != null) {
                            xmlString += varDeclElem.accept(this);
                        }
                    }
                    xmlString += "</ElseNode>";
                }

                if (node instanceof WriteStatNode) {
                    xmlString += "<WriteStatNode>";
                    xmlString += ((WriteStatNode) node).getExprNode().accept(this);
                    xmlString += "</WriteStatNode>";
                }

                if (node instanceof AssignStatNode) {
                    xmlString += "<AssignStatNode>";
                    xmlString += ((AssignStatNode) node).getLeafNode().accept(this);
                    xmlString += ((AssignStatNode) node).getExprNode().accept(this);
                    xmlString += "</AssignStatNode>";
                }


                if (node instanceof ReadStatNode) {
                    xmlString += "<ReadStatNode>";
                    ArrayList<LeafNode> idListNode = ((ReadStatNode) node).getIdListNode();
                    for (LeafNode idElem : idListNode) {
                        if (idElem != null) {
                            xmlString += idElem.accept(this);
                        }
                    }
                    if (((ReadStatNode) node).getExprNode() != null) {
                        xmlString += ((ReadStatNode) node).getExprNode().accept(this);
                    }
                    xmlString += "</ReadStatNode>";
                }

                if (node instanceof ReturnNode) {
                    xmlString += "<ReturnNode>";
                    xmlString += ((ReturnNode) node).getExprNode().accept(this);
                    xmlString += "</ReturnNode>";
                }


                if (node instanceof CallFunNode) {
                    xmlString += "<CallFunNode>";

                    xmlString += ((CallFunNode) node).getLeafNode().accept(this);
                    ArrayList<ExprNode> exprRefsNode = ((CallFunNode) node).getExprRefsNode();
                    for (ExprNode exprElem : exprRefsNode) {
                        if (exprElem != null) {
                            xmlString += exprElem.accept(this) + "\n";
                        }

                    }
                    xmlString += "</CallFunNode>";
                }

            }
            if (node instanceof ConstNode) {
                xmlString += "<ConstNode> ";

                xmlString += "("+ ((ConstNode) node).getNameID() +",";
                if (((ConstNode) node).getValue()!= null) {
                    xmlString +=    ((ConstNode) node).getValue().toString() +")";
                }
                xmlString += "</ConstNode> ";
            }

            if (node instanceof BinOpNode) {
                xmlString += "<BinOpNode> ";
                xmlString += ((BinOpNode) node).getExprNode1().accept(this);
                xmlString += ((BinOpNode) node).getName();
                xmlString += ((BinOpNode) node).getExprNode2().accept(this);
                xmlString += "</BinOpNode> ";
            }

            if (node instanceof RelOpNode) {
                xmlString += "<RelOpNode> ";
                xmlString += ((RelOpNode) node).getExprNode1().accept(this);
                xmlString += ((RelOpNode) node).getName();
                xmlString += ((RelOpNode) node).getExprNode2().accept(this);
                xmlString += "</RelOpNode> ";
            }

            if (node instanceof ConcatNode) {
                xmlString += "<ConcatNode> ";
                xmlString += ((ConcatNode) node).getExprNode1().accept(this);
                xmlString += ((ConcatNode) node).getName();
                xmlString += ((ConcatNode) node).getExprNode2().accept(this);
                xmlString += "</ConcatNode> ";
            }


            if (node instanceof OrAndOpNode) {
                xmlString += "<OrAndOpNode> ";
                xmlString += ((OrAndOpNode) node).getExprNode1().accept(this);
                xmlString += ((OrAndOpNode) node).getName();
                xmlString += ((OrAndOpNode) node).getExprNode2().accept(this);
                xmlString += "</OrAndOpNode> ";
            }

            if (node instanceof UnOpNode) {
                xmlString += "<UnOpNode> ";
                xmlString += ((UnOpNode) node).getExprNode1().accept(this);
                xmlString += ((UnOpNode) node).getName();
                xmlString += "</UnOpNode> ";
            }
            if (node instanceof LeafNode) {
                xmlString += "(" + ((LeafNode) node).getName() + "," + ((LeafNode) node).getNameId() + ") \n";
            }
            return xmlString;
        }
        return xmlString;
    }
    //ExprNode END
}
