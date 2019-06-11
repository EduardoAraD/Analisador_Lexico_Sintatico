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
                } else if(token.getPadrao() == 55) { // simbolo ;
                	if(block()) {
                		token = GerandoTokens.getNextToken();
                        if(token.getPadrao() == 53) // simbolo .
                            return true;
                	}
                }
            }
        }
        //GerandoTokens.voltaToken();
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
        GerandoTokens.voltaToken();
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
        GerandoTokens.voltaToken();
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
        GerandoTokens.voltaToken();
        return false;
    }
    public static boolean const_definition(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 61) { // simbolo =
    			return _const();
    		}
    	}
    	GerandoTokens.voltaToken();
        return false;
    }
    public static boolean type_declaration_part(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 34) { // palavra Reservada TYPE
    		boolean funcionou = type_declaration();
    		if(funcionou) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				funcionou = type_declaration();
    				while(funcionou) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) { // simbolo ;
    						return false;
    					}else {
    						funcionou = type_declaration();
    					}
    				}
    				return true;
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
        return false;
    }
    public static boolean type_declaration(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 61) { // simbolo =
    			return type();
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 71) { // simbolo ^
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) // padrao IDENTIFIER
    			return true;
    	} else if(token.getPadrao() == 2){ // palavra reservada ARRAY
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			boolean funcionou = simple_type();
    			if(funcionou) { 
    				token = GerandoTokens.getNextToken();
    				while(token.getPadrao() == 56) { // simbolo ,
    					if(!simple_type())
    						return false;
    					else
    						token = GerandoTokens.getNextToken();
    				}
    				if(token.getPadrao() == 58) { // simbolo ]
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 22) { // palavra Reservada OF
    						return type();
    					}
    				}
    			}
    		}
    	} else if(token.getPadrao() == 28) { // palavra Reservada RECORD
    		if(field_list()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 12) // palavra Reservada END
    				return true;
    		}
    	} else {
    		GerandoTokens.voltaToken();
    		return simple_type();
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean simple_type(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		return true;
    	} else if(token.getPadrao() == 59) { // simbolo (
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) { // padrao IDENTIFIER
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 56) { // simbolo ,
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() != 42)
    					return false;
    				
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 60) { // simbolo )
    				return true;
    			}
    		}
    	} else {
    		GerandoTokens.voltaToken();
    		if(_const()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 52) { // simbolo ..
    				return _const();
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean _const(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 44) { // padrao STRING
    		return false;
    	} else if(token.getPadrao() == 57) { // simbolo [
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 70 || token.getPadrao() == 68) { // simbolo + ou -
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 58) { // simbolo ]
    				token = GerandoTokens.getNextToken();
    				if (token.getPadrao() == 41 || token.getPadrao() == 42)
    					return true;
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean field_list(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 57) { // simbolo [
    		if(identifier_list()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 54) { // simbolo :
    				if(type()) {
    					token = GerandoTokens.getNextToken();
    					while(token.getPadrao() == 55) { // simbolo ;
    						return field_list();
    					}
    					GerandoTokens.voltaToken();
    					return true;
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean var_declaration_part(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 36) { // palavra Reservada VAR
    		if(var_declaration()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				while(var_declaration()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 55) // simbolo ;
    						return false;
    				}
    				return true;
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
        return false;
    }
    public static boolean var_declaration(){
    	if(identifier_list()) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 54) { // simbolo :
    			return type();
    		}
    		GerandoTokens.voltaToken();
    	}
    	return false;
    }
    public static boolean identifier_list(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		token = GerandoTokens.getNextToken();
    		while(token.getPadrao() == 56) { // simbolo ,
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() != 42) // padrao IDENTIFIER 
    				return false;
    			token = GerandoTokens.getNextToken();
    		}
    		GerandoTokens.voltaToken();
    		return true;
    	}
    	GerandoTokens.voltaToken();
        return false;
    }
    public static boolean subroutine_declaration_part(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 73) { // simbolo {
    		if(procedure_declaration() || function_declaration()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 55) { // simbolo ;
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 74) // simbolo }
    					return true;
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
        return false;
    }
    public static boolean procedure_declaration(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 25) { // palavra Reservada PROCEDURE
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) { // padrao IDENTIFIER
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 57) { // simbolo [
    				if(formal_parameters()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 58) { // simbolo ]
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 55) { // simbolo ;
    							return block();
    						}
    					}
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean function_declaration(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 14) {// palavra Reservada FUNCTION
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) { // padrao IDENTIFIER
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 57) { // simbolo [
    				if(formal_parameters()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 58) { // simbolo ]
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 54) { // simbolo :
    							token = GerandoTokens.getNextToken();
    							if(token.getPadrao() == 42) { // padrao IDENTIFIER
    								token = GerandoTokens.getNextToken();
    								if(token.getPadrao() == 55) { // simbolo ;
    									return block();
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean formal_parameters(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		if(param_section()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				if(!param_section())
    					return false;
    				token = GerandoTokens.getNextToken();
    			}
    			GerandoTokens.voltaToken();
    			return true;
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean param_section(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 57) { //simbolo [
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 36) { // palavra Reservada VAR
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 58) { // simbolo ]
    				if(identifier_list()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 54) { // simbolo :
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 42) { // simbolo IDENTIFIER
    							return true;
    						}
    					}
    				}
    			}
    		}
    	} else if(token.getPadrao() == 14) { // palavra Reservada FUNCTION
    		if(identifier_list()) {
    			token = GerandoTokens.getNextToken();
				if(token.getPadrao() == 54) { // simbolo :
					token = GerandoTokens.getNextToken();
					if(token.getPadrao() == 42) { // simbolo IDENTIFIER
						return true;
					}
				}
    		}
    	} else if(token.getPadrao() == 25) { // palavra Reservada PROCEDURE
    		return identifier_list();
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean compound_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 3) { // palavra Reservada BEGIN
    		if(labeled_statement()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				if(!labeled_statement()) {
    					return false;
    				}
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 12) { // palavra Reservada END
    				return true;
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
        return false;
    }
    public static boolean labeled_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 57) { // simbolo [
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 41) { // padrao NUMBER
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 54) { // simbolo :
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 58) { // simbolo ]
    					return statement();
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean statement(){
    	Token token = GerandoTokens.getNextToken();
    	GerandoTokens.voltaToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		if(assing_statement())
    			return true;
    		else if(procedure_call())
    			return true;
    	} else if(token.getPadrao() == 16) // palavra Reservada IF
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
    	else
    		return true;
		return false;
    }
    public static boolean assing_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			if(infipo()) {
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 58) { // simbolo ]
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 51) { // simbolo :=
    						return expr();
    					}
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean procedure_call(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 59) { // simbolo (
    				if(expr_list()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 60) { // simbolo )
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 58) { // simbolo ]
    							return true;
    						}
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    public static boolean if_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 16) { // palavra Reservada IF
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 32) { // palavra Reservada THEN
    				if(statement()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 57) { // simbolo [
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 11) { // palavra Reservada ELSE
    							if(statement()) {
    								token = GerandoTokens.getNextToken();
    								if(token.getPadrao() == 58) { // simbolo ]
    									return true;
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean while_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 37) { // palavra Reservada WHILE
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 9) { // palavra Reservada DO
    				return statement();
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean repeat_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 29) { // palavra Reservada REPEAT
    		if(statement()) {
    			token = GerandoTokens.getNextToken();
    			while(token.getPadrao() == 55) { // simbolo ;
    				if(!statement())
    					return false;
    				token = GerandoTokens.getNextToken();
    			}
    			if(token.getPadrao() == 35) { // palavra Reservada UNTIL
    				return expr();
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean for_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 13) { // palavra Reservada FOR
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) { // padrao IDENTIFIER
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
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean with_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 38) { // palavra Reservada WITH
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) { // padrao IDENTIFIER
    			if(infipo()) {
    				token = GerandoTokens.getNextToken();
    				while(token.getPadrao() == 56) { // simbolo ,
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() != 42) // padrao IDENTIFIER
    						return false;
    					else if(!infipo())
    						return false;
    					token = GerandoTokens.getNextToken();
    				}
    				if(token.getPadrao() == 9) {
    					return statement();
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean case_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 22) { // palavra Reservada OF
    				if(_case()) {
    					token = GerandoTokens.getNextToken();
    					while(token.getPadrao() == 55) { // simbolo ;
    						if(!_case()) {
    							return false;
    						}
    						token = GerandoTokens.getNextToken();
    					}
    					if(token.getPadrao() == 12) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean _case(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 59) { // simbolo (
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 41 || token.getPadrao() == 42) { // padrao NUMBER ou IDENTIFIER
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 60) { // simbolo )
    				token = GerandoTokens.getNextToken();
    				if(token.getPadrao() == 54){ // simbolo :
    					return statement();
    				}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean goto_statement(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 15) { // palavra Reservada GOTO
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 41) { // padrao NUMBER
    			return true;
    		}
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    public static boolean infipo(){
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
    		}
    	} else if(token.getPadrao() == 53) { // simbolo .
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 42) { // padrao IDENTIFIER
    			return infipo();
    		}
    	} else if(token.getPadrao() == 71) { // simbolo ^
    		return infipo();
    	} else {
	    	GerandoTokens.voltaToken();
	    	return true;
    	}
    	return false;
    }
    public static boolean expr_list(){
    	if(expr()) {
    		Token token = GerandoTokens.getNextToken();
			while(token.getPadrao() == 56) { // simbolo ,
				if(!expr())
					return false;
				token = GerandoTokens.getNextToken();
			}
			GerandoTokens.voltaToken();
			return true;
    	}
    	return false;
    }
    public static boolean expr(){
    	if(simple_expr()) {
    		Token token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			if(relop()) {
    				if(simple_expr()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 58) { // simbolo ]
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
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
    	else 
    		return false;
    }
    public static boolean simple_expr(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 57) { // simbolo [
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 70 || token.getPadrao() == 68) { //simbolo + ou -
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 58) { // simbolo ]
	    			if(term()) {
	    				while(addop()) {
	    					if(!term())
	    						return false;
	    				}
	    				return true;
	    			}
    			}
    		}
    	}
    	GerandoTokens.voltaToken();
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
    	return false;
    }
    public static boolean term(){
    	if(factor()) {
    		while(mulop()) {
    			if(!factor())
    				return false;
    		}
    		return true;
    	}
    	return false;
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
    	return false;
    }
    public static boolean factor(){
    	Token token = GerandoTokens.getNextToken();
    	if(token.getPadrao() == 42) { // padrao IDENTIFIER
    		if(infipo())
    			return true;
    		token = GerandoTokens.getNextToken();
    		if(token.getPadrao() == 57) { // simbolo [
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 59) { // simbolo (
    				if(expr_list()) {
    					token = GerandoTokens.getNextToken();
    					if(token.getPadrao() == 60) { // simbolo )
    						token = GerandoTokens.getNextToken();
    						if(token.getPadrao() == 58) // simbolo ]
    							return true;
    					}
    				}
    			}
    		}
    	} else if(token.getPadrao() == 41) // padrao NUMBER
    		return true;
    	else if(token.getPadrao() == 44) // padrao STRING
    		return true;
    	else if(token.getPadrao() == 59) { // simbolo (
    		if(expr()) {
    			token = GerandoTokens.getNextToken();
    			if(token.getPadrao() == 60) // simbolo )
    				return true;
    		}
    	} else if(token.getPadrao() == 21) { // palavra Reservada NOT
    		return factor();
    	}
    	GerandoTokens.voltaToken();
    	return false;
    }
    
}
