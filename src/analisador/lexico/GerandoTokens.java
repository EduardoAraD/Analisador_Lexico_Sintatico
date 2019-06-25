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
public class GerandoTokens {
    private static ArrayList<String> linhas = new ArrayList<String>(); // as linhas pegas no arquivo
    private static int numero_linha = 1; // controle de linhas
    private static String linha; // atual linha analisada
    private static int numero_caracter = 0; // controle de caracter da linha
    private static ArrayList<Token> tokens = new ArrayList<Token>(); // armazenar tokens
    private static int numero_token = 0; // controle de tokens
    private static boolean erroLexico = false;
    
    public static int getNumero_linha() {
        return numero_linha;
    }
    public static String getLinha() {
        return linha;
    }
    public static int getNumero_token() {
    	return numero_token;
    }
    public static void setNumero_token( int num_token) {
    	numero_token = num_token;
    }
    public static boolean getErro() {
    	return erroLexico;
    }
    // adiciona as linhas que foram lidas no arquivo
    public static void addLinha(String linha){
        linhas.add(linha);
    }
    // Funcao que busca e armazena os tokens
    public static void gerarTokens(){
        PadroesToken pt = new PadroesToken();
        while(!fimArquivo()){
            Token tok = pt.reconhecerPadrao();
            if(tok.isErro())
            	erroLexico = true;
            tokens.add(tok);
        }
    }
    // Funcao que pega o proximo caracter do sistema
    public static char getCaracterLinha(){
        if(linha == null){ // verifica se a linha existe
            linha = (String)linhas.get(0);
            numero_linha = 1;
            numero_caracter = 0;
        }
        if(linha.length() > numero_caracter){ // verifica se a linha ainda tem caracter
            char token = linha.charAt(numero_caracter);
            numero_caracter += 1;
            return token;
        } else if(linhas.size() > numero_linha){ // verifica se ainda tem linha pra ler
            linha = (String)linhas.get(numero_linha);
            numero_linha += 1;
            numero_caracter = 0;
            return '\n';
        }else{ // retorna caracter para fim de arquivo
            numero_caracter += 1;
            return '\\';
        }
    }
    // diminui em 1 o controle de caracter da linha
    public static void voltaCaracter(){
        if(numero_caracter == 0){
            numero_linha -= 1;
            numero_caracter = ((String)linhas.get(numero_linha)).length();
        } else{
            numero_caracter -= 1;
        }
    }
    // verifica se já leu todas as linhas do arquivo
    public static boolean fimArquivo(){
        return (linhas.size() <= numero_linha && linha.length() <= numero_caracter);
    }
    // ler a tokens armazenados 
    public static void lerTokens(){
        int i = 0;
        Token tok = null;
        for(i = 0; i < tokens.size(); i++){
            tok = (Token)tokens.get(i);
            System.out.println(tok.imprimir());
        }
        numero_token = 0;
    }
    // retorna o proximo token
    public static Token getNextToken(){
        if(numero_token < tokens.size()){
            Token tok = (Token)tokens.get(numero_token);
            numero_token++;
            if(tok.isErro() || tok.getPadrao() == 46)
                return getNextToken();
            return tok;
        }
        Token tok = new Token();
        tok.setPadrao(-1);
        return tok;
    }
    // volta um token
    public static void voltaToken() {
    	numero_token--;
    	while(tokens.get(numero_token).isErro() || tokens.get(numero_token).getPadrao() == 46)
    		numero_token--;
    }
    public static ArrayList<Token> getTokens(){
    	return tokens;
    }
}
