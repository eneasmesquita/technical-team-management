/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eneas
 */
@Entity
@Table(name = "equipamento_ss", catalog = "corporativo", schema = "interior")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EquipamentoSs.findAll", query = "SELECT e FROM EquipamentoSs e"),
    @NamedQuery(name = "EquipamentoSs.findById", query = "SELECT e FROM EquipamentoSs e WHERE e.id = :id")})
public class EquipamentoSs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "defeito")
    private String defeito;
    @Size(min = 1, max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @JoinColumn(name = "solicitacao_servico", referencedColumnName = "id")
    @ManyToOne
    private SolicitacaoServico solicitacaoServico;
    @JoinColumn(name = "equipamento", referencedColumnName = "id")
    @ManyToOne
    private Equipamento equipamento;
    

    public EquipamentoSs() {
    }

    public EquipamentoSs(Long id, String defeito) {
        this.id = id;
        this.defeito = defeito;
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

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
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
        if (!(object instanceof EquipamentoSs)) {
            return false;
        }
        EquipamentoSs other = (EquipamentoSs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EquipamentoSs[ id=" + id + " ]";
    }
    
}
