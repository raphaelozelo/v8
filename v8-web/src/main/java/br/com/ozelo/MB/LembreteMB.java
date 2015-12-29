package br.com.ozelo.MB;

import br.com.ozelo.entidades.Lembrete;
import br.com.ozelo.servico.LembreteService;
import java.util.List;
import javax.ejb.EJB;
import java.util.Date;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;


@Named
@SessionScoped
public class LembreteMB extends BaseMB{
 
    @EJB
    private LembreteService lembreteService;
    
    @Inject
    private OperadorMB operadorMB;
 
    private List<Lembrete> lembretes; 
    private Lembrete lembreteSelecionado;

    public LembreteMB() {
    }
    
        private Lembrete findByParam(){
     FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
          if(params.get("id") == null) return null;
           Integer id = Integer.parseInt(params.get("id"));
            return lembreteService.getLembreteById(id);
        } 
    
    public void viewModal(){
    lembreteSelecionado = findByParam();
    RequestContext.getCurrentInstance().execute("PF('lembreteDialog').show();");     
    }
    
    public void cleanCash(){
    lembreteSelecionado = null;
    lembretes = null;
    }
    
public void novoLembrete(){
    lembreteSelecionado = new Lembrete();
    lembreteSelecionado.setCorCss("fdLight");
    lembreteSelecionado.setNivel(3);
    lembreteSelecionado.setDataEvento(new Date());
    lembreteSelecionado.setDescricao("");
    lembreteSelecionado.setOperador(operadorMB.getOperadorLogado());
    lembreteSelecionado.setOperadorIndicou(operadorMB.getOperadorLogado());
}

public void addLembrete(){
    lembreteService.novoLembrete(lembreteSelecionado);
    cleanCash(); 
}

public void changeNivel(){
    lembreteSelecionado = findByParam();
    Integer i = lembreteSelecionado.getNivel();
    i = ++i;
    if (i==0) {i=3;}
    lembreteSelecionado.setNivel(i);  
}

public void doDeletar(){
    if (lembreteSelecionado != null){
    lembreteService.removeLembrete(lembreteSelecionado);
    }
    RequestContext.getCurrentInstance().execute("PF('lembreteDialog').hide();");
}

    public void okLembrete(){
       if (lembreteSelecionado != null){
        lembreteService.atualizaLembrete(lembreteSelecionado);
    }  
       RequestContext.getCurrentInstance().execute("PF('lembreteDialog').hide();");
    }
    
    public List<Lembrete> getLembretes() {  //****************************************************************************************************
        if (lembretes == null){
            setLembretes(lembreteService.getListLembrete());
//           setLembretes(lembreteService.getListLembreteByOperador(operadorMB.getOperadorLogado().getId()));
        }
        return lembretes;
    }

    public void setLembretes(List<Lembrete> lembretes) {
        this.lembretes = lembretes;
    }

    public Lembrete getLembreteSelecionado() {
        return lembreteSelecionado;
    }

    public void setLembreteSelecionado(Lembrete lembreteSelecionado) {
        this.lembreteSelecionado = lembreteSelecionado;
    }

    public OperadorMB getOperadorMB() {
        return operadorMB;
    }

    public void setOperadorMB(OperadorMB operadorMB) {
        this.operadorMB = operadorMB;
    }
}
