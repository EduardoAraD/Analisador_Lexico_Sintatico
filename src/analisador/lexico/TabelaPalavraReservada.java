/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aluno
 */
public class TabelaPalavraReservada {
    public Map<Integer,String> padrao = new HashMap<>();

    public TabelaPalavraReservada() { // atrubui os id para cada palavra reservada
        padrao.put(-1,"DESCONHECIDO");
        
        padrao.put(1,"AND");
        padrao.put(2,"ARRAY");
        padrao.put(3,"BEGIN");
        padrao.put(4,"BOOLEAN");
        padrao.put(5,"CASE");
        padrao.put(6,"CHAR");
        padrao.put(7,"CONST");
        padrao.put(8,"DIV");
        padrao.put(9,"DO");
        padrao.put(10,"DOWNTO");
        padrao.put(11,"ELSE");
        padrao.put(12,"END");
        padrao.put(13,"FOR");
        padrao.put(14,"FUNCTION");
        padrao.put(15,"GOTO");
        padrao.put(16,"IF");
        padrao.put(17,"INTEGER");
        padrao.put(18,"LABEL");
        padrao.put(19,"MOD");
        padrao.put(20,"NIL");
        padrao.put(21,"NOT");
        padrao.put(22,"OF");
        padrao.put(23,"OR");
        padrao.put(24,"POINTER");
        padrao.put(25,"PROCEDURE");
        padrao.put(26,"PROGRAM");
        padrao.put(27,"REAL");
        padrao.put(28,"RECORD");
        padrao.put(29,"REPEAT");
        padrao.put(30,"SET");
        padrao.put(31,"STRING");
        padrao.put(32,"THEN");
        padrao.put(33,"TO");
        padrao.put(34,"TYPE");
        padrao.put(35,"UNTIL");
        padrao.put(36,"VAR");
        padrao.put(37,"WHILE");
        padrao.put(38,"WITH");
        padrao.put(39,"IN");
        
        padrao.put(41,"NUM");
        padrao.put(42,"IDENTIFIER");
        padrao.put(43,"CHAR1");
        padrao.put(44,"STRING");
        padrao.put(45,"COMMENT");
        padrao.put(46,"WS");
        padrao.put(49,"COMMENT");
        
        padrao.put(51,"ASSING_OP");     // :=
        padrao.put(52,"DOTDOT");        // ..
        padrao.put(53,"DOT");           // .
        padrao.put(54,"COLON");         // :
        padrao.put(55,"SEMICOLON");     // ;
        padrao.put(56,"COMMA");         // ,
        padrao.put(57,"LB");            // [
        padrao.put(58,"RB");            // ]
        padrao.put(59,"LP");            // (
        padrao.put(60,"RP");            // )
        padrao.put(61,"EQUAL");         // =
        padrao.put(62,"LE");            // <=
        padrao.put(63,"GE");            // >=
        padrao.put(64,"NE");            // <>
        padrao.put(65,"GT");            // >
        padrao.put(66,"LT");            // <
        padrao.put(67,"DIVIDE");        // /
        padrao.put(68,"MINUS");         // -
        padrao.put(69,"TIMES");         // *
        padrao.put(70,"PLUS");          // +
        padrao.put(71,"SIMB_POINTER");  // ^
        padrao.put(72,"ENDER");         // @
        padrao.put(73, "{");
        padrao.put(74, "}");
        padrao.put(75,"BLANK");         //
        padrao.put(76,"NEW LINE");      // \n
        padrao.put(77,"TAB");           // \t
        
    }
    
