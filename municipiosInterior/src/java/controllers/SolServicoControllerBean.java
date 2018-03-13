/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Equipamento;
import entities.EquipamentoSs;
import entities.Equipe;
import entities.HistoricoSituacao;
import entities.Municipio;
import entities.Prioridade;
import entities.Servico;
import entities.ServicoSs;
import entities.SolicitacaoServico;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import login.controller.LoginBean;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "SolSerCtrl")
@SessionScoped
public class SolServicoControllerBean {

    private List<SolicitacaoServico> solicitacoesSer = new ArrayList<>();
    private SolicitacaoServico solser;
    private SolicitacaoServico solser_inserir;
    private List<EquipamentoSs> eqSsServicos = new ArrayList<>();
    private EquipamentoSs eqSsServico_inserir;
    private List<ServicoSs> servicosSs = new ArrayList<>();
    private ServicoSs servicoSs_inserir;
    private List<Equipamento> equipamentosAddSs = new ArrayList<>();
    private List<Equipe> equipesAddSs = new ArrayList<>();
    private List<Servico> servicosAddSs = new ArrayList<>();
    private HistoricoSituacao registroSituacao;
    private List<HistoricoSituacao> historicoSituacao;
    private boolean solicitacaoServicoEncerrada_Auditoria;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");

    //VARIAVEIS DE FORMULARIO
    //++++++++++++++++++++++++++++++++++++++++++++++++++
    boolean exibe_painel_edit_ss = false;
    boolean no_result_ss = false;
    private long param_numSsOpen = 0;
    private long param_numSs = 0;
    private boolean no_result_add_eq = false;
    private boolean exibe_painel_add_equipamento = false;
    private boolean exibe_dataTable_add_equipamento = false;

    private String observacaoDefeito = "";
    private String defeito;
    private List<String> observacoesDefeitos = new ArrayList<>();
    private List<String> defeitos = new ArrayList<>();

    private Equipamento equipamentoDescartado;
    private Equipe equipeDescartada;
    private Servico servicoDescartado;
    private boolean exibe_botao_gravar = false;
    private int prioridade = 0;
    private int prioridade_aux = 0;
    private String observacao = "";
    private Integer paramEquipe = 0;
    private Integer paramServico = 0;
    private String paramSituacaoAux;
    private List<Servico> servicoAuditoria = new ArrayList<>();
    //++++++++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Creates a new instance of SolServicoControllerBean
     */
    public SolServicoControllerBean() {
    }

    public boolean isExibe_painel_edit_ss() {
        return exibe_painel_edit_ss;
    }

    public String getObservacaoDefeito() {
        return observacaoDefeito;
    }

    public void setObservacaoDefeito(String observacaoDefeito) {
        this.observacaoDefeito = observacaoDefeito;
    }

    public void setExibe_painel_edit_ss(boolean exibe_painel_edit_ss) {
        this.exibe_painel_edit_ss = exibe_painel_edit_ss;
    }

    public boolean isNo_result_ss() {
        return no_result_ss;
    }

    public void setNo_result_ss(boolean no_result_ss) {
        this.no_result_ss = no_result_ss;
    }

    public String getParamSituacaoAux() {
        return paramSituacaoAux;
    }

    public void setParamSituacaoAux(String paramSituacaoAux) {
        this.paramSituacaoAux = paramSituacaoAux;
    }

    public long getParam_numSs() {
        return param_numSs;
    }

    public void setParam_numSs(long param_numSs) {
        this.param_numSs = param_numSs;
    }

    public boolean isNo_result_add_eq() {
        return no_result_add_eq;
    }

    public void setNo_result_add_eq(boolean no_result_add_eq) {
        this.no_result_add_eq = no_result_add_eq;
    }

    public Integer getParamEquipe() {
        return paramEquipe;
    }

    public Integer getParamServico() {
        return paramServico;
    }

    public void setParamServico(Integer paramServico) {
        this.paramServico = paramServico;
    }

    public boolean getSolicitacaoServicoEncerrada_Auditoria() {
        return solicitacaoServicoEncerrada_Auditoria;
    }

    public void setSolicitacaoServicoEncerrada_Auditoria(boolean solicitacaoServicoEncerrada_Auditoria) {
        this.solicitacaoServicoEncerrada_Auditoria = solicitacaoServicoEncerrada_Auditoria;
    }

