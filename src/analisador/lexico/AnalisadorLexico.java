/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import analisador.sintatico.Producoes;
import analisador.sintatico.TipoErroSintatico;

/**
 *
 * @author aluno
 */
public class AnalisadorLexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    	new TabelaPalavraReservada();
        String arquivo = "";
        
        // pegar o nome do arquivo
        System.out.println("Digite o nome do Arquivo");
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        arquivo = br.readLine();
        boolean funcionou = LeituraArquivo.LeituraArquivoCMP(arquivo);
        
        //verifica se o arquivo foi encontrado em lido
        if(funcionou){
            System.out.println("Arquivo funcionando!\n");
            GerandoTokens.gerarTokens(); // Funcao que cria os tokens
            GerandoTokens.lerTokens(); // Funcao que imprime do console os tokens encontrados
            LeituraArquivo.GravacaodeResultado(); // Funcao que armazena o resultado no arquivo 
            if(!GerandoTokens.getErro()) {
            	TipoErroSintatico erro = Producoes.program();
	            if(erro == TipoErroSintatico.CORRETO) {
	            	System.out.println("\n"+TipoErroSintatico.getZeroErro());
	            	ArrayList<Token> tokensID = TabelaSimbolos.getTokens();
	            	for(Token t : tokensID)
	            		System.out.println(t.imprimirTokenID());
	            } else {
	            	System.out.print(TipoErroSintatico.getDescricao(erro,Producoes.linha,Producoes.nomeToken));
	            	
	            }
            } else 
            	System.out.println("\n\nErro Léxico!");
            System.out.println("\nFim de Programa\n");
            
        } else{
            System.out.println("Arquivo não encontrado!");
        }
        
    }
    
}
