/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import entities.SolicitacaoServico;
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
@ManagedBean(name = "RelatorioSsCtrl")
@SessionScoped
public class RelatoriosSSControllerBean {

    private boolean pnl_rel_analise = false;
    private boolean pnl_rel_equipe_servico = false;

    private boolean situacao = false;
    private boolean prioridade = false;
    private boolean exibeCalendar = false;
    private boolean exibeIntervalo = false;
    private boolean exibeCalendarES = false;
    private boolean exibeIntervaloES = false;

    private Date paramPeriodo;
    private Date paramPeriodoIntervalo;
    private Date paramPeriodoES;
    private Date paramPeriodoIntervaloES;

    private String periodoES = "";
    private String periodo = "";

    private int paramLocalidade = 0;
    private int paramEquipe = 0;
    private int paramServico = 0;
    private String paramSituacao = "";
    private String paramSituacaoAux; //FOI NECESSÁRIO TER SIDO CRIADA PARA GARANTIR QUE A 'situacao' APÓS O SWITCH, DISPONIBILIZA PARA A JPQL O PARAMETRO CORRETO.
    private int paramPrioridade = 0;

    private Date paramData;
    private Date paramDataES;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private List<SituacaoSS> situacoes = new ArrayList<>();

    /**
     * Creates a new instance of RelatoriosControllerBean
     */
    public RelatoriosSSControllerBean() {
    }

    public String getParamSituacaoAux() {
        return paramSituacaoAux;
    }

    public void setParamSituacaoAux(String paramSituacaoAux) {
        this.paramSituacaoAux = paramSituacaoAux;
    }

    public List<SituacaoSS> getSituacoes() {
        situacoes.clear();
        situacoes.add(new SituacaoSS("1", "AGUARDANDO BAIXA MANUTENÇÃO CAPITAL"));
        situacoes.add(new SituacaoSS("2", "AGUARDANDO PARECER TÉCNICO"));
        situacoes.add(new SituacaoSS("3", "AGUARDANDO FORNECIMENTO DE EQUIPAMENTO"));
        situacoes.add(new SituacaoSS("4", "SERVIÇO EM ANDAMENTO"));
        situacoes.add(new SituacaoSS("5", "SUBSTITUIÇÃO DE EQUIPAMENTO EM ANDAMENTO"));
        situacoes.add(new SituacaoSS("6", "ENCERRADA COM SERVIÇO EFETUADO"));
        situacoes.add(new SituacaoSS("7", "ENCERRADA SEM SERVIÇO"));
        situacoes.add(new SituacaoSS("8", "ENCERRADA COM SUBSTITUIÇÃO EFETUADA"));
        situacoes.add(new SituacaoSS("9", "ENCERRADA EQUIPAMENTO CONDENADO"));
        return situacoes;
    }

    public void setSituacoes(List<SituacaoSS> situacoes) {
        this.situacoes = situacoes;
    }

    public boolean isPnl_rel_analise() {
        return pnl_rel_analise;
    }

    public void setPnl_rel_analise(boolean pnl_rel_analise) {
        this.pnl_rel_analise = pnl_rel_analise;
    }

    public boolean isPnl_rel_equipe_servico() {
        return pnl_rel_equipe_servico;
    }

    public void setPnl_rel_equipe_servico(boolean pnl_rel_equipe_servico) {
        this.pnl_rel_equipe_servico = pnl_rel_equipe_servico;
    }

    public void exibePnlRelAnalise() {
        this.pnl_rel_analise = true;
        this.pnl_rel_equipe_servico = false;

        this.paramEquipe = 0;
        this.paramPeriodoES = null;
        this.exibeCalendarES = false;
        this.exibeIntervaloES = false;
        this.periodoES = "";
        this.paramPeriodoIntervaloES = null;
        this.paramServico = 0;
    }

    public void exibePnlRelEquipeServico() {
        this.pnl_rel_analise = false;
        this.pnl_rel_equipe_servico = true;

        this.exibeCalendar = false;
        this.exibeIntervalo = false;
        this.paramLocalidade = 0;
        this.paramPeriodoIntervalo = null;
        this.paramPrioridade = 0;
        this.paramSituacao = "";
        this.periodo = "";
        this.situacao = false;
        this.prioridade = false;
        this.paramPeriodo = null;
    }

