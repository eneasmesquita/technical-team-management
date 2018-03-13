/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import login.dao.ConexaoDB;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Eneas
 */
@ManagedBean(name = "ImprimirSsCtrl")
@SessionScoped
public class ImprimirSSControllerBean {

    private long numSS = 0;
    private String prioridade = "";
    private String situacao = "";
    private Date dataAbertura;
    private Date dataEncerramento;
    private String responsavelSS = "";
    private String observacoes = "";
    private boolean encerrada = false;
    private String parecerTecnico = "";
    private String localAberturaSS = "";
    private String equipe = "";

    /**
     * Creates a new instance of ImprimirSSControllerBean
     */
    public ImprimirSSControllerBean() {
    }

    public long getNumSS() {
        return numSS;
    }

    public void setNumSS(long numSS) {
        this.numSS = numSS;
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

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getResponsavelSS() {
        return responsavelSS;
    }

    public void setResponsavelSS(String responsavelSS) {
        this.responsavelSS = responsavelSS;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    public void setEncerrada(boolean encerrada) {
        this.encerrada = encerrada;
    }

    public String getParecerTecnico() {
        return parecerTecnico;
    }

    public void setParecerTecnico(String parecerTecnico) {
        this.parecerTecnico = parecerTecnico;
    }

    public String getLocalAberturaSS() {
        return localAberturaSS;
    }

    public void setLocalAberturaSS(String localAberturaSS) {
        this.localAberturaSS = localAberturaSS;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public void imprimirSS(ActionListener e) throws JRException, IOException {

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

        parameters.put("numSS", numSS);
        parameters.put("prioridade", prioridade);
        parameters.put("situacao", situacao);
        parameters.put("dataAbertura", dataAbertura);
        parameters.put("dataEncerramento", dataEncerramento);
        parameters.put("responsavelSS", responsavelSS);
        parameters.put("observacoes", observacoes);
        parameters.put("encerrada", encerrada);
        parameters.put("parecerTecnico", parecerTecnico);
        parameters.put("localAberturaSS", localAberturaSS);
        parameters.put("equipe", equipe);

        ByteArrayOutputStream retorno = new ByteArrayOutputStream();

        JasperReport jasperReport;

        try {
            File isReport = new File(context.getExternalContext().getRealPath("resources/reports/SolicitacaoServico.jasper"));
            jasperReport = (JasperReport) JRLoader.loadObject(isReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn.conectar());

            JRPdfExporter exporterPDF = new JRPdfExporter();
            exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
            exporterPDF.exportReport();

            byte[] bytes = retorno.toByteArray();
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=SSnº" + this.numSS + ".pdf");
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
