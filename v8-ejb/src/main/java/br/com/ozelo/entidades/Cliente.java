package br.com.ozelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    @NotNull
    @Size (min = 2, max= 120, message="Nome do Cliente Deve Ter Entre 2 à 120 Caracteres")
    @OrderBy ("nome")
    private String nome;
    @Column
    @Size(max = 25, message="Nome do Cliente deve Ter Entre 2 à 25 Caracteres")
    private String apelido;
    @Column
    @NotNull
    @Size(max = 1)
    private String f_j;
    @Column
    @Size(max = 1)
    private String sexo;
    @Column (length=14)
    private String cpf;
    @Column (length=18)
    private String cnpj;
    @Column (length=15)
    private String ie;
    @Column (length=13)
    private String rg;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtCad;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dtNasc;
    @Column    
    private boolean fornecedor;
    @Column (length=100)
    private String endereco;
    @Column (length=25)
    private String bairro;
    @Column (length=9)
    private String cep;
    @Column (length=50)
    private String cidade;
    @Column (length=2)
    private String uf;
    @Column
    private Integer star;
    @Column (length=40)
    private String email;
    @Column (length=40)
    private String website;
    @Column (length=40)
    private String contato;
    @Column (length=14)
    private String tel1;
    @Column (length=14)
    private String tel2;
    @Column (length=14)
    private String tel3;
    @Column (length=14)
    private String tel4;
    @Column (length=14)
    private String cel;
    @Column (length=14)
    private String nextel;
    @ManyToOne
    @JoinColumn(name="VINCULO_ID", referencedColumnName = "id")
    private Cliente vinculo;
    @Column
    private Integer nVeicComp;
    @Column
    private Integer nVeicVend;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dtUltNegocio;
    @Lob @Column
    private String obs;

    public Cliente() {
    }
 
    public Cliente novoPF(){
      Cliente novoCli = new Cliente();
      novoCli.setF_j("F");
      novoCli.setFornecedor(false);
      novoCli.setDtCad(new Date());
      novoCli.setSexo("M");
      novoCli.setStar(1);
      novoCli.setCidade("Limeira");
      novoCli.setUf("SP");
      return novoCli;
    }

    public Cliente novoPJ(){
      Cliente novoCli = new Cliente();
      novoCli.setF_j("J");
      novoCli.setFornecedor(false);
      novoCli.setDtCad(new Date());
      novoCli.setStar(1);
      novoCli.setCidade("Limeira");
      novoCli.setUf("SP");
      return novoCli;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getF_j() {
        return f_j;
    }

    public void setF_j(String f_j) {
        this.f_j = f_j;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Date getDtCad() {
        return dtCad;
    }

    public void setDtCad(Date dtCad) {
        this.dtCad = dtCad;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getTel4() {
        return tel4;
    }

    public void setTel4(String tel4) {
        this.tel4 = tel4;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getNextel() {
        return nextel;
    }

    public void setNextel(String nextel) {
        this.nextel = nextel;
    }

    public Cliente getVinculo() {
        return vinculo;
    }

    public void setVinculo(Cliente vinculo) {
        this.vinculo = vinculo;
    }

    public Integer getnVeicComp() {
        return nVeicComp;
    }

    public void setnVeicComp(Integer nVeicComp) {
        this.nVeicComp = nVeicComp;
    }

    public Integer getnVeicVend() {
        return nVeicVend;
    }

    public void setnVeicVend(Integer nVeicVend) {
        this.nVeicVend = nVeicVend;
    }

    public Date getDtUltNegocio() {
        return dtUltNegocio;
    }

    public void setDtUltNegocio(Date dtUltNegocio) {
        this.dtUltNegocio = dtUltNegocio;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
    
}
