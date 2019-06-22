package analisador.sintatico;

public enum TipoErroSintatico {
	CORRETO(0,"O código está lexicamente e sintaticamente correto"),
	BEGIN(3,"Era esperado a palavra reservada 'BEGIN'"),
	CONST(7,"Era esperado a palavra reservada 'CONST'"),
	END(12,"Era esperado a palavra reservada 'END'"),
	FUNCTION(14,"Era esperado a palavra reservada 'FUNCTION'"),
	LABEL(18,"Era esperado a palavra reservada 'LABEL"),
	OF(22,"Era esperado a palavra reservada 'OF'"),
	PROCEDURE(25,"Era esperado a palavra 'PROCEDURE'"),
	PROGRAM(26,"Era esperado a palavra reservada 'PROGRAM'"),
	TYPE(34,"Era esperado a palavra reservada 'TYPE'"),
	VAR(36,"Era esperado a palavra reservada 'VAR'"),
	NUMBER(41,"Era esperado um NUMBER"),
	IDENTIFIER(42,"Era esperado um IDENTIFICADOR"),
	DOTDOT(52,"Era esperado o simbolo '..'"),
	DOT(53,"Era esperado o simbolo '.'"),
	COLON(54,"Era esperado o simbolo ':'"),
	SEMI_COLON(55,"Era esperado o simbolo ';'"),
	LB(57,"Era esperado o simbolo '['"),
	RB(58,"Era esperado o simbolo ']'"),
	LP(59,"Era esperado o simbolo '('"),
	RP(60,"Era esperado o simbolo ')'"),
	EQUAL(61,"Era esperado o simbolo '='");
	
	private final int codigo;
	private final String descricao;
	
	private TipoErroSintatico(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static String getDescricao(TipoErroSintatico status, int linha) {
		return status.descricao + " na linha "+linha;
	}
	public static int getCodigo(TipoErroSintatico status) {
		return status.codigo;
	}
}
