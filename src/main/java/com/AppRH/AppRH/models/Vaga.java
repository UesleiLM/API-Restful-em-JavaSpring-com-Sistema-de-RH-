package com.AppRH.AppRH.models;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Vaga implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long codigo;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String descricao;
	
	@NotEmpty
	private String data;
	
	@NotEmpty
	private String salario;
	
	@OneToMany(mappedBy = "vaga", cascade = CascadeType.REMOVE)
	private List<Candidato> candidatos;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}

	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	public void setCandidatos(List<Candidato> candidatos) {
		this.candidatos = candidatos;
	}
	
	
	
}
