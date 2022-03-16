import java_cup.runtime.*;

%%
// Declarations for JFlex
%unicode // We wish to read text files
%class Lexer
%line
%column
%cup // Declare that we expect to use Java CUP

%eofval{
	return new Symbol(sym.EOF,new String("END OF FILE"));
%eofval}
%eofclose
// Abbreviations for regular expressions
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]
letter = [$_A-Za-z]
digit = [0-9]
digitWithNoZero = [1-9]
id = {letter}({letter} | {digit})*

Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "#*" [^*] ~"#"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}? | "#" {InputCharacter}* {LineTerminator}?
integer = 0 | {digitWithNoZero}{digit}*
number = ((\+|-)?({digit}+)(\.{digit}+)?)|((\+|-)?\.?{digit}+)


%{
    StringBuilder string = new StringBuilder();
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
%}


%state STRING

%%

<YYINITIAL> {
<<EOF>>             { return symbol(sym.EOF); }
"main"				{return symbol(sym.MAIN);}
"integer"			{return symbol(sym.INTEGER);}
"string"			{return symbol(sym.STRING);}
"real"				{return symbol(sym.REAL);}
"bool"				{return symbol(sym.BOOL);}
"("					{return symbol(sym.LPAR);}
")"					{return symbol(sym.RPAR);}
":"					{return symbol(sym.COLON);}
"fun"				{return symbol(sym.FUN);}
"end"				{return symbol(sym.END);}
"if"				{return symbol(sym.IF);}
"then"				{return symbol(sym.THEN);}
"else"				{return symbol(sym.ELSE);}
"while"				{return symbol(sym.WHILE);}
"loop"				{return symbol(sym.LOOP);}
"%"					{return symbol(sym.READ);}
"?"					{return symbol(sym.WRITE);}
"?."				{return symbol(sym.WRITELN);}
"?,"				{return symbol(sym.WRITEB);}
"?:"				{return symbol(sym.WRITET);}
":="				{return symbol(sym.ASSIGN);}
"+"					{return symbol(sym.PLUS);}
"-"					{return symbol(sym.MINUS);}
"*"					{return symbol(sym.TIMES);}
"div"				{return symbol(sym.DIVINT);}
"/"					{return symbol(sym.DIV);}
"^"					{return symbol(sym.POW);}
"&"					{return symbol(sym.STR_CONCAT);}
"=" 				{return symbol(sym.EQ);}
"<>"                {return symbol(sym.NE);}
"!="                {return symbol(sym.NE);}
"<" 				{return symbol(sym.LT);}
"<=" 				{return symbol(sym.LE);}
">" 				{return symbol(sym.GT);}
">="				{return symbol(sym.GE);}
"and"				{return symbol(sym.AND);}
"or"				{return symbol(sym.OR);}
"not"				{return symbol(sym.NOT);}
//"null"      		{return symbol(sym.NULL);}
"true"      		{return symbol(sym.TRUE);}
"false"         	{return symbol(sym.FALSE);}
{integer} 	        {return symbol(sym.INTEGER_CONST);}
{number}		    {return symbol(sym.REAL_CONST);}
\"                  {string.setLength(0); yybegin(STRING);}
\'                  {string.setLength(0); yybegin(STRING);}
";"					{return symbol(sym.SEMI);}
","					{return symbol(sym.COMMA);}
"return"			{return symbol(sym.RETURN);}
"@"					{return symbol(sym.OUTPAR);}
"var"				{return symbol(sym.VAR);}
"out"				{return symbol(sym.OUT);}
"loop"				{return symbol(sym.LOOP);}
{id}				{return symbol(sym.ID,yytext());}
}

<STRING> {
\'  {yybegin(YYINITIAL); return new Symbol(sym.STRING_CONST, string.toString()); }

[^\t\n\r'\\]+    { string.append(yytext()); }

\t { string.append("\t"); }

\n { string.append("\n"); }

\r { string.append("\r"); }

\' { string.append("'"); }

\  { string.append("\\"); }

\n  { /* ignore / }

\r  { / ignore */ }

\t { string.append("\t"); }

<<EOF>> {return new Symbol(sym.error, "STRINGA COSTANTE NON COMPLETATA: "+ string.toString()); }
         }

{WhiteSpace} { /* ignore */ }
[^]           { throw new Error("\n\nIllegal character < "+ yytext()+" >\n"); }
// End of file