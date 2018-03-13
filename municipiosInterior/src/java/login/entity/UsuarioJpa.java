/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package login.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Eneas
 */
@Entity
@Table(name = "usuario", catalog = "corporativo", schema = "intranet")
@NamedQueries({
    @NamedQuery(name = "UsuarioJpaJpa.findAll", query = "SELECT u FROM UsuarioJpa u ORDER BY u.nomeCompleto ASC"),
    @NamedQuery(name = "UsuarioJpa.findByUsername", query = "SELECT u FROM UsuarioJpa u WHERE u.username = :username"),
    @NamedQuery(name = "UsuarioJpa.findByNomeCompleto", query = "SELECT u FROM UsuarioJpa u WHERE u.nomeCompleto = :nomeCompleto"),
    @NamedQuery(name = "UsuarioJpa.findByBrma", query = "SELECT u FROM UsuarioJpa u WHERE u.brma = :brma"),
    @NamedQuery(name = "UsuarioJpa.findByExiste", query = "SELECT u FROM UsuarioJpa u WHERE u.existe = :existe"),
    @NamedQuery(name = "UsuarioJpa.findByMatricula", query = "SELECT u FROM UsuarioJpa u WHERE u.matricula = :matricula"),
    @NamedQuery(name = "UsuarioJpa.findByEmailCorp", query = "SELECT u FROM UsuarioJpa u WHERE u.emailCorp = :emailCorp"),
    @NamedQuery(name = "UsuarioJpa.findByEmailPessoal", query = "SELECT u FROM UsuarioJpa u WHERE u.emailPessoal = :emailPessoal"),
    @NamedQuery(name = "UsuarioJpa.findBySetorNome", query = "SELECT u FROM UsuarioJpa u WHERE u.setorNome = :setorNome"),
    @NamedQuery(name = "UsuarioJpa.findBySenha", query = "SELECT u FROM UsuarioJpa u WHERE u.senha = :senha"),
    @NamedQuery(name = "UsuarioJpa.findBySenhaTemp", query = "SELECT u FROM UsuarioJpa u WHERE u.senhaTemp = :senhaTemp"),
    @NamedQuery(name = "UsuarioJpa.findByFlagSenha", query = "SELECT u FROM UsuarioJpa u WHERE u.flagSenha = :flagSenha"),
    @NamedQuery(name = "UsuarioJpa.findByDataTrocaSenha", query = "SELECT u FROM UsuarioJpa u WHERE u.dataTrocaSenha = :dataTrocaSenha"),
    @NamedQuery(name = "UsuarioJpa.findByMatriculaRm", query = "SELECT u FROM UsuarioJpa u WHERE u.matriculaRm = :matriculaRm"),
    @NamedQuery(name = "UsuarioJpa.findByRedefinidaPor", query = "SELECT u FROM UsuarioJpa u WHERE u.redefinidaPor = :redefinidaPor"),
    @NamedQuery(name = "UsuarioJpa.findByDataRedefinicao", query = "SELECT u FROM UsuarioJpa u WHERE u.dataRedefinicao = :dataRedefinicao"),
    @NamedQuery(name = "UsuarioJpa.findByInativo", query = "SELECT u FROM UsuarioJpa u WHERE u.inativo = :inativo"),
    @NamedQuery(name = "UsuarioJpa.findByDataInativo", query = "SELECT u FROM UsuarioJpa u WHERE u.dataInativo = :dataInativo"),
    @NamedQuery(name = "UsuarioJpa.findByLdap", query = "SELECT u FROM UsuarioJpa u WHERE u.ldap = :ldap"),
    @NamedQuery(name = "UsuarioJpa.findByDataCadastro", query = "SELECT u FROM UsuarioJpa u WHERE u.dataCadastro = :dataCadastro")})
