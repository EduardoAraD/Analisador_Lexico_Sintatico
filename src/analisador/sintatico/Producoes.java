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
        if(token.getPadrao() == 26){ // palavra reservada PROGRAM
            token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 42){ // padrao IDENTIFIER
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 57){ // simbolo [
                    token = GerandoTokens.getNextToken();
                    if(token.getPadrao() == 59){ // simbolo (
                        boolean funcionou = identifier_list(); 
                        token = GerandoTokens.getNextToken();
                        if(funcionou && token.getPadrao() == 60){ // simbolo )
                            token = GerandoTokens.getNextToken();
                            if(token.getPadrao() == 58){ // simbolo ]
                                token = GerandoTokens.getNextToken();
                                if(token.getPadrao() == 55){ // simbolo ;
                                    funcionou = block();
                                    token = GerandoTokens.getNextToken();
                                    if(funcionou && token.getPadrao() == 53) // simbolo .
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
    public static boolean block(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 73){ // simbolo {
            token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 57){ // simbolo [
                boolean funcionou = label_declaration_part();
                token = GerandoTokens.getNextToken();
                if(funcionou && token.getPadrao() == 58){ // simbolo ]
                    token = GerandoTokens.getNextToken();
                    if(token.getPadrao() == 57){ // simbolo [
                        funcionou = const_declaration_part();
                        token = GerandoTokens.getNextToken();
                        if(funcionou && token.getPadrao() == 58){ // simbolo ]
                            token = GerandoTokens.getNextToken();
                            if(token.getPadrao() == 57){ // simbolo [
                                funcionou = type_declaration_part();
                                token = GerandoTokens.getNextToken();
                                if(funcionou && token.getPadrao() == 58){ // simbolo ]
                                    token = GerandoTokens.getNextToken();
                                    if(token.getPadrao() == 57){ //simbolo [
                                        funcionou = var_declaration_part();
                                        token = GerandoTokens.getNextToken();
                                        if(funcionou && token.getPadrao() == 58){ // simbolo ]
                                            token = GerandoTokens.getNextToken();
                                            if(token.getPadrao() == 57){ // simbolo]
                                                funcionou = subroutine_declaration_part();
                                                token = GerandoTokens.getNextToken();
                                                if(funcionou && token.getPadrao() == 58){ // simbolo ]
                                                    token = GerandoTokens.getNextToken();
                                                    if(token.getPadrao() == 74){ // simbolo }
                                                        return compound_statement();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean label_declaration_part(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 18){ // palavra reservada LABEL
            token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 41){ // padrao NUMBER
                token = GerandoTokens.getNextToken();
                while(token.getPadrao() == 56){ // simbolo ,
                    token = GerandoTokens.getNextToken();
                    if(token.getPadrao() != 41) // padrao NUMBER
                        return false;
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55){ // simbolo ;
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean const_declaration_part(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 7){// palavra reservada CONST
            boolean funcionou = const_definition();
            if(funcionou){
                token = GerandoTokens.getNextToken();
                while(token.getPadrao() == 55 && funcionou){ // simbolo ;
                    funcionou = const_definition();
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55) // simbolo ;
                    return true;
            }
            
        }
        return false;
    }
    public static boolean const_definition(){
        return false;
    }
    public static boolean type_declaration_part(){
        return false;
    }
    public static void type_declaration(){}
    public static void type(){}
    public static void simple_type(){}
    public static void _const(){}
    public static void field_list(){}
    public static boolean var_declaration_part(){
        return false;
    }
    public static void var_declaration(){}
    public static boolean identifier_list(){
        return false;
    }
    public static boolean subroutine_declaration_part(){
        return false;
    }
    public static void procedure_declaration(){}
    public static void function_declaration(){}
    public static void formal_parameters(){}
    public static void param_section(){}
    public static boolean compound_statement(){
        return false;
    }
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
