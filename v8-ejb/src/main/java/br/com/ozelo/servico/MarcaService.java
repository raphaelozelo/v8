package br.com.ozelo.servico;

import br.com.ozelo.DAO.MarcaDao;
import br.com.ozelo.entidades.Marca;
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
public class MarcaService extends BasicService{
    
    private static final long serialVersionUID = 2L;

    @PersistenceContext (unitName = "AdmCarPU")
    private EntityManager em;
    private MarcaDao marcaDao;
    
    @PostConstruct
    @PostActivate
    private void postConstruct() {
        marcaDao = new MarcaDao(em);
    }

    public MarcaService() {
      }
    
    public Marca getMarcaById(Integer id) {
        return marcaDao.getMarcaById(id);
    }   
    public Marca atualizaMarca(Marca marca) {
        return marcaDao.atualizaMarca(marca);
    }
    
    public void removeMarca(Marca marca) {
        marcaDao.removeMarca(marca);
    }
    
    public Marca novaMarca (Marca marca) {
        return marcaDao.novaMarca(marca);
    }
   
    public List<Marca> getListMarca() {
        return marcaDao.getListMarca();
    }
    
    public Marca getMarcaByNome(String nome) {
        return marcaDao.getMarcaByNome(nome);
    }
    
}