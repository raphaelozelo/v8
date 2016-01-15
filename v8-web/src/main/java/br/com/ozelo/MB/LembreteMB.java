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
    private Integer cashNivel = 0;
    private String cashDesc;

    public LembreteMB() {
    }
    
    private Lembrete findByParam(){
        lembreteSelecionado = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
          if(params.get("id") == null) return null;
           Integer id = Integer.parseInt(params.get("id"));
            return lembreteService.getLembreteById(id);
        } 
    
    public void viewModal(){
        System.out.println("lembretes:"+lembretes.size());
    lembreteSelecionado = findByParam();
    if (lembreteSelecionado != null){
    cashNivel = lembreteSelecionado.getNivel();
    cashDesc = lembreteSelecionado.getDescricao();
               System.out.println("sel:"+lembreteSelecionado.getId());
        System.out.println("lembretes:"+lembretes.size());
    RequestContext.getCurrentInstance().execute("PF('lembreteDialog').show();");
    }
            System.out.println("sel:"+lembreteSelecionado.getId());
        System.out.println("lembretes:"+lembretes.size());
    }
    
    public void cleanCash(){
    lembreteSelecionado = null;
    lembretes = null;
    cashNivel = 0;
    cashDesc = null;
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
    ++i;
    if (i==4) {i=0;}
    lembreteSelecionado.setNivel(i);  
}

public void doDeletar(){
    if (lembreteSelecionado != null){
    lembreteService.removeLembrete(lembreteSelecionado);
    }
    cleanCash();
 //   RequestContext.getCurrentInstance().execute("PF('lembreteDialog').hide();");
 RequestContext.getCurrentInstance().update("form:lembreteView");
    RequestContext.getCurrentInstance().update("form:lembreteTable");
}

    public void okLembrete(){
       if (lembreteSelecionado != null){
      if (!(cashNivel.equals(lembreteSelecionado.getNivel()))||!(cashDesc.equals(lembreteSelecionado.getDescricao()))){     
        lembreteService.atualizaLembrete(lembreteSelecionado);
      }
      cleanCash();
  //    RequestContext.getCurrentInstance().execute("PF('lembreteDialog').hide();");
  RequestContext.getCurrentInstance().update("form:lembreteView");
      RequestContext.getCurrentInstance().update("form:lembreteTable");
    }
    }
    
    //******************************************************************************************************************************************************
    public List<Lembrete> getLembretes() {
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
