/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "setor_tipo", catalog="corporativo", schema="intranet")
@NamedQueries({
    @NamedQuery(name = "SetorTipo.findAll", query = "SELECT s FROM SetorTipo s"),
    @NamedQuery(name = "SetorTipo.findBySetortpId", query = "SELECT s FROM SetorTipo s WHERE s.setortpId = :setortpId"),
    @NamedQuery(name = "SetorTipo.findBySetortpDescricao", query = "SELECT s FROM SetorTipo s WHERE s.setortpDescricao = :setortpDescricao"),
    @NamedQuery(name = "SetorTipo.findBySetortpInativo", query = "SELECT s FROM SetorTipo s WHERE s.setortpInativo = :setortpInativo")})
public class SetorTipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "setortp_id")
    private Integer setortpId;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 40)
    @Column(name = "setortp_descricao")
    private String setortpDescricao;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "setortp_inativo")
    private boolean setortpInativo;
    @OneToMany(mappedBy = "setorTpid")
    private Collection<Setor> setorCollection;

    public SetorTipo() {
    }

    public SetorTipo(Integer setortpId) {
        this.setortpId = setortpId;
    }

    public SetorTipo(Integer setortpId, String setortpDescricao, boolean setortpInativo) {
        this.setortpId = setortpId;
        this.setortpDescricao = setortpDescricao;
        this.setortpInativo = setortpInativo;
    }

    public Integer getSetortpId() {
        return setortpId;
    }

    public void setSetortpId(Integer setortpId) {
        this.setortpId = setortpId;
    }

    public String getSetortpDescricao() {
        return setortpDescricao;
    }

    public void setSetortpDescricao(String setortpDescricao) {
        this.setortpDescricao = setortpDescricao;
    }

    public boolean getSetortpInativo() {
        return setortpInativo;
    }

    public void setSetortpInativo(boolean setortpInativo) {
        this.setortpInativo = setortpInativo;
    }

    public Collection<Setor> getSetorCollection() {
        return setorCollection;
    }

    public void setSetorCollection(Collection<Setor> setorCollection) {
        this.setorCollection = setorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (setortpId != null ? setortpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SetorTipo)) {
            return false;
        }
        SetorTipo other = (SetorTipo) object;
        if ((this.setortpId == null && other.setortpId != null) || (this.setortpId != null && !this.setortpId.equals(other.setortpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "login.entity.SetorTipo[ setortpId=" + setortpId + " ]";
    }
    
}
