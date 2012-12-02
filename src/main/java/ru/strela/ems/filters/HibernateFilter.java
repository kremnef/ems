package ru.strela.ems.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;

import javax.servlet.*;
import java.io.IOException;


/**
 * User: hobal
 * Date: 27.02.12
 * Time: 15:04
 */
public class HibernateFilter implements Filter {


    private static Logger logger = LoggerFactory.getLogger(HibernateFilter.class);


    /**
     * Commit Hibernate transaction and close Hibernate session at the end of request
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
            HibernateUtil.commitTransaction();
        }
        catch (IOException e) {
            logger.debug("HibernateFilter error: " + e.getMessage());
        }
        catch (ServletException e) {
            logger.debug("HibernateFilter error: " + e.getMessage());
        }
        finally {
            HibernateUtil.closeSession();
            logger.debug("Hibernate filter: session closed");
        }
    }


    public void init(FilterConfig filterConfig) throws ServletException {
    }


    public void destroy() {
    }
}