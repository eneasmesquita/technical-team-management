/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import entities.Equipe;
import entities.EquipeMembros;
import entities.Situacao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import login.entity.UsuarioJpa;

/**
 *
 * @author Eneas
 */
@ManagedBean(name = "EquipeCtrl")
@SessionScoped
public class EquipeControllerBean {

    private Equipe equipe;
    private Equipe equipe_inserir;
    private List<Equipe> equipes = new ArrayList<>();
    private EquipeMembros equipe_membros;
    private EquipeMembros equipeMembros_inserir;
    private List<Equipe> equipeMembros = new ArrayList<>();

    boolean exibe_pnlEquipesAdd = false;
    boolean exibe_pnlEquipesJoin = false;
    private String param_busca = "";
    private UsuarioJpa usuario;
    private List<UsuarioJpa> usuarios;
    private Integer paramEquipe = 0;
    private Integer paramUsuario = 10;
    private List<Situacao> situacaoDB = new ArrayList<>();

    /**
     * Creates a new instance of EquipeControllerBean
     */
    public EquipeControllerBean() {
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();

    public Equipe getEquipe() {
        return equipe;
    }

    public UsuarioJpa getUsuario() {
        if (this.usuario == null) {
            this.usuario = new UsuarioJpa();
        }
        return usuario;
    }

    public Integer getParamUsuario() {
        return paramUsuario;
    }

    public void setParamUsuario(Integer paramUsuario) {
        this.paramUsuario = paramUsuario;
    }

    public Integer getParamEquipe() {
        return paramEquipe;
    }

    public void setParamEquipe(Integer paramEquipe) {
        this.paramEquipe = paramEquipe;
    }

    public void setUsuario(UsuarioJpa usuario) {
        this.usuario = usuario;
    }

    public String getParam_busca() {
        return param_busca;
    }

    public void setParam_busca(String param_busca) {
        this.param_busca = param_busca;
    }

    public boolean isExibe_pnlEquipesAdd() {
        return exibe_pnlEquipesAdd;
    }

    public void setExibe_pnlEquipesAdd(boolean exibe_pnlEquipesAdd) {
        this.exibe_pnlEquipesAdd = exibe_pnlEquipesAdd;
    }

    public boolean isExibe_pnlEquipesJoin() {
        return exibe_pnlEquipesJoin;
    }

    public void setExibe_pnlEquipesJoin(boolean exibe_pnlEquipesJoin) {
        this.exibe_pnlEquipesJoin = exibe_pnlEquipesJoin;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Equipe getEquipe_inserir() {
        if (this.equipe_inserir == null) {
            this.equipe_inserir = new Equipe();
        }
        return equipe_inserir;
    }

    public void exibirPainelCadEquipe() {
        this.exibe_pnlEquipesAdd = true;
        this.exibe_pnlEquipesJoin = false;
    }

    public void exibirPainelEquipeJoin() {
        this.exibe_pnlEquipesJoin = true;
        this.exibe_pnlEquipesAdd = false;
    }

    public void limpar() {

        this.exibe_pnlEquipesJoin = false;
        this.exibe_pnlEquipesAdd = false;
        this.equipe_inserir = new Equipe();
        this.equipes.clear();
        this.paramEquipe = 0;
        this.equipeMembros_inserir = new EquipeMembros();
        this.paramUsuario = 10;
        this.usuario = new UsuarioJpa();

    }

    public List<Situacao> getSituacaoDB() {
        
        Collection<Situacao> e = new ArrayList();
        EntityManager em = emf.createEntityManager();
        em.createNamedQuery("Situacao.findAll").getResultList();
        this.situacaoDB = new ArrayList(e);
        return situacaoDB;
        
    }

    public void setSituacaoDB(List<Situacao> situacaoDB) {
        this.situacaoDB = situacaoDB;
    }

    public void setEquipe_inserir(Equipe equipe_inserir) {
        this.equipe_inserir = equipe_inserir;
    }

    public List<UsuarioJpa> getUsuarios() {
        Collection<UsuarioJpa> e = new ArrayList();
        e = em.createNamedQuery("UsuarioJpaJpa.findAll").getResultList();
        this.usuarios = new ArrayList(e);
        return usuarios;
    }

    public void setUsuarios(List<UsuarioJpa> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Equipe> getEquipes() {
        Collection<Equipe> c = new ArrayList<>();
        Query query = em.createNamedQuery("Equipe.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.equipes = new ArrayList(c);
        return equipes;
    }

    public void setEquipes(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    public EquipeMembros getEquipe_membros() {
        return equipe_membros;
    }

    public void setEquipe_membros(EquipeMembros equipe_membros) {
        this.equipe_membros = equipe_membros;
    }

    public EquipeMembros getEquipeMembros_inserir() {
        if (this.equipeMembros_inserir == null) {
            this.equipeMembros_inserir = new EquipeMembros();
        }
        return equipeMembros_inserir;
    }

    public void setEquipeMembros_inserir(EquipeMembros equipeMembros_inserir) {
        this.equipeMembros_inserir = equipeMembros_inserir;
    }

    public List<Equipe> getEquipeMembros() {
        return equipeMembros;
    }

    public void setEquipeMembros(List<Equipe> equipeMembros) {
        this.equipeMembros = equipeMembros;
    }

    public void adicionarEquipe() {
        try {
            em.getTransaction().begin();
            this.equipe_inserir.setDescricao(this.equipe_inserir.getDescricao().toUpperCase());
            em.persist(equipe_inserir);
            em.getTransaction().commit();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Equipe " + equipe_inserir.getDescricao() + " foi registrada com Sucesso!", ""));

            getEquipes();
            this.equipe_inserir = new Equipe();

        } catch (Throwable t) {
            t.printStackTrace();
            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "A Equipe já foi cadastrada.", ""));
            }
        }
    }

    public void excluirEquipe() {
        try {

            Equipe temp = this.equipe;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getEquipes();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Equipe " + equipe.getDescricao() + " foi excluída com Sucesso!", ""));

            this.equipe = new Equipe();
            this.usuario = new UsuarioJpa();

        } catch (Throwable t) {

            t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão da Equipe.", ""));
        }
    }

    public void adicionarMembroEquipe() {
        try {
            em.getTransaction().begin();

            if (this.usuario.getInativo() == true) {
                this.equipeMembros_inserir.setAtivo(false);
            } else {
                this.equipeMembros_inserir.setAtivo(true);
            }
            this.equipeMembros_inserir.setDataParticipacao(new Date());
            this.equipeMembros_inserir.setMatricula(String.valueOf(this.usuario.getMatricula()));
            this.equipeMembros_inserir.setNome(this.usuario.getNomeCompleto());
            this.equipeMembros_inserir.setEquipe(em.find(Equipe.class, paramEquipe));
            em.persist(equipeMembros_inserir);
            em.getTransaction().commit();
            getEquipeMembros();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O Colaborador " + equipeMembros_inserir.getNome() + " "
                    + "foi vinculado a Equipe " + equipeMembros_inserir.getEquipe().getDescricao() + " com sucesso!", ""));

            this.equipeMembros_inserir = new EquipeMembros();
            this.paramUsuario = 10;
            this.paramEquipe = 0;
            this.usuario = new UsuarioJpa();

        } catch (Throwable t) {

            t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O Colaborador já faz parte da Equipe Selecionada!", ""));
        }
    }
    
