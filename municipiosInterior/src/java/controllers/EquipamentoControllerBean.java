/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Equipamento;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import org.primefaces.event.SelectEvent;

import entities.LocalInstalacao;
import entities.LocalOperacao;
import entities.LocalOperacaoTecnica;
import entities.Localidade;
import entities.Marca;
import entities.Municipio;
import entities.TipoEquipamento;
import entities.SolicitacaoServico;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "EquipamentoCtrl")
@SessionScoped
public class EquipamentoControllerBean {

    /**
     * Creates a new instance of EquipamentoController
     */
    private Equipamento equipamento;
    private Equipamento equipamento_inserir;
    private List<Equipamento> equipamentos = new ArrayList<>();
    private List<Equipamento> equipamentosHistPoco = new ArrayList<>();
    private List<TipoEquipamento> tipoEquipamento = new ArrayList<>();
    private List<LocalOperacao> localOperacao = new ArrayList<>();
    private List<LocalOperacaoTecnica> localOperacaoTecnica = new ArrayList<>();
    private List<LocalInstalacao> localInstalacao = new ArrayList<>();
    private List<Municipio> municipio = new ArrayList<>();
    private List<Marca> marca = new ArrayList<>();
    private List<Localidade> localidade = new ArrayList<>();
    private List<SolicitacaoServico> historicoEquipamento = new ArrayList<>();

    private Integer paramIdEquipamento = 0;
    private boolean exibe_painel_edit_equipamento = false;
    private boolean exibe_som_eq_lto = false;
    private boolean exibe_som_eq_li = false;
    private boolean exibe_pnlGrid_eq_li = false;
    private boolean exibe_painel_del_equipamento = false;
    private boolean exibe_painel_poco_del = false;
    private boolean exibe_quant_poco_acima_5 = false;
    private boolean exibe_som_eq_lto_edit = false;
    private boolean exibe_pnlGrid_eq_li_edit = false;

    private Integer local_instalacao_temp = 0;
    private boolean no_result_edit_eq = false;
    private boolean no_result_del_eq = false;
    private boolean no_result_hist_poco = false;
    private Integer param_localidade_id_hist_poco = 0;
    private Integer param_poco_hist_poco = 0;
    private boolean no_result_historico_eq = false;
    private boolean exibe_painel_historico_equipamento = false;

    private Integer filterParam_localidade = 0;
    private Integer filterParam_localOperacao = 0;
    private Integer filterParam_localOperacaoTecnica = 0;
 
    private Integer municipioId = 0;
    private Integer localInstalacaoId = 16;
    private Integer localOperacaoId = 0;
    private Integer localOperacaoTecnicaId = 0;
    private Long tipoEquipamentoId = Long.valueOf(0);
    private Integer marcaId = 0;
    private Integer localidadeId = 0;


    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();
    LoginBean lb = (LoginBean) getManagedBean("LoginController");

    public EquipamentoControllerBean() {
    }

    public Equipamento getEquipamento() {
        if (this.equipamento == null) {
            this.equipamento = new Equipamento();
        }
        return equipamento;
    }
    
    public Integer getParamIdEquipamento() {
        return paramIdEquipamento;
    }

    public void setParamIdEquipamento(Integer paramIdEquipamento) {
        this.paramIdEquipamento = paramIdEquipamento;
    }

    public boolean isExibe_painel_edit_equipamento() {
        return exibe_painel_edit_equipamento;
    }

    public void setExibe_painel_edit_equipamento(boolean exibe_painel_edit_equipamento) {
        this.exibe_painel_edit_equipamento = exibe_painel_edit_equipamento;
    }

    public boolean isExibe_som_eq_lto() {
        return exibe_som_eq_lto;
    }

    public void setExibe_som_eq_lto(boolean exibe_som_eq_lto) {
        this.exibe_som_eq_lto = exibe_som_eq_lto;
    }

    public boolean isExibe_som_eq_li() {
        return exibe_som_eq_li;
    }

    public void setExibe_som_eq_li(boolean exibe_som_eq_li) {
        this.exibe_som_eq_li = exibe_som_eq_li;
    }

    public boolean isExibe_pnlGrid_eq_li() {
        return exibe_pnlGrid_eq_li;
    }

    public void setExibe_pnlGrid_eq_li(boolean exibe_pnlGrid_eq_li) {
        this.exibe_pnlGrid_eq_li = exibe_pnlGrid_eq_li;
    }

