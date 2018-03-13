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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author eneas
 */
@Entity
@Table(catalog = "corporativo", schema = "interior", name = "prioridade")
@NamedQueries({
    @NamedQuery(name = "Prioridade.findAll", query = "SELECT p FROM Prioridade p"),
    @NamedQuery(name = "Prioridade.findById", query = "SELECT p FROM Prioridade p WHERE p.id = :id"),
    @NamedQuery(name = "Prioridade.findByDescricao", query = "SELECT p FROM Prioridade p WHERE p.descricao = :descricao")})
public class Prioridade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prioridade")
    private Collection<SolicitacaoServico> solicitacaoServicoCollection;

    public Prioridade() {
    }

    public Prioridade(Integer id) {
        this.id = id;
    }

    public Prioridade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Collection<SolicitacaoServico> getSolicitacaoServicoCollection() {
        return solicitacaoServicoCollection;
    }

    public void setSolicitacaoServicoCollection(Collection<SolicitacaoServico> solicitacaoServicoCollection) {
        this.solicitacaoServicoCollection = solicitacaoServicoCollection;
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
        if (!(object instanceof Prioridade)) {
            return false;
        }
        Prioridade other = (Prioridade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Prioridade[ id=" + id + " ]";
    }
    
}
