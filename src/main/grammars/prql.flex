package org.mvnsearch.plugins.prql.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.mvnsearch.plugins.prql.lang.psi.PrqlTypes;
import static org.mvnsearch.plugins.prql.lang.psi.PrqlTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import com.intellij.psi.TokenType;

%%


%{
  public PrqlLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class PrqlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

///////////////////////////////////////////////////////////////////////////////////////////////////
// Whitespaces
///////////////////////////////////////////////////////////////////////////////////////////////////

EOL_WS           = \n | \r | \r\n
LINE_WS          = [\ \t]
WHITE_SPACE_CHAR = {EOL_WS} | {LINE_WS}
WHITE_SPACE      = {WHITE_SPACE_CHAR}+

///////////////////////////////////////////////////////////////////////////////////////////////////
// Identifier
///////////////////////////////////////////////////////////////////////////////////////////////////

IDENTIFIER = [_\p{xidstart}][\p{xidcontinue}]*
PARAM = \$\d+
PARAM2 = [\:\$][\p{xidstart}][\p{xidcontinue}]*
PARAM3 = [\\\$]\{[\p{xidstart}][\p{xidcontinue}\.]*\}

///////////////////////////////////////////////////////////////////////////////////////////////////
// comment
///////////////////////////////////////////////////////////////////////////////////////////////////

COMMENT=("#")[^\n]*

///////////////////////////////////////////////////////////////////////////////////////////////////
// function
///////////////////////////////////////////////////////////////////////////////////////////////////

FUNCTION_NAME = {IDENTIFIER}
ARROW = "->"
EQ = "="
LET = "let"
FUNCTION_PARAM= {IDENTIFIER}(:\s*)?
FUNCTION_BODY= [^\n]*

///////////////////////////////////////////////////////////////////////////////////////////////////
// Literals
///////////////////////////////////////////////////////////////////////////////////////////////////

INTEGER_LITERAL=[\d][\d_]*
BINARY_NUMERICAL=0b([01]+)
OCTAL_NUMERICAL=0o([01234567]+)
HEXADECIMAL_NUMERICAL=0x([0123456789abcdefABCDEFG]+)
DOUBLE_LITERAL=([\d][\d_]*)(\.)([\d][\d_]*)

DATE_LITERAL = ("@")(\d{4})-(\d{2})-(\d{2})
TIME_LITERAL = ("@")(\d{2}):(\d{2})(:\d{2})?
TIMESTAMP_LITERAL = ("@")(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})(\.\d+)?([+-][\d]+(:\d+)?)?
INTERVAL_LITERAL = (\d+)(seconds|minutes|hours|days|months|years)
RAW_LITERAL   = (\`[^\\\`\r\n]*\`)
CHAR_LITERAL   = (\'[^\\\'\r\n]*\')
STRING_LITERAL = (\"[^\"\r\n]*\")
THREE_QUO = (\"\"\")
INDENTED_STRING = {THREE_QUO}([\"]{0,2}([^\"]))*{THREE_QUO}
INDENTED_STRING_QUOTE = (''')([']{0,2}([^']))*(''')

%state  LET_BOCK FUNCTION FUNCTION_BODY_BLOCK

%%

<LET_BOCK> {
  {WHITE_SPACE}+  {  yybegin(LET_BOCK); return TokenType.WHITE_SPACE; }
  {FUNCTION_NAME} / ([^\n]*)("->")   { yybegin(FUNCTION); return FUNCTION_NAME; }
  {IDENTIFIER}         {  yybegin(YYINITIAL); return IDENTIFIER; }
}

<FUNCTION> {
  {EQ}         {  yybegin(FUNCTION); return EQ; }
  {FUNCTION_PARAM}    {  yybegin(FUNCTION); return FUNCTION_PARAM; }
  {WHITE_SPACE}+  {  yybegin(FUNCTION); return TokenType.WHITE_SPACE; }
  {ARROW}         {  yybegin(FUNCTION_BODY_BLOCK); return ARROW; }
}

<FUNCTION_BODY_BLOCK> {
  {FUNCTION_BODY}         {  yybegin(YYINITIAL); return FUNCTION_BODY; }
}

<YYINITIAL> {
  "{"                             { return LBRACE; }
  "}"                             { return RBRACE; }
  "["                             { return LBRACK; }
  "]"                             { return RBRACK; }
  "("                             { return LPAREN; }
  ")"                             { return RPAREN; }
  ":"                             { return COLON; }
  ","                             { return COMMA; }
  "."                             { return DOT; }
  "!"                             { return EXCL; }
  ".."                            { return DOTDOT; }
  "="                             { return EQ; }
  "!="                            { return EXCLEQ; }
  "=="                            { return EQEQ; }
  "+"                             { return PLUS; }
  "-"                             { return MINUS; }
  "*"                             { return MUL; }
  "/"                             { return DIV; }
  "%"                             { return REM; }
  "||"                             { return D_OR; }
  "&&"                             { return D_AND; }
  "|"                             { return OR; }
  "<"                             { return LT; }
  "<="                            { return LT_EQ; }
  ">"                             { return GT; }
  ">="                            { return GT_EQ; }
  "->"                            { return ARROW; }
  "=>"                            { return EQARROW; }
  "~="                            { return MATCH; }
  "@"                             { return AT; }
  "$"                             { return DOLLAR; }
  "??"                            { return COALESCE; }
  "?"                             { return QUESTION; }
  "null"                          { return NULL; }

  "prql"|"func"|"table" | "into" |"aggregate"|"derive"|"filter"|"from" | "from_text" | "group"|"join" |"select" |"sort" | "take" | "window" | "concat" | "union" | "append" | "this" | "that"
                                  { return RESERVED_KEYWORD; }
   "min"|"max"|"count"|"average"|"stddev"|"every"|"any"|"sum"|"count_distinct"
                                  { return AGGREGATE_FUNCTION; }

  "true"                  { return BOOL_TRUE; }
  "false"                  { return BOOL_FALSE; }
  "and"                            { return AND_LITERAL; }
  "or"                           { return OR_LITERAL; }
  "in"                           { return IN; }
  "switch"                          { return SWITCH; }
  "case"                          { return CASE; }
  "loop"                          { return LOOP; }


  /* LITERALS */

  {INTEGER_LITERAL}                   { return INTEGER_LITERAL; }
  {BINARY_NUMERICAL}                   { return BINARY_NUMERICAL; }
  {OCTAL_NUMERICAL}                   { return OCTAL_NUMERICAL; }
  {HEXADECIMAL_NUMERICAL}             { return HEXADECIMAL_NUMERICAL; }
  {DOUBLE_LITERAL}                   { return  DOUBLE_LITERAL; }

  "f" {STRING_LITERAL}              { return F_STRING; }

  "s" {INDENTED_STRING}            { return S_INDENTED_STRING; }
  "s" {STRING_LITERAL}             { return S_STRING; }
  {INDENTED_STRING_QUOTE}   { return INDENTED_STRING_QUOTE; }
  {INDENTED_STRING}   { return INDENTED_STRING; }
  {DATE_LITERAL}             { return DATE_LITERAL; }
  {TIME_LITERAL}             { return TIME_LITERAL; }
  {TIMESTAMP_LITERAL}             { return TIMESTAMP_LITERAL; }
  {INTERVAL_LITERAL}             { return INTERVAL_LITERAL; }

  {CHAR_LITERAL}                { return CHAR_LITERAL; }
  {STRING_LITERAL}                { return STRING_LITERAL; }
  {RAW_LITERAL}                { return RAW_LITERAL; }

  {COMMENT}                      { return COMMENT; }
  {PARAM3}                        { return PARAM3; }
  {PARAM2}                        { return PARAM2; }
  {PARAM}                        { return PARAM; }
  {IDENTIFIER}                   { return IDENTIFIER; }

  {LET} / ([^\n]*)("=")   { yybegin(LET_BOCK); return LET; }

  {WHITE_SPACE}                   { return WHITE_SPACE; }
}

///////////////////////////////////////////////////////////////////////////////////////////////////
// Catch All
///////////////////////////////////////////////////////////////////////////////////////////////////

[^] { return BAD_CHARACTER; }