    public String painelEquipamento() {
        return "/equipamentos/tela_principal_equipamento.xhtml?faces-redirect=true";
    }

    public boolean isExibe_painel_del_equipamento() {
        return exibe_painel_del_equipamento;
    }

    public void setExibe_painel_del_equipamento(boolean exibe_painel_del_equipamento) {
        this.exibe_painel_del_equipamento = exibe_painel_del_equipamento;
    }

    public boolean isExibe_quant_poco_acima_5() {
        return exibe_quant_poco_acima_5;
    }

    public void setExibe_quant_poco_acima_5(boolean exibe_quant_poco_acima_5) {
        this.exibe_quant_poco_acima_5 = exibe_quant_poco_acima_5;
    }

    public boolean isExibe_painel_poco_del() {
        return exibe_painel_poco_del;
    }

    public void setExibe_painel_poco_del(boolean exibe_painel_poco_del) {
        this.exibe_painel_poco_del = exibe_painel_poco_del;
    }

    public Integer getParam_localidade_id_hist_poco() {
        return param_localidade_id_hist_poco;
    }

    public void setParam_localidade_id_hist_poco(Integer param_localidade_id_hist_poco) {
        this.param_localidade_id_hist_poco = param_localidade_id_hist_poco;
    }

    public Integer getParam_poco_hist_poco() {
        return param_poco_hist_poco;
    }

    public void setParam_poco_hist_poco(Integer param_poco_hist_poco) {
        this.param_poco_hist_poco = param_poco_hist_poco;
    }

    public boolean isExibe_som_eq_lto_edit() {
        return exibe_som_eq_lto_edit;
    }

    public void setExibe_som_eq_lto_edit(boolean exibe_som_eq_lto_edit) {
        this.exibe_som_eq_lto_edit = exibe_som_eq_lto_edit;
    }

    public boolean isExibe_pnlGrid_eq_li_edit() {
        return exibe_pnlGrid_eq_li_edit;
    }

    public void setExibe_pnlGrid_eq_li_edit(boolean exibe_pnlGrid_eq_li_edit) {
        this.exibe_pnlGrid_eq_li_edit = exibe_pnlGrid_eq_li_edit;
    }

    public boolean isExibe_painel_historico_equipamento() {
        return exibe_painel_historico_equipamento;
    }

    public void setExibe_painel_historico_equipamento(boolean exibe_painel_historico_equipamento) {
        this.exibe_painel_historico_equipamento = exibe_painel_historico_equipamento;
    }

    public boolean isNo_result_historico_eq() {
        return no_result_historico_eq;
    }

