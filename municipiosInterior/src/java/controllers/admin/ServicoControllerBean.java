/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Servico;
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
 * @author eneas
 */
@ManagedBean(name = "ServicoCtrl")
@SessionScoped
public class ServicoControllerBean {

    /**
     * Creates a new instance of ServicoControllerBean
     */
    private Servico servico;
    private Servico servico_inserir;
    private List<Servico> servicos = new ArrayList<Servico>();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();

    public ServicoControllerBean() {
    }

    public Servico getServico() {
        if (this.servico == null) {
            this.servico = new Servico();
        }
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<Servico> getServicos() {
        Collection<Servico> e = new ArrayList();
        Query query = em.createNamedQuery("Servico.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        e = query.getResultList();
        this.servicos = new ArrayList(e);
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public Servico getServico_inserir() {
        if (this.servico_inserir == null) {
            this.servico_inserir = new Servico();
        }
        return servico_inserir;
    }

    public void setServico_inserir(Servico servico_inserir) {
        this.servico_inserir = servico_inserir;
    }

    public EntityManager getEm() {
        return em;
    }

    public void adicionar() {

        try {

            em.getTransaction().begin();
            this.servico_inserir.setDescricaoServico(this.servico_inserir.getDescricaoServico().toUpperCase());
            em.persist(servico_inserir);
            em.getTransaction().commit();
            getServicos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O Serviço " + servico_inserir.getDescricaoServico() + " foi registrado com Sucesso!", ""));
            this.servico_inserir = new Servico();

        } catch (Throwable t) {
            t.printStackTrace();
            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Serviço já existe."));
            }
        }
    }

    public void alterar() {

        try {
            em.getTransaction().begin();
            Servico temp = this.servico;
            temp.setDescricaoServico(this.servico.getDescricaoServico());
            em.merge(temp);
            em.getTransaction().commit();
            getServicos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Alterado o Serviço: " + servico_inserir.getDescricaoServico()));
            this.servico = new Servico();

        } catch (Throwable t) {
            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na Alteração dos dados do Serviço."));
        }

    }

    public void excluir() {

        try {
            Servico temp = this.servico;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getServicos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O Serviço " + servico.getDescricaoServico() + " foi excluído com Sucesso!", ""));
            this.servico = new Servico();

        } catch (Throwable t) {
            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro na Exclusão do Serviço."));
        }

    }

    public void preparaInsercao() {
        this.servico = new Servico();
        this.servicos = new ArrayList<>();
    }
}
