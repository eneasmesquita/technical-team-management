/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Marca;
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
@ManagedBean(name = "MarcaCtrl")
@SessionScoped
public class MarcaControllerBean {

    private Marca marca;
    private Marca marca_inserir;
    private List<Marca> marcas = new ArrayList<>();
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();

    /**
     * Creates a new instance of MarcaControllerBean
     */
    public MarcaControllerBean() {
    }

    public Marca getMarca() {
        if (this.marca == null) {
            this.marca = new Marca();
        }
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Marca> getMarcas() {
        Collection<Marca> e = new ArrayList();
        Query query = em.createNamedQuery("Marca.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        e = query.getResultList();
        this.marcas = new ArrayList(e);
        return marcas;
    }

    public Marca getMarca_inserir() {
        if (this.marca_inserir == null) {
            this.marca_inserir = new Marca();
        }
        return marca_inserir;
    }

    public void setMarca_inserir(Marca marca_inserir) {
        this.marca_inserir = marca_inserir;
    }

    public void adicionar() {

        try {
            em.getTransaction().begin();
            this.marca_inserir.setDescricao(this.marca_inserir.getDescricao().toUpperCase());
            em.persist(marca_inserir);
            em.getTransaction().commit();
            getMarcas();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Marca " + marca_inserir.getDescricao() + " foi registrada com Sucesso!", ""));
            this.marca_inserir = new Marca();

        } catch (Throwable t) {

            t.printStackTrace();
            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Marca já existe.", ""));
            }
        }
    }

    public void excluir() {

        try {
            Marca temp = this.marca;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getMarcas();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Marca " + marca.getDescricao() + " foi excluída com Sucesso!", ""));
            this.marca = new Marca();

        } catch (Throwable t) {
            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão da Marca.", ""));
        }

    }

    public void preparaInsercao() {
        this.marca = new Marca();
        this.marcas.clear();
    }

}
