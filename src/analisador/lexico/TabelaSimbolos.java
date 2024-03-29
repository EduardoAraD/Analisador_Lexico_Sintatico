/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class TabelaSimbolos {
    private static ArrayList<Token> linhasTabela = new ArrayList<Token>();
    
    public static void addToken(Token tok){
        for(Token token: linhasTabela){
            if(token.getNome_atributo().equals(tok.getNome_atributo())){
                return;
            }
        }
        linhasTabela.add(tok);
    }
    public static int getIdToken(Token tok){
        int id = 0;
        for(Token token : linhasTabela){
            if(token.getNome_atributo().equals(tok.getNome_atributo())){
                return id;
            }
            id++;
        }
        return -1;
    }
    public static ArrayList<Token> getTokens(){
    	return linhasTabela;
    }
    public static Token getToken(String nome) {
    	for(Token token : linhasTabela) {
    		if(token.getNome_atributo().equals(nome))
    			return token;
    	}
    	return null;
    }
    
}
