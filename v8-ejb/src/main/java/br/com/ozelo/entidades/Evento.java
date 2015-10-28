package br.com.ozelo.entidades;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
public class Evento implements java.io.Serializable {
     private static final long serialVersionUID = 1L;  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (length = 50)
    @NotNull (message="Informe o Título do Evento!")
    private String titulo;
    @Column
    @NotNull
     @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtInicio;
    @Column
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtFim;
    @Column
    private String descricao;
    @Column
    private boolean todoDia;
    @Column
    private String cssEvento;
    @NotNull
    @ManyToOne
    @JoinColumn(name="OPERADOR_ID", referencedColumnName = "id")
    private Operador operador;

    public Evento() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isTodoDia() {
        return todoDia;
    }

    public void setTodoDia(boolean todoDia) {
        this.todoDia = todoDia;
    }

    public String getCssEvento() {
        return cssEvento;
    }

    public void setCssEvento(String cssEvento) {
        this.cssEvento = cssEvento;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
