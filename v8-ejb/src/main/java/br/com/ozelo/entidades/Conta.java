package br.com.ozelo.entidades;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Conta implements java.io.Serializable {
    private static final long serialVersionUID = 1L;  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column (length = 30)
    @NotNull (message="Informe a Descrição da Conta")
    private String descricao;
    @Column (length = 10)
    private String tipo;
    @Column (length = 30)
    private String banco;
    @Column (length = 4)
    private String agencia;
    @Column (length = 10)
    private String conta;
    @NotNull
    @Column(precision = 16, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;
    
    
    public Conta() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conta other = (Conta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }
    
     @Override
    public String toString() {
        return descricao;
    }
}
