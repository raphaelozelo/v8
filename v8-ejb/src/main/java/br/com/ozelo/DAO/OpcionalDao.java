package br.com.ozelo.DAO;

import br.com.ozelo.entidades.Opcional;
import java.util.List;
import javax.persistence.EntityManager;

public class OpcionalDao extends BasicDao {
    
    private static final long serialVersionUID = 2L;

    public OpcionalDao(EntityManager entityManager) {
        super(entityManager);
    }

    public Opcional  getOpcionalById(Integer id) {
        return getEntity(Opcional.class,id);
    }
    
    public Opcional atualizaOpcional(Opcional opcional) {
        return setEntity(Opcional.class, opcional);
    }
    
    public void removeOpcional(Opcional opcional) {
        removeEntity(opcional);
    }
    
    public Opcional novoOpcional(Opcional opcional) {
          addEntity(Opcional.class, opcional);
        return opcional;
    }
    public List<Opcional> getListOpcional() {
        return getPureList(Opcional.class, "select o from Opcional o order by o.nome");
    }
    
    public List<Opcional> getListOpcionalNivel() {
        return getPureList(Opcional.class, "select o from Opcional o order by o.nivel,o.nome");
    }
    
    
    
}
