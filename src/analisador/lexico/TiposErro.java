/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisador.lexico;

/**
 * Pagina de erros do sistema, por enquanto so erros léxicos
 * @author Eduardo
 */
public enum TiposErro { // associa o erro do padrao ao seu id 
    DESCONHECIDO(-1,"Caractere informado não existente no alfabeto"),
    STRING(44,"Era esperado '"),
    COMMENT1(45,"Era esperado '*)'"),
    COMMENT2(49,"Era esperado '}'");
    
    
    private final int codigo;
    private final String descricao;

    private TiposErro(int codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
    }
    
    public static int getCodigo(TiposErro status){
		return status.codigo;
    }
	
    public static String getDescricao(TiposErro status){
        return status.descricao;
    }
	
    public static TiposErro getTiposErro(int codigo) throws IllegalArgumentException{
        if(codigo == -1){
            return TiposErro.DESCONHECIDO;
        }
        if(codigo == 44){
            return TiposErro.STRING;
        }
        if(codigo == 45){
            return TiposErro.COMMENT1;
        }
        if(codigo == 49){
            return TiposErro.COMMENT2;
        }
        
        throw new IllegalArgumentException();
    }
    
}
