/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import entities.Equipamento;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import login.dao.ConexaoDB;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Eneas
 */
@ManagedBean(name = "RelatorioEqCtrl")
@SessionScoped
public class RelatoriosEqControllerBean {

    //DINAMISMO DE UI
    private boolean pnl_rel_analise_eq = false;
    private boolean pnl_historico_equipamento = false;
    private boolean exibeIntervalo = false;
    private boolean exibeIntervaloHE = false;

    //MONTAGEM DO RELATÓRIO
    private boolean marca = false;
    private boolean tipoEquipamento = false;
    private boolean ativo = false;
    private boolean inativo = false;

    //PERÍODO DO RELATÓRIO
    private String periodoRelatorio = "";
    private String periodoHE = "";

    //PARAMETROS JASPER
    //PARAMETROS HISTORICO EQUIPAMENTO
    private Date paramPeriodoHE;
    private Date paramPeriodoIntervaloHE;
    private long paramEquipamento = 0;

    //PARAMETROS RELATORIO ANALISE EQUIPAMENTO
    private int paramLocalidade = 0;
    private Date paramPeriodo;
    private Date paramPeriodoIntervalo;
    private long paramTipoEquipamento = 0;
    private int paramMarca = 0;
    private int paramLocalidadeTecnica = 0;
    private int paramLocalidadeTecnicaOperacional = 0;
    private int paramLocalInstalacao = 0;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public RelatoriosEqControllerBean() {
    }

    public boolean isMarca() {
        return marca;
    }

    public void setMarca(boolean marca) {
        this.marca = marca;
    }

