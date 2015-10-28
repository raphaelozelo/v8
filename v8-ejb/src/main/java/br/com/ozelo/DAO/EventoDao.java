package br.com.ozelo.DAO;

import br.com.ozelo.entidades.Evento;
import br.com.ozelo.entidades.Operador;
import java.util.List;
import javax.persistence.EntityManager;

public class EventoDao extends BasicDao {
    
    private static final long serialVersionUID = 2L;

    public EventoDao(EntityManager entityManager) {
        super(entityManager);
    }

    public Evento  getEventoById(Long id) {
        return getEntity(Evento.class,id);
    }
    
    public Evento atualizaEvento(Evento evento) {
        return setEntity(Evento.class, evento);
    }
    
    public void removeEvento(Evento evento) {
        removeEntity(evento);
    }
    
    public Evento novoEvento(Evento evento) {
          addEntity(Evento.class, evento);
        return evento;
    }
    
    public List<Evento> getListEventoByOperador(Operador operador) {
        return getPureList(Evento.class, "select e from Evento e where e.operador = ?1", operador);
    }
    
    
}