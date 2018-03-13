package login.filtro;


import java.io.IOException;
import java.util.List;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import login.util.Usuario;

@WebFilter(value = {"/equipamentos/*","/solicitacao_servico/*","/admin/*","/index.jsf"})
public class Filtro
        implements Filter {

    private static final String IDSISTEMA = "30";
   // LoginBean lb = (LoginBean) getManagedBean("loginBean");

    public Filtro() {
    }

    public void init(FilterConfig filterconfig)
            throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();

        Usuario usu = (Usuario) session.getAttribute("usuarioLogado");

        if (session.getAttribute("autenticado") != null) {
            List<String> acessos = usu.getAcessos();
            if (!acessos.contains(IDSISTEMA)) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("/municipiosInterior");
               // lb.setAcesso(false);
            } else {
                chain.doFilter(request, response);
              //  lb.setAcesso(true);
            }

        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("/municipiosInterior");
        }
    }

    public void destroy() {
    }

    public static Object getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();

        Object bean;
        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }

        if (bean == null) {
            throw new FacesException("Managed bean with name '" + beanName + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
        }

        return bean;
    }
}
