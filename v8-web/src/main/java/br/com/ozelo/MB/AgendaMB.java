package br.com.ozelo.MB;

import br.com.ozelo.entidades.Evento;
import br.com.ozelo.servico.EventoService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named
@SessionScoped
public class AgendaMB extends BaseMB {

    @Inject
    private OperadorMB operadorMB;
    @EJB
    private EventoService eventoService;
    private ScheduleModel listaEventos;
    private ScheduleEvent eventoSelecionado = new DefaultScheduleEvent();
   
    public AgendaMB() {
    }
    
    @PostConstruct
    public void carregaListaEventos(){
        listaEventos = new DefaultScheduleModel();
        Date hoje = soData(new Date());
        List<Evento> evOper = eventoService.getListEventoByOperador(operadorMB.getOperadorLogado());
        if (evOper != null){
            for (Evento ev : evOper) {
              DefaultScheduleEvent evt = new DefaultScheduleEvent();
              evt.setData(ev.getId());
              evt.setStartDate(ev.getDtInicio());
              evt.setEndDate(ev.getDtFim());
              evt.setTitle(ev.getTitulo());
              evt.setDescription(ev.getDescricao());
              evt.setAllDay(ev.isTodoDia());
              evt.setStyleClass(ev.getCssEvento());
              evt.setEditable(true);
            listaEventos.addEvent(evt);
            if (hoje.after(ev.getDtInicio())||(hoje.before(ev.getDtFim()))){
                
            }
         }
        }
    }

    public ScheduleEvent retornaScheduleEvent(Evento ev){
              DefaultScheduleEvent evt = new DefaultScheduleEvent();
              evt.setData(ev.getId());
              evt.setStartDate(ev.getDtInicio());
              evt.setEndDate(ev.getDtFim());
              evt.setTitle(ev.getTitulo());
              evt.setDescription(ev.getDescricao());
              evt.setAllDay(ev.isTodoDia());
              evt.setStyleClass(ev.getCssEvento());
              evt.setEditable(true);    
        return evt;
    }
    
    public Evento retornaEvento (ScheduleEvent evt){
              Evento ev = new Evento();
              ev.setId((Long) evt.getData());
              ev.setDtInicio(evt.getStartDate());
              ev.setDtFim(evt.getEndDate());
              ev.setTitulo(evt.getTitle());
              ev.setDescricao(evt.getDescription());
              ev.setTodoDia(evt.isAllDay());
              ev.setCssEvento(evt.getStyleClass());
              ev.setOperador(operadorMB.getOperadorLogado());
            return ev;
    }
    
public void eventoClicado(SelectEvent selectEvent){ 
    ScheduleEvent eventoClicado = (ScheduleEvent) selectEvent.getObject();
   eventoSelecionado = retornaScheduleEvent(eventoService.getEventoById((Long) eventoClicado.getData()));
}

public void onDateSelect(SelectEvent selectEvent) {
        eventoSelecionado = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
}

public boolean isDatasCertas(){
    return (eventoSelecionado.getStartDate().getTime() <= eventoSelecionado.getEndDate().getTime());
}

public void doSalvar(){
     if (isDatasCertas()== false){
         createFacesErrorMessage("", "Data Inicial Deve Ser Anterior A Final!");
         return;
     }
     Evento novoEvento = retornaEvento(eventoSelecionado);
    if (eventoSelecionado.getData() == null){
        eventoService.novoEvento(novoEvento);
      } else {
        eventoService.atualizaEvento(novoEvento);
    }
}

public void doDeletar (){
   if (eventoSelecionado.getData() != null){
     Evento novoEvento = retornaEvento(eventoSelecionado);
     eventoService.removeEvento(novoEvento);
     carregaListaEventos();
   }    
}

public void doMover (ScheduleEntryMoveEvent event){
    ScheduleEvent evt = (ScheduleEvent) event;
    eventoService.atualizaEvento(retornaEvento(evt));
}

public void doRedimencionar (ScheduleEntryResizeEvent event){
    ScheduleEvent evt = (ScheduleEvent) event;
    eventoService.atualizaEvento(retornaEvento(evt));
}

    public ScheduleModel getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(ScheduleModel listaEventos) {
        this.listaEventos = listaEventos;
    }

    public ScheduleEvent getEventoSelecionado() {
        return eventoSelecionado;
    }

    public void setEventoSelecionado(ScheduleEvent eventoSelecionado) {
        this.eventoSelecionado = eventoSelecionado;
    }
    
    
}
