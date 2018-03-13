/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eneas
 */
@Entity
@Table(catalog = "corporativo", schema = "interior", name = "servico_ss")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServicoSs.findAll", query = "SELECT s FROM ServicoSs s"),
    @NamedQuery(name = "ServicoSs.findById", query = "SELECT s FROM ServicoSs s WHERE s.id = :id")})
public class ServicoSs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "solicitacao_servico", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SolicitacaoServico solicitacaoServico;
    @JoinColumn(name = "servico", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servico servico;

    public ServicoSs() {
    }

    public ServicoSs(Long id) {
        this.id = id;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
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
        if (!(object instanceof ServicoSs)) {
            return false;
        }
        ServicoSs other = (ServicoSs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ServicoSs[ id=" + id + " ]";
    }
    
}
