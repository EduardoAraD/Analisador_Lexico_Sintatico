package analisador.sintatico;

public enum TipoErroSintatico {
	CORRETO(0,"O código está lexicamente e sintaticamente correto!");
	
	private final int codigo;
	private final String descricao;
	
	private TipoErroSintatico(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static String getDescricao(TipoErroSintatico status) {
		return status.descricao;
	}
	public static int getCodigo(TipoErroSintatico status) {
		return status.codigo;
	}
}
