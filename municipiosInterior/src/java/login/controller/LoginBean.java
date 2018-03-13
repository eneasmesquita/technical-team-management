package login.controller;

import entities.Municipio;
import login.dao.UsuarioDao;
import login.util.Usuario;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import login.entity.UsuarioJpa;

@ManagedBean(name = "LoginController")
@SessionScoped
public class LoginBean implements Serializable {

    
    private static final String ID_SISTEMA = "30";
    private static final int CCO = 94;
    private static final int GTI = 24;
    private static final int DT = 7;
    private static final String AGENCIA = "AGÊNCIA";
    private static final String SUB_AGENCIA = "SUB-AGÊNCIA";
    private static final String CAPITAL = "BOA VISTA";
    private static final int CAPITAL_ID = 5;
    Usuario usuario = new Usuario();
    UsuarioJpa usuario_jpa = new UsuarioJpa();
    FacesContext fc;
    private List<Municipio> municipio = new ArrayList<Municipio>();
    private boolean acesso = false;
    HttpSession session;
    private String exibe = "";
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("municipiosInteriorPU");
    private EntityManager em = emf.createEntityManager();

    public UsuarioJpa getUsuario_jpa() {
        return usuario_jpa;
    }

    public void setUsuario_jpa(UsuarioJpa usuario_jpa) {
        this.usuario_jpa = usuario_jpa;
    }

    public EntityManager getEm() {
        return em;
    }

    public HttpSession getSession() {
        return session;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getExibe() {
        return exibe;
    }

    public void setExibe(String exibe) {
        this.exibe = exibe;
    }

    public HttpSession criaSession() {
        fc = FacesContext.getCurrentInstance();
        session = (HttpSession) fc.getExternalContext().getSession(false);
        return session;
    }

    public static String md5(String texto) {
        String sen = "";
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
        sen = hash.toString(16);

        return sen;
    }

    public boolean isAcesso() {
        return acesso;
    }

    public void setAcesso(boolean acesso) {
        this.acesso = acesso;
    }

    public String logon() {

        criaSession();
        UsuarioDao dao = new UsuarioDao();
        Usuario usu = new Usuario();
        usu = dao.autenticarUsuario(usuario.getLogin(), usuario.getSenha());

        if (usu != null) {

            //APENAS USUÁRIOS DESSES SETORES SÃO CANDIDATOS A ACESSAREM A APLICAÇÃO 
            if(usu.getSetorNome().startsWith(AGENCIA) || usu.getSetorNome().startsWith(SUB_AGENCIA) || 
                    usu.getSetorId() == DT || usu.getSetorId() == CCO || usu.getSetorId() == GTI || usu.getSetorSuperior() == GTI){
            
            //SE O USUÁRIO POSSUI ACESSO LIBERADO À APLICAÇÃO
            if (usu.getAcessos().contains(ID_SISTEMA)) {

                Query query = em.createQuery("select u from UsuarioJpa u where u.nomeCompleto = '" + usu.getNome() + "'");
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                this.usuario_jpa = (UsuarioJpa) query.getSingleResult();

                session.setAttribute("autenticado", true);
                session.setAttribute("usuarioLogado", usu);
                session.setAttribute("login", usu.getLogin());
                session.setAttribute("nome", usu.getNome());
                session.setAttribute("setor", usuario_jpa.getSetorId().getSetorNome());
                session.setAttribute("setor_id", usuario_jpa.getSetorId().getSetorId());
                
                if(usu.getSetorId() == DT || usu.getSetorId() == CCO || usu.getSetorId() == GTI || usu.getSetorSuperior() == GTI){
                    session.setAttribute("municipio", CAPITAL);
                    session.setAttribute("municipio_id",CAPITAL_ID);
                } else{
                    
                    String setor = usuario_jpa.getSetorId().getSetorNome();
                    listarMunicipios();
                    for (Municipio municipio1 : municipio) {
                        if(setor.contains(municipio1.getDescricao())){
                            session.setAttribute("municipio", municipio1.getDescricao());
                            session.setAttribute("municipio_id", municipio1.getId());
                        }
                    }
                }

                return "index.xhtml?faces-redirect=true";

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário não tem permissão para acessar o Sistema!", ""));
                
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Entre em contato com a GTI."));
                
                return null;
            }

            }else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário não tem permissão para acessar o Sistema!", ""));
                
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Entre em contato com a GTI."));
                
                return null;
            }

        } else {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário e/ou Senha [intranet] Inválidos!!!", ""));

            return null;
        }
    }

    public String logout() {

        FacesContext fcon = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fcon.getExternalContext().getSession(false);
        session.invalidate();
        
        return "login.xhtml?faces-redirect=true";
    }
    
    public String logoutApp(){
        
        FacesContext fcon = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fcon.getExternalContext().getSession(false);
        session.invalidate();
        
        return "../login.xhtml?faces-redirect=true";
        
    }

    public String principal() {
        return "index.xhtml?faces-redirect=true";
    }

    public String inicial() {
        return "login.xhtml?faces-redirect=true";
    }
    
    public List<Municipio> listarMunicipios(){
        municipio = em.createNamedQuery("Municipio.findAll").getResultList();
        return municipio;
    }
}
