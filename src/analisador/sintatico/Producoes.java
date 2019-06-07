/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.sintatico;

import analisador.lexico.GerandoTokens;
import analisador.lexico.Token;

/**
 *
 * @author Eduardo
 */
public class Producoes {
    
    public static boolean program(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 26){
            token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 42){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 57){
                    token = GerandoTokens.getNextToken();
                    if(token.getPadrao() == 59){
                        identifier_list();
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() == 60){
                            token = GerandoTokens.getNextToken();
                            if(token.getPadrao() == 58){
                                token = GerandoTokens.getNextToken();
                                if(token.getPadrao() == 55){
                                    block();
                                    token = GerandoTokens.getNextToken();
                                    if(token.getPadrao() == 53)
                                        return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static void block(){}
    public static void label_declaration_part(){}
    public static void const_declaration_part(){}
    public static void const_definition(){}
    public static void type_declaration_part(){}
    public static void type_declaration(){}
    public static void type(){}
    public static void simple_type(){}
    public static void _const(){}
    public static void field_list(){}
    public static void var_declaration_part(){}
    public static void var_declaration(){}
    public static void identifier_list(){}
    public static void subroutine_declaration_part(){}
    public static void procedure_declaration(){}
    public static void function_declaration(){}
    public static void formal_parameters(){}
    public static void param_section(){}
    public static void compound_statement(){}
    public static void labeled_statement(){}
    public static void statement(){}
    public static void assing_statement(){}
    public static void procedure_call(){}
    public static void if_statement(){}
    public static void while_statement(){}
    public static void repeat_statement(){}
    public static void for_statement(){}
    public static void with_statement(){}
    public static void case_statement(){}
    public static void _case(){}
    public static void goto_statement(){}
    public static void infipo(){}
    public static void expr_list(){}
    public static void expr(){}
    public static void relop(){}
    public static void simple_expr(){}
    public static void addop(){}
    public static void term(){}
    public static void mulop(){}
    public static void factor(){}
    
}