    public void excluirMembroEquipe(){
        try {

            EquipeMembros temp = this.equipe_membros;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getEquipes();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O Colaborador " + equipe_membros.getNome() + " "
                    + "foi desvinculado da Equipe "+equipe_membros.getEquipe().getDescricao()+" com Sucesso!", ""));
            this.equipe_membros = new EquipeMembros();

        } catch (Throwable t) {

            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao desvinculador o colaborador da Equipe!", ""));
        }
        
    }

    public List<String> completaNomeColaborador(String q) {
        Collection<String> c = new ArrayList<>();
        Query query = em.createQuery("SELECT DISTINCT u.nomeCompleto FROM UsuarioJpa u WHERE u.nomeCompleto LIKE '" + q.toUpperCase() + "%' ORDER BY u.nomeCompleto");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        return new ArrayList<>(c);
    }

    public void carregaDadosColaborador() {
        if (paramUsuario != 10) {
            this.usuario = (UsuarioJpa) em.createQuery("SELECT u FROM UsuarioJpa u WHERE u.matricula = " + this.paramUsuario + " AND u.matricula <> -1 and u.existe <> FALSE").getSingleResult();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO NOS DADOS DO COLABORADOR, ENTRE EM CONTATO COM A GTI!", ""));
        }
    }

}
