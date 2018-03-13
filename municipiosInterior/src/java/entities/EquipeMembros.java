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
 * @author eneas
 */
@Entity
@Table(catalog = "corporativo", schema = "interior", name = "equipe_membros")
@NamedQueries({
    @NamedQuery(name = "EquipeMembros.findAll", query = "SELECT e FROM EquipeMembros e"),
    @NamedQuery(name = "EquipeMembros.findById", query = "SELECT e FROM EquipeMembros e WHERE e.id = :id"),
    @NamedQuery(name = "EquipeMembros.findByNome", query = "SELECT e FROM EquipeMembros e WHERE e.nome = :nome"),
    @NamedQuery(name = "EquipeMembros.findByMatricula", query = "SELECT e FROM EquipeMembros e WHERE e.matricula = :matricula"),
    @NamedQuery(name = "EquipeMembros.findByAtivo", query = "SELECT e FROM EquipeMembros e WHERE e.ativo = :ativo"),
    @NamedQuery(name = "EquipeMembros.findByDataParticipacao", query = "SELECT e FROM EquipeMembros e WHERE e.dataParticipacao = :dataParticipacao"),
    @NamedQuery(name = "EquipeMembros.findByDataInativo", query = "SELECT e FROM EquipeMembros e WHERE e.dataInativo = :dataInativo")})
public class EquipeMembros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ativo")
    private boolean ativo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_participacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataParticipacao;
    @Column(name = "data_inativo")
    @Temporal(TemporalType.TIME)
    private Date dataInativo;
    @JoinColumn(name = "equipe", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipe equipe;

    public EquipeMembros() {
    }

    public EquipeMembros(Integer id) {
        this.id = id;
    }

    public EquipeMembros(Integer id, String nome, String matricula, boolean ativo, Date dataParticipacao) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.ativo = ativo;
        this.dataParticipacao = dataParticipacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataParticipacao() {
        return dataParticipacao;
    }

    public void setDataParticipacao(Date dataParticipacao) {
        this.dataParticipacao = dataParticipacao;
    }

    public Date getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(Date dataInativo) {
        this.dataInativo = dataInativo;
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
        if (!(object instanceof EquipeMembros)) {
            return false;
        }
        EquipeMembros other = (EquipeMembros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.EquipeMembros[ id=" + id + " ]";
    }
    
}
