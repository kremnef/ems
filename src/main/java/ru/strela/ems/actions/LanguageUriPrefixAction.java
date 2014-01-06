package ru.strela.ems.actions;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.acting.Action;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Redirector;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.SourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.tools.ServerTools;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: kremnevandrey
 * Date: 20.12.13
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class LanguageUriPrefixAction implements Action {


    private final static Logger log = LoggerFactory.getLogger(LanguageUriPrefixAction.class);
    public String languageCode;
    public static final String LANG_PARAMETER = "lang";
    public static final Pattern NON_WORD_PATTERN = Pattern.compile("\\W");

    public Map act(Redirector redirector,
                   SourceResolver resolver,
                   Map objectModel,
                   String source,
                   Parameters params) {
        System.out.println("LanguageUriPrefixAction__________________ Start ");
        Request request = ObjectModelHelper.getRequest(objectModel);
        /*System.out.println("request.getContextPath() = " + request.getContextPath());
        System.out.println("request.getPathInfo() = " + request.getPathInfo());
        System.out.println("request.getPathTranslated() = " + request.getPathTranslated());
        System.out.println("request.getHeaders() = " + request.getHeaders());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.getQueryString() = " + request.getQueryString());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getServerName() = " + request.getServerName());
        System.out.println("request.getServletPath() = " + request.getServletPath());
        System.out.println("request.getSitemapPath() = " + request.getSitemapPath());
        System.out.println("request.getSitemapURIPrefix() = " + request.getSitemapURIPrefix());
        System.out.println("request.getScheme() = " + request.getScheme());*/

//        String langParameter = request.getParameter(LANG_PARAMETER);
        String pathInfo = request.getPathInfo();
        System.out.println("pathInfo =  "+ pathInfo);
        if (pathInfo != null && pathInfo.trim().length() > 3) {

            String languagePrefix = pathInfo.substring(1,3);
            System.out.println("languagePrefix = " + languagePrefix);
//            langParameter = NON_WORD_PATTERN.matcher(languagePrefix).replaceAll("");
            this.languageCode =languagePrefix;
            request.setAttribute(ServerTools.LOCALE_TITLE, this.languageCode);
            HttpSession session = request.getSession();
            session.setAttribute(ServerTools.LOCALE_TITLE, this.languageCode);

        }
       /* else if (langParameter != null && langParameter.trim().length() > 0) {
            System.out.println("langParameter != null   " + langParameter);
            langParameter = NON_WORD_PATTERN.matcher(langParameter).replaceAll("");
            this.languageCode =langParameter;
            request.setAttribute(ServerTools.LOCALE_TITLE, this.languageCode);
            HttpSession session = request.getSession();
            session.setAttribute(ServerTools.LOCALE_TITLE, this.languageCode);

        }*/
        else {

            System.out.println("pathInfo =  "+ pathInfo);
            this.languageCode = ServerTools.checkLocaleWithLanguageCode(request);

        }


//        System.out.println("languageCode " + languageCode);
        System.out.println("Session attr " + request.getSession().getAttribute("site_locale"));

        Map map = new HashMap();
        map.put("languageCode", this.languageCode);
        System.out.println("LanguageUriPrefixAction__________________ END ");
        return map;

    }


}
