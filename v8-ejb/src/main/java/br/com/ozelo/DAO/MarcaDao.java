package br.com.ozelo.DAO;

import br.com.ozelo.entidades.Marca;
import java.util.List;
import javax.persistence.EntityManager;

public class MarcaDao extends BasicDao {
    
    private static final long serialVersionUID = 2L;

    public MarcaDao(EntityManager entityManager) {
        super(entityManager);
    }

    public Marca  getMarcaById(Integer id) {
        return getEntity(Marca.class,id);
    }
    
    public Marca atualizaMarca(Marca marca) {
        return setEntity(Marca.class, marca);
    }
    
    public void removeMarca(Marca marca) {
        removeEntity(marca);
    }
    
    public Marca novaMarca(Marca marca) {
          addEntity(Marca.class, marca);
        return marca;
    }
    public List<Marca> getListMarca() {
        return getPureList(Marca.class, "select m from Marca m order by m.nome");
    }
    
    public Marca getMarcaByNome(String nomeMarca) {
        return getValorUnico(Marca.class, "select m from Marca m where UPPER(m.nome)= ?1", nomeMarca);
    }
    
    
}
