/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers.admin;

import entities.Situacao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "SituacaoCtrl")
@SessionScoped
public class SituacaoControllerBean {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private Situacao situacao;
    private List<Situacao> situacoes = new ArrayList<>();

    /**
     * Creates a new instance of SituacaoControllerBean
     */
    public SituacaoControllerBean() {
    }

    public Situacao getSituacao() {
        if(situacao == null){
            situacao = new Situacao();
        }
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public List<Situacao> getSituacoes() {
        Collection<Situacao> e = new ArrayList();
        EntityManager em = emf.createEntityManager();
        em.createQuery("SELECT s FROM Situacao s ORDER BY s.descricao ASC").getResultList();
        this.situacoes = new ArrayList(e);
        return situacoes;
    }

    
}
