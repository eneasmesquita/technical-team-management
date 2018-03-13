/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(catalog = "corporativo", schema = "interior", name = "historico_situacao")
@NamedQueries({
    @NamedQuery(name = "HistoricoSituacao.findAll", query = "SELECT h FROM HistoricoSituacao h"),
    @NamedQuery(name = "HistoricoSituacao.findByDataAlteracao", query = "SELECT h FROM HistoricoSituacao h WHERE h.dataAlteracao = :dataAlteracao"),
    @NamedQuery(name = "HistoricoSituacao.findByColaboradorAlterador", query = "SELECT h FROM HistoricoSituacao h WHERE h.colaboradorAlterador = :colaboradorAlterador"),
    @NamedQuery(name = "HistoricoSituacao.findById", query = "SELECT h FROM HistoricoSituacao h WHERE h.id = :id")})
public class HistoricoSituacao implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "situacao")
    private String situacao;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @Size(max = 80)
    @Column(name = "colaborador_alterador")
    private String colaboradorAlterador;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "solicitacao_servico", referencedColumnName = "id")
    @ManyToOne
    private SolicitacaoServico solicitacaoServico;

    public HistoricoSituacao() {
    }

    public HistoricoSituacao(Long id) {
        this.id = id;
    }

    public HistoricoSituacao(Long id, Date dataAlteracao) {
        this.id = id;
        this.dataAlteracao = dataAlteracao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getColaboradorAlterador() {
        return colaboradorAlterador;
    }

    public void setColaboradorAlterador(String colaboradorAlterador) {
        this.colaboradorAlterador = colaboradorAlterador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitacaoServico getSolicitacaoServico() {
        return solicitacaoServico;
    }

    public void setSolicitacaoServico(SolicitacaoServico solicitacaoServico) {
        this.solicitacaoServico = solicitacaoServico;
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
        if (!(object instanceof HistoricoSituacao)) {
            return false;
        }
        HistoricoSituacao other = (HistoricoSituacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.HistoricoSituacao[ id=" + id + " ]";
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
}
