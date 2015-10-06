package br.com.ozelo.MB;

import br.com.ozelo.entidades.Operador;
import br.com.ozelo.servico.OperService;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;        
        
@Named
@SessionScoped
public class OperadorMB extends BaseMB{
    
    @EJB
    private OperService operadorService;
    
    private Operador operadorLogado;
   
    private static final String OPERADOR_NOVO_CADASTRO = "/adm/diretor/config/operadorNovoCadastro.xhtml?faces-redirect=true";
    private static final String OPERADOR_CADASTRO = "/adm/diretor/config/operadorCadastro.xhtml?faces-redirect=true";
    private static final String OPERADOR_LISTA = "/adm/diretor/config/listOperador.xhtml?faces-redirect=true";
    private static final String OPERADOR_DEMITIDO = "/adm/diretor/config/operadorDemitido.xhtml?faces-redirect=true";
    private static final String OPERADOR_TROCA_SENHA = "/adm/config/trocaSenha.faces?faces-redirect=true";
    private String userIcon;
    private Operador operadorSelecionado;
    private Operador operadorSelecionadoInativo;
    private Operador operadorView;
    private Boolean isEditar = false;
    
    @NotNull(message = "Operador/Senha Inválidos!")
    @NotEmpty(message = "Operador/Senha Inválidos!")
    private String apelido;
    @NotNull(message = "Operador/Senha Inválidos!")
    @NotEmpty(message = "Operador/Senha Inválidos!")
    private String password;
    
    @NotNull
    @NotEmpty(message = "Você precisa especificar uma senha")
    @Length(min = 6, max = 20, message = "Sua senha deve conter no minimo 6 caracteres e máximo 20 caracteres.")
    private String newPassword;
    
    private List<Operador> operadoresAtivos;
    private List<Operador> operadoresInativos;
    private List<Operador> operadoresApelidos;
    private List<Operador> aniversariantes;
    private Boolean euAniversariante = false;
    private List <Integer> nAlertas = new ArrayList();
    private List <String> cssAlertas = new ArrayList(); 
    
    public OperadorMB() {
    }
    
    // [0] Lembretes, [1] Agenda,[2]Pendecias Veículos
    // [3] Pendencias Site, [4] Contas À Pagar, [5] À Receber
    public void contarAlertas(){
        System.out.println("operador ja logou  "+operadorLogado.getId());
      nAlertas = operadorService.getAlertas(operadorLogado);
      for (Integer i=0; i < nAlertas.size(); i++){
        if (nAlertas.get(i)>0){ cssAlertas.add("animated swing infinite");
        }else{cssAlertas.add("");
        }
      }
       }
    
    @SuppressWarnings("empty-statement")
    public String doEditarAtivo(){
        if (operadorSelecionado == null)  return null;
       isEditar = true;
        operadorView = operadorSelecionado;
        return OPERADOR_CADASTRO;
    }
    
    public String doFinishEditaAtido(){
        if (existsViolationsForJSF(operadorView)){
    return OPERADOR_CADASTRO;
       } 
       operadorService.atualizaOperador(operadorView);
      return OPERADOR_LISTA;   
    }
    
    public String doViewDemitido(){
        if (operadorSelecionadoInativo == null)  return null; 
           operadorView = operadorSelecionadoInativo;
        
       if (operadorSelecionadoInativo.isDemitido()){
          return OPERADOR_DEMITIDO;
       }else{
           isEditar = true;
           return OPERADOR_CADASTRO;
       }
    }
    
    public String doDemitir(Operador operadorDemitir){
        if (operadorDemitir == null) return null;
       operadorDemitir.setAtivo(false);
       operadorDemitir.setNivel(0);
       operadorDemitir.setSenha("######");
       operadorDemitir.setDemitido(true);
       operadorService.atualizaOperador(operadorDemitir);
       operadoresInativos = null;
       return OPERADOR_LISTA;
    }
    
    public String doNovoOperador(){
        isEditar = false;
        operadorSelecionado = null;
        operadorView = new Operador ();
        operadorView.iniciar();
     return OPERADOR_NOVO_CADASTRO;    
    }
    public String addOperador(){
       if (existsViolationsForJSF(operadorView)){
    return OPERADOR_NOVO_CADASTRO; 
       } 
        operadorService.novoOperador(operadorView);
         fabricarLog(operadorLogado, 2, "Cadastrado Novo Operador: ["+operadorView.getApelido()+"]"+operadorView.getNome(), operadorView);
        operadoresAtivos = null;
       return OPERADOR_LISTA; 
    }
    
  public String doLogin() {
        operadorLogado = null;
        operadorLogado = operadorService.getOperadorByLoginPassword(apelido, password);
        if (operadorLogado == null) {
            createFacesErrorMessage("Operador/Senha Inválidos!",null);
            return null;
        } else {
            if ("123456".equals(password)){
               password=null;
               isEditar = true;
               fabricarLog(operadorLogado, 1, "Operador Logou com Senha Provisória", null);
               return  OPERADOR_TROCA_SENHA;
           }
            password=null;
            Integer i = operadorLogado.getNrAcessos();
            i++;
            operadorLogado.setNrAcessos(i);
            operadorLogado.setDtUltAcesso(operadorLogado.getDtAcessoAtual());
            operadorLogado.setDtAcessoAtual(new Date());
            operadorService.atualizaOperador(operadorLogado);
            if (operadorLogado.getSexo().equals("M")){
                userIcon = "icon/userMasc.svg"; 
            } else{                   
            userIcon = "icon/userFem.svg";
            }
            fabricarLog(operadorLogado, 1, "Operador Logou o Acesso de nº "+i, null);
            aniversariantes = operadorService.getAniversariantes();
            euAniversariante = aniversariantes.contains(operadorLogado);
            contarAlertas();
           return INDEX_PAGE;
        }
    }  

