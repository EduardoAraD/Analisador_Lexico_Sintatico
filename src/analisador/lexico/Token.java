/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

/**
 * 
 * @author Eduardo
 */
public class Token {
    private int numero_linha;
    private int padrao;
    private String nome_atributo;
    private boolean erro;

    
    public Token() {
        numero_linha = 0;
        padrao = 0;
        nome_atributo = "";
        erro = false;
    }

    public int getNumero_linha() {
        return numero_linha;
    }

    public void setNumero_linha(int numero_linha) {
        this.numero_linha = numero_linha;
    }

    public int getPadrao() {
        return padrao;
    }

    public void setPadrao(int padrao) {
        this.padrao = padrao;
    }

    public String getNome_atributo() {
        return nome_atributo;
    }

    public void setNome_atributo(String nome_atributo) {
        this.nome_atributo = nome_atributo;
    }
    
    public boolean isErro() {
        return erro;
    }

    public void setErro(boolean erro) {
        this.erro = erro;
    }
    
    public void imprimir(){ // imprime os atributos do token
        TabelaPalavraReservada ts = new TabelaPalavraReservada();
        if(this.erro){ // imprime os Token com erros
            if(padrao == 45){ // resolvendo a duplicidade do erro do Comment
                if(this.nome_atributo.charAt(0) == '{')
                    padrao = 49;
            }    
            System.out.println("Erro na linha: "+numero_linha+", no padrao: "+ts.padrao.get(padrao));
            System.out.println(TiposErro.getDescricao(TiposErro.getTiposErro(padrao))+" na linha "+numero_linha);
        } else {
            if(padrao == 42) // padrao IDENTIFIER
                System.out.println("< "+numero_linha+", "+nome_atributo+", token( id "+TabelaSimbolos.getIdToken(this)+" )>");
            else if(padrao > 50) // padrao OPERADOR
                System.out.println("< "+numero_linha+", "+nome_atributo+", token( RELOP,"+ts.padrao.get(padrao)+" )>");
            else if(padrao >= 73) // padrao DELIM
                System.out.println("< "+numero_linha+", token( "+ts.padrao.get(padrao)+" )>");
            else // PALAVRAS RESERVADAS
                System.out.println("< "+numero_linha+", "+nome_atributo+", token( "+ts.padrao.get(padrao)+" )>");
        }
    }
    
}
