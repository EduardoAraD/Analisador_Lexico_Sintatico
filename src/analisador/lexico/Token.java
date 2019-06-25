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
    private boolean criado;
    private boolean utilizado;
    private int tipoID;
        
    public Token() {
        numero_linha = 0;
        padrao = 0;
        nome_atributo = "";
        tipoID = -1;
        erro = false;
        criado = false;
        utilizado = false;
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
    
    public boolean isCriado() {
		return criado;
	}

	public void setCriado(boolean criado) {
		this.criado = criado;
	}

	public boolean isUtilizado() {
		return utilizado;
	}

	public void setUtilizado(boolean utilizado) {
		this.utilizado = utilizado;
	}

	public int getTipoID() {
		return tipoID;
	}

	public void setTipoID(int tipoID) {
		this.tipoID = tipoID;
	}
    
    public String imprimir(){ // imprime os atributos do token
        //TabelaPalavraReservada ts = new TabelaPalavraReservada();
    	String imprimirToken = "";
        if(this.erro){ // imprime os Token com erros
            if(padrao == 45){ // resolvendo a duplicidade do erro do Comment
                if(this.nome_atributo.charAt(0) == '{')
                    padrao = 49;
            }    
            imprimirToken = "Erro na linha: "+numero_linha+", no padrao: "+TabelaPalavraReservada.padrao.get(padrao);
            imprimirToken += TiposErro.getDescricao(TiposErro.getTiposErro(padrao))+" na linha "+numero_linha;
        } else {
            if(padrao == 42) // padrao IDENTIFIER
                imprimirToken = "< "+numero_linha+", "+nome_atributo+", token( id "+TabelaSimbolos.getIdToken(this)+" )>";
            else if(padrao > 50) // padrao OPERADOR
                imprimirToken = "< "+numero_linha+", "+nome_atributo+", token( RELOP,"+TabelaPalavraReservada.padrao.get(padrao)+" )>";
            else if(padrao >= 73) // padrao DELIM
                imprimirToken = "< "+numero_linha+", token( "+TabelaPalavraReservada.padrao.get(padrao)+" )>";
            else // PALAVRAS RESERVADAS
                imprimirToken = "< "+numero_linha+", "+nome_atributo+", token( "+TabelaPalavraReservada.padrao.get(padrao)+" )>";
        }
        return imprimirToken;
    }
    
    public String imprimirTokenID() {
    	//TabelaPalavraReservada ts = new TabelaPalavraReservada();
    	String dadosToken = "< "+numero_linha+", "+nome_atributo+", token( id "+TabelaSimbolos.getIdToken(this)+" ),";
    	dadosToken += " Tipo: "+TabelaPalavraReservada.padrao.get(tipoID)+", ";
    	if(criado) dadosToken += "Criado: SIM, ";
    	else dadosToken += "Criado: NÂO, ";
    	if(utilizado) dadosToken += "Utilizado: SIM >";
    	else dadosToken += "Utilizado: NÃO >";
    	return dadosToken;
    }
    
}