    public void zerarPainelRelatoriosSS() {
        this.pnl_rel_analise = false;
        this.pnl_rel_equipe_servico = false;

        this.exibeCalendar = false;
        this.exibeIntervalo = false;
        this.paramLocalidade = 0;
        this.paramPeriodo = null;
        this.paramPeriodoIntervalo = null;
        this.paramPrioridade = 0;
        this.paramSituacao = "";
        this.periodo = "";
        this.situacao = false;
        this.prioridade = false;

        this.paramEquipe = 0;
        this.paramPeriodoES = null;
        this.exibeCalendarES = false;
        this.exibeIntervaloES = false;
        this.periodoES = "";
        this.paramPeriodoIntervaloES = null;
        this.paramServico = 0;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public boolean isPrioridade() {
        return prioridade;
    }

    public void setPrioridade(boolean prioridade) {
        this.prioridade = prioridade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public boolean isExibeCalendar() {
        return exibeCalendar;
    }

    public void setExibeCalendar(boolean exibeCalendar) {
        this.exibeCalendar = exibeCalendar;
    }

    public boolean isExibeIntervalo() {
        return exibeIntervalo;
    }

    public void setExibeIntervalo(boolean exibeIntervalo) {
        this.exibeIntervalo = exibeIntervalo;
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

    public String getPeriodoES() {
        return periodoES;
    }

    public void setPeriodoES(String periodoES) {
        this.periodoES = periodoES;
    }

    public Date getParamPeriodoES() {
        return paramPeriodoES;
    }

    public void setParamPeriodoES(Date paramPeriodoES) {
        this.paramPeriodoES = paramPeriodoES;
    }

    public Date getParamPeriodoIntervaloES() {
        return paramPeriodoIntervaloES;
    }

    public void setParamPeriodoIntervaloES(Date paramPeriodoIntervaloES) {
        this.paramPeriodoIntervaloES = paramPeriodoIntervaloES;
    }

    public boolean isExibeCalendarES() {
        return exibeCalendarES;
    }

    public void setExibeCalendarES(boolean exibeCalendarES) {
        this.exibeCalendarES = exibeCalendarES;
    }

    public boolean isExibeIntervaloES() {
        return exibeIntervaloES;
    }

    public void setExibeIntervaloES(boolean exibeIntervaloES) {
        this.exibeIntervaloES = exibeIntervaloES;
    }

    public int getParamLocalidade() {
        return paramLocalidade;
    }

    public void setParamLocalidade(int paramLocalidade) {
        this.paramLocalidade = paramLocalidade;
    }

    public int getParamEquipe() {
        return paramEquipe;
    }

    public void setParamEquipe(int paramEquipe) {
        this.paramEquipe = paramEquipe;
    }

    public int getParamServico() {
        return paramServico;
    }

    public void setParamServico(int paramServico) {
        this.paramServico = paramServico;
    }

    public String getParamSituacao() {
        return paramSituacao;
    }

    public void setParamSituacao(String paramSituacao) {
        this.paramSituacao = paramSituacao;
    }

    public int getParamPrioridade() {
        return paramPrioridade;
    }

    public void setParamPrioridade(int paramPrioridade) {
        this.paramPrioridade = paramPrioridade;
    }

    public Date getParamData() {
        return paramData;
    }

    public void setParamData(Date paramData) {
        this.paramData = paramData;
    }

    public Date getParamDataES() {
        return paramDataES;
    }

    public void setParamDataES(Date paramDataES) {
        this.paramDataES = paramDataES;
    }

    public void inserirSituacao() {
        this.situacao = this.isSituacao();
        if (!situacao) {
            paramSituacao = "";
        }
    }

    public void inserirPrioridade() {
        this.prioridade = isPrioridade();
        if (!prioridade) {
            paramPrioridade = 0;
        }
    }

    public void inserirPeriodo() {

        if (this.periodo.equals("INTERVALO")) {
            this.exibeIntervalo = true;
        } else {
            this.exibeIntervalo = false;
            this.paramPeriodo = null;
            this.paramPeriodoIntervalo = null;
        }
    }

    public void manterValorParamLocalidade() {
        this.paramLocalidade = this.getParamLocalidade();
    }

    public void manterValorParamPeriodo() {
        this.paramPeriodo = this.getParamPeriodo();
    }

    public void manterValorParamPeriodoIntervalo() {
        this.paramPeriodoIntervalo = this.getParamPeriodoIntervalo();
    }

    public void manterValorParamSituacao() {

        switch (this.getParamSituacao()) {
            case "1":
                this.paramSituacao = "AGUARDANDO BAIXA MANUTENÇÃO CAPITAL";
                break;
            case "2":
                this.paramSituacao = "AGUARDANDO PARECER TÉCNICO";
                break;
            case "3":
                this.paramSituacao = "AGUARDANDO FORNECIMENTO DE EQUIPAMENTO";
                break;
            case "4":
                this.paramSituacao = "SERVIÇO EM ANDAMENTO";
                break;
            case "5":
                this.paramSituacao = "ENCERRADA COM SERVIÇO EFETUADO";
                break;
            case "6":
                this.paramSituacao = "ENCERRADA SEM SERVIÇO";
                break;
            case "7":
                this.paramSituacao = "ENCERRADA COM SUBSTITUIÇÃO EFETUADA";
                break;
            case "8":
                this.paramSituacao = "ENCERRADA EQUIPAMENTO CONDENADO";
                break;
            default:
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Você está inserindo uma solicitação nula, o relatório não será gerado!", null));
        }

    }

    public void manterValorParamPrioridade() {
        this.paramPrioridade = this.getParamPrioridade();
    }

    public void manterValorParamEquipe() {
        this.paramEquipe = this.getParamEquipe();
    }

    public void manterValorParamServico() {
        this.paramServico = this.getParamServico();
    }

    public void manterValorLocalidade() {
        this.paramLocalidade = this.getParamLocalidade();
    }

    public void inserirPeriodoES() {

        if (this.periodoES.equals("INTERVALO")) {
            this.exibeIntervaloES = true;
        } else {
            this.exibeIntervaloES = false;
            this.paramPeriodoES = null;
            this.paramPeriodoIntervaloES = null;
        }
    }

    public void manterValorParamPeriodoES() {
        this.paramPeriodoES = this.getParamPeriodoES();
    }

    public void manterValorParamPeriodoIntervaloES() {
        this.paramPeriodoIntervaloES = this.getParamPeriodoIntervaloES();
    }

    public void gerarRelatorioAnaliseSS(ActionListener e) throws JRException, IOException, ParseException {

        if ((periodo.equals("INTERVALO") && paramPeriodo == null && paramPeriodoIntervalo == null)
                || (periodo.equals("INTERVALO") && paramPeriodo == null)
                || periodo.equals("INTERVALO") && paramPeriodoIntervalo == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Com a opção 'INTERVALO DE TEMPO' selecionada, os campos de datas não podem ser vazios!", ""));

        } else {

            //OPTEI POR NÃO UTILIZAR 'Criteria' DEVIDO A FALTA DE TEMPO PARA IMPLEMENTÁ-LA!
            List<SolicitacaoServico> ssLista = new ArrayList<SolicitacaoServico>();
            List<AnaliseSs> AnaliseSsLista = new ArrayList<AnaliseSs>();
            EntityManager em = emf.createEntityManager();

            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            String data1;
            String data2;

            if (periodo.equals("INTERVALO")) {

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

            if (situacao && periodo.equals("GERAL")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.situacao = :situacao AND ss.municipio.id = :localidade");
                query.setParameter("situacao", paramSituacaoAux);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else if (situacao && periodo.equals("INTERVALO")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.situacao = :situacao AND ss.dataAbertura BETWEEN :data1 AND :data2 AND ss.municipio.id = :localidade");
                query.setParameter("situacao", paramSituacaoAux);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else if (prioridade && periodo.equals("GERAL")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.prioridade.id = :prioridade AND ss.municipio.id = :localidade");
                query.setParameter("prioridade", paramPrioridade);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else if (prioridade && periodo.equals("INTERVALO")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.prioridade.id = :prioridade AND ss.dataAbertura BETWEEN :data1 AND :data2 AND ss.municipio.id = :localidade");
                query.setParameter("prioridade", paramPrioridade);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else if (situacao && prioridade && periodo.equals("GERAL")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.prioridade.id = :prioridade AND ss.situacao = :situacao AND ss.municipio.id = :localidade");
                query.setParameter("situacao", paramSituacaoAux);
                query.setParameter("prioridade", paramPrioridade);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else if (situacao && prioridade && periodo.equals("INTERVALO")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.situacao = :situacao AND ss.prioridade.id = :prioridade AND ss.dataAbertura BETWEEN :data1 AND :data2 AND ss.municipio.id = :localidade");
                query.setParameter("prioridade", paramPrioridade);
                query.setParameter("situacao", paramSituacaoAux);
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else if (periodo.equals("INTERVALO")) {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.dataAbertura BETWEEN :data1 AND :data2 AND ss.municipio.id = :localidade");
                query.setParameter("data1", paramPeriodo);
                query.setParameter("data2", paramPeriodoIntervalo);
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            } else {
                Query query = em.createQuery("SELECT ss FROM SolicitacaoServico ss WHERE ss.municipio.id = :localidade");
                query.setParameter("localidade", paramLocalidade);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                ssLista = query.getResultList();
            }

            for (SolicitacaoServico ss : ssLista) {
                AnaliseSs analiseSs = new AnaliseSs();
                analiseSs.numero = String.valueOf(ss.getId());
                analiseSs.dataAbertura = sdf.format(ss.getDataAbertura());
                if (ss.getDataFechamento() == null) {
                    analiseSs.dataFechamento = "-----";
                } else {
                    analiseSs.dataFechamento = sdf.format(ss.getDataFechamento());
                }
                if (ss.getEquipe() == null) {
                    analiseSs.equipe = "-----";
                } else {
                    analiseSs.equipe = ss.getEquipe().getDescricao();
                }
                if (ss.getParecerTecnico().isEmpty() || ss.getParecerTecnico() == null) {
                    analiseSs.parecerTecnico = "-----";
                } else {
                    analiseSs.parecerTecnico = ss.getParecerTecnico();
                }

                analiseSs.localSolicitacao = ss.getLocalSolicitacao();
                analiseSs.prioridade = ss.getPrioridade().getDescricao();
                analiseSs.responsavel = ss.getResponsavel();
                analiseSs.situacao = ss.getSituacao();
                if (ss.getEncerrada()) {
                    analiseSs.encerrada = "SIM";
                } else {
                    analiseSs.encerrada = "NÃO";
                }
                AnaliseSsLista.add(analiseSs);
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
                File isReport = new File(context.getExternalContext().getRealPath("resources/reports/AnaliseSs.jasper"));
                jasperReport = (JasperReport) JRLoader.loadObject(isReport);
                JRDataSource jRDataSource = new JRBeanCollectionDataSource(AnaliseSsLista, false);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jRDataSource);

                JRPdfExporter exporterPDF = new JRPdfExporter();
                exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
                exporterPDF.exportReport();

                byte[] bytes = retorno.toByteArray();
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename=AnaliseSs.pdf");
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

    public void gerarRelatorioEquipeServico(ActionListener e) throws JRException, IOException {

        if ((periodoES.equals("INTERVALO") && paramPeriodoES == null && paramPeriodoIntervaloES == null)
                || (periodoES.equals("INTERVALO") && paramPeriodoES == null)
                || periodoES.equals("INTERVALO") && paramPeriodoIntervaloES == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Com a opção 'INTERVALO DE TEMPO' selecionada, os campos de datas não podem ser vazios!", ""));

        } else {

            String equipe;
            String servico;
            EntityManager em = emf.createEntityManager();

            Query query = em.createQuery("SELECT e.descricao FROM Equipe e WHERE e.id = " + paramEquipe + "");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            equipe = (String) query.getSingleResult();

            query = em.createQuery("SELECT s.descricaoServico FROM Servico s WHERE s.id = " + paramServico + "");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            servico = (String) query.getSingleResult();

            if (this.periodoES.equals("GERAL")) {
                ConexaoDB conn = new ConexaoDB();
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
                parameters.put("paramEquipe", paramEquipe);
                parameters.put("paramServico", paramServico);
                parameters.put("equipe", equipe);
                parameters.put("servico", servico);
                ByteArrayOutputStream retorno = new ByteArrayOutputStream();

                JasperReport jasperReport;

                try {
                    File isReport = new File(context.getExternalContext().getRealPath("resources/reports/RelatorioEquipeServicoGeral.jasper"));
                    jasperReport = (JasperReport) JRLoader.loadObject(isReport);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn.conectar());

                    JRPdfExporter exporterPDF = new JRPdfExporter();
                    exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
                    exporterPDF.exportReport();

                    byte[] bytes = retorno.toByteArray();
                    response.setContentType("application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=RelatorioEquipeServicoGeral" + paramEquipe + "" + paramServico + ".pdf");
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

                calendar1.setTime(paramPeriodoES);
                calendar1.set(Calendar.HOUR, 0);
                calendar1.set(Calendar.MINUTE, 0);
                calendar1.set(Calendar.SECOND, 0);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(paramPeriodoIntervaloES);
                calendar2.set(Calendar.HOUR, 23);
                calendar2.set(Calendar.MINUTE, 59);
                calendar2.set(Calendar.SECOND, 59);

                ConexaoDB conn = new ConexaoDB();

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
                parameters.put("paramPeriodo", sdf.format(calendar1.getTime()));
                parameters.put("paramPeriodoIntervalo", sdf.format(calendar2.getTime()));
                parameters.put("paramEquipe", paramEquipe);
                parameters.put("paramServico", paramServico);
                parameters.put("equipe", equipe);
                parameters.put("servico", servico);

                ByteArrayOutputStream retorno = new ByteArrayOutputStream();

                JasperReport jasperReport;

                try {
                    File isReport = new File(context.getExternalContext().getRealPath("resources/reports/RelatorioEquipeServico.jasper"));
                    jasperReport = (JasperReport) JRLoader.loadObject(isReport);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn.conectar());

                    JRPdfExporter exporterPDF = new JRPdfExporter();
                    exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
                    exporterPDF.exportReport();

                    byte[] bytes = retorno.toByteArray();
                    response.setContentType("application/pdf");
                    response.addHeader("Content-Disposition", "attachment; filename=RelatorioEquipeServico" + paramEquipe + "" + paramServico + ".pdf");
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

    public class SituacaoSS {

        private String valor;
        private String label;

        public SituacaoSS(String valor, String label) {
            this.valor = valor;
            this.label = label;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public class AnaliseSs {

        private String numero;
        private String dataAbertura;
        private String dataFechamento;
        private String prioridade;
        private String situacao;
        private String localSolicitacao;
        private String responsavel;
        private String equipe;
        private String parecerTecnico;
        private String encerrada;

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getDataAbertura() {
            return dataAbertura;
        }

        public void setDataAbertura(String dataAbertura) {
            this.dataAbertura = dataAbertura;
        }

        public String getDataFechamento() {
            return dataFechamento;
        }

        public void setDataFechamento(String dataFechamento) {
            this.dataFechamento = dataFechamento;
        }

        public String getLocalSolicitacao() {
            return localSolicitacao;
        }

        public void setLocalSolicitacao(String localSolicitacao) {
            this.localSolicitacao = localSolicitacao;
        }

        public String getPrioridade() {
            return prioridade;
        }

        public void setPrioridade(String prioridade) {
            this.prioridade = prioridade;
        }

        public String getSituacao() {
            return situacao;
        }

        public void setSituacao(String situacao) {
            this.situacao = situacao;
        }

        public String getResponsavel() {
            return responsavel;
        }

        public void setResponsavel(String responsavel) {
            this.responsavel = responsavel;
        }

        public String getEncerrada() {
            return encerrada;
        }

        public void setEncerrada(String encerrada) {
            this.encerrada = encerrada;
        }

        public String getEquipe() {
            return equipe;
        }

        public void setEquipe(String equipe) {
            this.equipe = equipe;
        }

        public String getParecerTecnico() {
            return parecerTecnico;
        }

        public void setParecerTecnico(String parecerTecnico) {
            this.parecerTecnico = parecerTecnico;
        }
    }
}