  public String trocaSenha(){
            if ("123456".equals(newPassword)){
          createFacesErrorMessage("Erro!","Nova Senha Não Pode ser Igual a Senha Provisória!");      
          password=null;
          newPassword=null;
          fabricarLog(operadorLogado, 1, "Operador Tentou Cadastrar Nova Senha com a Provisória Padrão", null);
          return null;
            }
          if (operadorLogado.getSenha().equals(getMd5(password))||(isEditar == true)){
        operadorService.setPassword(operadorLogado.getId(), newPassword);
        password=null;
        newPassword=null;
        fabricarLog(operadorLogado, 1, "Operador Cadastrou Uma Nova Senha", null);
      return INDEX_PAGE;
          } else {
             createFacesErrorMessage("Erro!","Senha Atual Não Confere!");      
              fabricarLog(operadorLogado, 1, "Senha Atual do Operador Não Confere, Foi Negado o Cadastramento de Nova Senha", null);
              password=null;
              newPassword=null;
              return null;
           }
  }
   
  public String editaProprio(){
   operadorService.atualizaOperador(operadorLogado);      
    return INDEX_PAGE;
}
  public String changeAtivo(Operador operadorChange){
      operadorChange.setAtivo(!operadorChange.isAtivo());
      operadorService.atualizaOperador(operadorChange);
      operadoresAtivos = null;
      operadoresInativos = null;
      return OPERADOR_LISTA;
  }

  public String gerarNovaSenha(Operador operNovaSenha){
             FacesContext context = FacesContext.getCurrentInstance();      
      if (operNovaSenha != null){
          operNovaSenha.gerarNovaSenha();
          operadorService.atualizaOperador(operNovaSenha);
        context.addMessage(null, new FacesMessage("Sucesso!",  "Foi Gerado uma Nova Senha Provissória para o Operador: "+operNovaSenha.getApelido()+" - "+operNovaSenha.getNome()) );
        return null;
      }
   context.addMessage(null, new FacesMessage("Erro", "Não foi possível Gerar Nova Senha!"));
   return null;
  }
  
  public String ultimoAcessoOperador(){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy  HH:mm");
    return sdf.format(operadorLogado.getDtUltAcesso());
  }
  
    public Operador buscaOperadorId (int id){
      return  operadorService.getOperadorById(id);
    } 
   
    public Operador buscaOperadorApelido (String apelido){
        System.out.println("44"+apelido);
      return  operadorService.getOperadorByApelido(apelido);
    } 
  
    public Operador getOperadorLogado() {
        return operadorLogado;
    }

    public void setOperadorLogado(Operador operadorLogado) {
        this.operadorLogado = operadorLogado;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Operador> getOperadoresAtivos() {
        if (operadoresAtivos == null) {
            setOperadoresAtivos (operadorService.getOperadoresAtivos());
          }
        return operadoresAtivos;
    }

    public void setOperadoresAtivos(List<Operador> operadoresAtivos) {
        this.operadoresAtivos = operadoresAtivos;
    }

    public List<Operador> getOperadoresInativos() {
        if (operadoresInativos == null ){
            setOperadoresInativos (operadorService.getOperadoresInativos());
        }
        return operadoresInativos;
    }

    public void setOperadoresInativos(List<Operador> operadoresInativos) {
        this.operadoresInativos = operadoresInativos;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Operador getOperadorSelecionado() {
        return operadorSelecionado;
    }

    public void setOperadorSelecionado(Operador operadorSelecionado) {
        this.operadorSelecionado = operadorSelecionado;
    }

    public Operador getOperadorSelecionadoInativo() {
        return operadorSelecionadoInativo;
    }

    public void setOperadorSelecionadoInativo(Operador operadorSelecionadoInativo) {
        this.operadorSelecionadoInativo = operadorSelecionadoInativo;
    }

    public Operador getOperadorView() {
        return operadorView;
    }

    public void setOperadorView(Operador operadorView) {
        this.operadorView = operadorView;
    }

    public Boolean isIsEditar() {
        return isEditar;
    }

    public void setIsEditar(Boolean isEditar) {
        this.isEditar = isEditar;
    }
    
    public String getUserIcon() {
        return userIcon;
    }

    public List<Operador> getOperadoresApelidos() {
        if (operadoresApelidos == null ){
            setOperadoresApelidos (operadorService.getApelidos());
        }
        return operadoresApelidos;
    }

    public void setOperadoresApelidos(List<Operador> operadoresApelidos) {
        this.operadoresApelidos = operadoresApelidos;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }    

    public List<Operador> getAniversariantes() {
        return aniversariantes;
    }

    public void setAniversariantes(List<Operador> aniversariantes) {
        this.aniversariantes = aniversariantes;
    }

    public OperService getOperadorService() {
        return operadorService;
    }

    public void setOperadorService(OperService operadorService) {
        this.operadorService = operadorService;
    }

    public Boolean getEuAniversariante() {
        return euAniversariante;
    }

    public void setEuAniversariante(Boolean euAniversariante) {
        this.euAniversariante = euAniversariante;
    }

    public List<Integer> getnAlertas() {
        return nAlertas;
    }

    public void setnAlertas(List<Integer> nAlertas) {
        this.nAlertas = nAlertas;
    }

    public List<String> getCssAlertas() {
        return cssAlertas;
    }

    public void setCssAlertas(List<String> cssAlertas) {
        this.cssAlertas = cssAlertas;
    }

    public String getMd5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return digest;
    }  



}
