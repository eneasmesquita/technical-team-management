/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login.entity;

import entities.Localidade;
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
@Table(name = "setor", catalog="corporativo", schema="intranet")
@NamedQueries({
    @NamedQuery(name = "Setor.findAll", query = "SELECT s FROM Setor s"),
    @NamedQuery(name = "Setor.findBySetorId", query = "SELECT s FROM Setor s WHERE s.setorId = :setorId"),
    @NamedQuery(name = "Setor.findBySetorDescricao", query = "SELECT s FROM Setor s WHERE s.setorDescricao = :setorDescricao"),
    @NamedQuery(name = "Setor.findBySetorSigla", query = "SELECT s FROM Setor s WHERE s.setorSigla = :setorSigla"),
    @NamedQuery(name = "Setor.findBySetorNome", query = "SELECT s FROM Setor s WHERE s.setorNome = :setorNome"),
    @NamedQuery(name = "Setor.findBySetorAcessoria", query = "SELECT s FROM Setor s WHERE s.setorAcessoria = :setorAcessoria"),
    @NamedQuery(name = "Setor.findBySetorInativo", query = "SELECT s FROM Setor s WHERE s.setorInativo = :setorInativo")})
public class Setor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "setor_id")
    private Integer setorId;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 80)
    @Column(name = "setor_descricao")
    private String setorDescricao;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 10)
    @Column(name = "setor_sigla")
    private String setorSigla;
    //@Size(max = 100)
    @Column(name = "setor_nome")
    private String setorNome;
    @Column(name = "setor_acessoria")
    private Boolean setorAcessoria;
    @Column(name = "setor_inativo")
    private Boolean setorInativo;
    @JoinColumn(name = "setor_tpid", referencedColumnName = "setortp_id")
    @ManyToOne
    private SetorTipo setorTpid;
    @OneToMany(mappedBy = "setorSuperior")
    private Collection<Setor> setorCollection;
    @JoinColumn(name = "setor_superior", referencedColumnName = "setor_id")
    @ManyToOne
    private Setor setorSuperior;
    @OneToMany(mappedBy = "setorId")
    private Collection<UsuarioJpa> usuarioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "setor") 
    private Collection<Localidade> localidadeCollection;

    public Setor() {
    }

    public Setor(Integer setorId) {
        this.setorId = setorId;
    }

    public Setor(Integer setorId, String setorDescricao, String setorSigla) {
        this.setorId = setorId;
        this.setorDescricao = setorDescricao;
        this.setorSigla = setorSigla;
    }

    public Integer getSetorId() {
        return setorId;
    }

    public void setSetorId(Integer setorId) {
        this.setorId = setorId;
    }

    public Collection<Localidade> getLocalidadeCollection() {
        return localidadeCollection;
    }

    public void setLocalidadeCollection(Collection<Localidade> localidadeCollection) {
        this.localidadeCollection = localidadeCollection;
    }

    public String getSetorDescricao() {
        return setorDescricao;
    }

    public void setSetorDescricao(String setorDescricao) {
        this.setorDescricao = setorDescricao;
    }

    public String getSetorSigla() {
        return setorSigla;
    }

    public void setSetorSigla(String setorSigla) {
        this.setorSigla = setorSigla;
    }

    public String getSetorNome() {
        return setorNome;
    }

    public void setSetorNome(String setorNome) {
        this.setorNome = setorNome;
    }

    public Boolean getSetorAcessoria() {
        return setorAcessoria;
    }

    public void setSetorAcessoria(Boolean setorAcessoria) {
        this.setorAcessoria = setorAcessoria;
    }

    public Boolean getSetorInativo() {
        return setorInativo;
    }

    public void setSetorInativo(Boolean setorInativo) {
        this.setorInativo = setorInativo;
    }

    public SetorTipo getSetorTpid() {
        return setorTpid;
    }

    public void setSetorTpid(SetorTipo setorTpid) {
        this.setorTpid = setorTpid;
    }

    public Collection<Setor> getSetorCollection() {
        return setorCollection;
    }

    public void setSetorCollection(Collection<Setor> setorCollection) {
        this.setorCollection = setorCollection;
    }

    public Setor getSetorSuperior() {
        return setorSuperior;
    }

    public void setSetorSuperior(Setor setorSuperior) {
        this.setorSuperior = setorSuperior;
    }

    public Collection<UsuarioJpa> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<UsuarioJpa> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (setorId != null ? setorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setor)) {
            return false;
        }
        Setor other = (Setor) object;
        if ((this.setorId == null && other.setorId != null) || (this.setorId != null && !this.setorId.equals(other.setorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "login.entity.Setor[ setorId=" + setorId + " ]";
    }
    
}
