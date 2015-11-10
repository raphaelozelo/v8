package br.com.ozelo.servico;

import br.com.ozelo.DAO.ClienteDao;
import br.com.ozelo.DAO.OpcionalDao;
import br.com.ozelo.entidades.Cliente;
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
public class ClienteService extends BasicService{
    
    private static final long serialVersionUID = 2L;

    @PersistenceContext (unitName = "AdmCarPU")
    private EntityManager em;
    private ClienteDao clienteDao;
    
    @PostConstruct
    @PostActivate
    private void postConstruct() {
        clienteDao = new ClienteDao(em);
    }

    public ClienteService() {
      }
    
    public Cliente getClienteById(Integer id) {
        return clienteDao.getClienteById(id);
    }   
    public Cliente atualizaCliente(Cliente cliente) {
        return clienteDao.atualizaCliente (cliente);
    }
    
    public void removeCliente(Cliente cliente) {
        clienteDao.removeCliente(cliente);
    }
    
    public Cliente novoCliente (Cliente cliente) {
        return clienteDao.novoCliente(cliente);
    }
   
    public List<Cliente> getListClientes() {
        return clienteDao.getListClientes();
    }
    
    public List<Cliente> getListClientesPF() {
        return clienteDao.getListClientesPF();
    }
    
    public List<Cliente> getListClientesPJ() {
        return clienteDao.getListClientesPJ();
    }
    
    public List<Cliente> getListClientesFornecedores() {
        return clienteDao.getListClientesFornecedores();
    }
   
    public Cliente getClienteByCPF(String cpf){
        return clienteDao.getClienteByCPF(cpf);
    }
    
    public Cliente getClienteByCNPJ(String cnpj){
        return clienteDao.getClienteByCNPJ(cnpj);
    }
    
    public List<Cliente> getListVinculo(Cliente cliente){
        return clienteDao.getListVinculo(cliente);
    }
}