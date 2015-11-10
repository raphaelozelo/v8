package br.com.ozelo.DAO;

import br.com.ozelo.entidades.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

public class ClienteDao extends BasicDao {
      private static final long serialVersionUID = 1L;
      
    public ClienteDao(EntityManager entityManager) {
        super(entityManager);
    }

    public Cliente getClienteById(Integer id) {
        return getEntity(Cliente.class,id);
    }
    
    public Cliente atualizaCliente(Cliente cliente) {
        return setEntity(Cliente.class, cliente);
    }
    
    public void removeCliente(Cliente cliente) {
        removeEntity(cliente);
    }
    
    public Cliente novoCliente (Cliente cliente) {
        addEntity(Cliente.class, cliente);
        return cliente;
    }

    public List<Cliente> getListClientes(){
        return getPureList(Cliente.class, "select c from Cliente c order by c.nome");
    }
    
    public List<Cliente> getListClientesPF() {
        return getPureList(Cliente.class, "select c from Cliente where c.f_j = 'F' order by c.nome");
    }
    
    public List<Cliente> getListClientesPJ() {
        return getPureList(Cliente.class, "select c from Cliente where c.f_j = 'J' order by c.nome");
    }
    
    public List<Cliente> getListClientesFornecedores() {
        return getPureList(Cliente.class, "select c from Cliente where c.fornecedor = true order by c.nome");
    }
    
    public Cliente getClienteByCPF(String cpf){
      return getClienteByDoc("select c from Cliente c where c.cpf = ?1", cpf);
    }
    
    public Cliente getClienteByCNPJ(String cnpj){
      return getClienteByDoc("select c from Cliente c where c.cnpj = ?1", cnpj);
    }
    
    private Cliente getClienteByDoc(String documento, String sql) {
        List <Cliente> listaClientes = getPureList(Cliente.class, sql, documento);
        if (listaClientes.isEmpty()) { return null;}
        return listaClientes.get(0);
       }

    public List<Cliente> getListVinculo(Cliente cliente) {
        return getPureList(Cliente.class, "select c from Cliente where c.vinculo = ?1 order by c.nome", cliente);
    }

    
    
}