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
@Table(catalog = "corporativo", schema = "interior", name = "local_operacao")
@NamedQueries({
    @NamedQuery(name = "LocalOperacao.findAll", query = "SELECT l FROM LocalOperacao l"),
    @NamedQuery(name = "LocalOperacao.findById", query = "SELECT l FROM LocalOperacao l WHERE l.id = :id"),
    @NamedQuery(name = "LocalOperacao.findByDescricao", query = "SELECT l FROM LocalOperacao l WHERE l.descricao = :descricao")})
public class LocalOperacao implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localOperacao")
    private Collection<LocalOperacaoTecnica> localOperacaoTecnicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "localOperacao")
    private Collection<Equipamento> equipamentoCollection;

    public LocalOperacao() {
    }

    public LocalOperacao(Integer id) {
        this.id = id;
    }

    public LocalOperacao(Integer id, String descricao) {
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

    public Collection<LocalOperacaoTecnica> getLocalOperacaoTecnicaCollection() {
        return localOperacaoTecnicaCollection;
    }

    public void setLocalOperacaoTecnicaCollection(Collection<LocalOperacaoTecnica> localOperacaoTecnicaCollection) {
        this.localOperacaoTecnicaCollection = localOperacaoTecnicaCollection;
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
        if (!(object instanceof LocalOperacao)) {
            return false;
        }
        LocalOperacao other = (LocalOperacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LocalOperacao[ id=" + id + " ]";
    }
    
}
