/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Prioridade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "PrioridadeCtrl")
@SessionScoped
public class PrioridadeControllerBean {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();
    private Prioridade prioridade;
    private Prioridade prioridade_inserir;
    private List<Prioridade> prioridades = new ArrayList<>();

    /**
     * Creates a new instance of PrioridadeControllerBean
     */
    public PrioridadeControllerBean() {
    }

    public Prioridade getPrioridade() {
        if (prioridade == null) {
            prioridade = new Prioridade();
        }
        return prioridade;
    }

    public Prioridade getPrioridade_inserir() {
        if (this.prioridade_inserir == null) {
            this.prioridade_inserir = new Prioridade();
        }
        return prioridade_inserir;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public void setPrioridade_inserir(Prioridade prioridade_inserir) {
        this.prioridade_inserir = prioridade_inserir;
    }

    public void setPrioridades(List<Prioridade> prioridades) {
        this.prioridades = prioridades;
    }

    public List<Prioridade> getPrioridades() {
        Collection<Prioridade> e = new ArrayList();
        e = em.createNamedQuery("Prioridade.findAll").getResultList();
        this.prioridades = new ArrayList(e);
        return prioridades;
    }

    public void adicionar() {

        try {

            em.getTransaction().begin();
            this.prioridade_inserir.setDescricao(this.prioridade_inserir.getDescricao().toUpperCase());
            em.persist(prioridade_inserir);
            em.getTransaction().commit();
            getPrioridades();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Prioridade " + prioridade_inserir.getDescricao() + " foi registrada com Sucesso!", ""));
            this.prioridade_inserir = new Prioridade();

        } catch (Throwable t) {
            t.printStackTrace();
            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A Prioridade já existe."));
            }
        }
    }

     public void excluir() {

        try {

            Prioridade temp = this.prioridade;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getPrioridades();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Prioridade " + prioridade.getDescricao() + " foi excluída com Sucesso!", ""));
            this.prioridade = new Prioridade();

        } catch (Throwable t) {
            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão da Prioridade.", ""));
        }

    }

    public void preparaInsercao() {
        this.prioridade = new Prioridade();
        this.prioridades = new ArrayList<>();
    }

}