public class UsuarioJpa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome_completo")
    private String nomeCompleto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brma")
    private boolean brma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "existe")
    private boolean existe;
    @Column(name = "matricula")
    private Integer matricula;
    @Size(max = 100)
    @Column(name = "email_corp")
    private String emailCorp;
    @Size(max = 100)
    @Column(name = "email_pessoal")
    private String emailPessoal;
    @Size(max = 100)
    @Column(name = "setor_nome")
    private String setorNome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "senha")
    private String senha;
    @Size(max = 25)
    @Column(name = "senha_temp")
    private String senhaTemp;
    @Column(name = "flag_senha")
    private Boolean flagSenha;
    @Column(name = "data_troca_senha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTrocaSenha;
    @Size(max = 10)
    @Column(name = "matricula_rm")
    private String matriculaRm;
    @Size(max = 50)
    @Column(name = "redefinida_por")
    private String redefinidaPor;
    @Column(name = "data_redefinicao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRedefinicao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inativo")
    private boolean inativo;
    @Column(name = "data_inativo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInativo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ldap")
    private boolean ldap;
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;
    @JoinColumn(name = "setor_id", referencedColumnName = "setor_id")
    @ManyToOne
    private Setor setorId;
    @Transient
    private List<String> acessos;
    @Transient
    private List<String> recursos;
    @Transient
    private List<String> paginas;
    @Transient
    private boolean autorizado; 
   

    public UsuarioJpa() {
    }

    public UsuarioJpa(String username) {
        this.username = username;
    }

    public UsuarioJpa(String username, String nomeCompleto, boolean brma, boolean existe, String senha, boolean inativo, boolean ldap) {
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.brma = brma;
        this.existe = existe;
        this.senha = senha;
        this.inativo = inativo;
        this.ldap = ldap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public boolean getBrma() {
        return brma;
    }

    public void setBrma(boolean brma) {
        this.brma = brma;
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getEmailCorp() {
        return emailCorp;
    }

    public void setEmailCorp(String emailCorp) {
        this.emailCorp = emailCorp;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }

    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public String getSetorNome() {
        return setorNome;
    }

    public void setSetorNome(String setorNome) {
        this.setorNome = setorNome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaTemp() {
        return senhaTemp;
    }

    public void setSenhaTemp(String senhaTemp) {
        this.senhaTemp = senhaTemp;
    }

    public Boolean getFlagSenha() {
        return flagSenha;
    }

    public void setFlagSenha(Boolean flagSenha) {
        this.flagSenha = flagSenha;
    }

    public Date getDataTrocaSenha() {
        return dataTrocaSenha;
    }

    public void setDataTrocaSenha(Date dataTrocaSenha) {
        this.dataTrocaSenha = dataTrocaSenha;
    }

    public String getMatriculaRm() {
        return matriculaRm;
    }

    public void setMatriculaRm(String matriculaRm) {
        this.matriculaRm = matriculaRm;
    }

    public String getRedefinidaPor() {
        return redefinidaPor;
    }

    public void setRedefinidaPor(String redefinidaPor) {
        this.redefinidaPor = redefinidaPor;
    }

    public Date getDataRedefinicao() {
        return dataRedefinicao;
    }

    public void setDataRedefinicao(Date dataRedefinicao) {
        this.dataRedefinicao = dataRedefinicao;
    }

    public boolean getInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public Date getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(Date dataInativo) {
        this.dataInativo = dataInativo;
    }

    public boolean getLdap() {
        return ldap;
    }

    public void setLdap(boolean ldap) {
        this.ldap = ldap;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Setor getSetorId() {
        return setorId;
    }

    public void setSetorId(Setor setorId) {
        this.setorId = setorId;
    }

    public List<String> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<String> acessos) {
        this.acessos = acessos;
    }

    public List<String> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<String> recursos) {
        this.recursos = recursos;
    }

    public List<String> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<String> paginas) {
        this.paginas = paginas;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioJpa)) {
            return false;
        }
        UsuarioJpa other = (UsuarioJpa) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "login.entity.UsuarioJpa[ username=" + username + " ]";
    }
    
}
