package analisador.sintatico;

public enum TipoErroSintatico {
	CORRETO(0,"O código está lexicamente e sintaticamente correto"),
	BEGIN(3,"Era esperado a palavra reservada 'BEGIN'"),
	CONST(7,"Era esperado a palavra reservada 'CONST'"),
	DO(9,"Era esperado a palavra reservada 'DO'"),
	DOWNTO_TO(10,"Era esperado a palavra reservada 'DOWNTO' ou 'TO'"),
	END(12,"Era esperado a palavra reservada 'END'"),
	FOR(13,"Era esperado a palavra reservada 'FOR'"),
	FUNCTION(14,"Era esperado a palavra reservada 'FUNCTION'"),
	GOTO(15,"Era esperado a palavra reservada 'GOTO'"),
	IF(16,"Era esperado a palavra reservada 'IF'"),
	LABEL(18,"Era esperado a palavra reservada 'LABEL"),
	OF(22,"Era esperado a palavra reservada 'OF'"),
	PROCEDURE(25,"Era esperado a palavra 'PROCEDURE'"),
	PROGRAM(26,"Era esperado a palavra reservada 'PROGRAM'"),
	REPEAT(29,"Era esperado a palavra reservada 'REPEAT'"),
	THEN(32,"Era esperado a palavra reservada 'THEN'"),
	TYPE(34,"Era esperado a palavra reservada 'TYPE'"),
	UNTIL(35,"Era esperado a palavra reservada 'UNTIL'"),
	VAR(36,"Era esperado a palavra reservada 'VAR'"),
	WHILE(37,"Era esperado a palavra reservada 'WHILE'"),
	WITH(38,"Era esperado a palavra reservada 'WITH'"),
	NUMBER(41,"Era esperado um NUMBER"),
	IDENTIFIER(42,"Era esperado um IDENTIFICADOR"),
	ASSING_OP(51,"Era esperado o simbolo ':='"),
	DOTDOT(52,"Era esperado o simbolo '..'"),
	DOT(53,"Era esperado o simbolo '.'"),
	COLON(54,"Era esperado o simbolo ':'"),
	SEMI_COLON(55,"Era esperado o simbolo ';'"),
	LB(57,"Era esperado o simbolo '['"),
	RB(58,"Era esperado o simbolo ']'"),
	LP(59,"Era esperado o simbolo '('"),
	RP(60,"Era esperado o simbolo ')'"),
	EQUAL(61,"Era esperado o simbolo '='"),
	MULOP(80,"Era esperado operadores '*' ou '/' ou 'DIV' ou 'MOD' ou 'AND'"),
	ADDOP(81,"Era esperado operadores '+' ou '-' ou 'OR'"),
	RELOP(82,"Era esperado operadores '=' ou '<' ou '>' ou '<>' ou '<=' ou '>=' ou 'IN'");
	
	private final int codigo;
	private final String descricao;
	
	private TipoErroSintatico(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	public static String getZeroErro() {
		return TipoErroSintatico.CORRETO.descricao;
	}
	public static String getDescricao(TipoErroSintatico status, int linha, String nome) {
		return status.descricao + " no lugar de '"+nome+"' na linha "+linha;
	}
	public static int getCodigo(TipoErroSintatico status) {
		return status.codigo;
	}
}
