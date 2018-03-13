/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.TipoEquipamento;
import entities.TiposEquipamentos;
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
import javax.persistence.Query;

/**
 *
 * @author Eneas
 */
@ManagedBean(name = "TipoEquipamentoCtrl")
@SessionScoped
public class TipoEquipamentoControllerBean {

    private TipoEquipamento tipoEquipamento;
    private TipoEquipamento tipoEquipamento_inserir;
    private List<TipoEquipamento> tipoEquipamentos = new ArrayList<>();
    private TiposEquipamentos tiposEquipamentos;
    //private List<TiposEquipamentos> tiposEquipamentosList = new ArrayList<>();

    /**
     * Creates a new instance of TipoEquipamentoControllerBean
     */
    public TipoEquipamentoControllerBean() {
    }

    public TiposEquipamentos getTiposEquipamentos() {
        if (tiposEquipamentos == null) {
            this.tiposEquipamentos = new TiposEquipamentos();
        }
        return tiposEquipamentos;
    }

    public void setTiposEquipamentos(TiposEquipamentos tiposEquipamentos) {
        this.tiposEquipamentos = tiposEquipamentos;
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();

    public TipoEquipamento getTipoEquipamento() {
        if (this.tipoEquipamento == null) {
            this.tipoEquipamento = new TipoEquipamento();
        }
        return tipoEquipamento;
    }

    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public TipoEquipamento getTipoEquipamento_inserir() {
        if (this.tipoEquipamento_inserir == null) {
            this.tipoEquipamento_inserir = new TipoEquipamento();
        }
        return tipoEquipamento_inserir;
    }

    public void setTipoEquipamento_inserir(TipoEquipamento tipoEquipamento_inserir) {
        this.tipoEquipamento_inserir = tipoEquipamento_inserir;
    }

    public List<TipoEquipamento> getTipoEquipamentos() {
        Collection<TipoEquipamento> e = new ArrayList();
        Query query = em.createNamedQuery("TipoEquipamento.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        e = query.getResultList();
        this.tipoEquipamentos = new ArrayList(e);
        return tipoEquipamentos;
    }

    public void setTipoEquipamentos(List<TipoEquipamento> tipoEquipamentos) {
        this.tipoEquipamentos = tipoEquipamentos;
    }

    public void adicionar() {

        try {
            em.getTransaction().begin();
            this.tipoEquipamento_inserir.setDescricao(this.tipoEquipamento_inserir.getDescricao().toUpperCase());
            em.persist(tipoEquipamento_inserir);
            em.getTransaction().commit();
            getTipoEquipamentos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O Tipo de Equipamento " + tipoEquipamento_inserir.getDescricao() + " foi registrado com Sucesso!", ""));
            this.tipoEquipamento_inserir = new TipoEquipamento();

        } catch (Throwable t) {
            t.printStackTrace();
            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de Equipamento já existe.", ""));
            }
        }
    }

    public void excluir() {

        try {
            TipoEquipamento temp = this.tipoEquipamento;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getTipoEquipamentos();
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O Tipo de Equipamento " + tipoEquipamento.getDescricao() + " foi excluído com Sucesso!", ""));
            this.tipoEquipamento = new TipoEquipamento();

        } catch (Throwable t) {
            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão do TipoEquipamento.", ""));
        }

    }

    public void preparaInsercao() {
        this.tipoEquipamento = new TipoEquipamento();
        this.tipoEquipamentos = new ArrayList<>();

    }

}
