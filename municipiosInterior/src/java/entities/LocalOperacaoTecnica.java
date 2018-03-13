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

/**
 *
 * @author eneas
 */
@Entity
@Table(catalog = "corporativo", schema = "interior", name = "local_operacao_tecnica")
@NamedQueries({
    @NamedQuery(name = "LocalOperacaoTecnica.findAll", query = "SELECT l FROM LocalOperacaoTecnica l"),
    @NamedQuery(name = "LocalOperacaoTecnica.findById", query = "SELECT l FROM LocalOperacaoTecnica l WHERE l.id = :id"),
    @NamedQuery(name = "LocalOperacaoTecnica.findByDescricao", query = "SELECT l FROM LocalOperacaoTecnica l WHERE l.descricao = :descricao")})
public class LocalOperacaoTecnica implements Serializable {
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
    @JoinColumn(name = "local_operacao", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LocalOperacao localOperacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localOperacaoTecnica")
    private Collection<Equipamento> equipamentoCollection;

    public LocalOperacaoTecnica() {
    }

    public LocalOperacaoTecnica(Integer id) {
        this.id = id;
    }

    public LocalOperacaoTecnica(Integer id, String descricao) {
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

    public LocalOperacao getLocalOperacao() {
        return localOperacao;
    }

    public void setLocalOperacao(LocalOperacao localOperacao) {
        this.localOperacao = localOperacao;
    }

    public Collection<Equipamento> getEquipamentoCollection() {
        return equipamentoCollection;
    }

    public void setEquipamentoCollection(Collection<Equipamento> equipamentoCollection) {
        this.equipamentoCollection = equipamentoCollection;
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
        if (!(object instanceof LocalOperacaoTecnica)) {
            return false;
        }
        LocalOperacaoTecnica other = (LocalOperacaoTecnica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LocalOperacaoTecnica[ id=" + id + " ]";
    }
    
}
