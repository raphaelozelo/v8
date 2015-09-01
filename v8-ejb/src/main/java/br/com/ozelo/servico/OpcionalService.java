package br.com.ozelo.servico;

import br.com.ozelo.DAO.OpcionalDao;
import br.com.ozelo.entidades.Opcional;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.PostActivate;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OpcionalService extends BasicService{
    
    private static final long serialVersionUID = 2L;

    @PersistenceContext (unitName = "AdmCarPU")
    private EntityManager em;
    private OpcionalDao opcionalDao;
    
    @PostConstruct
    @PostActivate
    private void postConstruct() {
        opcionalDao = new OpcionalDao(em);
    }

    public OpcionalService() {
      }
    
    public Opcional getOpcionalById(Integer id) {
        return opcionalDao.getOpcionalById(id);
    }   
    public Opcional atualizaLembrete(Opcional opcional) {
        return opcionalDao.atualizaOpcional(opcional);
    }
    
    public void removeLembrete(Opcional opcional) {
        opcionalDao.removeOpcional(opcional);
    }
    
    public Opcional novoOpcional(Opcional opcional) {
        return opcionalDao.novoOpcional(opcional);
    }
   
    public List<Opcional> getListOpcional() {
        return opcionalDao.getListOpcional();
    }
    
    public List<Opcional> getListOpcionalNivel() {
        return opcionalDao.getListOpcionalNivel();
    }
    
    
}