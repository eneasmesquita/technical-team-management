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
@Table(catalog = "corporativo", schema = "interior", name = "tipo_equipamento")
@NamedQueries({
    @NamedQuery(name = "TipoEquipamento.findAll", query = "SELECT t FROM TipoEquipamento t ORDER BY t.descricao ASC"),
    @NamedQuery(name = "TipoEquipamento.findById", query = "SELECT t FROM TipoEquipamento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoEquipamento.findByDescricao", query = "SELECT t FROM TipoEquipamento t WHERE t.descricao = :descricao")})
public class TipoEquipamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEquipamento")
    private Collection<Equipamento> equipamentoCollection;

    public TipoEquipamento() {
    }

    public TipoEquipamento(Long id) {
        this.id = id;
    }

    public TipoEquipamento(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof TipoEquipamento)) {
            return false;
        }
        TipoEquipamento other = (TipoEquipamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.TipoEquipamento[ id=" + id + " ]";
    }
    
}
