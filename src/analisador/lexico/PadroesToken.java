/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.util.HashMap;

/**
 *
 * @author Eduardo
 */
public class PadroesToken {
    public HashMap<Character,Boolean> letras = new HashMap<>(); // armazenado as letras do alfabeto do sistema
    public HashMap<Character,Boolean> digito = new HashMap<>(); // armazenado os digitos do alfabeto do sistema
    public HashMap<Character,Boolean> simbolo = new HashMap<>(); //armazenado os simbolo iniciais do alfabeto
    public HashMap<Character,Boolean> simbolo2 = new HashMap<>(); // armazena os simbolos finais do albafeto
    public HashMap<Character,Boolean> delim = new HashMap<>(); // armazena os simbolos de tabulacao, new linha
    public HashMap<Integer,String> padroes = new HashMap<>(); // armazena os padroes reconhecidos
            
    public PadroesToken(){
        letras.put('a', true);
        letras.put('b', true);
        letras.put('c', true);
        letras.put('d', true);
        letras.put('e', true);
        letras.put('f', true);
        letras.put('g', true);
        letras.put('h', true);
        letras.put('i', true);
        letras.put('j', true);
        letras.put('k', true);
        letras.put('l', true);
        letras.put('m', true);
        letras.put('n', true);
        letras.put('o', true);
        letras.put('p', true);
        letras.put('q', true);
        letras.put('r', true);
        letras.put('s', true);
        letras.put('t', true);
        letras.put('u', true);
        letras.put('v', true);
        letras.put('w', true);
        letras.put('x', true);
        letras.put('y', true);
        letras.put('z', true);
        letras.put('_', true);
        letras.put('A', true);
        letras.put('B', true);
        letras.put('C', true);
        letras.put('D', true);
        letras.put('E', true);
        letras.put('F', true);
        letras.put('G', true);
        letras.put('H', true);
        letras.put('I', true);
        letras.put('J', true);
        letras.put('K', true);
        letras.put('L', true);
        letras.put('M', true);
        letras.put('N', true);
        letras.put('O', true);
        letras.put('P', true);
        letras.put('Q', true);
        letras.put('R', true);
        letras.put('S', true);
        letras.put('T', true);
        letras.put('U', true);
        letras.put('V', true);
        letras.put('W', true);
        letras.put('X', true);
        letras.put('Y', true);
        letras.put('Z', true);
        
        digito.put('0', true);
        digito.put('1', true);
        digito.put('2', true);
        digito.put('3', true);
        digito.put('4', true);
        digito.put('5', true);
        digito.put('6', true);
        digito.put('7', true);
        digito.put('8', true);
        digito.put('9', true);
        
        simbolo.put(':', true);
        simbolo.put('=', true);
        simbolo.put('.', true);
        simbolo.put(';', true);
        simbolo.put(',', true);
        simbolo.put('[', true);
        simbolo.put(']', true);
        simbolo.put('(', true);
        simbolo.put(')', true);
        simbolo.put('<', true);
        simbolo.put('>', true);
        simbolo.put('/', true);
        simbolo.put('-', true);
        simbolo.put('*', true);
        simbolo.put('+', true);
        simbolo.put('^', true);
        simbolo.put('@', true);
        simbolo.put('\'', true);
        simbolo.put('{', true);
        simbolo.put('}', true);
        
        simbolo2.put('=', true);
        simbolo2.put('.', true);
        simbolo2.put('>', true);
        
        delim.put('\n',true);
        delim.put('\t',true);
        
        
    }
    