    public void setNo_result_historico_eq(boolean no_result_historico_eq) {
        this.no_result_historico_eq = no_result_historico_eq;
    }

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }

    public int getLocalInstalacaoId() {
        return localInstalacaoId;
    }

    public void setLocalInstalacaoId(int localInstalacaoId) {
        this.localInstalacaoId = localInstalacaoId;
    }

    public int getLocalOperacaoId() {
        return localOperacaoId;
    }

    public void setLocalOperacaoId(int localOperacaoId) {
        this.localOperacaoId = localOperacaoId;
    }

    public int getLocalOperacaoTecnicaId() {
        return localOperacaoTecnicaId;
    }

    public Integer getLocalidadeId() {
        return localidadeId;
    }

    public void setLocalidadeId(Integer localidadeId) {
        this.localidadeId = localidadeId;
    }

    public void setLocalOperacaoTecnicaId(int localOperacaoTecnicaId) {
        this.localOperacaoTecnicaId = localOperacaoTecnicaId;
    }

    public long getTipoEquipamentoId() {
        return tipoEquipamentoId;
    }

    public void setTipoEquipamentoId(long tipoEquipamentoId) {
        this.tipoEquipamentoId = tipoEquipamentoId;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public List<Equipamento> getEquipamentosHistPoco() {
        Collection<Equipamento> e = new ArrayList();
        Query query = em.createQuery("select e from Equipamento e where e.localInstalacao.id = " + param_poco_hist_poco + " and e.localidade.id = " + param_localidade_id_hist_poco + "");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        e = query.getResultList();
        this.equipamentosHistPoco = new ArrayList(e);
        return equipamentosHistPoco;
    }

    public void setEquipamentosHistPoco(List<Equipamento> equipamentosHistPoco) {
        this.equipamentosHistPoco = equipamentosHistPoco;
    }

    public List<SolicitacaoServico> getHistoricoEquipamento() {
        return historicoEquipamento;
    }

    public void setHistoricoEquipamento(List<SolicitacaoServico> historicoEquipamento) {
        this.historicoEquipamento = historicoEquipamento;
    }

    public boolean isNo_result_edit_eq() {
        return no_result_edit_eq;
    }

    public void setNo_result_edit_eq(boolean no_result_alt_eq) {
        this.no_result_edit_eq = no_result_alt_eq;
    }

    public boolean isNo_result_del_eq() {
        return no_result_del_eq;
    }

    public void setNo_result_del_eq(boolean no_result_del_eq) {
        this.no_result_del_eq = no_result_del_eq;
    }

    public Integer getLocal_instalacao_temp() {
        return local_instalacao_temp;
    }

    public void setLocal_instalacao_temp(Integer local_instalacao_temp) {
        this.local_instalacao_temp = local_instalacao_temp;
    }

    public boolean isNo_result_hist_poco() {
        return no_result_hist_poco;
    }

    public void setNo_result_hist_poco(boolean no_result_hist_poco) {
        this.no_result_hist_poco = no_result_hist_poco;
    }

    public Integer getFilterParam_localidade() {
        return filterParam_localidade;
    }

    public void setFilterParam_municipio(Integer filterParam_localidade) {
        this.filterParam_localidade = filterParam_localidade;
    }

    public Integer getFilterParam_localOperacao() {
        return filterParam_localOperacao;
    }

    public void setFilterParam_localOperacao(Integer filterParam_localOperacao) {
        this.filterParam_localOperacao = filterParam_localOperacao;
    }

    public Integer getFilterParam_localOperacaoTecnica() {
        return filterParam_localOperacaoTecnica;
    }

    public void setFilterParam_localOperacaoTecnica(Integer filterParam_localOperacaoTecnica) {
        this.filterParam_localOperacaoTecnica = filterParam_localOperacaoTecnica;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Equipamento getEquipamento_inserir() {
        if (this.equipamento_inserir == null) {
            this.equipamento_inserir = new Equipamento();
        }
        return equipamento_inserir;
    }

    public void setEquipamento_inserir(Equipamento equipamento_inserir) {
        this.equipamento_inserir = equipamento_inserir;
    }

    public List<Equipamento> getEquipamentos() {
        Collection<Equipamento> e = new ArrayList();
        Query query = em.createQuery("select e from Equipamento e order by e.id asc");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        e = query.getResultList();
        this.equipamentos = new ArrayList(e);
        return equipamentos;
    }

    public List<LocalInstalacao> getPocos() {

        localInstalacao = em.createQuery("select li from LocalInstalacao li where li.descricao like '%POÇO%'").getResultList();
        return localInstalacao;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public EntityManager getEm() {
        return em;
    }

    public void adicionar() {

        try {

            em.getTransaction().begin();
            this.equipamento_inserir.setDescricao(this.equipamento_inserir.getDescricao().toUpperCase());
            this.equipamento_inserir.setLocalInstalacao(em.find(LocalInstalacao.class, localInstalacaoId));
            this.equipamento_inserir.setLocalOperacao(em.find(LocalOperacao.class, localOperacaoId));
            this.equipamento_inserir.setLocalOperacaoTecnica(em.find(LocalOperacaoTecnica.class, localOperacaoTecnicaId));
            this.equipamento_inserir.setMarca(em.find(Marca.class, this.marcaId));
            this.equipamento_inserir.setTipoEquipamento(em.find(TipoEquipamento.class, tipoEquipamentoId));
            this.equipamento_inserir.setTombamento(this.equipamento_inserir.getTombamento());
            this.equipamento_inserir.setLocalidade(em.find(Localidade.class, localidadeId));
            this.equipamento_inserir.setDataCadastro(new Date());
            this.equipamento_inserir.setAtivo(true);
            em.persist(equipamento_inserir);
            em.flush();
            em.getTransaction().commit();
            getEquipamentos();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Inserido o Equipamento: " + equipamento_inserir.getDescricao(), null));
            //A LINHA ABAIXO SERÁ USADA CASO A TELA DE CADASTRO DE EQUIPAMENTO NÃO SEJA FECHADA A CADA NOVO EQUIPAMENTO CADASTRADO
            preparaInsercao();

        } catch (Throwable t) {

            t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Igualdade no nº de Tombamento, O Equipamento já foi cadastrado.", null));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Igualdade no nº de Tombamento, O Equipamento já foi cadastrado.", null));
            }
        }
    }

    public void alterar() {

        try {

            Equipamento temp = this.equipamento;
            temp.setDescricao(this.equipamento.getDescricao().toUpperCase());
            temp.setTombamento(this.equipamento.getTombamento());
            temp.setDataCadastro(this.equipamento.getDataCadastro());
            temp.setAtivo(this.equipamento.getAtivo());
            temp.setDataInativo(this.equipamento.getDataInativo());
            //FK's
            temp.setLocalOperacao(this.equipamento.getLocalOperacao());
            temp.setLocalOperacaoTecnica(this.equipamento.getLocalOperacaoTecnica());
            Integer localOptec_aux_add = this.equipamento.getLocalOperacaoTecnica().getId();

            if (localOptec_aux_add == 1 || localOptec_aux_add == 2 || localOptec_aux_add == 3) {
                temp.setLocalInstalacao(this.equipamento.getLocalInstalacao());
            } else {
                temp.setLocalInstalacao(em.find(LocalInstalacao.class, 16));
            }

            temp.setMarca(this.equipamento.getMarca());
            temp.setTipoEquipamento(this.equipamento.getTipoEquipamento());
            temp.setLocalidade(this.equipamento.getLocalidade());

            em.getTransaction().begin();

            em.clear();
            em.merge(temp);
            em.getTransaction().commit();

            getEquipamentos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Alterado o Equipamento: " + equipamento.getDescricao(), null));

            this.equipamento = new Equipamento();
            this.paramIdEquipamento = 0;
            this.exibe_painel_edit_equipamento = false;

        } catch (Throwable t) {
            t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Alteração dos dados do equipamento.", null));
        }
    }

    public void excluir(ActionListener al) {

        try {

            Equipamento temp = this.equipamento;
            em.getTransaction().begin();
            em.remove(temp);
            em.getTransaction().commit();
            getEquipamentos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Excluído o Equipamento: " + this.equipamento.getDescricao(), null));

            this.equipamento = new Equipamento();
            this.exibe_painel_del_equipamento = false;
            this.paramIdEquipamento = 0;
            this.exibe_painel_del_equipamento = false;
            this.exibe_painel_poco_del = false;

        } catch (Throwable t) {
            t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Exclusão do Equipamento.", null));
        }

    }

    public void preparaInsercao() {

        this.equipamento_inserir = new Equipamento();
        this.equipamentos = new ArrayList<>();
        this.equipamento = new Equipamento();

        this.exibe_pnlGrid_eq_li = false;
        this.exibe_som_eq_lto = false;
        this.exibe_painel_edit_equipamento = false;
        this.exibe_painel_del_equipamento = false;
        this.paramIdEquipamento = 0;
        this.exibe_quant_poco_acima_5 = false;
        this.exibe_pnlGrid_eq_li_edit = false;
        this.exibe_som_eq_lto_edit = false;

        this.no_result_edit_eq = false;
        this.no_result_del_eq = false;

        this.localOperacaoId = 0;
        this.localInstalacaoId = 16;
        this.localOperacaoTecnicaId = 0;
        this.marcaId = 0;
        this.localidadeId = 0;
        this.tipoEquipamentoId = Long.valueOf(0);
    }

    public void limpa() {

        this.equipamentosHistPoco.clear();
        this.param_localidade_id_hist_poco = 0;
        this.param_poco_hist_poco = 0;
        this.historicoEquipamento.clear();
        this.paramIdEquipamento = 0;
        this.exibe_painel_historico_equipamento = false;
        this.equipamento = new Equipamento();
    }

    public void zerarFiltro() {
        this.filterParam_localOperacao = 0;
        this.filterParam_localOperacaoTecnica = 0;
        this.filterParam_localidade = 0;
//        this.filtro = false;
    }
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public void carregaHistoricoEquipamento() {

        try {

            Collection<SolicitacaoServico> e = new ArrayList<>();
            Query query = em.createQuery("select ss from SolicitacaoServico ss, EquipamentoSs eqSs where SS.id = eqSs.solicitacaoServico.id and eqSs.equipamento.id = " + paramIdEquipamento + "");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            e = query.getResultList();
            this.historicoEquipamento = new ArrayList(e);

            query = em.createQuery("select e from Equipamento e where e.id = " + paramIdEquipamento + "");
            this.equipamento = (Equipamento) query.getSingleResult();

            this.exibe_painel_historico_equipamento = true;

        } catch (NoResultException nr) {
            this.no_result_historico_eq = true;
            historicoEquipamento.clear();
            this.exibe_painel_historico_equipamento = false;

        }

    }

    public Equipamento retornaEquipamentoAdd(ActionListener e) throws ParseException {

        SolServicoControllerBean sscb = (SolServicoControllerBean) getManagedBean("SolSerCtrl");

        try {

            Query query = em.createQuery("select e from Equipamento e where e.id = " + paramIdEquipamento + "");
            this.equipamento = (Equipamento) query.getSingleResult();
            sscb.setNo_result_add_eq(false);
            sscb.setExibe_painel_add_equipamento(true);
            sscb.setExibe_dataTable_add_equipamento(true);
            sscb.setDefeito("");
            sscb.setObservacaoDefeito("");

            return equipamento;

        } catch (NoResultException nre) {

            sscb.setNo_result_add_eq(true);
            sscb.setExibe_painel_add_equipamento(false);

            return null;
        }

    }

    public Equipamento retornaEquipamentoEdit() {

        try {

            Query query = em.createQuery("select e from Equipamento e where e.id = " + equipamento.getId() + "");
            this.equipamento = (Equipamento) query.getSingleResult();

            this.no_result_edit_eq = false;
            this.exibe_painel_edit_equipamento = true;

            if (this.equipamento.getLocalOperacao().getId() == 0 || this.equipamento.getLocalOperacao().getId() == null) {

                this.exibe_pnlGrid_eq_li_edit = false;
                this.exibe_som_eq_lto_edit = false;
            } else {
                this.exibe_som_eq_lto_edit = true;
                this.exibe_pnlGrid_eq_li_edit = true;
            }

            Integer localOpTec_aux = this.equipamento.getLocalOperacaoTecnica().getId();
            this.exibe_pnlGrid_eq_li_edit = localOpTec_aux == 1 || localOpTec_aux == 2 || localOpTec_aux == 3;

            if (this.equipamento.getLocalInstalacao() == null) {
                //PARA NÃO DAR ERRO NO FORMULARIO DE ALTERAÇÃO DE EQUIPAMENTO
                this.equipamento.setLocalInstalacao(em.find(LocalInstalacao.class, 16));
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return equipamento;
    }

    public Equipamento retornaEquipamentoDel(ActionListener e) throws ParseException {

        try {
            Query query = em.createQuery("select e from Equipamento e where e.id = " + paramIdEquipamento + "");
            this.equipamento = (Equipamento) query.getSingleResult();

            this.no_result_del_eq = false;
            this.exibe_painel_del_equipamento = true;

            if (this.equipamento.getLocalInstalacao() != null) {
                this.local_instalacao_temp = this.equipamento.getLocalInstalacao().getId();
                if (!this.local_instalacao_temp.equals("TOMADA D'ÁGUA") || !this.local_instalacao_temp.equals("FLUTUANTE") || !this.local_instalacao_temp.equals("POÇO")) {
                    this.exibe_painel_poco_del = true;
                }
            }
        } catch (NoResultException nre) {
            this.no_result_del_eq = true;
            this.exibe_painel_del_equipamento = false;
        }
        return equipamento;
    }
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    // ======================= MÉTODOS PARA AUXILIAR NO DINAMISMO DE UI ==============================
    public void exibeLocalOpeTec(SelectEvent e) {

        if (this.localOperacaoId == 0) {
            this.exibe_pnlGrid_eq_li = false;
            this.exibe_som_eq_lto = false;
        } else {
            this.getLocalOperacaoTecnica();
            this.exibe_som_eq_lto = true;
        }
    }

    public void exibeLocalInsta(SelectEvent e) {

        if (this.localOperacaoTecnicaId == 0) {
            this.exibe_pnlGrid_eq_li = false;
        } else if (this.localOperacaoTecnicaId == 1 || this.localOperacaoTecnicaId == 2 || this.localOperacaoTecnicaId == 3) {
            this.exibe_pnlGrid_eq_li = true;
        } else {
            this.exibe_pnlGrid_eq_li = false;
        }
    }

    public void exibeLocalOpeTecEdit(SelectEvent e) {

        if (this.equipamento.getLocalOperacao().getId() == 0 || this.equipamento.getLocalOperacao().getId() == null) {
            this.exibe_pnlGrid_eq_li_edit = false;
            this.exibe_som_eq_lto_edit = false;
        } else {
            this.getLocalOperacaoTecnica();
            this.exibe_som_eq_lto_edit = true;
        }
    }

    public void exibeLocalInstaEdit(SelectEvent e) {

        Integer localOpTec_aux = this.equipamento.getLocalOperacaoTecnica().getId();
        if (localOpTec_aux == 0 || localOpTec_aux == null) {
            this.exibe_pnlGrid_eq_li_edit = false;
        } else if (localOpTec_aux == 1 || localOpTec_aux == 2 || localOpTec_aux == 3) {
            this.exibe_pnlGrid_eq_li_edit = true;
        } else {
            this.exibe_pnlGrid_eq_li_edit = false;
        }
    }

    public List<Municipio> getMunicipio() {
        municipio = em.createQuery("select m from Municipio m order by m.descricao asc").getResultList();
        return municipio;
    }

    public List<TipoEquipamento> getTipoEquipamento() {
        tipoEquipamento = em.createQuery("select te from TipoEquipamento te order by te.descricao asc").getResultList();
        return tipoEquipamento;
    }

    public List<LocalOperacao> getLocalOperacao() {
        localOperacao = em.createQuery("select lo from LocalOperacao lo order by lo.descricao asc").getResultList();
        return localOperacao;

    }

    public List<LocalOperacaoTecnica> getLocalOperacaoTecnica() {
        if (localOperacaoId != 0) {
            localOperacaoTecnica = em.createQuery("select l from LocalOperacaoTecnica l where l.localOperacao.id = " + this.localOperacaoId + "").getResultList();
        } else {
            localOperacaoTecnica = em.createQuery("select lot from LocalOperacaoTecnica lot order by lot.descricao asc").getResultList();
        }
        return localOperacaoTecnica;
    }

    public List<LocalOperacaoTecnica> getLocalOperacaoTecnicaEdit() {
        int localOp_aux = this.equipamento.getLocalOperacao().getId();
        if (localOp_aux != 0) {
            localOperacaoTecnica = em.createQuery("select l from LocalOperacaoTecnica l where l.localOperacao.id = " + localOp_aux + "").getResultList();
        } else {
            localOperacaoTecnica = em.createQuery("select lot from LocalOperacaoTecnica lot order by lot.descricao asc").getResultList();
        }
        return localOperacaoTecnica;
    }

    public List<LocalInstalacao> getLocalInstalacao() {
        if (this.localOperacaoTecnicaId == 1) {
            localInstalacao = em.createQuery("select li from LocalInstalacao li where li.descricao not like '%POÇO%' and li.descricao <> 'DEFAULT'").getResultList();
        } else if (this.param_localidade_id_hist_poco != 0) {
            localInstalacao = em.createQuery("select li from LocalInstalacao li where li.descricao like '%POÇO%'").getResultList();
        } else {
            localInstalacao = em.createQuery("select li from LocalInstalacao li where li.descricao <> 'DEFAULT'").getResultList();
        }
        return localInstalacao;
    }

    public List<Marca> getMarca() {
        marca = em.createQuery("select m from Marca m order by m.descricao asc").getResultList();
        return marca;
    }

    public List<Localidade> getLocalidade() {
        String municio_temp = (String) lb.getSession().getAttribute("municipio");
        if (municio_temp.equals("BOA VISTA")) {
            localidade = em.createQuery("select l from Localidade l order by l.setor.setorNome asc").getResultList();
        } else {
            localidade = em.createQuery("select l from Localidade l where l.municipio.descricao = '" + municio_temp + "'").getResultList();
        }
        return localidade;
    }

    public List<Localidade> getLocalidadeFiltro() {
        localidade = em.createQuery("select l from Localidade l order by l.setor.setorNome asc").getResultList();
        return localidade;
    }

    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
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