    public void setParamEquipe(Integer paramEquipe) {
        this.paramEquipe = paramEquipe;
    }

    public Equipe getEquipeDescartada() {
        if (this.equipeDescartada == null) {
            this.equipeDescartada = new Equipe();
        }
        return equipeDescartada;
    }

    public ServicoSs getServicoSs_inserir() {
        if (this.servicoSs_inserir == null) {
            this.servicoSs_inserir = new ServicoSs();
        }
        return servicoSs_inserir;
    }

    public void setServicoSs_inserir(ServicoSs servicoSs_inserir) {
        this.servicoSs_inserir = servicoSs_inserir;
    }

    public void setEquipeDescartada(Equipe equipeDescartada) {
        this.equipeDescartada = equipeDescartada;
    }

    public Servico getServicoDescartado() {
        if (this.servicoDescartado == null) {
            this.servicoDescartado = new Servico();
        }
        return servicoDescartado;
    }

    public void setServicoDescartado(Servico servicoDescartado) {
        this.servicoDescartado = servicoDescartado;
    }

    public List<String> getObservacoesDefeitos() {
        return observacoesDefeitos;
    }

    public void setObservacoesDefeitos(List<String> observacoesDefeitos) {
        this.observacoesDefeitos = observacoesDefeitos;
    }

    public boolean isExibe_painel_add_equipamento() {
        return exibe_painel_add_equipamento;
    }

