package br.com.ozelo.servico;


import br.com.ozelo.DAO.EventoDao;
import br.com.ozelo.entidades.Evento;
import br.com.ozelo.entidades.Operador;
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
public class EventoService extends BasicService{
    
    private static final long serialVersionUID = 2L;

    @PersistenceContext (unitName = "AdmCarPU")
    private EntityManager em;
    private EventoDao eventoDao;
    
    @PostConstruct
    @PostActivate
    private void postConstruct() {
        eventoDao = new EventoDao(em);
    }

    public EventoService() {
      }
    
   public Evento getEventoById(Long id) {
        return eventoDao.getEventoById(id);
    } 
   
    public Evento atualizaEvento(Evento evento) {
        return eventoDao.atualizaEvento(evento);
    }
    
    public void removeEvento(Evento evento) {
        eventoDao.removeEvento(evento);
    }
    
    public Evento novoEvento(Evento evento) {
        return eventoDao.novoEvento(evento);
    }
   
    public List<Evento> getListEventoByOperador(Operador operador) {
        return eventoDao.getListEventoByOperador(operador);
    }
    
}