    public int palavraReservada(String palavra){ // procura a palavra reservada a partir do tamanho
        int tamanho = palavra.length();
        char tok = palavra.charAt(0);
        String aux;
        switch (tamanho) {
            case 2:
                if(tok == 'D'){
                    tok = palavra.charAt(1);
                    if(tok == 'O')
                        return 9;
                } else if(tok == 'I'){
                    tok = palavra.charAt(1);
                    if(tok == 'F')
                        return 16;
                    else if(tok == 'N')
                    	return 39;
                } else if(tok == 'O'){
                    tok = palavra.charAt(1);
                    if(tok == 'F')
                        return 22;
                    else if(tok == 'R')
                        return 23;
                } else if(tok == 'T'){
                    tok = palavra.charAt(1);
                    if(tok == 'O')
                        return 33;
                }
                return 0;
            case 3:
                if(tok == 'A'){
                    aux = palavra.substring(1);
                    if(aux.equals("ND"))
                        return 1;
                } else if(tok == 'D'){
                    aux = palavra.substring(1);
                    if(aux.equals("IV"))
                        return 8;
                } else if(tok == 'E'){
                    aux = palavra.substring(1);
                    if(aux.equals("ND"))
                        return 12;
                } else if(tok == 'F'){
                    aux = palavra.substring(1);
                    if(aux.equals("OR"))
                        return 13;
                } else if(tok == 'M'){
                    aux = palavra.substring(1);
                    if(aux.equals("OD"))
                        return 19;
                } else if(tok == 'N'){
                    tok = palavra.charAt(1);
                    if(tok == 'I'){
                        tok = palavra.charAt(2);
                        if(tok == 'L')
                            return 20;
                    } else if(tok == 'O'){
                        tok = palavra.charAt(2);
                        if(tok == 'T')
                            return 21;
                    }
                } else if(tok == 'S'){
                    aux = palavra.substring(1);
                    if(aux.equals("ET"))
                        return 30;
                } else if(tok == 'V'){
                    aux = palavra.substring(1);
                    if(aux.equals("AR"))
                        return 36;
                }
                return 0;
            case 4:
                if(tok == 'C'){
                    tok = palavra.charAt(1);
                    if(tok == 'A'){
                        aux = palavra.substring(2);
                        if(aux.equals("SE"))
                            return 5;
                    } else if(tok == 'H'){
                        aux = palavra.substring(2);
                        if(aux.equals("AR"))
                            return 6;
                    }
                } else if(tok == 'E'){
                    aux = palavra.substring(1);
                    if(aux.equals("LSE"))
                        return 11;
                } else if(tok == 'G'){
                    aux = palavra.substring(1);
                    if(aux.equals("OTO"))
                        return 15;
                } else if(tok == 'R'){
                    aux = palavra.substring(1);
                    if(aux.equals("EAL"))
                        return 27;
                } else if(tok == 'T'){
                    tok = palavra.charAt(1);
                    if(tok == 'H'){
                        aux = palavra.substring(2);
                        if(aux.equals("EN"))
                            return 32;
                    } else if(tok == 'Y'){
                        aux = palavra.substring(2);
                        if(aux.equals("PE"))
                        return 34;
                    }
                } else if(tok == 'W'){
                    aux = palavra.substring(1);
                    if(aux.equals("ITH"))
                        return 38;
                }
                return 0;
            case 5:
                if(tok == 'A'){
                    aux = palavra.substring(1);
                    if(aux.equals("RRAY"))
                        return 2;
                } else if(tok == 'B'){
                    aux = palavra.substring(1);
                    if(aux.equals("EGIN"))
                        return 3;
                } else if(tok == 'C'){
                    aux = palavra.substring(1);
                    if(aux.equals("ONST"))
                        return 5;
                } else if(tok == 'L'){
                    aux = palavra.substring(1);
                    if(aux.equals("ABEL"))
                        return 18;
                } else if(tok == 'U'){
                    aux = palavra.substring(1);
                    if(aux.equals("NTIL"))
                        return 35;
                } else if(tok == 'W'){
                    aux = palavra.substring(1);
                    if(aux.equals("HILE"))
                        return 37;
                }
                return 0;
            case 6:
                if(tok == 'D'){
                    aux = palavra.substring(1);
                    if(aux.equals("OWNTO"))
                        return 10;
                } else if(tok == 'R'){
                    tok = palavra.charAt(1);
                    if(tok == 'E'){
                        tok = palavra.charAt(2);
                        if(tok == 'C'){
                            aux = palavra.substring(3);
                            if(aux.equals("ORD"))
                                return 28;
                        } else if(tok == 'P'){
                            aux = palavra.substring(3);
                            if(aux.equals("EAT"))
                                return 29;
                        }
                    }
                } else if(tok == 'S'){
                    aux = palavra.substring(1);
                    if(aux.equals("TRING"))
                        return 31;
                }
                return 0;
            case 7:
                if(tok == 'B'){
                    aux = palavra.substring(1);
                    if(aux.equals("OOLEAN"))
                        return 4;
                } else if(tok == 'I'){
                    aux = palavra.substring(1);
                    if(aux.equals("NTEGER"))
                        return 17;
                } else if(tok == 'P'){
                    tok = palavra.charAt(1);
                    if(tok == 'O'){
                        aux = palavra.substring(2);
                        if(aux.equals("INTER"))
                            return 24;
                    } else if(tok == 'R'){
                        aux = palavra.substring(2);
                        if(aux.equals("OGRAM"))
                            return 26;
                    }
                }
                return 0;
            case 8:
                if(palavra.equals("FUNCTION"))
                    return 14;
                return 0;
            case 9:
                if(palavra.equals("PROCEDURE"))
                    return 25;
                return 0;
            default:
                return 0;
        }
    }
    
    public int operador(String palavra){ // procura o operador reservado pelo tamanho
        int tamanho = palavra.length();
        if(tamanho == 1){
            char caracter = palavra.charAt(0);
            switch(caracter){
                case '.':
                    return 53;
                case ':':
                    return 54;
                case ';':
                    return 55;
                case ',':
                    return 56;
                case '[':
                    return 57;
                case ']':
                    return 58;
                case '(':
                    return 59;
                case ')':
                    return 60;
                case '=':
                    return 61;
                case '>':
                    return 65;
                case '<':
                    return 66;
                case '/':
                    return 67;
                case '-':
                    return 68;
                case '*':
                    return 69;
                case '+':
                    return 70;
                case '^':
                    return 71;
                case '@':
                    return 72;
                
            }
            return 0;
        } else if(tamanho == 2){
            char caracter = palavra.charAt(0);
            switch(caracter){
                case ':':
                    caracter = palavra.charAt(1);
                    if(caracter == '='){
                        return 51;
                    }
                    break;
                case '.':
                    caracter = palavra.charAt(1);
                    if(caracter == '.'){
                        return 52;
                    }
                    break;
                case '<':
                    caracter = palavra.charAt(1);
                    if(caracter == '='){
                        return 62;
                    } else if(caracter == '>'){
                        return 14;
                    }
                    break;
                case '>':
                    caracter = palavra.charAt(1);
                    if(caracter == '='){
                        return 63;
                    }
                    break;
            }
            return 0;
        } else 
            return 0;
    }
    
}
