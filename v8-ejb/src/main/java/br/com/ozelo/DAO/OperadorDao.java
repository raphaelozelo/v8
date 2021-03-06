package br.com.ozelo.DAO;

import br.com.ozelo.entidades.Lembrete;
import br.com.ozelo.entidades.Operador;
import javax.persistence.EntityManager;
import java.util.List;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.ArrayList;

public class OperadorDao extends BasicDao {
    
    private static final long serialVersionUID = 1L;

    public OperadorDao(EntityManager entityManager) {
        super(entityManager);
    }

    public Operador getOperadorById(Integer id) {
        return getEntity(Operador.class,id);
    }
    
    public Operador atualizaOperador(Operador operador) {
        return setEntity(Operador.class, operador);
    }
    
    public void removeOperador(Operador operador) {
        removeEntity(operador);
    }
    
    public Operador novoOperador(Operador operador) {
        operador.setSenha(getMd5(operador.getSenha()));
        addEntity(Operador.class, operador);
        return operador;
    }

    public Operador getOperadorByLoginPassword(String apelido, String password) {
         if (!"123456".equals(password)){
             password= getMd5(password);
       }
      return getPurePojo(Operador.class, "select o from Operador o where o.ativo = true and o.apelido = ?1 and o.senha = ?2", apelido, password);
      }

    public List<Operador> getListApelidos(){
        return getPureList(Operador.class, "select o from Operador o where o.ativo = true order by o.apelido");
    }
    
    public List<Operador> getListOperadoresAtivos() {
        return getPureList(Operador.class, "select o from Operador o where o.ativo = true order by o.nome");
    }
    
    public List<Operador> getListOperadoresInativos() {
        return getPureList(Operador.class, "select o from Operador o where o.ativo = false order by o.nome");
    }

    public Operador getOperadorByApelido(String apelido) {
        apelido = apelido.toUpperCase();
        List <Operador> listaOperadores = new ArrayList<>();
        System.out.println(listaOperadores.size());
        listaOperadores =  getPureList(Operador.class, "select o from Operador o where UPPER(o.apelido) = ?1",apelido);
        if (listaOperadores.isEmpty()) { return null;}
        return listaOperadores.get(0);
    }
    
    public List<Operador> getListOperadoresTodosbyApelido(String apelido) {
        apelido = apelido.toUpperCase();
        return getPureList(Operador.class, "select o from Operador o where UPPER(o.apelido) =?1 order by o.nome", apelido);
    }
    
    public void setPassword(String newPassword,int idOfOperador) {
        String np = getMd5(newPassword);
        Operador operador = getOperadorById(idOfOperador);
        operador.setSenha(np);
        atualizaOperador(operador);
    }
   public String getMd5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return digest;
    }   

    public List<Operador> getAniversariantes() {
        LocalDate hoje = LocalDate.now();
        return getPureList(Operador.class, "select o from Operador o where o.ativo = true and Extract('Day' From o.dtNasc)=?1 and Extract('Month' From o.dtNasc)=?2",
            hoje.getDayOfMonth(),hoje.getMonthValue());
    }
    
    public List getEventosOperadorHoje(Operador operador){
        LocalDate hoje = LocalDate.now();
        return getPureList(Operador.class, "select e from Evento e where Extract('Day' From e.dtInicio)=?1 and Extract('Month' From e.dtInicio)=?2 and Extract('Year' Fron e.dtInicio)=?3 and e.operador= ?4",
            hoje.getDayOfMonth(),hoje.getMonthValue(),hoje.getYear(), operador);        
    }
    
// [0] Lembretes, [1] Agenda,[2]Pendecias Veículos
    // [3] Pendencias Site, [4] Contas À Pagar, [5] À Receber
    public List<Integer> getAlertas (Operador operador){
        List <Integer> listaAlertas = new ArrayList<>();
      //  listaAlertas.add(retornaSize(getPureList(Lembrete.class, "select l from Lembrete l where l.operador = ?1", operador)));
      listaAlertas.add(0);
        listaAlertas.add(retornaSize(getEventosOperadorHoje(operador)));
        listaAlertas.add(0);
        listaAlertas.add(0);
        listaAlertas.add(0);
        listaAlertas.add(0);
       return listaAlertas;
    }
    
    private Integer retornaSize(List lista){
        Integer size = (lista == null) ? 0 : lista.size();
        return size;
    }
}
