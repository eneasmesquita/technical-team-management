/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Eneas
 */
@Entity
@Table(catalog = "corporativo", schema = "interior", name = "solicitacao_servico")
@NamedQueries({
    @NamedQuery(name = "SolicitacaoServico.findAll", query = "SELECT s FROM SolicitacaoServico s"),
    @NamedQuery(name = "SolicitacaoServico.findByResponsavel", query = "SELECT s FROM SolicitacaoServico s WHERE s.responsavel = :responsavel"),
    @NamedQuery(name = "SolicitacaoServico.findByParecerTecnico", query = "SELECT s FROM SolicitacaoServico s WHERE s.parecerTecnico = :parecerTecnico"),
    @NamedQuery(name = "SolicitacaoServico.findByDataAbertura", query = "SELECT s FROM SolicitacaoServico s WHERE s.dataAbertura = :dataAbertura"),
    @NamedQuery(name = "SolicitacaoServico.findByDataFechamento", query = "SELECT s FROM SolicitacaoServico s WHERE s.dataFechamento = :dataFechamento"),
    @NamedQuery(name = "SolicitacaoServico.findById", query = "SELECT s FROM SolicitacaoServico s WHERE s.id = :id"),
    @NamedQuery(name = "SolicitacaoServico.findByObservacao", query = "SELECT s FROM SolicitacaoServico s WHERE s.observacao = :observacao"),
    @NamedQuery(name = "SolicitacaoServico.findByEncerrada", query = "SELECT s FROM SolicitacaoServico s WHERE s.encerrada = :encerrada"),
    @NamedQuery(name = "SolicitacaoServico.findByLocalSolicitacao", query = "SELECT s FROM SolicitacaoServico s WHERE s.localSolicitacao = :localSolicitacao")})
public class SolicitacaoServico implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "situacao")
    private String situacao;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "responsavel")
    private String responsavel;
    @Size(max = 2147483647)
    @Column(name = "parecer_tecnico")
    private String parecerTecnico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_abertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAbertura;
    @Column(name = "data_fechamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFechamento;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "encerrada")
    private Boolean encerrada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "local_solicitacao")
    private String localSolicitacao;
    @OneToMany(mappedBy = "solicitacaoServico")
    private Collection<EquipamentoSs> equipamentoSsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitacaoServico")
    private Collection<ServicoSs> servicoSsCollection;
    @OneToMany(mappedBy = "solicitacaoServico")
    private Collection<HistoricoSituacao> historicoSituacaoCollection;
    @JoinColumn(name = "prioridade", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Prioridade prioridade;
    @JoinColumn(name = "municipio", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Municipio municipio;
    @JoinColumn(name = "equipe", referencedColumnName = "id")
    @ManyToOne
    private Equipe equipe;

    public SolicitacaoServico() {
    }

    public SolicitacaoServico(Long id) {
        this.id = id;
    }

    public SolicitacaoServico(Long id, String responsavel, Date dataAbertura, String localSolicitacao) {
        this.id = id;
        this.responsavel = responsavel;
        this.dataAbertura = dataAbertura;
        this.localSolicitacao = localSolicitacao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getParecerTecnico() {
        return parecerTecnico;
    }

    public void setParecerTecnico(String parecerTecnico) {
        this.parecerTecnico = parecerTecnico;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getEncerrada() {
        return encerrada;
    }

    public void setEncerrada(Boolean encerrada) {
        this.encerrada = encerrada;
    }

    public String getLocalSolicitacao() {
        return localSolicitacao;
    }

    public void setLocalSolicitacao(String localSolicitacao) {
        this.localSolicitacao = localSolicitacao;
    }

    public Collection<EquipamentoSs> getEquipamentoSsCollection() {
        return equipamentoSsCollection;
    }

    public void setEquipamentoSsCollection(Collection<EquipamentoSs> equipamentoSsServicoCollection) {
        this.equipamentoSsCollection = equipamentoSsServicoCollection;
    }

    public Collection<ServicoSs> getServicoSsCollection() {
        return servicoSsCollection;
    }

    public void setServicoSsCollection(Collection<ServicoSs> servicoSsCollection) {
        this.servicoSsCollection = servicoSsCollection;
    }

    public Collection<HistoricoSituacao> getHistoricoSituacaoCollection() {
        return historicoSituacaoCollection;
    }

    public void setHistoricoSituacaoCollection(Collection<HistoricoSituacao> historicoSituacaoCollection) {
        this.historicoSituacaoCollection = historicoSituacaoCollection;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitacaoServico)) {
            return false;
        }
        SolicitacaoServico other = (SolicitacaoServico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SolicitacaoServico[ id=" + id + " ]";
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
}
