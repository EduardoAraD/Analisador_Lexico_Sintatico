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
	public static int linha = 0;
	public static int numToken = 0;
	private static TipoErroSintatico tes = TipoErroSintatico.CORRETO;
	
    public static TipoErroSintatico program(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 26){ // palavra reservada PROGRAM
            TipoErroSintatico tes = identifier(); 
        	if(tes == TipoErroSintatico.CORRETO){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 59){ // simbolo (
                	tes = identifier_list();
                    if(tes == TipoErroSintatico.CORRETO){
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() != 60){
                            return erro(token.getNumero_linha(),TipoErroSintatico.RP);
                        }
                    }else{
                         return tes;
                    }
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55) { // simbolo ;
                	tes = block();
                	if(tes == TipoErroSintatico.CORRETO) {
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() == 53) // simbolo .
                            return TipoErroSintatico.CORRETO;
                        return erro(token.getNumero_linha(),TipoErroSintatico.DOT);
                    }
                	return tes;
                }
                return erro(token.getNumero_linha(),TipoErroSintatico.SEMI_COLON);
            }
            return tes;
        }
        return erro(token.getNumero_linha(),TipoErroSintatico.PROGRAM);
    }
    public static TipoErroSintatico block(){
        TipoErroSintatico tes = label_declaration_part();
    	if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.LABEL)
    		return tes;
    	tes = const_declaration_part();
        if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.CONST)
        	return tes;
        tes = type_declaration_part();
        if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.TYPE)
        	return tes;
        tes = var_declaration_part();
        if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.VAR)
        	return tes;
        //subroutine_declaration_part();
        
        return compound_statement();
        
    }
    public static TipoErroSintatico label_declaration_part(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 18){ // palavra reservada LABEL
            token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 41){ // padrao NUMBER
                token = GerandoTokens.getNextToken();
                while(token.getPadrao() == 56){ // simbolo ,
                    token = GerandoTokens.getNextToken();
                    if(token.getPadrao() != 41) // padrao NUMBER
                        return erro(token.getNumero_linha(),TipoErroSintatico.NUMBER); // falta de NUMBER
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55){ // simbolo ;
                    return TipoErroSintatico.CORRETO;
                } else
                	return erro(token.getNumero_linha(),TipoErroSintatico.SEMI_COLON); // Erro falta de ;
            }else
            	return erro(token.getNumero_linha(),TipoErroSintatico.NUMBER); // Erro falta de NUM
        }
        GerandoTokens.voltaToken();
        return erro(token.getNumero_linha(),TipoErroSintatico.LABEL); // erro falta palavra LABEL
    }
    public static TipoErroSintatico const_declaration_part(){
    	int num_tok = GerandoTokens.getNumero_token();
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 7){// palavra reservada CONST
        	TipoErroSintatico tes = const_definition();
            if(tes == TipoErroSintatico.CORRETO){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 55){ // simbolo ;
                	tes = const_definition();
                    while(tes == TipoErroSintatico.CORRETO){
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() != 55) // simbolo ;
                            return erro(token.getNumero_linha(),TipoErroSintatico.SEMI_COLON); // falta de ;
                        tes = const_definition();
                    }
                    return TipoErroSintatico.CORRETO;
                }
                return erro(token.getNumero_linha(), TipoErroSintatico.SEMI_COLON); // erro falta de ;
            }
            return tes;// erro falta const_definition()
        }
        GerandoTokens.setNumero_token(num_tok);
        return erro(token.getNumero_linha(),TipoErroSintatico.CONST); // erro, falta de palavra CONST
    }
    public static TipoErroSintatico const_definition(){
    	TipoErroSintatico tes = identifier();
    	if(tes == TipoErroSintatico.CORRETO) {
            Token token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 61) { // simbolo =
                return _const();
            }
            return erro(token.getNumero_linha(), TipoErroSintatico.EQUAL);
            // erro falta do simbolo =
    	}
    	return tes; // erro funcao Identifier()
    }
    public static TipoErroSintatico type_declaration_part(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 34) { // palavra Reservada TYPE
    		TipoErroSintatico tes = type_definition();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				tes = type_definition();
    				while(tes == TipoErroSintatico.CORRETO) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return erro(token.getNumero_linha(),TipoErroSintatico.SEMI_COLON); // erro falta de ;
    					tes = type_definition();
    				}
    				return TipoErroSintatico.CORRETO;
    			}
                return erro(token.getNumero_linha(),TipoErroSintatico.SEMI_COLON);
    		}
            return tes;
    	}
    	GerandoTokens.voltaToken(); // falta de palavra TYPE
        return erro(token.getNumero_linha(),TipoErroSintatico.TYPE);
    }
    public static TipoErroSintatico type_definition(){
    	TipoErroSintatico tes = identifier();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 61) { // simbolo =
    			return type();
    		}
    		return erro(token.getNumero_linha(),TipoErroSintatico.EQUAL); // erro falta de =
    	}
    	return tes; // erro funcao Identifier() 
    }
    public static TipoErroSintatico type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 71) { // simbolo ^
    		return identifier();
    	} else if(token.getPadrao() == 2){ // palavra reservada ARRAY
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			TipoErroSintatico tes = simple_type();
    			if(tes == TipoErroSintatico.CORRETO) { 
    				token = GerandoTokens.getNextToken();
    				while(token.getPadrao() == 56) { // simbolo ,
    					tes = simple_type();
    					if(tes != TipoErroSintatico.CORRETO)
    						return tes; // erro funcao simpletype();
    					token = GerandoTokens.getNextToken();
    				}
    				if(token.getPadrao() == 58) { // simbolo ]
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 22) { // palavra Reservada OF
    						return type();
    					}
                        return erro(token.getNumero_linha(),TipoErroSintatico.OF);// erro falta de OF
    				}
                    return erro(token.getNumero_linha(), TipoErroSintatico.RB);// erro falta de ]
    			}
    			return tes; // erro falta de simple_type()
    		}
            return erro(token.getNumero_linha(), TipoErroSintatico.LB);// erro falta de [
    	} else if(token.getPadrao() == 30) { // palavra reservada SET
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 22) { // palavra resevada OF
    			return simple_type();
    		}
    		return erro(token.getNumero_linha(), TipoErroSintatico.OF); // falta da palavra reservada OF
    		
    	} else if(token.getPadrao() == 28) { // palavra Reservada RECORD
    		tes = field_list();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 12) // palavra Reservada END
    				return TipoErroSintatico.CORRETO;
                return erro(token.getNumero_linha(), TipoErroSintatico.END);// erro falta de END
    		}
            return tes;// erro, funcao FIELD_LIST()
    	} else {
    		GerandoTokens.voltaToken();
    		return simple_type();
    	}
    }
    public static TipoErroSintatico simple_type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		TipoErroSintatico tes = identifier();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 56) { // simbolo ,
    				tes = identifier();
    				if(tes == TipoErroSintatico.CORRETO)
    					return tes; // ERRO falta funcao IDENTIFIER
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 60) { // simbolo )
    				return TipoErroSintatico.CORRETO;
    			}
    			return erro(token.getNumero_linha(), TipoErroSintatico.RP); // erro falta do simbolo )
    		}
    		return tes;// erro falta funcao IDENTIFIER
    	} else {
    		GerandoTokens.voltaToken();
    		int tok = GerandoTokens.getNumero_token();
    		TipoErroSintatico tes = _const();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 52) { // simbolo ..
    				return _const();
    			}
    			//return erro(token.getNumero_linha(), TipoErroSintatico.DOTDOT);// erro falta de ..
    		}
    		GerandoTokens.setNumero_token(tok);
    		return identifier();
    		// erro falta de _const()
    	}
    }
    public static TipoErroSintatico _const(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 44) { // padrao STRING
    		return TipoErroSintatico.CORRETO;
    	}
    	if(token.getPadrao() == 70 || token.getPadrao() == 68) { // simbolo + ou -
    		token = GerandoTokens.getNextToken();
    	}
    	if (token.getPadrao() == 41) // padrao NUMBER
    		return TipoErroSintatico.CORRETO;
    	else {
    		GerandoTokens.voltaToken();
    		return identifier();
    	}
    }
    public static TipoErroSintatico field_list(){
    	TipoErroSintatico tes = identifier_list();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			tes = type();
    			if(tes == TipoErroSintatico.CORRETO) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 55) { // simbolo ;
    					return field_list();
    				}
    				GerandoTokens.voltaToken();
    				return TipoErroSintatico.CORRETO;
    			}
    			return tes;// erro funcao type()
    		}
    		return erro(token.getNumero_linha(),TipoErroSintatico.COLON);// erro falta de :
    	} else {
    		return TipoErroSintatico.CORRETO;
    	}
    }
    public static TipoErroSintatico var_declaration_part(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 36) { // palavra Reservada VAR
    		TipoErroSintatico tes = var_declaration();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				tes = var_declaration();
    				while(tes == TipoErroSintatico.CORRETO) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return erro(token.getNumero_linha(), TipoErroSintatico.SEMI_COLON); // erro falta do simbolo ;
    					tes = var_declaration();
    				}
    				if(tes == TipoErroSintatico.IDENTIFIER)
    					return TipoErroSintatico.CORRETO;
    				else 
    					return tes;
    			}
    			return erro(token.getNumero_linha(), TipoErroSintatico.SEMI_COLON);// erro falta do simbolo ;
    		}
    		return tes;// erro funcao var_declaration()
    	}
    	GerandoTokens.voltaToken(); // falta da palavra VAR
        return erro(token.getNumero_linha(), TipoErroSintatico.VAR);
    }
    public static TipoErroSintatico var_declaration(){
    	TipoErroSintatico tes = identifier_list();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			return type();
    		}
    		return erro(token.getNumero_linha(), TipoErroSintatico.COLON); // erro falta do simbolo :
    	}
    	return tes; // erro funcao identifier_list()
    }
    public static TipoErroSintatico identifier_list(){
    	tes = identifier();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		while(token.getPadrao() == 56) { // simbolo ,
    			tes = identifier();
    			if(tes != TipoErroSintatico.CORRETO)  
    				return tes; // erro falta funcao IDENTIFIER
    			token = GerandoTokens.getNextToken();
    		}
    		GerandoTokens.voltaToken();
    		return TipoErroSintatico.CORRETO;
    	}
    	return tes;
    }
    /*public static boolean subroutine_declaration_part(){
    	int num_token = GerandoTokens.getNumero_token();
    	if(procedure_declaration() || function_declaration()) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 55) // simbolo ;
    			return true;
    		// erro falta do simbolo ;
    	}
    	GerandoTokens.setNumero_token(num_token); // erro nas duas funcoes
        return false;
    }
    public static boolean procedure_declaration(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 25) { // palavra Reservada PROCEDURE
    		if(identifier()) {
    			formal_parameters();
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				return block();
    			}
    			// erro falta simbolo ;
    		}
    		// erro falta funcao IDENTIFIER
    	}
    	GerandoTokens.setNumero_token(num_token); // erro falta palavra PROCEDURE
    	return false;
    }
    public static boolean function_declaration(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 14) {// palavra Reservada FUNCTION
    		if(identifier()) {
    			formal_parameters();
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 54) { // simbolo :
    				if(identifier()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 55) { // simbolo ;
    						return block();
    					}
    					// erro, falta do simbolo ;
    				}
    				// erro falta funcao IDENTIFIER
    			}
    			// erro do simbolo :
    		}
    		// erro, falta funcao IDENTIFIER
    	}
    	GerandoTokens.setNumero_token(num_token); // erro, falta da palavra FUNCTION
    	return false;
    }
    public static boolean formal_parameters(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		if(param_section()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				if(!param_section())
    					return false; // erro, function param_section()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 60) {
    				return true;
    			}
    			// erro falta simbolo )
    		}
    		// erro, funcao param_section()
    	}
    	GerandoTokens.setNumero_token(num_token); // erro, falta do simbolo (
    	return false;
    }
    public static boolean param_section(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 14) { // palavra Reservada FUNCTION
    		if(identifier_list()) {
    			token = GerandoTokens.getNextToken();
				if(token.getPadrao() == 54) { // simbolo :
					return identifier();
				}
				// erro falta de simbolo :
    		}
    		// erro funcao identifier_list()
    		return false;
    	} else if(token.getPadrao() == 25) { // palavra Reservada PROCEDURE
    		return identifier_list();
    	} else if(token.getPadrao() != 36) { // palavra Reservada VAR
    		GerandoTokens.voltaToken();
    	}
    	if(identifier_list()) {
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			return identifier();
    		}
    		// erro falta de :
    	}
    	return false; // erro falta de identifier_lis()
    }*/
    public static TipoErroSintatico compound_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 3) { // palavra Reservada BEGIN
    		//if(labeled_statement()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				//if(!labeled_statement())
    				//	return false; // falta de Labeled_statement()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 12) { // palavra Reservada END
    				return TipoErroSintatico.CORRETO;
    			} else
    				return TipoErroSintatico.END;
    			// Erro falta de palavra END
    		//}
    		// erro funcao Labeled_statement()
    	}
        return TipoErroSintatico.BEGIN;// falta de palavra BEGIN
    }
    /*public static boolean labeled_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 41) { // padrao NUMBER
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) // simbolo :
    			token = GerandoTokens.getNextToken();
    		else {
    			GerandoTokens.voltaToken();
    			GerandoTokens.voltaToken();
    		}
    	} else
    		GerandoTokens.voltaToken();
    	return statement();
    }
    public static boolean statement(){
    	Token token = GerandoTokens.getNextToken();
    	GerandoTokens.voltaToken();
    	if(token.getPadrao() == 16) // palavra Reservada IF
    		return if_statement();
    	else if(token.getPadrao() == 37) // palavra Reservada WHILE
    		return while_statement();
    	else if(token.getPadrao() == 29) // palavra Reservada REPEAT
    		return repeat_statement();
    	else if(token.getPadrao() == 13) // palavra Reservada FOR
    		return for_statement();
    	else if(token.getPadrao() == 38) // palavra Rservada WITH
    		return with_statement();
    	else if(token.getPadrao() == 15) // palavra Reservada GOTO
    		return goto_statement();
    	else if(token.getPadrao() == 3) // pakavra Rservada BEGIN
    		return compound_statement();
    	else {
    		if(assing_statement())
    			return true;
    		else if(procedure_call())
    			return true;
    	}
    	return true;
    }
    public static boolean assing_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	if(identifier()) { // padrao IDENTIFIER
    		infipo();
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 51) { // simbolo :=
    			return expr();
    		}
    		// erro falta simbolo :=
    	}
    	GerandoTokens.setNumero_token(num_token); // erro, falta padrao IDENTIFIER
    	return false;
    }
    public static boolean procedure_call(){
    	int num_token = GerandoTokens.getNumero_token();
    	if(identifier()) { // padrao IDENTIFIER
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 59) { // simbolo (
    			if(expr_list()) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 60) { // simbolo )
    					return true;
    				}
    				// erro falta de simbolo )
    			}
    			// erro expr_list()
    		} else {
    			return true;
    		}
    	}
    	GerandoTokens.setNumero_token(num_token);
    	return false; // erro, falta funcao IDENTIFIER
    }
    public static boolean if_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 16) { // palavra Reservada IF
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 32) { // palavra Reservada THEN
    				if(statement()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 11) { // palavra Reservada ELSE
    						return statement();
    					}else {
    						GerandoTokens.voltaToken();
    						return true;
    					}
    				}
    				// erro funcao STATEMENT()
    			}
    			// erro falta palavra THEN 
    		}
    		// erro, funcao expr()
    	}
    	GerandoTokens.setNumero_token(num_token); // ERRO, falta do IF
    	return false;
    }
    public static boolean while_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 37) { // palavra Reservada WHILE
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 9) { // palavra Reservada DO
    				return statement();
    			}
    			// erro, falta palavra DO
    		}
    		// erro funcao expr()
    	}
    	GerandoTokens.setNumero_token(num_token); // falta do palavra WHILE
    	return false;
    }
    public static boolean repeat_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 29) { // palavra Reservada REPEAT
    		if(statement()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				if(!statement())
    					return false; // erro funcao statement()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 35) { // palavra Reservada UNTIL
    				return expr();
    			}
    			// erro falta palavra UNTIL
    		}
    		// erro funcao statement()
    	}
    	GerandoTokens.setNumero_token(num_token); // erro, falta palavra REPEAT
    	return false;
    }
    public static boolean for_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 13) { // palavra Reservada FOR
    		if(identifier()) {
    			if(infipo()) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 51) { // simbolo :=
    					if(expr()) {
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 33 || token.getPadrao() == 10) { // palavra Reservada TO ou DOWNTO
    							if(expr()) {
    								token = GerandoTokens.getNextToken();
    								if(token.getPadrao() == 9) { // palavra Reservada DO
    									return statement();
    								}
    								// erro falta palavra DO
    							}
    							// erro funcao expr()
    						}
    						// erro falta palavra TO ou DOWNTO
    					}
    					//erro funcao expr()
    				}
    				// erro falta simbolo :=
    			}
    			// erro funcao infipo()
    		}
    		// erro falta funcao IDENTIFIER
    	}
    	GerandoTokens.setNumero_token(num_token); // erro falta palavra FOR
    	return false;
    }
    public static boolean with_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 38) { // palavra Reservada WITH
    		if(identifier()) {
    			if(infipo()) {
    				token = GerandoTokens.getNextToken();
    				while(token.getPadrao() == 56) { // simbolo ,
    					if(!identifier())
    						return false; // erro falta funcao IDENTIFIER
    					else if(!infipo())
    						return false; // erro funcao infipo()
    					token = GerandoTokens.getNextToken();
    				}
    				if(token.getPadrao() == 9) { // palavra Reservada DO
    					return statement();
    				}
    				// erro falta plavra DO
    			}
    			// erro funcao infipo()
    		}
    		// erro falta funcao IDENTIFIER
    	}
    	GerandoTokens.setNumero_token(num_token); // erro falta palavra WITH
    	return false;
    }
    public static boolean case_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 5) { // palavra Rservada CASE
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 22) { // palavra Reservada OF
    				if(_case()) {
    					token = GerandoTokens.getNextToken();
    					while(token.getPadrao() == 55) { // simbolo ;
    						if(!_case()) {
    							return false; // erro funcao case()
    						}
    						token = GerandoTokens.getNextToken();
    					}
    					if(token.getPadrao() == 12) { // palavra Rservada END
    						return true;
    					}
    					// erro falta palavra END
    				}
    				// erro funcao case()
    			}
    			// erro falta palavra OF
    		}
    		// erro funcao expr()
    	}
    	GerandoTokens.setNumero_token(num_token); // erro falta palavra CASE
    	return false;
    }
    public static boolean _case(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 41) {
    	}else {
    		GerandoTokens.voltaToken();
    		if(!identifier()) {
    			return false; // falta do padrao NUMBER ou funcao IDENTIFIER
    		}
    	}
    	token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 54){ // simbolo :
    		return statement();
    	}
    	// erro falata simbolo :
    	GerandoTokens.voltaToken(); // erro falta padrao NUMBER ou IDENTIFIER
    	return false;
    }
    public static boolean goto_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 15) { // palavra Reservada GOTO
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 41) { // padrao NUMBER
    			return true;
    		}
    		// erro falta padrao NUMBER
    	}
    	GerandoTokens.setNumero_token(num_token); // erro falta palavra GOTO
    	return false;
    }
    public static boolean infipo(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 57) { // simbolo [
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 56) { // simbolo ,
    				if(!expr())
    					return false;
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 58) { // simbolo ]
    				return infipo();
    			}
    			// erro falta simbolo ]
    		}
    		// erro funcao expr()
    	} else if(token.getPadrao() == 53) { // simbolo .
    		if(identifier()) {
    			return infipo();
    		}
    		// erro falta padrao IDENTIFIER
    	} else if(token.getPadrao() == 71) { // simbolo ^
    		return infipo();
    	} else {
	    	GerandoTokens.voltaToken();
	    	return true;
    	}
    	GerandoTokens.setNumero_token(num_token);
    	return false;
    }
    public static boolean expr_list(){
    	if(expr()) {
    		Token token = GerandoTokens.getNextToken();
			while(token.getPadrao() == 56) { // simbolo ,
				if(!expr())
					return false; // erro funcao expr()
				token = GerandoTokens.getNextToken();
			}
			GerandoTokens.voltaToken();
			return true;
    	}
    	return false; // erro funcao expr()
    }
    public static boolean expr(){
    	if(simple_expr()) {
    		if(relop()) {
    			return simple_expr();
    		} else {
    			GerandoTokens.voltaToken();
    			return true;
    		}
    	}
    	return false; // erro funcao simple_expr()
    }
    public static boolean relop(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 61) // simbolo =
    		return true;
    	else if(token.getPadrao() == 66) // simbolo <
    		return true;
    	else if(token.getPadrao() == 65) // simbolo >
    		return true;
    	else if(token.getPadrao() == 64) // simbolo <>
    		return true;
    	else if(token.getPadrao() == 63) // simbolo >=
    		return true;
    	else if(token.getPadrao() == 62) // simbolo <=
    		return true;
    	else if(token.getPadrao() == 39) // plavra Reservada IN
    		return true;
    	else {
    		GerandoTokens.voltaToken();
    		return false; // erro falta de operadores relacionais
    	}
    }
    public static boolean simple_expr(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 70 || token.getPadrao() == 68) { //simbolo + ou -
    		token = GerandoTokens.getNextToken();
    	}
    	GerandoTokens.voltaToken();
    	if(term()) {
	    	while(addop()) {
	    		if(!term())
	    			return false; // erro funcao term()
	    	}
	    	return true;
    	}
    	GerandoTokens.setNumero_token(num_token); // erro funcao term()
    	return false;
    }
    public static boolean addop(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 70) //simbolo +
    		return true;
    	else if(token.getPadrao() == 68) // simbolo -
    		return true;
    	else if(token.getPadrao() == 23) // palavra Reservada OR
    		return true;
    	else 
    		GerandoTokens.voltaToken();
    	return false; // erro falta de operadores
    }
    public static boolean term(){
    	if(factor()) {
    		while(mulop()) {
    			if(!factor())
    				return false; // erro funcao factor()
    		}
    		return true;
    	}
    	return false; // erro funcao factor();
    }
    public static boolean mulop(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 69) // simbolo *
    		return true;
    	else if(token.getPadrao() == 67) // simbolo /
    		return true;
    	else if(token.getPadrao() == 8) // palavra Reservada DIV
    		return true;
    	else if(token.getPadrao() == 19) // palavra Reservada MOD
    		return true;
    	else if(token.getPadrao() == 1) // palavra Reservada AND
    		return true;
    	else
    		GerandoTokens.voltaToken();
    	return false; // erro falta de opadores de divisao
    }
    public static boolean factor(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 41) // padrao NUMBER
    		return true;
    	else if(token.getPadrao() == 44) // padrao STRING
    		return true;
    	else if(token.getPadrao() == 59) { // simbolo (
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 60) // simbolo )
    				return true;
    			// erro falta simbolo )
    		}
    		// erro expr()
    	} else if(token.getPadrao() == 21) { // palavra Reservada NOT
    		return factor();
    	} else {
    		GerandoTokens.voltaToken();
    		if(identifier()) {
        		token = GerandoTokens.getNextToken();
        		if(token.getPadrao() == 59) { // simbolo (
        			if(expr_list()) {
        				token = GerandoTokens.getNextToken();
        				if(token.getPadrao() == 60) // simbolo )
        					return true;
        				// erro falta so simbolo )
        			}
        			// erro funcao expr_list()
        			return false;
        		}
        		if(infipo())
        			return true;
        		// erro funcao infipo()
    		}
    		// erro falta de identifier
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }*/
    public static TipoErroSintatico identifier() {
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) // padrao IDENTIFIER
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 4) // palavra Reservada BOOLEAN 
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 6) // palavra Reservada CHAR
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 17) // palavra Reservada INTEGER
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 20) // palavra Reservada NIL
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 24) // palavra Reservada POINTER
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 27) // palavra Reservada REAL
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 31) // palavra Reservada STRING
    		return TipoErroSintatico.CORRETO;
    	else 
    		GerandoTokens.voltaToken();
    	return erro(token.getNumero_linha(),TipoErroSintatico.IDENTIFIER);
    }
    private static TipoErroSintatico erro(int num_linha, TipoErroSintatico tes) {
    	linha = num_linha;
    	return tes;
    }
}