    public void setExibe_painel_add_equipamento(boolean exibe_painel_add_equipamento) {
        this.exibe_painel_add_equipamento = exibe_painel_add_equipamento;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public List<Servico> getServicoAuditoria() {
        return servicoAuditoria;
    }

    public void setServicoAuditoria(List<Servico> servicoAuditoria) {
        this.servicoAuditoria = servicoAuditoria;
    }

    public List<Equipamento> getEquipamentosAddSs() {
        return equipamentosAddSs;
    }

    public void setEquipamentosAddSs(List<Equipamento> equipamentosAddSs) {
        this.equipamentosAddSs = equipamentosAddSs;
    }

    public List<Equipe> getEquipesAddSs() {
        return equipesAddSs;
    }

    public void setEquipesAddSs(List<Equipe> equipesAddSs) {
        this.equipesAddSs = equipesAddSs;
    }

    public List<Servico> getServicosAddSs() {
        return servicosAddSs;
    }

    public void setServicosAddSs(List<Servico> servicosAddSs) {
        this.servicosAddSs = servicosAddSs;
    }

    public boolean isExibe_dataTable_add_equipamento() {
        return exibe_dataTable_add_equipamento;
    }

    public void setExibe_dataTable_add_equipamento(boolean exibe_dataTable_add_equipamento) {
        this.exibe_dataTable_add_equipamento = exibe_dataTable_add_equipamento;
    }

    public Equipamento getEquipamentoDescartado() {
        if (this.equipamentoDescartado == null) {
            this.equipamentoDescartado = new Equipamento();
        }
        return equipamentoDescartado;
    }

    public void setEquipamentoDescartado(Equipamento equipamentoDescartado) {
        this.equipamentoDescartado = equipamentoDescartado;
    }

    public boolean isExibe_botao_gravar() {
        return exibe_botao_gravar;
    }

    public void setExibe_botao_gravar(boolean exibe_botao_gravar) {
        this.exibe_botao_gravar = exibe_botao_gravar;
    }

    public List<EquipamentoSs> getEqSsServicos() {
        return eqSsServicos;
    }

    public List<ServicoSs> getServicosSs() {
        return servicosSs;
    }

    public void setServicosSs(List<ServicoSs> servicosSs) {
        this.servicosSs = servicosSs;
    }

    public void setEqSsServicos(List<EquipamentoSs> eqSsServicos) {
        this.eqSsServicos = eqSsServicos;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getPrioridade_aux() {
        return prioridade_aux;
    }

    public void setPrioridade_aux(int prioridade_aux) {
        this.prioridade_aux = prioridade_aux;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public HistoricoSituacao getRegistroSituacao() {
        if (this.registroSituacao == null) {
            this.registroSituacao = new HistoricoSituacao();
        }
        return registroSituacao;
    }

    public void setRegistroSituacao(HistoricoSituacao registroSituacao) {
        this.registroSituacao = registroSituacao;
    }

    public List<HistoricoSituacao> getHistoricoSituacao() {
        EntityManager em = emf.createEntityManager();
        Collection<HistoricoSituacao> c = new ArrayList();
        Query query = em.createQuery("SELECT hs FROM HistoricoSituacao hs WHERE hs.solicitacaoServico.id = " + param_numSs + "");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.historicoSituacao = new ArrayList(c);
        return historicoSituacao;
    }

    public void setHistoricoSituacao(List<HistoricoSituacao> historicoSituacao) {
        this.historicoSituacao = historicoSituacao;
    }

    public long getParam_numSsOpen() {
        EntityManager em = emf.createEntityManager();
        try {
            this.param_numSsOpen = (Long) em.createQuery("select MAX(ss.id) from SolicitacaoServico ss").getSingleResult();
            if (param_numSsOpen == param_numSsOpen + 1) {
                return param_numSsOpen;
            } else {
                param_numSsOpen += 1;
                return param_numSsOpen;
            }
        } catch (NullPointerException npe) {
            return param_numSsOpen = 1;

        }
    }

    public void setParam_numSsOpen(long param_numSsOpen) {
        this.param_numSsOpen = param_numSsOpen;
    }

    public SolicitacaoServico getSolser() {
        if (solser == null) {
            solser = new SolicitacaoServico();
        }
        return solser;
    }

    public void setSolser(SolicitacaoServico solser) {
        this.solser = solser;
    }

    public EquipamentoSs getEqSsServico_inserir() {
        if (this.eqSsServico_inserir == null) {
            this.eqSsServico_inserir = new EquipamentoSs();
        }
        return eqSsServico_inserir;
    }

    public void setEqSsServico_inserir(EquipamentoSs eqSsServico_inserir) {
        this.eqSsServico_inserir = eqSsServico_inserir;
    }

    public SolicitacaoServico getSolser_inserir() {
        if (solser_inserir == null) {
            this.solser_inserir = new SolicitacaoServico();
        }
        return solser_inserir;
    }

    public void setSolser_inserir(SolicitacaoServico solser_inserir) {
        this.solser_inserir = solser_inserir;
    }

    public List<SolicitacaoServico> getSolicitacoesSer() {
        EntityManager em = emf.createEntityManager();
        Collection<SolicitacaoServico> e = new ArrayList();
        Query query = em.createQuery("select ss from SolicitacaoServico ss order by ss.id asc");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        e = query.getResultList();
        this.solicitacoesSer = new ArrayList(e);
        return solicitacoesSer;
    }

    public void inserePrioridade() {
        prioridade = this.prioridade;
    }

    public void alterarPrioridade() {
        solser.setPrioridade(solser.getPrioridade());
    }

    public void atualizarHistorico() {
        getHistoricoSituacao();
    }

    public void alterarSituacao() {
        this.solser.setSituacao(this.solser.getSituacao());
    }

    public void carregarEquipamentoSS(ActionListener e) {

        EquipamentoControllerBean ecb = (EquipamentoControllerBean) getManagedBean("EquipamentoCtrl");
        int count = 0;
        for (Equipamento eq : equipamentosAddSs) {
            if (eq.getId() == ecb.getEquipamento().getId()) {
                count++;
            }
        }

        if (count == 0) {
            this.equipamentosAddSs.add(ecb.getEquipamento());
            this.exibe_painel_add_equipamento = false;
            this.exibe_botao_gravar = true;
            ecb.setParamIdEquipamento(0);
            defeitos.add(this.getDefeito().toUpperCase());
            if (this.getObservacaoDefeito().equals("") || this.getObservacaoDefeito() == null) {
                observacoesDefeitos.add("NADA CONSTA");
            } else {
                observacoesDefeitos.add(this.getObservacaoDefeito().toUpperCase());
            }

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O Equipamento já foi inserido na Solicitação de Serviço!", ""));
        }
    }

    public void carregarEquipeSS(ActionListener e) {

        int count = 0;
        for (Equipe equipe : equipesAddSs) {
            if (equipe.getId() == paramEquipe) {
                count++;
            }
        }

        //PARA SUPORTAR APENAS UM ELEMENTO NA LISTA
        if (equipesAddSs.size() < 1) {
            if (count == 0) {

                EntityManager em = emf.createEntityManager();
                Query query = em.createQuery("SELECT e FROM Equipe e WHERE e.id = " + this.paramEquipe + "");
                Equipe equipe_aux = (Equipe) query.getSingleResult();
                this.equipesAddSs.add(equipe_aux);
                this.paramEquipe = 0;
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A Equipe já foi inserida na Solicitação de Serviço!", ""));
            }

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Apenas uma Equipe por Solicitação de Serviço!", ""));
        }

    }

    public void carregarServicosSS(ActionListener e) {

        int count = 0;
        for (Servico servico : servicosAddSs) {
            if (servico.getId() == paramServico) {
                count++;
            }
        }

        if (count == 0) {

            EntityManager em = emf.createEntityManager();
            Query query = em.createQuery("SELECT s FROM Servico s WHERE s.id = " + this.paramServico + "");
            Servico servico_aux = (Servico) query.getSingleResult();
            if (servicosAddSs.isEmpty()) {
                this.servicosAddSs.add(servico_aux);
            } else {
                this.servicosAddSs.add(servicosAddSs.size() - 1, servico_aux);
            }
            this.paramServico = 0;

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O Serviço já foi inserido na Solicitação de Serviço!", ""));
        }
    }

    public void descarregarEquipamentoSS() {

        this.equipamentosAddSs.remove(equipamentoDescartado);

        equipamentoDescartado = new Equipamento();

        if (equipamentosAddSs.isEmpty()) {
            this.exibe_botao_gravar = false;
        }
    }

    public void descarregarEquipeSs() {
        this.equipesAddSs.remove(equipeDescartada);
        equipeDescartada = new Equipe();
    }

    public void descarregarServicosSs() {
        this.servicosAddSs.remove(servicoDescartado);
        servicoDescartado = new Servico();
    }

    public SolicitacaoServico retornaSsAlteracao() {

        EntityManager em = emf.createEntityManager();
        Collection<Equipamento> e = new ArrayList();
        Collection<Servico> se = new ArrayList();
        Collection<Equipe> eq = new ArrayList();

        try {
            Query query = em.createQuery("SELECT ss from SolicitacaoServico ss WHERE ss.id = " + solser.getId() + "");
            solser = (SolicitacaoServico) query.getSingleResult();
            solicitacaoServicoEncerrada_Auditoria = solser.getEncerrada();
            this.paramSituacaoAux = solser.getSituacao();
            query = em.createQuery("SELECT e FROM Equipamento e, EquipamentoSs ess WHERE e.id = ess.equipamento.id AND ess.solicitacaoServico.id = " + solser.getId() + "");
            e = query.getResultList();
            this.equipamentosAddSs = new ArrayList(e);
            query = em.createQuery("SELECT s FROM Servico s, ServicoSs Sss WHERE s.id = Sss.servico.id AND Sss.solicitacaoServico.id = " + solser.getId() + "");
            se = query.getResultList();
            this.servicoAuditoria = new ArrayList(se);
            this.servicosAddSs = new ArrayList(se);
            query = em.createQuery("SELECT e FROM Equipe e, SolicitacaoServico ss WHERE e.id = ss.equipe.id AND ss.id = " + solser.getId() + "");
            eq = query.getResultList();
            this.equipesAddSs = new ArrayList(eq);
            this.exibe_painel_edit_ss = true;
            this.no_result_ss = false;
            return solser;

        } catch (NoResultException | NullPointerException nre) {
            this.no_result_ss = true;
            this.exibe_painel_edit_ss = false;
            return null;
        }

    }

    public void adicionar() {

        if (this.prioridade == 0) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insira um nível de Prioridade para a Solicitação de Serviço!", ""));

        } else {

            LoginBean lb = (LoginBean) getManagedBean("LoginController");
            EquipamentoControllerBean ecb = (EquipamentoControllerBean) getManagedBean("EquipamentoCtrl");
            EntityManager em = emf.createEntityManager();

            try {

                solser_inserir.setMunicipio(em.find(Municipio.class, lb.getSession().getAttribute("municipio_id")));
                solser_inserir.setResponsavel(String.valueOf(lb.getSession().getAttribute("nome")));
                solser_inserir.setLocalSolicitacao(String.valueOf(lb.getSession().getAttribute("municipio")));
                solser_inserir.setEncerrada(false);
                solser_inserir.setPrioridade(em.find(Prioridade.class, this.prioridade));
                solser_inserir.setSituacao("AGUARDANDO BAIXA MANUTENÇÃO CAPITAL");
                solser_inserir.setDataAbertura(new Date());
                solser_inserir.setParecerTecnico("");

                if (!this.getObservacao().equals("")) {
                    solser_inserir.setObservacao(this.getObservacao().toUpperCase());
                } else {
                    solser_inserir.setObservacao("NADA CONSTA");
                }

                em.getTransaction().begin();
                em.persist(solser_inserir);
                em.getTransaction().commit();

                int count = 0;

                long ssIdAux = (Long) em.createQuery("select MAX(ss.id) from SolicitacaoServico ss").getSingleResult();

                //CASO A TABELA SOLICITACAO_SERVICO ESTEJA VAZIA
                //O LOOP ABAIXO ESTÁ INSERINDO CORRETAMENTE OS OBJETOS E ATRIBUTOS NA LISTA DE eqSsServico_inserir!!
                for (Equipamento equipamentoSs : equipamentosAddSs) {
                    this.eqSsServico_inserir = new EquipamentoSs();
                    eqSsServico_inserir.setEquipamento(em.find(Equipamento.class, equipamentoSs.getId()));
                    eqSsServico_inserir.setSolicitacaoServico(em.find(SolicitacaoServico.class, ssIdAux));
                    eqSsServico_inserir.setDefeito(defeitos.get(count));
                    eqSsServico_inserir.setObservacao(observacoesDefeitos.get(count));
                    eqSsServicos.add(eqSsServico_inserir);
                    count++;
                }

                em.getTransaction().begin();

                for (EquipamentoSs eqSsS : eqSsServicos) {
                    em.persist(eqSsS);
                }
                em.getTransaction().commit();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Solicitação de Serviço nº: " + param_numSsOpen + " foi registrada com Sucesso!", ""));

                preparaInsercao();

            } catch (Exception e) {

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da Solicitação de Serviço!", ""));

                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }
    }

    public void alterar() {

        EntityManager em = emf.createEntityManager();

        if (equipesAddSs.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Escale uma Equipe para atender a Solicitação de Serviço!", ""));
        } else if (servicosAddSs.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe um ou mais Serviços que a Equipe realizará para atender a Solicitação de Serviço!", ""));
        } else if (this.solser.getSituacao().contains("ENCERRADA") && this.solser.getEncerrada() == false) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a Situação atual da Solicitação de Serviço!", ""));
        } else {

            try {

                SolicitacaoServico temp = this.solser;
                temp.setObservacao(this.solser.getObservacao().toUpperCase());

                if (!this.solser.getParecerTecnico().equals("") || this.solser.getParecerTecnico() != null) {
                    temp.setParecerTecnico(this.solser.getParecerTecnico().toUpperCase());
                }
                if (!this.equipesAddSs.isEmpty()) {
                    temp.setEquipe(this.equipesAddSs.get(0));
                }

                em.getTransaction().begin();

                //CONDIÇÃO APROPRIADA PARA GETTERS
                if (!Objects.equals(this.paramSituacaoAux, this.solser.getSituacao())) {
                    LoginBean lb = (LoginBean) getManagedBean("LoginController");
                    this.registroSituacao.setColaboradorAlterador((String) lb.getSession().getAttribute("nome"));
                    this.registroSituacao.setDataAlteracao(new Date());
                    this.registroSituacao.setSituacao(this.solser.getSituacao());
                    this.registroSituacao.setSolicitacaoServico(em.find(SolicitacaoServico.class, solser.getId()));
                    em.persist(registroSituacao);
                }

                em.merge(temp);
                em.getTransaction().commit();

                // A LISTA servicosAddSs NUNCA ESTARÁ VAZIA
                if (servicoAuditoria.isEmpty()) {

                    for (Servico servico : servicosAddSs) {
                        this.servicoSs_inserir = new ServicoSs();
                        servicoSs_inserir.setServico(em.find(Servico.class, servico.getId()));
                        servicoSs_inserir.setSolicitacaoServico(em.find(SolicitacaoServico.class, solser.getId()));
                        servicosSs.add(servicoSs_inserir);
                    }
                    em.getTransaction().begin();
                    for (ServicoSs servicoSs : servicosSs) {
                        em.persist(servicoSs);
                    }
                    em.getTransaction().commit();

                } else {

                    if (servicosAddSs.size() != servicoAuditoria.size()) {
                        if (servicosAddSs.size() > servicoAuditoria.size()) {
                            //UTILIZAR O MÉTODO DE subList(interval 1, interval 2) PASSANDO COMO PARAMETROS O ULTIMO INDICE DA AUDITORIA
                            //E O ULTIMO INDICE DA SERVIÇO CORRENTE
                            //UTILIZAR a
                            List<Servico> servicoTempAdd = servicosAddSs.subList(servicoAuditoria.size() - 1, servicosAddSs.size() - 1);
                            for (Servico servicos : servicoTempAdd) {
                                this.servicoSs_inserir = new ServicoSs();
                                servicoSs_inserir.setServico(em.find(Servico.class, servicos.getId()));
                                servicoSs_inserir.setSolicitacaoServico(em.find(SolicitacaoServico.class, solser.getId()));
                                servicosSs.add(servicoSs_inserir);
                            }
                            em.getTransaction().begin();
                            for (ServicoSs servicoSs : servicosSs) {
                                em.persist(servicoSs);
                            }
                            em.getTransaction().commit();

                        } else {
                            //REALIZA UMA BUSCA DE OBJETOS DA LISTA AUDITORIA EM SERVIÇOS CORRENTES PARA SALVAR EM UMA LISTA AUXILIAR
                            //O OU OS OBJETOS DIVERGENTES PARA DAR UM LOOP remove  NA LISTA AUXILIAR
                            //List<Servico> servicoTempRemove = servicoAuditoria.subList(servicosAddSs.size() - 1, servicoAuditoria.size() - 1);
                            List<Servico> servicoTempRemove = new ArrayList<>();
                            for (Servico servicoauditoria : servicoAuditoria) {
                                if (!servicosAddSs.contains(servicoauditoria)) {
                                    if (!servicoTempRemove.contains(servicoauditoria)) {
                                        servicoTempRemove.add(servicoauditoria);
                                    }
                                }
                            }
                            for (Servico servicos : servicoTempRemove) {
                                Query query = em.createQuery("SELECT sSs FROM ServicoSs sSs WHERE SSS.servico.id = " + servicos.getId() + " AND SSS.solicitacaoServico.id = " + solser.getId() + "");
                                ServicoSs ssRemove = (ServicoSs) query.getSingleResult();
                                servicosSs.add(ssRemove);
                            }
                            em.getTransaction().begin();
                            for (ServicoSs servicoSs : servicosSs) {
                                ServicoSs servicoSsTemp = servicoSs;
                                em.remove(servicoSsTemp);
                            }
                            em.getTransaction().commit();
                        }
                    } else {
                        //REALIZA UM LOOP ENCADEADO NAS LISTAS DE SERVIÇO CORRENTE E DE AUDITORIA
                        //UTILIZA O MÉTODO contains(Object), CASO DE FALSO, SALVA O OBJETO EM UMA LISTA AUX_PERSIST
                        //UTILIZA O MÉTODO contains(Object) NO CAMINHO REVERSO, ACUSANDO O OBJETO DIVERGENTE, SALVAR EM UMA LISTA AUX_REMOVE
                        List<Servico> servicoTempAdd = new ArrayList<>();
                        List<Servico> servicoTempRemove = new ArrayList<>();

//                        for (Servico servicoAuditorado : servicosAddSs) {
                        for (Servico servicoauditoria : servicoAuditoria) {
                            if (!servicosAddSs.contains(servicoauditoria)) {
                                if (!servicoTempRemove.contains(servicoauditoria)) {
                                    servicoTempRemove.add(servicoauditoria);
                                }
                            }
                        }

                        //SE AUDITORIA POSSUI OU O OBJETO DA LISTA AUDITORIA É IGUAL AO DA LISTA AUDITORADO
                        for (Servico servico : servicosAddSs) {
                            if (!servicoAuditoria.contains(servico)) {
                                if (!servicoTempAdd.contains(servico)) {
                                    servicoTempAdd.add(servico);
                                }
                            }
                        }

                        if (!servicoTempRemove.isEmpty() && !servicoTempAdd.isEmpty()) {
                            em.getTransaction().begin();

                            for (Servico servicos : servicoTempRemove) {
                                Query query = em.createQuery("SELECT sSs FROM ServicoSs sSs WHERE SSS.servico.id = " + servicos.getId() + " AND SSS.solicitacaoServico.id = " + solser.getId() + "");
                                ServicoSs ssRemove = (ServicoSs) query.getSingleResult();
                                servicosSs.add(ssRemove);
                                for (ServicoSs servicoSs : servicosSs) {
                                    em.remove(servicoSs);
                                }
                            }
                            servicosSs.clear();
                            for (Servico servicos : servicoTempAdd) {
                                this.servicoSs_inserir = new ServicoSs();
                                servicoSs_inserir.setServico(em.find(Servico.class, servicos.getId()));
                                servicoSs_inserir.setSolicitacaoServico(em.find(SolicitacaoServico.class, solser.getId()));
                                servicosSs.add(servicoSs_inserir);
                            }

                            for (ServicoSs servicoSs : servicosSs) {
                                em.persist(servicoSs);
                            }
                            em.getTransaction().commit();
                        }
                    }
                }
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Solicitação de Serviço nº: " + param_numSs + " foi alterada com Sucesso!", ""));

                preparaInsercao();

            } catch (Exception e) {

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Alteração dos dados da Solicitação de Serviço!", ""));

                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
                em.getTransaction().rollback();
            } finally {
                em.close();
            }
        }

    }

    public void alterarEncerramento() {

        this.solser.setEncerrada(this.solser.getEncerrada());
    }

    public void encerrarSS() {

        EntityManager em = emf.createEntityManager();

        try {

            SolicitacaoServico temp = this.solser;

            em.getTransaction().begin();

            if (solser.getEncerrada()) {
                if (solicitacaoServicoEncerrada_Auditoria == false) {
                    temp.setEncerrada(true);
                    temp.setDataFechamento(new Date());
                    temp.setSituacao(this.solser.getSituacao());
                    //ENCERRAR A SS CONTA COMO HISTORICO DE ALTERAÇÃO
                    LoginBean lb = (LoginBean) getManagedBean("LoginController");
                    this.registroSituacao.setColaboradorAlterador((String) lb.getSession().getAttribute("nome"));
                    this.registroSituacao.setDataAlteracao(new Date());
                    this.registroSituacao.setSituacao(this.solser.getSituacao());
                    this.registroSituacao.setSolicitacaoServico(em.find(SolicitacaoServico.class, solser.getId()));
                    em.persist(registroSituacao);
                }
            }

            em.merge(temp);
            em.getTransaction().commit();

            if (solser.getEncerrada()) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "A Solicitação de Serviço nº: " + solser.getId() + " foi encerrada com Sucesso!", ""));
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O usuário optou por não encerrar a Solicitação de Serviço nº: " + solser.getId() + "", ""));
            }

            preparaInsercao();

        } catch (Exception e) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar encerrar a Solicitação de Serviço!", ""));

            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void excluir(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void pegarObservacao() {
        this.observacao = this.getObservacao();
    }

    public void alterarObservacao() {
        this.solser.setObservacao(this.solser.getObservacao().toUpperCase());
    }

    public void pegarParecerTecnico() {
        this.solser.setParecerTecnico(this.solser.getParecerTecnico().toUpperCase());
    }

    public void preparaInsercao() {

        EquipamentoControllerBean ecb = (EquipamentoControllerBean) getManagedBean("EquipamentoCtrl");
        ecb.setParamIdEquipamento(0);

        this.param_numSs = 0;
        this.prioridade = 0;
        this.param_numSsOpen = 0;
        this.paramSituacaoAux = "";
        this.defeito = "";
        this.observacao = "";
        this.equipamentosAddSs.clear();
        this.servicosAddSs.clear();
        this.servicoAuditoria.clear();
        this.equipesAddSs.clear();
        this.eqSsServicos.clear();
        this.defeitos.clear();
        this.observacoesDefeitos.clear();
        this.servicosSs.clear();
        this.historicoSituacao.clear();
        this.exibe_botao_gravar = false;
        this.exibe_painel_add_equipamento = false;
        this.no_result_add_eq = false;
        this.no_result_ss = false;
        this.exibe_painel_edit_ss = false;
        this.eqSsServico_inserir = new EquipamentoSs();
        this.solser_inserir = new SolicitacaoServico();
        this.eqSsServico_inserir = new EquipamentoSs();
        this.solser_inserir = new SolicitacaoServico();
        this.servicoSs_inserir = new ServicoSs();
        this.registroSituacao = new HistoricoSituacao();
        this.solicitacaoServicoEncerrada_Auditoria = false;

    }

    public static Object getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();

        Object bean;
        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }

        if (bean == null) {
            throw new FacesException("Managed bean with name '" + beanName + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
        }

        return bean;
    }

}
