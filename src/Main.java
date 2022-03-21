import nodes.ProgramNode;
import visitor.clang.CGeneratorVisitor;
import visitor.semantic.SemanticVisitor;

import java.io.File;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        String inPathFile = args[0];

        //controllo sul file in input: vedere se il file è ".txt"
        if (!(inPathFile.endsWith(".txt"))){
            System.err.println("Dare in input un file .txt");
            System.exit(1);
        }
        parser p = new parser(new Lexer(new FileReader(new File(inPathFile))));

        ProgramNode programOp = (ProgramNode) p.debug_parse().value;
//
//        XMLVisitor xmlVisitor = new XMLVisitor();
//        System.out.println(programOp.accept(xmlVisitor));


        //semantica vistor scope
        SemanticVisitor semanticVisitor = new SemanticVisitor();
        programOp.accept(semanticVisitor);


        //visitor typecheck-regole d'inferenza
        //visitor codegen
        CGeneratorVisitor codeGenVisitor = new CGeneratorVisitor(inPathFile);
        programOp.accept(codeGenVisitor);
    }
}
