/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class LeituraArquivo {
    
    // Funcao que lÃª o arquivo e armazena as linhas no GerandoTokens
    public static boolean LeituraArquivoCMP(String arq){
        //arquivo ".Cmp"
        try{
            BufferedReader br;
            // procura e verifica os arquivos
            if(arq.length() < 4){
                br = new BufferedReader(new FileReader(arq+".Cmp"));
            } else if(arq.substring(arq.length()-4, arq.length()).equals(".Cmp"))
                br = new BufferedReader(new FileReader(arq));
            else
                br = new BufferedReader(new FileReader(arq+".Cmp"));
            
            // armazena  as linhas do arquivo no GerandoTokens
            while(br.ready()){
                String linha = br.readLine();
                GerandoTokens.addLinha(linha);
            }
            
            br.close();
            
            return true;
            
        }catch(IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    
    }
    
    // Grava os tokens ancontrados no arquivo Resultado.txt
    public static void GravacaodeResultado(){
        try{
            FileWriter arq = new FileWriter("Resultado.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            
            // pegar token 
            ArrayList<Token> tokens = GerandoTokens.getTokens();
            
            for(Token token : tokens){
                gravarArq.println(token.imprimir());
            }
            
            gravarArq.println("\n Analise Sintática \n Tabela de Simbolos\n");
            tokens = TabelaSimbolos.getTokens();
            for(Token token : tokens) {
            	gravarArq.println(token.imprimirTokenID());
            }

            arq.close();
        
        } catch (IOException e){
            Logger.getLogger(LeituraArquivo.class.getName()).log(Level.SEVERE, null, e);
        }
}
}
