/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pt.ipleiria.dae.gpe.lib.beans.UserBean;
import pt.ipleiria.dae.gpe.lib.dtos.UserDTO;
import pt.ipleiria.dae.gpe.lib.exceptions.EntityNotFoundException;

/**
 *
 * @author joeld
 */
@WebFilter(filterName = "AuthFilter", urlPatterns={"/gpeapi/student/*"})
public class AuthFilter implements Filter {

    @EJB
    private UserBean userBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String header = req.getHeader("CurrentUser");
            System.out.println(header);
            try {
                UserDTO user = userBean.find(Integer.parseInt(header));
                if (user != null) {
                    Session.Current.setUser(user);
                } else {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (NumberFormatException | EntityNotFoundException ex) {
                Logger.getLogger(AuthFilter.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServletException();
            }
            chain.doFilter(request, response);
        } catch (IOException | ServletException t) {
            t.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

}