    public boolean isTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(boolean tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public Date getParamPeriodo() {
        return paramPeriodo;
    }

    public void setParamPeriodo(Date paramPeriodo) {
        this.paramPeriodo = paramPeriodo;
    }

    public Date getParamPeriodoIntervalo() {
        return paramPeriodoIntervalo;
    }

    public void setParamPeriodoIntervalo(Date paramPeriodoIntervalo) {
        this.paramPeriodoIntervalo = paramPeriodoIntervalo;
    }

    public long getParamTipoEquipamento() {
        return paramTipoEquipamento;
    }

    public void setParamTipoEquipamento(long paramTipoEquipamento) {
        this.paramTipoEquipamento = paramTipoEquipamento;
    }

    public int getParamMarca() {
        return paramMarca;
    }

    public void setParamMarca(int paramMarca) {
        this.paramMarca = paramMarca;
    }

    public int getParamLocalidadeTecnica() {
        return paramLocalidadeTecnica;
    }

    public void setParamLocalidadeTecnica(int paramLocalidadeTecnica) {
        this.paramLocalidadeTecnica = paramLocalidadeTecnica;
    }

    public int getParamLocalidadeTecnicaOperacional() {
        return paramLocalidadeTecnicaOperacional;
    }

    public void setParamLocalidadeTecnicaOperacional(int paramLocalidadeTecnicaOperacional) {
        this.paramLocalidadeTecnicaOperacional = paramLocalidadeTecnicaOperacional;
    }

    public int getParamLocalInstalacao() {
        return paramLocalInstalacao;
    }

    public void setParamLocalInstalacao(int paramLocalInstalacao) {
        this.paramLocalInstalacao = paramLocalInstalacao;
    }

    public int getParamLocalidade() {
        return paramLocalidade;
    }

    public void setParamLocalidade(int paramLocalidade) {
        this.paramLocalidade = paramLocalidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isExibeIntervaloHE() {
        return exibeIntervaloHE;
    }

    public void setExibeIntervaloHE(boolean exibeIntervaloHE) {
        this.exibeIntervaloHE = exibeIntervaloHE;
    }

    public String getPeriodoHE() {
        return periodoHE;
    }

    public void setPeriodoHE(String periodoHE) {
        this.periodoHE = periodoHE;
    }

    public Date getParamPeriodoHE() {
        return paramPeriodoHE;
    }

    public void setParamPeriodoHE(Date paramPeriodoHE) {
        this.paramPeriodoHE = paramPeriodoHE;
    }

    public Date getParamPeriodoIntervaloHE() {
        return paramPeriodoIntervaloHE;
    }

    public void setParamPeriodoIntervaloHE(Date paramPeriodoIntervaloHE) {
        this.paramPeriodoIntervaloHE = paramPeriodoIntervaloHE;
    }

    public long getParamEquipamento() {
        return paramEquipamento;
    }

    public void setParamEquipamento(long paramEquipamento) {
        this.paramEquipamento = paramEquipamento;
    }

    public boolean isInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public boolean isExibeIntervalo() {
        return exibeIntervalo;
    }

    public void setExibeIntervalo(boolean exibeIntervalo) {
        this.exibeIntervalo = exibeIntervalo;
    }

    public boolean isPnl_rel_analise_eq() {
        return pnl_rel_analise_eq;
    }

    public void setPnl_rel_analise_eq(boolean pnl_rel_analise_eq) {
        this.pnl_rel_analise_eq = pnl_rel_analise_eq;
    }

    public boolean isPnl_historico_equipamento() {
        return pnl_historico_equipamento;
    }

    public void setPnl_historico_equipamento(boolean pnl_historico_equipamento) {
        this.pnl_historico_equipamento = pnl_historico_equipamento;
    }

    public String getPeriodoRelatorio() {
        return periodoRelatorio;
    }

    public void setPeriodoRelatorio(String periodoRelatorio) {
        this.periodoRelatorio = periodoRelatorio;
    }

    public void inserirPeriodoRelatorio() {
        if (this.periodoRelatorio.equals("INTERVALO")) {
            this.exibeIntervalo = true;
        } else {
            this.exibeIntervalo = false;
            this.paramPeriodo = null;
            this.paramPeriodoIntervalo = null;
        }
    }

    public void inserirPeriodoHE() {
        if (this.periodoHE.equals("INTERVALO")) {
            this.exibeIntervaloHE = true;
        } else {
            this.exibeIntervaloHE = false;
            this.paramPeriodoHE = null;
            this.paramPeriodoIntervaloHE = null;
        }
    }

    public void exibePnlRelAnaliseEq() {
        this.pnl_rel_analise_eq = true;
        this.pnl_historico_equipamento = false;

        //ZERAR PAINEL HISTORICO EQUIPAMENTO
        this.exibeIntervaloHE = false;
        this.paramPeriodoHE = null;
        this.paramPeriodoIntervaloHE = null;
        this.paramEquipamento = 0;
        this.periodoHE = "";
    }

    public void exibePnlHistoricoEquipamento() {

        this.pnl_historico_equipamento = true;
        this.pnl_rel_analise_eq = false;

        //ZERA PAINEL HISTORICO EQUIPAMENTO
        this.exibeIntervaloHE = false;
        this.paramPeriodoHE = null;
        this.paramPeriodoIntervaloHE = null;
        this.paramEquipamento = 0;
        this.periodoHE = "";

        //ZERAR PAINEL ANALISE SS
        this.exibeIntervalo = false;
        this.periodoRelatorio = "";
        this.tipoEquipamento = false;
        this.marca = false;
        this.ativo = false;
        this.inativo = false;

        //ZERA OS VALORES DOS COMPONENTES
        this.paramLocalidade = 0;
        this.paramLocalInstalacao = 0;
        this.paramLocalidadeTecnica = 0;
        this.paramLocalidadeTecnicaOperacional = 0;
        this.paramMarca = 0;
        this.paramPeriodo = null;
        this.paramPeriodoIntervalo = null;
        this.paramTipoEquipamento = 0;

    }

    public void zerarPainelRelatoriosEq() {

        this.exibeIntervaloHE = false;
        this.paramPeriodoHE = null;
        this.paramPeriodoIntervaloHE = null;
        this.paramEquipamento = 0;
        this.periodoHE = "";

        this.pnl_rel_analise_eq = false;
        this.pnl_historico_equipamento = false;
        this.exibeIntervalo = false;

        //ZERAR PAINEL ANALISE SS
        this.periodoRelatorio = "";
        this.tipoEquipamento = false;
        this.marca = false;
        this.ativo = false;
        this.inativo = false;

        //ZERA OS VALORES DOS COMPONENTES
        this.paramLocalidade = 0;
        this.paramLocalInstalacao = 0;
        this.paramLocalidadeTecnica = 0;
        this.paramLocalidadeTecnicaOperacional = 0;
        this.paramMarca = 0;
        this.paramPeriodo = null;
        this.paramPeriodoIntervalo = null;
        this.paramTipoEquipamento = 0;
    }

    public void manterValorLocalidade() {
        this.paramLocalidade = this.getParamLocalidade();
    }

    public void manterValorPeriodo() {
        this.paramPeriodo = this.getParamPeriodo();
    }

    public void manterValorIntervalo() {
        this.paramPeriodoIntervalo = this.getParamPeriodoIntervalo();
    }

    public void manterValorTipoEquipamento() {
        this.paramTipoEquipamento = this.getParamTipoEquipamento();
    }

    public void manterValorMarca() {
        this.paramMarca = this.getParamMarca();
    }

    public void manterValorLocalidadeTecnica() {
        this.paramLocalidadeTecnica = this.getParamLocalidadeTecnica();
    }

    public void manterValorLocalidadeTecnicaOperacional() {
        this.paramLocalidadeTecnicaOperacional = this.getParamLocalidadeTecnicaOperacional();
    }

    public void manterLocalInstalacao() {
        this.paramLocalInstalacao = this.getParamLocalInstalacao();
    }

    public void manterValorParamEquipamento() {
        this.paramEquipamento = this.getParamEquipamento();
    }

    public void manterValorPeriodoHE() {
        this.paramPeriodoHE = this.getParamPeriodoHE();
    }

    public void manterValorIntervaloHE() {
        this.paramPeriodoIntervaloHE = this.getParamPeriodoIntervaloHE();
    }

    public void inserirTipoEquipamento() {
        this.tipoEquipamento = this.isTipoEquipamento();
        if (tipoEquipamento == false) {
            this.paramTipoEquipamento = 0;
        }
    }

    public void inserirMarca() {
        this.marca = this.isMarca();
        if (marca == false) {
            this.paramMarca = 0;
        }
    }

    public void inserirEquipamentosAtivos() {
        this.ativo = this.isAtivo();
    }

    public void inserirEquipamentosInativos() {
        this.inativo = this.isInativo();
    }

    public void gerarRelatorioAnaliseEquipamento(ActionListener e) throws JRException, IOException, ParseException {

        if ((periodoRelatorio.equals("INTERVALO") && paramPeriodo == null && paramPeriodoIntervalo == null)
                || (periodoRelatorio.equals("INTERVALO") && paramPeriodo == null)
                || periodoRelatorio.equals("INTERVALO") && paramPeriodoIntervalo == null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Com a opção 'INTERVALO DE TEMPO' selecionada, os campos de datas não podem ser vazios!", ""));

        } else {

            List<Equipamento> eqLista = new ArrayList<Equipamento>();
            List<AnaliseEquipamento> AnaliseEqLista = new ArrayList<AnaliseEquipamento>();
            EntityManager em = emf.createEntityManager();

            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            String data1;
            String data2;

            if (periodoRelatorio.equals("INTERVALO")) {

                calendar1.setTime(paramPeriodo);
                calendar1.set(Calendar.HOUR, 0);
                calendar1.set(Calendar.MINUTE, 0);
                calendar1.set(Calendar.SECOND, 0);

                calendar2.setTime(paramPeriodoIntervalo);
                calendar2.set(Calendar.HOUR, 23);
                calendar2.set(Calendar.MINUTE, 59);
                calendar2.set(Calendar.SECOND, 59);

                data1 = sdf.format(calendar1.getTime());
                data2 = sdf.format(calendar2.getTime());

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                paramPeriodo = df.parse(data1);
                paramPeriodoIntervalo = df.parse(data2);
            }

            if (tipoEquipamento && marca && ativo && inativo && periodoRelatorio.equals("GERAL")) {
                //System.out.println("OPÇÕES SELECIONADAS: TODAS COM GERAL");
                //TODOS COM GERAL
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.localidade.municipio.id = :localidade");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && ativo && inativo && periodoRelatorio.equals("INTERVALO")) {
                //System.out.println("OPÇÕES SELECIONADAS: TODAS COM INTERVALO");
                //TODOS COM INTERVALO
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && ativo && periodoRelatorio.equals("GERAL")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS INATIVO COM GERAL");
                //MENOS INATIVO COM GERAL
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.ativo = true AND e.localidade.municipio.id = :localidade");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && ativo && periodoRelatorio.equals("INTERVALO")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS INATIVO COM INTERVALO");
                //MENOS INATIVO COM INTERVALO
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.ativo = true AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && inativo && periodoRelatorio.equals("GERAL")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS ATIVO COM GERAL");
                //MENOS ATIVO COM GERAL
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.ativo = false AND e.localidade.municipio.id = :localidade");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && inativo && periodoRelatorio.equals("INTERVALO")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS ATIVO COM INTERVALO");
                //MENOS ATIVO COM INTERVALO
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.ativo = false AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && ativo && inativo && periodoRelatorio.equals("GERAL")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS MARCA COM GERAL");
                //MENOS MARCA COM GERAL
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.localidade.municipio.id = :localidade");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && ativo && inativo && periodoRelatorio.equals("INTERVALO")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS MARCA COM INTERVALO");
                //MENOS MARCA COM INTERVALO
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && periodoRelatorio.equals("GERAL")) {
                //APENAS TIPO EQUIPAMENTO E MARCA COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: APENAS TIPO EQUIPAMENTO E MARCA COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.localidade.municipio.id = :localidade");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && marca && periodoRelatorio.equals("INTERVALO")) {
                //APENAS TIPO EQUIPAMENTO E MARCA COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: APENAS TIPO EQUIPAMENTO E MARCA COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.marca.id = :marca AND e.localidade.municipio.id = :localidade  AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && ativo && inativo && periodoRelatorio.equals("GERAL")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO COM GERAL");
                //MENOS TIPO EQUIPAMENTO COM GERAL
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.localidade.municipio.id = :localidade");
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && ativo && inativo && periodoRelatorio.equals("INTERVALO")) {
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO COM INTERVALO");
                //MENOS EQUIPAMENTO COM INTERVALO
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && ativo && periodoRelatorio.equals("GERAL")) {
                //MENOS MARCA E INATIVO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: MENOS MARCA E INATIVO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.ativo = true AND e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("tipo", paramTipoEquipamento);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && ativo && periodoRelatorio.equals("INTERVALO")) {
                //MENOS MARCA E INATIVO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: MENOS MARCA E INATIVO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.ativo = true AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && inativo && periodoRelatorio.equals("GERAL")) {
                //MENOS MARCA E ATIVO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: MENOS MARCA E ATIVO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.ativo = false AND e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("tipo", paramTipoEquipamento);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && inativo && periodoRelatorio.equals("INTERVALO")) {
                //MENOS MARCA E ATIVO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: MENOS MARCA E ATIVO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.ativo = false AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && ativo && periodoRelatorio.equals("GERAL")) {
                //MENOS TIPO EQUIPAMENTO E INATIVO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO E INATIVO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.ativo = true AND e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("marca", paramMarca);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && ativo && periodoRelatorio.equals("INTERVALO")) {
                //MENOS TIPO EQUIPAMENTO E INATIVO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO E INATIVO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.ativo = true AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("marca", paramMarca);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && inativo && periodoRelatorio.equals("GERAL")) {
                //MENOS TIPO EQUIPAMENTO E ATIVO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO E ATIVO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.ativo = false AND e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("marca", paramMarca);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && inativo && periodoRelatorio.equals("INTERVALO")) {
                //MENOS TIPO EQUIPAMENTO E ATIVO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO E ATIVO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.ativo = false AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("marca", paramMarca);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (ativo && inativo && periodoRelatorio.equals("GERAL")) {
                //MENOS TIPO EQUIPAMENTO E MARCA COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO E MARCA COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (ativo && inativo && periodoRelatorio.equals("INTERVALO")) {
                //MENOS TIPO EQUIPAMENTO E MARCA COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TIPO EQUIPAMENTO E MARCA COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (ativo && periodoRelatorio.equals("GERAL")) {
                //APENAS ATIVO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: APENAS ATIVO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.ativo = true AND e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (ativo && periodoRelatorio.equals("INTERVALO")) {
                //APENAS ATIVO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: APENAS ATIVO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.ativo = true AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (inativo && periodoRelatorio.equals("GERAL")) {
                //APENAS INATIVO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: APENAS INATIVO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.ativo = false AND e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (inativo && periodoRelatorio.equals("INTERVALO")) {
                //APENAS INATIVO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: APENAS INATIVO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.ativo = false AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && periodoRelatorio.equals("GERAL")) {
                //APENAS TIPO EQUIPAMENTO COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: APENAS TIPO EQUIPAMENTO COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.localidade.municipio.id = :localidade");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (tipoEquipamento && periodoRelatorio.equals("INTERVALO")) {
                //APENAS TIPO EQUIPAMENTO COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: APENAS TIPO EQUIPAMENTO COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.tipoEquipamento.id = :tipo AND e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("tipo", paramTipoEquipamento);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && periodoRelatorio.equals("GERAL")) {
                //APENAS MARCA COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: APENAS MARCA COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.localidade.municipio.id = :localidade");
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (marca && periodoRelatorio.equals("INTERVALO")) {
                //APENAS MARCA COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: APENAS MARCA COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.marca.id = :marca AND e.localidade.municipio.id = :localidade  AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("marca", paramMarca);
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else if (periodoRelatorio.equals("GERAL")) {
                //MENOS TODOS COM GERAL
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TODOS COM GERAL");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.localidade.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            } else {
                //MENOS TODOS COM INTERVALO
                //System.out.println("OPÇÕES SELECIONADAS: MENOS TODOS COM INTERVALO");
                Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.localidade.municipio.id = :localidade AND e.dataCadastro BETWEEN :data1 AND :data2");
                query.setParameter("localidade", paramLocalidade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                eqLista = query.getResultList();
            }

            for (Equipamento eqlista : eqLista) {
                AnaliseEquipamento analiseEq = new AnaliseEquipamento();
                if (eqlista.getAtivo() == true) {
                    analiseEq.ativo = "SIM";
                } else {
                    analiseEq.ativo = "NÃO";
                }
                analiseEq.codigo = String.valueOf(eqlista.getId());
                analiseEq.descricao = eqlista.getDescricao();
                if (eqlista.getLocalInstalacao().getDescricao().isEmpty() || eqlista.getLocalInstalacao().getDescricao() == null) {
                    analiseEq.localInstalacao = "-----";
                } else {
                    analiseEq.localInstalacao = eqlista.getLocalInstalacao().getDescricao();
                }
                analiseEq.localOperacao = eqlista.getLocalOperacao().getDescricao();
                analiseEq.localOperacaoTecnica = eqlista.getLocalOperacaoTecnica().getDescricao();
                analiseEq.marca = eqlista.getMarca().getDescricao();
                analiseEq.tipo = eqlista.getTipoEquipamento().getDescricao();
                analiseEq.tombamento = eqlista.getTombamento();
                analiseEq.dataCadastro = sdf.format(eqlista.getDataCadastro());
                if (eqlista.getDataInativo() == null) {
                    analiseEq.dataInativo = "-----";
                } else {
                    analiseEq.dataInativo = sdf.format(eqlista.getDataInativo());
                }
                AnaliseEqLista.add(analiseEq);
            }

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            String img1 = "logo.jpg";
            String img2 = "logoRR.jpg";
            String caminho1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reports/" + img1);
            String caminho2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reports/" + img2);

            Map parameters = new HashMap();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
            parameters.put("logo", caminho1);
            parameters.put("logoRR", caminho2);

            ByteArrayOutputStream retorno = new ByteArrayOutputStream();
            JasperReport jasperReport;

            try {
                File isReport = new File(context.getExternalContext().getRealPath("resources/reports/AnaliseEquipamento.jasper"));
                jasperReport = (JasperReport) JRLoader.loadObject(isReport);
                JRDataSource jRDataSource = new JRBeanCollectionDataSource(AnaliseEqLista, false);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jRDataSource);

                JRPdfExporter exporterPDF = new JRPdfExporter();
                exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
                exporterPDF.exportReport();

                byte[] bytes = retorno.toByteArray();
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename=AnaliseEquipamentoNº" + this.paramEquipamento + ".pdf");
                response.setContentLength(bytes.length);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (JRException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

    public void gerarHistoricoEquipamento() throws JRException, IOException {

        if ((periodoHE.equals("INTERVALO") && paramPeriodoHE == null && paramPeriodoIntervaloHE == null)
                || (periodoHE.equals("INTERVALO") && paramPeriodoHE == null)
                || periodoHE.equals("INTERVALO") && paramPeriodoIntervaloHE == null) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Com a opção 'INTERVALO DE TEMPO' selecionada, os campos de datas não podem ser vazios!", ""));

        } else {

            Equipamento equipamento = new Equipamento();
            String descricao;
            String tipo;
            String marca;
            String ativo;
            Date dataEntrada;
            Date dataFechamento;
            String localOperacao;
            String localOperacaoTecnica;
            String localInstalacao;
            String localidade;
            String tombamento;

            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e FROM Equipamento e WHERE e.id = " + paramEquipamento + "");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            equipamento = (Equipamento) query.getSingleResult();
            descricao = equipamento.getDescricao();
            tipo = equipamento.getTipoEquipamento().getDescricao();
            if (equipamento.getAtivo()) {
                ativo = "SIM";
            } else {
                ativo = "NÃO";
            }

            marca = equipamento.getMarca().getDescricao();
            dataEntrada = equipamento.getDataCadastro();
            dataFechamento = equipamento.getDataInativo();
            localOperacao = equipamento.getLocalOperacao().getDescricao();
            localOperacaoTecnica = equipamento.getLocalOperacaoTecnica().getDescricao();
            if (equipamento.getLocalInstalacao().equals("DEFAULT")) {
                localInstalacao = "---";
            } else {
                localInstalacao = equipamento.getLocalInstalacao().getDescricao();
            }
            localidade = equipamento.getLocalidade().getMunicipio().getDescricao() + " - " + equipamento.getLocalidade().getSetor().getSetorDescricao();
            tombamento = equipamento.getTombamento();

            if (this.periodoHE.equals("GERAL")) {

                ConexaoDB conn = new ConexaoDB();

                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                String img1 = "logoCaer.jpg";
                String img2 = "logoRR.jpg";
                String caminho1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reports/" + img1);
                String caminho2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reports/" + img2);

                Map parameters = new HashMap();
                parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
                parameters.put("logoCaer", caminho1);
                parameters.put("logoRR", caminho2);
                parameters.put("paramEquipamento", paramEquipamento);

                parameters.put("descricao", descricao);
                parameters.put("tipo", tipo);
                parameters.put("marca", marca);
                parameters.put("ativo", ativo);
                parameters.put("dataEntrada", dataEntrada);
                parameters.put("dataFechamento", dataFechamento);
                parameters.put("tombamento", tombamento);
                parameters.put("localOperacao", localOperacao);
                parameters.put("localOperacaoTecnica", localOperacaoTecnica);
                parameters.put("localInstalacao", localInstalacao);
                parameters.put("localidade", localidade);

                ByteArrayOutputStream retorno = new ByteArrayOutputStream();

                JasperReport jasperReport;

                try {
                    File isReport = new File(context.getExternalContext().getRealPath("resources/reports/HistoricoEquipamentoGeral.jasper"));
                    jasperReport = (JasperReport) JRLoader.loadObject(isReport);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn.conectar());

                    JRPdfExporter exporterPDF = new JRPdfExporter();
                    exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
                    exporterPDF.exportReport();

                    byte[] bytes = retorno.toByteArray();
                    response.setContentType("application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=HEnº" + this.paramEquipamento + ".pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    outputStream.close();
                    FacesContext.getCurrentInstance().responseComplete();

                } catch (JRException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else {

                Calendar calendar1 = Calendar.getInstance();

                calendar1.setTime(paramPeriodoHE);
                calendar1.set(Calendar.HOUR, 0);
                calendar1.set(Calendar.MINUTE, 0);
                calendar1.set(Calendar.SECOND, 0);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(paramPeriodoIntervaloHE);
                calendar2.set(Calendar.HOUR, 23);
                calendar2.set(Calendar.MINUTE, 59);
                calendar2.set(Calendar.SECOND, 59);

                ConexaoDB conn = new ConexaoDB();

                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                String img1 = "logoCaer.jpg";
                String img2 = "logoRR.jpg";
                String caminho1 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reports/" + img1);
                String caminho2 = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/reports/" + img2);

                Map parameters = new HashMap();
                parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
                parameters.put("logoCaer", caminho1);
                parameters.put("logoRR", caminho2);

                parameters.put("paramPeriodoHE", sdf.format(calendar1.getTime()));
                parameters.put("paramPeriodoIntervaloHE", sdf.format(calendar2.getTime()));
                parameters.put("paramEquipamento", paramEquipamento);

                parameters.put("descricao", descricao);
                parameters.put("tipo", tipo);
                parameters.put("marca", marca);
                parameters.put("ativo", ativo);
                parameters.put("dataEntrada", dataEntrada);
                parameters.put("dataFechamento", dataFechamento);
                parameters.put("tombamento", tombamento);
                parameters.put("localOperacao", localOperacao);
                parameters.put("localOperacaoTecnica", localOperacaoTecnica);
                parameters.put("localInstalacao", localInstalacao);
                parameters.put("localidade", localidade);

                ByteArrayOutputStream retorno = new ByteArrayOutputStream();

                JasperReport jasperReport;

                try {
                    File isReport = new File(context.getExternalContext().getRealPath("resources/reports/HistoricoEquipamento.jasper"));
                    jasperReport = (JasperReport) JRLoader.loadObject(isReport);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn.conectar());

                    JRPdfExporter exporterPDF = new JRPdfExporter();
                    exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
                    exporterPDF.exportReport();

                    byte[] bytes = retorno.toByteArray();
                    response.setContentType("application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=HEnº" + this.paramEquipamento + ".pdf");
                    response.setContentLength(bytes.length);
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    outputStream.close();
                    FacesContext.getCurrentInstance().responseComplete();

                } catch (JRException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public class AnaliseEquipamento {

        private String codigo;
        private String descricao;
        private String tipo;
        private String marca;
        private String localOperacao;
        private String localOperacaoTecnica;
        private String localInstalacao;
        private String tombamento;
        private String ativo;
        private String dataCadastro;
        private String dataInativo;

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getMarca() {
            return marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public String getLocalOperacao() {
            return localOperacao;
        }

        public void setLocalOperacao(String localOperacao) {
            this.localOperacao = localOperacao;
        }

        public String getLocalOperacaoTecnica() {
            return localOperacaoTecnica;
        }

        public void setLocalOperacaoTecnica(String localOperacaoTecnica) {
            this.localOperacaoTecnica = localOperacaoTecnica;
        }

        public String getLocalInstalacao() {
            return localInstalacao;
        }

        public void setLocalInstalacao(String localInstalacao) {
            this.localInstalacao = localInstalacao;
        }

        public String getTombamento() {
            return tombamento;
        }

        public void setTombamento(String tombamento) {
            this.tombamento = tombamento;
        }

        public String getAtivo() {
            return ativo;
        }

        public void setAtivo(String ativo) {
            this.ativo = ativo;
        }

        public String getDataCadastro() {
            return dataCadastro;
        }

        public void setDataCadastro(String dataCadastro) {
            this.dataCadastro = dataCadastro;
        }

        public String getDataInativo() {
            return dataInativo;
        }

        public void setDataInativo(String dataInativo) {
            this.dataInativo = dataInativo;
        }
    }

}
