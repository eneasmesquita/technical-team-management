/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.HistoricoSituacao;
import java.util.ArrayList;
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
@ManagedBean(name = "HistSituacaoCtrl")
@SessionScoped
public class HistSituacaoControllerBean {

    /**
     * Creates a new instance of HistSituacaoControllerBean
     */
    
    
    private HistoricoSituacao historicoSituacao;
    private List<HistoricoSituacao> historicoSituacao_list = new ArrayList<HistoricoSituacao>();

    public HistSituacaoControllerBean() {
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();

    public HistoricoSituacao getHistoricoSituacao() {
        if(historicoSituacao == null){
            this.historicoSituacao = new HistoricoSituacao();
        }
        return historicoSituacao;
    }

    public void setHistoricoSituacao(HistoricoSituacao historicoSituacao) {
        this.historicoSituacao = historicoSituacao;
    }

    public List<HistoricoSituacao> getHistoricoSituacao_list() {
        return historicoSituacao_list;
    }

    public void setHistoricoSituacao_list(List<HistoricoSituacao> historicoSituacao_list) {
        this.historicoSituacao_list = historicoSituacao_list;
    }

    public EntityManager getEm() {
        return em;
    }
    
    public void adicionar(){
    }
    
    public void alterar(){
    }
    
    public void excluir(){
    }
    
    public void preparaInsercao(){
    }
}
