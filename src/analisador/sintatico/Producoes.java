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
            if(identifier()){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 59){ // simbolo (
                    if(identifier_list()){
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() != 60){
                            return false; // erro falta do simbolo )
                        }
                    }else{
                        return false; // erro falta de identifier_list
                    }
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55) { // simbolo ;
                    if(block()) {
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() == 53) // simbolo .
                            return true;
                        // erro falta de .
                    }
                    // erro falta de block
                }
                // erro Falta de ;
            }
            // erro falta funcao IDENTIFIER
        }
        //GerandoTokens.voltaToken(); erro falta de PROGRAM
        return false;
    }
    public static boolean block(){
        label_declaration_part();
        const_declaration_part();
        type_declaration_part();
        var_declaration_part();
        subroutine_declaration_part();
        
        return compound_statement();
        
    }
    public static boolean label_declaration_part(){
    	int num_token = GerandoTokens.getNumero_token();
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 18){ // palavra reservada LABEL
            token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 41){ // padrao NUMBER
                token = GerandoTokens.getNextToken();
                while(token.getPadrao() == 56){ // simbolo ,
                    token = GerandoTokens.getNextToken();
                    if(token.getPadrao() != 41) // padrao NUMBER
                        return false; // falta de NUMBER
                    token = GerandoTokens.getNextToken();
                }
                if(token.getPadrao() == 55){ // simbolo ;
                    return true;
                }
                // Erro falta de ;
            }
            // Erro falta de NUM
        }
        GerandoTokens.setNumero_token(num_token);
        return false; // erro falta palavra LABEL
    }
    public static boolean const_declaration_part(){
    	int num_tok = GerandoTokens.getNumero_token();
        Token token = GerandoTokens.getNextToken();
        if(token.getPadrao() == 7){// palavra reservada CONST
            if(const_definition()){
                token = GerandoTokens.getNextToken();
                if(token.getPadrao() == 55){ // simbolo ;
                    while(const_definition()){
                        token = GerandoTokens.getNextToken();
                        if(token.getPadrao() != 55) // simbolo ;
                            return false; // falta de ;
                    }
                    return true;
                }
                // erro falta de ;
            }
            // erro falta const_definition()
        }
        GerandoTokens.setNumero_token(num_tok);;
        return false; // erro, falta de palavra CONST
    }
    public static boolean const_definition(){
    	if(identifier()) {
            Token token = GerandoTokens.getNextToken();
            if(token.getPadrao() == 61) { // simbolo =
                return _const();
            }
            // erro falta do simbolo =
    	}
    	return false; // erro funcao Identifier()
    }
    public static boolean type_declaration_part(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 34) { // palavra Reservada TYPE
    		if(type_definition()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				while(type_definition()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return false; // erro falta de ;
    				}
    				return true;
    			}
                // falta do simbolo ;
    		}
            // falta de type_declaration()
    	}
    	GerandoTokens.setNumero_token(num_token); // falta de palavra TYPE
        return false;
    }
    public static boolean type_definition(){
    	if(identifier()) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 61) { // simbolo =
    			return type();
    		}
    		// erro falta de =
    	}
    	return false; // erro funcao Identifier() 
    }
    public static boolean type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 71) { // simbolo ^
    		return identifier();
    	} else if(token.getPadrao() == 2){ // palavra reservada ARRAY
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			if(simple_type()) { 
    				token = GerandoTokens.getNextToken();
    				while(token.getPadrao() == 56) { // simbolo ,
    					if(!simple_type())
    						return false; // erro funcao simpletype();
    					token = GerandoTokens.getNextToken();
    				}
    				if(token.getPadrao() == 58) { // simbolo ]
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 22) { // palavra Reservada OF
    						return type();
    					}
                        // erro falta de OF
    				}
                    // erro falta de ]
    			}
                // erro falta de simple_type()
    		}
            // erro falta de [
    	} else if(token.getPadrao() == 28) { // palavra Reservada RECORD
    		if(field_list()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 12) // palavra Reservada END
    				return true;
                // erro falta de END
    		}
            // erro, funcao FIELD_LIST()
    	} else {
    		GerandoTokens.voltaToken();
    		return simple_type();
    	}
    	GerandoTokens.voltaToken(); // n√o chega aqui se for tudo feito em cima
    	return false;
    }
    public static boolean simple_type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		if(identifier()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 56) { // simbolo ,
    				if(!identifier())
    					return false; // ERRO falta funcao IDENTIFIER
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 60) { // simbolo )
    				return true;
    			}
    			// erro falta do simbolo )
    		}
    		// erro falta funcao IDENTIFIER
    	} else {
    		GerandoTokens.voltaToken();
    		if(_const()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 52) { // simbolo ..
    				return _const();
    			}
    			// erro falta de ..
    		} else 
	    		return identifier();
    		// erro falta de _const()
    	}
    	GerandoTokens.voltaToken(); // n„o chega aqui
    	return false;
    }
    public static boolean _const(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 44) { // padrao STRING
    		return true;
    	}
    	if(token.getPadrao() == 70 || token.getPadrao() == 68) { // simbolo + ou -
    		token = GerandoTokens.getNextToken();
    	}
    	if (token.getPadrao() == 41) // padrao NUMBER
    		return true;
    	else {
    		GerandoTokens.voltaToken();
    		return identifier();
    	}
    }
    public static boolean field_list(){
    	if(identifier_list()) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			if(type()) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 55) { // simbolo ;
    					return field_list();
    				}
    				GerandoTokens.voltaToken();
    				return true;
    			}
    			// erro funcao type()
    		}
    		// erro falta de :
    	} else {
    		return true;
    	}
    	return false;
    }
    public static boolean var_declaration_part(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 36) { // palavra Reservada VAR
    		if(var_declaration()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				while(var_declaration()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return false; // erro falta do simbolo ;
    				}
    				return true;
    			}
    			// erro falta do simbolo ;
    		}
    		// erro funcao var_declaration()
    	}
    	GerandoTokens.setNumero_token(num_token);; // falta da palavra VAR
        return false;
    }
    public static boolean var_declaration(){
    	if(identifier_list()) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			return type();
    		}
    		GerandoTokens.voltaToken(); // erro falta do simbolo :
    	}
    	return false; // erro funcao identifier_list()
    }
    public static boolean identifier_list(){
    	if(identifier()) {
    		Token token = GerandoTokens.getNextToken();
    		while(token.getPadrao() == 56) { // simbolo ,
    			if(identifier())  
    				return false; // erro falta funcao IDENTIFIER
    			token = GerandoTokens.getNextToken();
    		}
    		GerandoTokens.voltaToken();
    		return true;
    	}
    	// erro falta funcao IDENTIFIER
    	return false;
    }
    public static boolean subroutine_declaration_part(){
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
    }
    public static boolean compound_statement(){
    	int num_token = GerandoTokens.getNumero_token();
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 3) { // palavra Reservada BEGIN
    		if(labeled_statement()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				if(!labeled_statement())
    					return false; // falta de Labeled_statement()
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 12) { // palavra Reservada END
    				return true;
    			}
    			// Erro falta de palavra END
    		}
    		// erro funcao Labeled_statement()
    	}
    	GerandoTokens.setNumero_token(num_token); // falta de palavra BEGIN
        return false;
    }
    public static boolean labeled_statement(){
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
    }
    public static boolean identifier() {
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) // padrao IDENTIFIER
    		return true;
    	else if(token.getPadrao() == 4) // palavra Reservada BOOLEAN 
    		return true;
    	else if(token.getPadrao() == 6) // palavra Reservada CHAR
    		return true;
    	else if(token.getPadrao() == 17) // palavra Reservada INTEGER
    		return true;
    	else if(token.getPadrao() == 20) // palavra Reservada NIL
    		return true;
    	else if(token.getPadrao() == 24) // palavra Reservada POINTER
    		return true;
    	else if(token.getPadrao() == 27) // palavra Reservada REAL
    		return true;
    	else if(token.getPadrao() == 31) // palavra Reservada STRING
    		return true;
    	else 
    		GerandoTokens.voltaToken();
    	return false;
    }
    
}