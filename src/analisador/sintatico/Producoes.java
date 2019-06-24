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
	public static String nomeToken = "";
	
    public static TipoErroSintatico program(){
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 26){ // palavra reservada PROGRAM
            TipoErroSintatico tes = identifier(26,true,true); 
        	if(tes == TipoErroSintatico.CORRETO){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 59){ // simbolo (
                	tes = identifier_list(0,false,true);
                    if(tes == TipoErroSintatico.CORRETO){
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() != 60){
                            return erro(token,TipoErroSintatico.RP);
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
                        return erro(token,TipoErroSintatico.DOT);
                    }
                	return tes;
                }
                return erro(token,TipoErroSintatico.SEMI_COLON);
            }
            return tes;
        }
        return erro(token,TipoErroSintatico.PROGRAM);
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
        tes = subroutine_declaration_part();
        if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.FUNCTION)
        	return tes;
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
                        return erro(token,TipoErroSintatico.NUMBER); // falta de NUMBER
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55){ // simbolo ;
                    return TipoErroSintatico.CORRETO;
                } else
                	return erro(token,TipoErroSintatico.SEMI_COLON); // Erro falta de ;
            }else
            	return erro(token,TipoErroSintatico.NUMBER); // Erro falta de NUM
        }
        GerandoTokens.voltaToken();
        return erro(token,TipoErroSintatico.LABEL); // erro falta palavra LABEL
    }
    public static TipoErroSintatico const_declaration_part(){
    	int num_tok = GerandoTokens.getNumero_token();
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 7){// palavra reservada CONST
        	TipoErroSintatico tes = const_definition();
            if(tes == TipoErroSintatico.CORRETO){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 55){ // simbolo ;
                	tes = identifier(0,false,false);
                	while(tes == TipoErroSintatico.CORRETO){
                		GerandoTokens.voltaToken();
                		tes = const_definition();
                		if(tes != TipoErroSintatico.CORRETO)
                			return tes;
                		token = GerandoTokens.getNextToken();
                        if(token.getPadrao() != 55) // simbolo ;
                            return erro(token,TipoErroSintatico.SEMI_COLON); // falta de ;
                        tes = identifier(0,false,false);
                    }
                    return TipoErroSintatico.CORRETO;
                }
                return erro(token, TipoErroSintatico.SEMI_COLON); // erro falta de ;
            }
            return tes;// erro falta const_definition()
        }
        GerandoTokens.setNumero_token(num_tok);
        return erro(token,TipoErroSintatico.CONST); // erro, falta de palavra CONST
    }
    public static TipoErroSintatico const_definition(){
    	TipoErroSintatico tes = identifier(7,true,false);
    	if(tes == TipoErroSintatico.CORRETO) {
            Token token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 61) { // simbolo =
                return _const();
            }
            return erro(token, TipoErroSintatico.EQUAL); // erro falta do simbolo =
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
    				tes = identifier(0,false,false);
    				while(tes == TipoErroSintatico.CORRETO) {
    					GerandoTokens.voltaToken();
    					tes = type_definition();
    					if(tes != TipoErroSintatico.CORRETO)
    						return tes;
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return erro(token,TipoErroSintatico.SEMI_COLON); // erro falta de ;
    					tes = identifier(0,false,false);
    				}
    				return TipoErroSintatico.CORRETO;
    			}
                return erro(token,TipoErroSintatico.SEMI_COLON);
    		}
            return tes;
    	}
    	GerandoTokens.voltaToken(); // falta de palavra TYPE
        return erro(token,TipoErroSintatico.TYPE);
    }
    public static TipoErroSintatico type_definition(){
    	TipoErroSintatico tes = identifier(34,true,false);
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 61) { // simbolo =
    			return type();
    		}
    		return erro(token,TipoErroSintatico.EQUAL); // erro falta de =
    	}
    	return tes; // erro funcao Identifier() 
    }
    public static TipoErroSintatico type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 71) { // simbolo ^
    		return identifier(0,false,true);
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
                        return erro(token,TipoErroSintatico.OF);// erro falta de OF
    				}
                    return erro(token, TipoErroSintatico.RB);// erro falta de ]
    			}
    			return tes; // erro falta de simple_type()
    		}
            return erro(token, TipoErroSintatico.LB);// erro falta de [
    	} else if(token.getPadrao() == 30) { // palavra reservada SET
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 22) { // palavra resevada OF
    			return simple_type();
    		}
    		return erro(token, TipoErroSintatico.OF); // falta da palavra reservada OF
    		
    	} else if(token.getPadrao() == 28) { // palavra Reservada RECORD
    		TipoErroSintatico tes = field_list();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 12) // palavra Reservada END
    				return TipoErroSintatico.CORRETO;
                return erro(token, TipoErroSintatico.END);// erro falta de END
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
    		TipoErroSintatico tes = identifier(0,false,true);
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 56) { // simbolo ,
    				tes = identifier(0,false,true);
    				if(tes == TipoErroSintatico.CORRETO)
    					return tes; // ERRO falta funcao IDENTIFIER
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 60) { // simbolo )
    				return TipoErroSintatico.CORRETO;
    			}
    			return erro(token, TipoErroSintatico.RP); // erro falta do simbolo )
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
    			// erro falta de ..
    		}
    		GerandoTokens.setNumero_token(tok);
    		return identifier(0,false,true);
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
    		return identifier(0,false,true);
    	}
    }
    public static TipoErroSintatico field_list(){
    	TipoErroSintatico tes = identifier_list(0,false,true);
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
    		return erro(token,TipoErroSintatico.COLON);// erro falta de :
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
    				tes = identifier(0,false,false);
    				while(tes == TipoErroSintatico.CORRETO) {
    					GerandoTokens.voltaToken();
    					tes = var_declaration();
    					if(tes != TipoErroSintatico.CORRETO)
    						return tes;
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return erro(token, TipoErroSintatico.SEMI_COLON); // erro falta do simbolo ;
    					tes = identifier(0,false,false);
    				}
    				return TipoErroSintatico.CORRETO;
    			}
    			return erro(token, TipoErroSintatico.SEMI_COLON);// erro falta do simbolo ;
    		}
    		return tes;// erro funcao var_declaration()
    	}
    	GerandoTokens.voltaToken(); // falta da palavra VAR
        return erro(token, TipoErroSintatico.VAR);
    }
    public static TipoErroSintatico var_declaration(){
    	TipoErroSintatico tes = identifier_list(36,true,false);
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			return type();
    		}
    		return erro(token, TipoErroSintatico.COLON); // erro falta do simbolo :
    	}
    	return tes; // erro funcao identifier_list()
    }
    public static TipoErroSintatico identifier_list(int tipo, boolean criado, boolean utilizado){
    	TipoErroSintatico tes = identifier(tipo,criado,utilizado);
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		while(token.getPadrao() == 56) { // simbolo ,
    			tes = identifier(tipo,criado,utilizado);
    			if(tes != TipoErroSintatico.CORRETO)  
    				return tes; // erro falta funcao IDENTIFIER
    			token = GerandoTokens.getNextToken();
    		}
    		GerandoTokens.voltaToken();
    		return TipoErroSintatico.CORRETO;
    	}
    	return tes;
    }
    public static TipoErroSintatico subroutine_declaration_part(){
    	int num_token = GerandoTokens.getNumero_token();
    	TipoErroSintatico tes = procedure_declaration();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 55) // simbolo ;
    			return TipoErroSintatico.CORRETO;
    		return erro(token, TipoErroSintatico.SEMI_COLON);// erro falta do simbolo ;
    	} else if(tes != TipoErroSintatico.PROCEDURE)
    		return tes;
    	tes = function_declaration();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 55) // simbolo ;
    			return TipoErroSintatico.CORRETO;
    		return erro(token, TipoErroSintatico.SEMI_COLON);// erro falta do simbolo ;
    	} else if(tes != TipoErroSintatico.FUNCTION)
    		return tes;
    	GerandoTokens.setNumero_token(num_token); // erro nas duas funcoes
        return tes;
    }
    public static TipoErroSintatico procedure_declaration(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 25) { // palavra Reservada PROCEDURE
    		TipoErroSintatico tes = identifier(25,true,false);
    		if(tes == TipoErroSintatico.CORRETO) {
    			tes = formal_parameters();
    			if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.LP)
    				return tes;
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				return block();
    			}
    			return erro(token, TipoErroSintatico.SEMI_COLON);// erro falta simbolo ;
    		}
    		return tes;// erro falta funcao IDENTIFIER
    	}
    	GerandoTokens.voltaToken(); // erro falta palavra PROCEDURE
    	return erro(token,TipoErroSintatico.PROCEDURE);
    }
    public static TipoErroSintatico function_declaration(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 14) {// palavra Reservada FUNCTION
    		TipoErroSintatico tes = identifier(14,true,false);
    		if(tes == TipoErroSintatico.CORRETO) {
    			tes = formal_parameters();
    			if(tes != TipoErroSintatico.CORRETO && tes != TipoErroSintatico.LP)
    				return tes;
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 54) { // simbolo :
    				tes = identifier(0,false,true);
    				if(tes == TipoErroSintatico.CORRETO) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 55) { // simbolo ;
    						return block();
    					}
    					return erro(token, TipoErroSintatico.SEMI_COLON);// erro, falta do simbolo ;
    				}
    				return tes;// erro falta funcao IDENTIFIER
    			}
    			return erro(token, TipoErroSintatico.COLON);// erro do simbolo :
    		}
    		return tes;// erro, falta funcao IDENTIFIER
    	}
    	GerandoTokens.voltaToken(); // erro, falta da palavra FUNCTION
    	return erro(token, TipoErroSintatico.FUNCTION);
    }
    public static TipoErroSintatico formal_parameters(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		TipoErroSintatico tes = param_section();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				tes = param_section();
    				if(tes != TipoErroSintatico.CORRETO)
    					return tes; // erro, function param_section()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 60) { // simbolo )
    				return TipoErroSintatico.CORRETO;
    			}
    			return erro(token, TipoErroSintatico.RP);// erro falta simbolo )
    		}
    		return tes;// erro, funcao param_section()
    	}
    	GerandoTokens.voltaToken(); // erro, falta do simbolo (
    	return erro(token,TipoErroSintatico.LP);
    }
    public static TipoErroSintatico param_section(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 14) { // palavra Reservada FUNCTION
    		TipoErroSintatico tes = identifier_list(14,true,true);
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
				if(token.getPadrao() == 54) { // simbolo :
					return identifier(0,false,true);
				}
				return erro(token, TipoErroSintatico.COLON);// erro falta de simbolo :
    		}
    		return tes;// erro funcao identifier_list()
    	} else if(token.getPadrao() == 25) { // palavra Reservada PROCEDURE
    		return identifier_list(25,true,true);
    	} else if(token.getPadrao() != 36) { // palavra Reservada VAR
    		GerandoTokens.voltaToken();
    	}
    	TipoErroSintatico tes = identifier_list(36,true,true);
    	if(tes == TipoErroSintatico.CORRETO) {
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			return identifier(0,false,true);
    		}
    		return erro(token, TipoErroSintatico.COLON); //erro, falta do simbolo :
    	}
    	return tes; // erro falta de identifier_lis()
    }
    public static TipoErroSintatico compound_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 3) { // palavra Reservada BEGIN
    		TipoErroSintatico tes = labeled_statement();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				tes = labeled_statement();
    				if(tes != TipoErroSintatico.CORRETO)
    					return tes; // falta de Labeled_statement()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 12) // palavra Reservada END
    				return TipoErroSintatico.CORRETO;
    			return erro(token,TipoErroSintatico.END); // Erro falta de palavra END
    		}
    		return tes;// erro funcao Labeled_statement()
    	}
        return erro(token, TipoErroSintatico.BEGIN);// falta de palavra BEGIN
    }
    public static TipoErroSintatico labeled_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 41) { // padrao NUMBER
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() != 54) { // simbolo :
    			GerandoTokens.voltaToken();
    			GerandoTokens.voltaToken();
    		}
    	} else
    		GerandoTokens.voltaToken();
    	return statement();
    }
    public static TipoErroSintatico statement(){
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
    	else if(token.getPadrao() == 5) // palavra Reservada CASE
    		return case_statement();
    	else if(token.getPadrao() == 3) // pakavra Rservada BEGIN
    		return compound_statement();
    	else {
    		TipoErroSintatico tes = identifier(0,false,false);
    		if(tes == TipoErroSintatico.CORRETO) {
    			GerandoTokens.voltaToken();
        		tes = assing_statement();
	    		if(tes == TipoErroSintatico.CORRETO)
	    			return TipoErroSintatico.CORRETO;
	    		else if(tes != TipoErroSintatico.ASSING_OP)
		    		return tes;
	    		tes = procedure_call();
	    		if(tes == TipoErroSintatico.CORRETO)
	    			return tes;
    		}
    	}
    	return TipoErroSintatico.CORRETO;
    }
    public static TipoErroSintatico assing_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	TipoErroSintatico tes = identifier(0,false,true);
    	if(tes == TipoErroSintatico.CORRETO) { // padrao IDENTIFIER
    		tes = infipo();
    		if(tes != TipoErroSintatico.CORRETO)
    			return tes;
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 51) { // simbolo :=
    			return expr();
    		}
    		GerandoTokens.setNumero_token(num_token);
    		return erro(token, TipoErroSintatico.ASSING_OP);// erro falta simbolo :=
    	}
    	return tes; // erro, falta padrao IDENTIFIER
    }
    public static TipoErroSintatico procedure_call(){
    	TipoErroSintatico tes = identifier(0,false,true);
    	if(tes == TipoErroSintatico.CORRETO) { // padrao IDENTIFIER
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 59) { // simbolo (
    			tes = expr_list();
    			if(tes == TipoErroSintatico.CORRETO) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 60) { // simbolo )
    					return TipoErroSintatico.CORRETO;
    				}
    				return erro(token, TipoErroSintatico.RP);// erro falta de simbolo )
    			}
    			return tes;// erro expr_list()
    		} else {
    			GerandoTokens.voltaToken();
    			return TipoErroSintatico.CORRETO;
    		}
    	}
    	return tes; // erro, falta funcao IDENTIFIER
    }
    public static TipoErroSintatico if_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 16) { // palavra Reservada IF
    		TipoErroSintatico tes = expr();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 32) { // palavra Reservada THEN
    				tes = statement();
    				if(tes == TipoErroSintatico.CORRETO) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 11) { // palavra Reservada ELSE
    						return statement();
    					}else {
    						GerandoTokens.voltaToken();
    						return TipoErroSintatico.CORRETO;
    					}
    				}
    				return tes;// erro funcao STATEMENT()
    			}
    			return erro(token,TipoErroSintatico.THEN);// erro falta palavra THEN 
    		}
    		return tes;// erro, funcao expr()
    	}
    	GerandoTokens.setNumero_token(num_token); // ERRO, falta do IF
    	return erro(token, TipoErroSintatico.IF);
    }
    public static TipoErroSintatico while_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 37) { // palavra Reservada WHILE
    		TipoErroSintatico tes = expr();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 9) { // palavra Reservada DO
    				return statement();
    			}
    			return erro(token, TipoErroSintatico.DO);// erro, falta palavra DO
    		}
    		return tes;// erro funcao expr()
    	}
    	GerandoTokens.setNumero_token(num_token); // falta do palavra WHILE
    	return erro(token, TipoErroSintatico.WHILE);
    }
    public static TipoErroSintatico repeat_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 29) { // palavra Reservada REPEAT
    		TipoErroSintatico tes = statement();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				tes = statement();
    				if(tes != TipoErroSintatico.CORRETO)
    					return tes; // erro funcao statement()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 35) { // palavra Reservada UNTIL
    				return expr();
    			}
    			return erro(token,TipoErroSintatico.UNTIL);// erro falta palavra UNTIL
    		}
    		return tes;// erro funcao statement()
    	}
    	GerandoTokens.setNumero_token(num_token); // erro, falta palavra REPEAT
    	return erro(token,TipoErroSintatico.REPEAT);
    }
    public static TipoErroSintatico for_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 13) { // palavra Reservada FOR
    		TipoErroSintatico tes = identifier(0,false,true);
    		if(tes == TipoErroSintatico.CORRETO) {
    			tes = infipo();
    			if(tes == TipoErroSintatico.CORRETO) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 51) { // simbolo :=
    					tes = expr();
    					if(tes == TipoErroSintatico.CORRETO) {
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 33 || token.getPadrao() == 10) { // palavra Reservada TO ou DOWNTO
    							tes = expr();
    							if(tes == TipoErroSintatico.CORRETO) {
    								token = GerandoTokens.getNextToken();
    								if(token.getPadrao() == 9) { // palavra Reservada DO
    									return statement();
    								}
    								return erro(token, TipoErroSintatico.DO);// erro falta palavra DO
    							}
    							return tes;// erro funcao expr()
    						}
    						return erro(token, TipoErroSintatico.DOWNTO_TO);// erro falta palavra TO ou DOWNTO
    					}
    					return tes;//erro funcao expr()
    				}
    				return erro(token,TipoErroSintatico.ASSING_OP);// erro falta simbolo :=
    			}
    			return tes;// erro funcao infipo()
    		}
    		return tes;// erro falta funcao IDENTIFIER
    	}
    	GerandoTokens.setNumero_token(num_token); // erro falta palavra FOR
    	return erro(token, TipoErroSintatico.FOR);
    }
    public static TipoErroSintatico with_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 38) { // palavra Reservada WITH
    		TipoErroSintatico tes = identifier(0,false,true);
    		if(tes == TipoErroSintatico.CORRETO) {
    			tes = infipo();
    			if(tes == TipoErroSintatico.CORRETO) {
    				token = GerandoTokens.getNextToken();
    				while(token.getPadrao() == 56) { // simbolo ,
    					tes = identifier(0,false,true);
    					if(tes != TipoErroSintatico.CORRETO)
    						return tes; // erro falta funcao IDENTIFIER
    					tes = infipo();
    					if(tes != TipoErroSintatico.CORRETO)
    						return tes; // erro funcao infipo()
    					token = GerandoTokens.getNextToken();
    				}
    				if(token.getPadrao() == 9) { // palavra Reservada DO
    					return statement();
    				}
    				return erro(token, TipoErroSintatico.DO);// erro falta plavra DO
    			}
    			return tes;// erro funcao infipo()
    		}
    		return tes;// erro falta funcao IDENTIFIER
    	}
    	GerandoTokens.voltaToken(); // erro falta palavra WITH
    	return erro(token, TipoErroSintatico.WITH);
    }
    public static TipoErroSintatico case_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 5) { // palavra Rservada CASE
    		TipoErroSintatico tes = expr();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 22) { // palavra Reservada OF
    				tes = _case();
    				if(tes == TipoErroSintatico.CORRETO) {
    					token = GerandoTokens.getNextToken();
    					while(token.getPadrao() == 55) { // simbolo ;
    						tes = _case();
    						if(tes != TipoErroSintatico.CORRETO) {
    							return tes; // erro funcao case()
    						}
    						token = GerandoTokens.getNextToken();
    					}
    					if(token.getPadrao() == 12) { // palavra Reservada END
    						return TipoErroSintatico.CORRETO;
    					}
    					return erro(token,TipoErroSintatico.END);// erro falta palavra END
    				}
    				return tes;// erro funcao case()
    			}
    			return erro(token, TipoErroSintatico.OF);// erro falta palavra OF
    		}
    		return tes;// erro funcao expr()
    	}
    	GerandoTokens.voltaToken(); // erro falta palavra CASE
    	return erro(token, TipoErroSintatico.CASE);
    }
    public static TipoErroSintatico _case(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 41) { // padrao NUMBER
    	}else {
    		GerandoTokens.voltaToken();
    		TipoErroSintatico tes = identifier(0,false,true);
    		if(tes != TipoErroSintatico.CORRETO) {
    			return TipoErroSintatico.NUMBER_ID; // falta do padrao NUMBER ou funcao IDENTIFIER
    		}
    	}
    	token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 54){ // simbolo :
    		return statement();
    	}
    	return erro(token, TipoErroSintatico.COLON);// erro falata simbolo :
    }
    public static TipoErroSintatico goto_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 15) { // palavra Reservada GOTO
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 41) { // padrao NUMBER
    			return TipoErroSintatico.CORRETO;
    		}
    		return erro(token, TipoErroSintatico.NUMBER);// erro falta padrao NUMBER
    	}
    	GerandoTokens.voltaToken(); // erro falta palavra GOTO
    	return erro(token,TipoErroSintatico.GOTO);
    }
    public static TipoErroSintatico infipo(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 57) { // simbolo [
    		TipoErroSintatico tes = expr();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 56) { // simbolo ,
    				tes = expr();
    				if(tes != TipoErroSintatico.CORRETO)
    					return tes;
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 58) { // simbolo ]
    				return infipo();
    			}
    			return erro(token, TipoErroSintatico.RB);// erro falta simbolo ]
    		}
    		return tes; // erro funcao expr()
    	} else if(token.getPadrao() == 53) { // simbolo .
    		TipoErroSintatico tes = identifier(0,false,true);
    		if(tes == TipoErroSintatico.CORRETO) {
    			return infipo();
    		}
    		return tes; // erro falta padrao IDENTIFIER
    	} else if(token.getPadrao() == 71) { // simbolo ^
    		return infipo();
    	} else {
	    	GerandoTokens.voltaToken();
	    	return TipoErroSintatico.CORRETO;
    	}
    }
    public static TipoErroSintatico expr_list(){
    	TipoErroSintatico tes = expr();
    	if(tes == TipoErroSintatico.CORRETO) {
    		Token token = GerandoTokens.getNextToken();
			while(token.getPadrao() == 56) { // simbolo ,
				tes = expr();
				if(tes != TipoErroSintatico.CORRETO)
					return tes; // erro funcao expr()
				token = GerandoTokens.getNextToken();
			}
			GerandoTokens.voltaToken();
			return TipoErroSintatico.CORRETO;
    	}
    	return tes; // erro funcao expr()
    }
    public static TipoErroSintatico expr(){
    	TipoErroSintatico tes = simple_expr();
    	if(tes == TipoErroSintatico.CORRETO) {
    		tes = relop();
    		if(tes == TipoErroSintatico.CORRETO) {
    			return simple_expr();
    		} else {
    			return TipoErroSintatico.CORRETO;
    		}
    	}
    	return tes; // erro funcao simple_expr()
    }
    public static TipoErroSintatico relop(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 61) // simbolo =
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 66) // simbolo <
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 65) // simbolo >
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 64) // simbolo <>
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 63) // simbolo >=
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 62) // simbolo <=
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 39) // plavra Reservada IN
    		return TipoErroSintatico.CORRETO;
    	else {
    		GerandoTokens.voltaToken();
    		return erro(token, TipoErroSintatico.RELOP); // erro falta de operadores relacionais
    	}
    }
    public static TipoErroSintatico simple_expr(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 70 || token.getPadrao() == 68) { //simbolo + ou -
    		token = GerandoTokens.getNextToken();
    	}
    	GerandoTokens.voltaToken();
    	TipoErroSintatico tes = term();
    	if(tes == TipoErroSintatico.CORRETO) {
	    	tes = addop();
    		while(tes == TipoErroSintatico.CORRETO) {
	    		tes = term();
    			if(tes != TipoErroSintatico.CORRETO)
	    			return tes; // erro funcao term()
    			tes = addop();
	    	}
	    	return TipoErroSintatico.CORRETO;
    	}
    	GerandoTokens.setNumero_token(num_token); // erro funcao term()
    	return tes;
    }
    public static TipoErroSintatico addop(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 70) //simbolo +
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 68) // simbolo -
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 23) // palavra Reservada OR
    		return TipoErroSintatico.CORRETO;
    	else 
    		GerandoTokens.voltaToken();
    	return erro(token, TipoErroSintatico.ADDOP); // erro falta de operadores
    }
    public static TipoErroSintatico term(){
    	TipoErroSintatico tes = factor();
    	if(tes == TipoErroSintatico.CORRETO) {
    		tes = mulop();
    		while(tes == TipoErroSintatico.CORRETO) {
    			tes = factor();
    			if(tes != TipoErroSintatico.CORRETO)
    				return tes; // erro funcao factor()
    			tes = mulop();
    		}
    		return TipoErroSintatico.CORRETO;
    	}
    	return tes; // erro funcao factor();
    }
    public static TipoErroSintatico mulop(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 69) // simbolo *
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 67) // simbolo /
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 8) // palavra Reservada DIV
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 19) // palavra Reservada MOD
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 1) // palavra Reservada AND
    		return TipoErroSintatico.CORRETO;
    	else
    		GerandoTokens.voltaToken();
    	return erro(token,TipoErroSintatico.MULOP); // erro falta de opadores de divisao
    }
    public static TipoErroSintatico factor(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 41) // padrao NUMBER
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 44) // padrao STRING
    		return TipoErroSintatico.CORRETO;
    	else if(token.getPadrao() == 59) { // simbolo (
    		TipoErroSintatico tes = expr();
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 60) // simbolo )
    				return TipoErroSintatico.CORRETO;
    			return erro(token, TipoErroSintatico.RP);// erro falta simbolo )
    		}
    		return tes;// erro expr()
    	} else if(token.getPadrao() == 21) { // palavra Reservada NOT
    		return factor();
    	} else {
    		GerandoTokens.voltaToken();
    		TipoErroSintatico tes = identifier(0,false,true);
    		if(tes == TipoErroSintatico.CORRETO) {
    			token = GerandoTokens.getNextToken();
        		if(token.getPadrao() == 59) { // simbolo (
        			tes = expr_list();
        			if(tes == TipoErroSintatico.CORRETO) {
        				token = GerandoTokens.getNextToken();
        				if(token.getPadrao() == 60) // simbolo )
        					return TipoErroSintatico.CORRETO;
        				return erro(token,TipoErroSintatico.RP);// erro falta so simbolo )
        			}
        			return tes;// erro funcao expr_list()
        		} else {
        			GerandoTokens.voltaToken();
        			tes = infipo();
        			if(tes == TipoErroSintatico.CORRETO)
        				return TipoErroSintatico.CORRETO;
        		}
        		return erro(token, TipoErroSintatico.LP);
    		}
    		return tes;// erro falta de identifier
    	}
    }
    public static TipoErroSintatico identifier(int tipo,boolean criado, boolean utilizado) {
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		if(token.getTipoID() == -1 && tipo != 0) 
    			token.setTipoID(tipo);
    		if(criado) 
    			token.setCriado(criado);
    		if(utilizado)
    			token.setUtilizado(utilizado);
    		return TipoErroSintatico.CORRETO;
    	}
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
    	return erro(token,TipoErroSintatico.IDENTIFIER);
    }
    private static TipoErroSintatico erro(Token token, TipoErroSintatico tes) {
    	linha = token.getNumero_linha();
    	nomeToken = token.getNome_atributo();
    	return tes;
    }
}