    public boolean letter(char token){ // tipo letra
        return letras.get(token) != null;
    }
    public boolean digit(char token){ // tipo digito
        return digito.get(token) != null;
    }
    public boolean simbolo(char token){ // tipo simbolo
        return simbolo.get(token) != null;
    }
    public boolean simbolo2(char token){ // tipo simbolo com dois elementos, somente com os tokens do segundo simbolo
        return simbolo2.get(token) != null;
    }
    public boolean delim(char token){
        return delim.get(token) != null;
    }
    public Token identifier(Token token){ // identifica de é digito
        char tok = GerandoTokens.getCaracterLinha();
        String aux = "";
        if(letter(tok)){ // verifica se é primeiro caracter
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
            while(letter(tok) || digit(tok)){ // verifica se é letra ou caracter
                aux += tok;
                tok = GerandoTokens.getCaracterLinha();
            }
        }
        GerandoTokens.voltaCaracter();
        token.setNome_atributo(aux);
        return token;
    }
    public Token digits(Token token){ // identifica digito, lendo pelo menos um digito
        char tok = GerandoTokens.getCaracterLinha();
        String aux = "";
        if(digit(tok)){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
            while(digit(tok)){
                aux += tok;
                tok = GerandoTokens.getCaracterLinha();
            }
            GerandoTokens.voltaCaracter();
            token.setNome_atributo(token.getNome_atributo()+aux);
            return token;
        } else{
            GerandoTokens.voltaCaracter();
            token.setErro(true);
        }
        return token;
    }
    public Token optional_fraction(Token token){ // le as parte fracionaria
        char tok = GerandoTokens.getCaracterLinha();
        if( tok == '.'){
            token.setNome_atributo(token.getNome_atributo()+tok);
            token = digits(token);
            if(token.isErro()){
                String aux = token.getNome_atributo();
                token.setNome_atributo(aux.substring(0, aux.length()-1));
                GerandoTokens.voltaCaracter();
                token.setErro(false);
            }
        } else {
            GerandoTokens.voltaCaracter();
        }
        return token;
    }
    public Token optional_expoent(Token token){ // le a parte exponencial
        char tok = GerandoTokens.getCaracterLinha();
        String aux = "";
        if(tok == 'E' || tok == 'e'){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
            if(tok == '+' || tok == '-')
                aux += tok;
            else
                GerandoTokens.voltaCaracter();
            
            token.setNome_atributo(token.getNome_atributo()+aux);
            token = digits(token);
            if(token.isErro()){
                String aux2 = token.getNome_atributo();
                token.setNome_atributo(aux2.substring(0, aux2.length() - aux.length()));
                if(aux.length() == 2)
                    GerandoTokens.voltaCaracter();
                GerandoTokens.voltaCaracter();
                token.setErro(false);
            }
        } else {
            GerandoTokens.voltaCaracter();
        }
        return token;
    }
    public Token num(Token token){ // reconhece o padrao num(digito , fracionario, exponencial)
        token = digits(token);
        token = optional_fraction(token);
        token = optional_expoent(token);
        return token;
    }
    public Token char1(Token token){ // reconhece padrao char1
        char tok = GerandoTokens.getCaracterLinha();
        String aux = "";
        while(letter(tok) || digit(tok) || simbolo(tok)){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
        }
        GerandoTokens.voltaCaracter();
        token.setNome_atributo(token.getNome_atributo()+aux);
        return token;
    }
    public Token string(Token token){ // reconhece String
        String aux = "";
        char tok = GerandoTokens.getCaracterLinha();
        if(tok == '\''){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
            while(tok != '\'' && !delim(tok)){ // compara se é final de string ou tipo delim
                if(GerandoTokens.fimArquivo())
                    break;
                aux += tok;
                tok = GerandoTokens.getCaracterLinha();
            }
            if(tok == '\''){ // armazena a string completa
                aux += tok;
                token.setNome_atributo(token.getNome_atributo()+aux);
            } else { // coloca o token com erro
                token.setErro(true);
            }
            return token;
        }
        return token;
    }
    public Token character(Token token){ // armazena o tipo caracter
        String aux = "";
        char tok = GerandoTokens.getCaracterLinha();
        while(letter(tok) || digit(tok) || simbolo(tok) || delim(tok)){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
        }
        token.setNome_atributo(token.getNome_atributo()+aux);
        return token;
    }
    public Token comment(Token token){ // verifica se é do padrao Comment
        token.setPadrao(-1);
        char tok = GerandoTokens.getCaracterLinha();
        if(tok == '('){ // Verifica o padrao do Comment "(* *)"
            tok = GerandoTokens.getCaracterLinha();
            if(tok == '*'){
                tok = GerandoTokens.getCaracterLinha();
                char tok2 = GerandoTokens.getCaracterLinha();
                //if(tok == '*' && tok2 == ')') (**-**) 
                while(tok != '*' || tok2 != ')'){
                    if(GerandoTokens.fimArquivo()){
                        token.setNome_atributo("(");
                        token.setErro(true);
                        break;
                    }
                    tok = tok2;
                    tok2 = GerandoTokens.getCaracterLinha();
                }
            } else{ // nao entrou no padrao do comment
                GerandoTokens.voltaCaracter();
                GerandoTokens.voltaCaracter();
                token.setPadrao(0);
            }
        } else if(tok == '{'){ // verifica o padrao2 do Comment "{ }" , esse só em uma linha
            tok = GerandoTokens.getCaracterLinha();
            //if(tok == '}' || delim(tok) || GerandoTokens.fimArquivo())
            while(tok != '}' && !delim(tok) && !GerandoTokens.fimArquivo()){
                tok = GerandoTokens.getCaracterLinha();
            }
            if(tok != '}'){
                token.setNome_atributo("{");
                token.setErro(true);
            }
            return token;
        } else{ // volta com o padrao não encontrado ainda
            token.setPadrao(0);
            GerandoTokens.voltaCaracter();
            return token;
        }
        return token;
    }
    public Token operador(Token token){ // encontra os padroes simbolos encontrados no codigo;
        String aux = "";
        char tok = GerandoTokens.getCaracterLinha();
        if(simbolo(tok)){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
            if(simbolo2(tok)){
                aux += tok;
            } else
                GerandoTokens.voltaCaracter();
        } else
            GerandoTokens.voltaCaracter();
        
        token.setNome_atributo(aux);
        return token;
    }
    public Token ws(Token token){ // verifica os padroes de Delim (Tabulacao, newLine, Black), precisa de pelo menos um
        String aux = "";
        char tok = GerandoTokens.getCaracterLinha();
        if(delim(tok)){
            aux += tok;
            tok = GerandoTokens.getCaracterLinha();
            while(delim(tok)){
                aux += tok;
                tok = GerandoTokens.getCaracterLinha();
            }
            token.setNome_atributo(aux);
        }
        GerandoTokens.voltaCaracter();
        return token;
    }
    
