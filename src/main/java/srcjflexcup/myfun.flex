import java_cup.runtime.*;

%%
// Declarations for JFlex
%unicode // We wish to read text files
%class Lexer
%line
%column
%cup // Declare that we expect to use Java CUP

%eofval{
	return new symbol(Sym.EOF,new String("END OF FILE"));
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

comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "#*" [^*] ~"#"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}? | "#" {InputCharacter}* {LineTerminator}?
integer = 0 | {digitWithNoZero}{digit}*
number = ((\+|-)?({digit}+)(\.{digit}+)?)

%{
    StringBuilder string = new StringBuilder();
    private symbol symbol(int type){
        return new symbol(type, yyline, yycolumn);
    }
    private symbol symbol(int type, Object value){
        return new symbol(type, yyline, yycolumn, value);
    }
%}


%state STRING

%%

<YYINITIAL> {
<<EOF>>             { return symbol(Sym.EOF); }
"main"				{return symbol(Sym.MAIN);}
"integer"			{return symbol(Sym.INTEGER);}
"string"			{return symbol(Sym.STRING);}
"real"				{return symbol(Sym.REAL);}
"bool"				{return symbol(Sym.BOOL);}
"("					{return symbol(Sym.LPAR);}
")"					{return symbol(Sym.RPAR);}
":"					{return symbol(Sym.COLON);}
"fun"				{return symbol(Sym.FUN);}
"end"				{return symbol(Sym.END);}
"if"				{return symbol(Sym.IF);}
"then"				{return symbol(Sym.THEN);}
"else"				{return symbol(Sym.ELSE);}
"while"				{return symbol(Sym.WHILE);}
"loop"				{return symbol(Sym.LOOP);}
"%"					{return symbol(Sym.READ);}
"?"					{return symbol(Sym.WRITE);}
"?."				{return symbol(Sym.WRITELN);}
"?,"				{return symbol(Sym.WRITEB);}
"?:"				{return symbol(Sym.WRITET);}
":="				{return symbol(Sym.ASSIGN);}
"+"					{return symbol(Sym.PLUS);}
"-"					{return symbol(Sym.MINUS);}
"*"					{return symbol(Sym.TIMES);}
"div"				{return symbol(Sym.DIVINT);}
"/"					{return symbol(Sym.DIV);}
"^"					{return symbol(Sym.POW);}
"&"					{return symbol(Sym.STR_CONCAT);}
"=" 				{return symbol(Sym.EQ);}
"<>"                {return symbol(Sym.NE);}
"!="                {return symbol(Sym.NE);}
"<" 				{return symbol(Sym.LT);}
"<=" 				{return symbol(Sym.LE);}
">" 				{return symbol(Sym.GT);}
">="				{return symbol(Sym.GE);}
"and"				{return symbol(Sym.AND);}
"or"				{return symbol(Sym.OR);}
"not"				{return symbol(Sym.NOT);}
//"null"      		{return symbol(symbol.Sym.NULL);}
"true"      		{return symbol(Sym.TRUE, yytext());}
"false"         	{return symbol(Sym.FALSE, yytext());}
{integer} 	        {return symbol(Sym.INTEGER_CONST, yytext());}
{number}		    {return symbol(Sym.REAL_CONST, yytext());}
\"                  {string.setLength(0); yybegin(STRING);}
\'                  {string.setLength(0); yybegin(STRING);}
";"					{return symbol(Sym.SEMI);}
","					{return symbol(Sym.COMMA);}
"return"			{return symbol(Sym.RETURN);}
"@"					{return symbol(Sym.OUTPAR);}
"var"				{return symbol(Sym.VAR);}
"out"				{return symbol(Sym.OUT);}
"loop"				{return symbol(Sym.LOOP);}
{id}				{return symbol(Sym.ID,yytext());}
}

<STRING> {
\'  {yybegin(YYINITIAL); return new Symbol(Sym.STRING_CONST, string.toString()); }

[^\t\n\r'\\]+    { string.append(yytext()); }

\t { string.append("\t"); }

\n { string.append("\n"); }

\r { string.append("\r"); }

\' { string.append("'"); }

\  { string.append("\\"); }

\n  { /* ignore / }

\r  { / ignore */ }

\t { string.append("\t"); }

<<EOF>> {return new Symbol(Sym.error, "STRINGA COSTANTE NON COMPLETATA: "+ string.toString()); }
}

{comment} {/* ignore */}
{WhiteSpace} { /* ignore */ }
[^]           { throw new Error("\n\nIllegal character < "+ yytext()+" >\n"); }
// End of file