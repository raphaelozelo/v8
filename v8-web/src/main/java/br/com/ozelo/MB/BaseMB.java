package br.com.ozelo.MB;

import br.com.ozelo.entidades.Log4Ozelo;
import br.com.ozelo.entidades.Operador;
import br.com.ozelo.servico.Log4OzeloService;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class BaseMB implements java.io.Serializable {
    
    public static final String INDEX_PAGE = "/adm/index.faces?faces-redirect=true";
    
    private static final long serialVersionUID = 1L;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    @EJB
    private Log4OzeloService log4OzeloService;
    
    protected void createFacesErrorMessage(String titulo, String msg) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, msg);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    protected Set<ConstraintViolation<Serializable>> getViolations(Serializable entidade) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Serializable>> toReturn = validator.validate(entidade);
        return toReturn;
    }
    
    protected boolean existsViolationsForJSF(Serializable entidade) {
        Set<ConstraintViolation<Serializable>> toReturn = getViolations(entidade);
        if (toReturn.isEmpty()) return false;
        for (ConstraintViolation<Serializable> constraintViolation : toReturn) {
            createFacesErrorMessage(constraintViolation.getMessage(),null);
        }
        return true;
    }

     public Date soData(Date data) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(format.format(data));
        } catch (ParseException ex) {
            Logger.getLogger(BaseMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public SimpleDateFormat getSdf() {
        return sdf;
    }
    
    public SimpleDateFormat getSdtF(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm");
    } 

//    public void fabricarLog (Integer nivel, String descricao, Veiculo veiculo, Cliente cliente, Operador operadorAfetado){
    public void fabricarLog (Operador operadorLogado, Integer nivel, String descricao, Operador operadorAfetado){
        if ((nivel == null)&&(descricao == null)) return;
            Log4Ozelo log = new Log4Ozelo ();
            log.setOperador(operadorLogado);
            log.setNivel(nivel);
            log.setDescricao(descricao);
            log.setHorario(new Date());
            log.setOperadorAfetado(operadorAfetado);
        log4OzeloService.novolog4Ozelo(log);
    }
    
     public String nomeSobrenome(String nomeCompleto){
       if (nomeCompleto == null) return null;
       String[] s = nomeCompleto.trim().split(" ");  
       String retorno = s[0];
       if (s.length > 0) retorno=retorno+" "+s[s.length-1];  
       return retorno;
     }
     
     public Integer idade(Date dtNascimento){
         Calendar cal = Calendar.getInstance();  
         cal.setTime(dtNascimento);  
         Integer ano = cal.get(Calendar.YEAR); 
         cal = Calendar.getInstance(); 
         return cal.get(Calendar.YEAR)-ano;
     }
}