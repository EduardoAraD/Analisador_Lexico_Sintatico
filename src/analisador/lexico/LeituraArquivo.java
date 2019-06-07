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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class LeituraArquivo {
    
    // Funcao que lê o arquivo e armazena as linhas no GerandoTokens
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
            Token token = GerandoTokens.getNextToken();
            TabelaPalavraReservada ts = new TabelaPalavraReservada();
            
            while(token != null){
                if(token.isErro()){ // caso o token esteja com erro
                    if(token.getPadrao() == 45){ // diferencia os erros do padrao 'COMMENT' 
                        if(token.getNome_atributo().charAt(0) == '{')
                            token.setPadrao(49);
                    }
                    gravarArq.println("Erro na linha: "+token.getNumero_linha()+", no padrao: "+ts.padrao.get(token.getPadrao()));
                    gravarArq.println(TiposErro.getDescricao(TiposErro.getTiposErro(token.getPadrao()))+" na linha "+token.getNumero_linha());
                } else {
                    if(token.getPadrao() == 42) // atribuindo para os id para os caracter 'IDENTIFIER' 
                        gravarArq.println("< "+token.getNumero_linha()+", "+token.getNome_atributo()+", token( id "+TabelaSimbolos.getIdToken(token)+" )>");
                    else if(token.getPadrao() > 50) // atribuindo para as palavras reservadas de sinais
                        gravarArq.println("< "+token.getNumero_linha()+", "+token.getNome_atributo()+", token( RELOP,"+ts.padrao.get(token.getPadrao())+" )>");
                    else if(token.getPadrao() >= 73) // atrubui para o padrao de 'WS'
                        gravarArq.println("< "+token.getNumero_linha()+", token( "+ts.padrao.get(token.getPadrao())+" )>");
                    else // para os restantes token
                        gravarArq.println("< "+token.getNumero_linha()+", "+token.getNome_atributo()+", token( "+ts.padrao.get(token.getPadrao())+" )>");
                }
                //pega o proximo token
                token = GerandoTokens.getNextToken();
            }

            arq.close();
        
        } catch (IOException e){
            Logger.getLogger(LeituraArquivo.class.getName()).log(Level.SEVERE, null, e);
        }
}
}
