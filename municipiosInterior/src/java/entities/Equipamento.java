/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eneas
 */
@Entity
@Table(name = "equipamento", catalog = "corporativo", schema = "interior")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipamento.findAll", query = "SELECT e FROM Equipamento e"),
    @NamedQuery(name = "Equipamento.findByDescricao", query = "SELECT e FROM Equipamento e WHERE e.descricao = :descricao"),
    @NamedQuery(name = "Equipamento.findByTombamento", query = "SELECT e FROM Equipamento e WHERE e.tombamento = :tombamento"),
    @NamedQuery(name = "Equipamento.findById", query = "SELECT e FROM Equipamento e WHERE e.id = :id"),
    @NamedQuery(name = "Equipamento.findByAtivo", query = "SELECT e FROM Equipamento e WHERE e.ativo = :ativo"),
    @NamedQuery(name = "Equipamento.findByDataCadastro", query = "SELECT e FROM Equipamento e WHERE e.dataCadastro = :dataCadastro"),
    @NamedQuery(name = "Equipamento.findByDataInativo", query = "SELECT e FROM Equipamento e WHERE e.dataInativo = :dataInativo")})
public class Equipamento implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao")
    private String descricao;
    @Size(max = 10)
    @Column(name = "tombamento")
    private String tombamento;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ativo")
    private boolean ativo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @Column(name = "data_inativo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInativo;
    @OneToMany(mappedBy = "equipamento")
    private Collection<EquipamentoSs> equipamentoSsCollection;
    @JoinColumn(name = "tipo_equipamento", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoEquipamento tipoEquipamento;
    @JoinColumn(name = "marca", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Marca marca;
    @JoinColumn(name = "localidade", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Localidade localidade;
    @JoinColumn(name = "local_operacao_tecnica", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LocalOperacaoTecnica localOperacaoTecnica;
    @JoinColumn(name = "local_operacao", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LocalOperacao localOperacao;
    @JoinColumn(name = "local_instalacao", referencedColumnName = "id")
    @ManyToOne
    private LocalInstalacao localInstalacao;

    public Equipamento() {
    }

    public Equipamento(Long id) {
        this.id = id;
    }

    public Equipamento(Long id, String descricao, boolean ativo, Date dataCadastro) {
        this.id = id;
        this.descricao = descricao;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTombamento() {
        return tombamento;
    }

    public void setTombamento(String tombamento) {
        this.tombamento = tombamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(Date dataInativo) {
        this.dataInativo = dataInativo;
    }

    @XmlTransient
    public Collection<EquipamentoSs> getEquipamentoSsCollection() {
        return equipamentoSsCollection;
    }

    public void setEquipamentoSsCollection(Collection<EquipamentoSs> equipamentoSsServicoCollection) {
        this.equipamentoSsCollection = equipamentoSsServicoCollection;
    }

    public TipoEquipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public LocalOperacaoTecnica getLocalOperacaoTecnica() {
        return localOperacaoTecnica;
    }

    public void setLocalOperacaoTecnica(LocalOperacaoTecnica localOperacaoTecnica) {
        this.localOperacaoTecnica = localOperacaoTecnica;
    }

    public LocalOperacao getLocalOperacao() {
        return localOperacao;
    }

    public void setLocalOperacao(LocalOperacao localOperacao) {
        this.localOperacao = localOperacao;
    }

    public LocalInstalacao getLocalInstalacao() {
        return localInstalacao;
    }

    public void setLocalInstalacao(LocalInstalacao localInstalacao) {
        this.localInstalacao = localInstalacao;
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
        if (!(object instanceof Equipamento)) {
            return false;
        }
        Equipamento other = (Equipamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Equipamento[ id=" + id + " ]";
    }

    
}