    public Token reconhecerPadrao(){
        Token token = new Token();
        token.setNumero_linha(GerandoTokens.getNumero_linha());
        char tok = GerandoTokens.getCaracterLinha();
        if(tok == ' ')
            return reconhecerPadrao();
        else if(digit(tok)){ // verifica padrao (NUM)
            GerandoTokens.voltaCaracter();
            token = num(token);
            token.setPadrao(41);
            return token;
            
        } else if(letter(tok)){ // verifica padrao (IDENTIFIER)
            GerandoTokens.voltaCaracter();
            token = identifier(token);
            token.setNumero_linha(GerandoTokens.getNumero_linha());
            //TabelaPalavraReservada pr = new TabelaPalavraReservada();
            int numPalavra = TabelaPalavraReservada.palavraReservada(token.getNome_atributo()); // verifica se palavra é reservada
            if(numPalavra == 0){ // IDENTIFIER
                token.setPadrao(42);
                TabelaSimbolos.addToken(token);
            } else { // PALAVRA RESERVADA
                token.setPadrao(numPalavra);
            }
            return token;
            
        } else if(simbolo(tok)){ // verifica padrao (STRING, COMMENT, OPERADOR)
            GerandoTokens.voltaCaracter();
            if(tok == '\'' ){ // STRING
                token = string(token);
                token.setNumero_linha(GerandoTokens.getNumero_linha());
                token.setPadrao(44);
                return token;
                
            } if(tok == '(' || tok == '{'){ // COMMENT
                token = comment(token);
                if(token.getPadrao() != 0){
                    if(token.isErro()){
                        token.setPadrao(45);
                        return token;
                    }
                    return reconhecerPadrao();
                }
            }
            token = operador(token);
            //TabelaPalavraReservada pr = new TabelaPalavraReservada();
            int numPalavra = TabelaPalavraReservada.operador(token.getNome_atributo()); // verifica se o operador é reservado
            if(numPalavra == 0){ // verifica se o operador foi encontrado
                String aux = token.getNome_atributo();
                if(aux.length() == 2){ // voltar uma letra e verificar se é operador
                    aux = aux.charAt(0)+"";
                    numPalavra = TabelaPalavraReservada.operador(aux);
                    GerandoTokens.voltaCaracter();
                    token.setNome_atributo(aux);
                    if(numPalavra == 0){
                        token.setPadrao(-1);
                        token.setErro(true);
                    }
                    else{
                        token.setPadrao(numPalavra);
                    }
                } else{
                    token.setPadrao(-1);
                    token.setErro(true);
                }
            } else {
                token.setPadrao(numPalavra);
            }
            return token;
            
        } else if(delim(tok)) { // padrao DELIM
            GerandoTokens.voltaCaracter();
            token = ws(token);
            token.setNumero_linha(GerandoTokens.getNumero_linha());
            token.setPadrao(46);
            return token;
        } else { // caso não possui a letra no alfabeto
            token.setNome_atributo(tok+"");
            token.setErro(true);
            token.setPadrao(-1);
        }
        return token;
    }
    
}
