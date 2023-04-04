package com.nielsen.desafiofullstack.app.domain.enums;

public enum TipoPessoa {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoPessoa(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static TipoPessoa toEnum(Integer codigo) {
		
		if (codigo == null) {
			return null;
		} 

		for(TipoPessoa item: TipoPessoa.values()) {
			
			if (codigo.intValue() == item.getCodigo()) {
				return item;
			}
		}
		
		throw new IllegalArgumentException("Tipo Pessoa inválido - id: " + codigo.intValue());
	}
}
