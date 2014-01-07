package ru.strela.ems.tools;


import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.cocoon.components.modules.input.InputModule;
import org.apache.cocoon.components.modules.input.SettingsInputModule;
import org.apache.cocoon.core.container.spring.avalon.AvalonServiceSelector;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.spring.configurator.WebAppContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import ru.strela.ems.core.dao.LanguageDao;
import ru.strela.ems.core.model.Language;
import ru.strela.ems.security.model.Customer;
import ru.strela.ems.security.service.CustomerService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


/**
 * User: hobal
 * Date: 05.07.2010
 * Time: 15:47:05
 */
public class ServerTools {


    private final static Logger log = LoggerFactory.getLogger(ServerTools.class);
    private static WebApplicationContext springbean;
    public static final String USER_DATA_NAME = "name";
    public static final String USER_DATA_ID = "id";
    public static final String LOCALE_TITLE = "site_locale";
    public static final String LANG_PARAMETER = "lang";
    public static final Pattern NON_WORD_PATTERN = Pattern.compile("\\W");


    public static WebApplicationContext getWebApplicationContext() {
        return WebAppContextUtils.getCurrentWebApplicationContext();
    }


    public static String getContextRealPath() {
        return getWebApplicationContext().getServletContext().getRealPath("/");
    }


    public static Object getGlobalParameter(String parameterName) {
        Object attribute = null;
        WebApplicationContext currentWebApplicationContext = getWebApplicationContext();
        AvalonServiceSelector selector = (AvalonServiceSelector) currentWebApplicationContext.getBean(InputModule.ROLE + "Selector");
        try {
            SettingsInputModule global = (SettingsInputModule) selector.select("global");
            attribute = global.getAttribute(parameterName, null, null);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return attribute;
    }


    public static HashMap<String, Object> getLoggedInUserData(HttpSession session) {
        HashMap<String, Object> userData = new HashMap<String, Object>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();


        WebApplicationContext context = ServerTools.getWebApplicationContext();

        CustomerService сustomerService = (CustomerService) context.getBean("customerService");


        //log.info("getLoggedInUserData login = " + login);
        Customer customer = сustomerService.getCustomerByLogin(login);
        if (customer != null) {
            Integer id = customer.getId();
            userData.put(USER_DATA_NAME, login);
            userData.put(USER_DATA_ID, id);
        }
        return userData;
    }


    /*public static HashMap<String, Object> getLoggedInUserData(HttpSession session) {
        HashMap<String, Object> userData = new HashMap<String, Object>();
        User systemUser = (User) session.getAttribute(ApplicationManager.USER + "-ems");
        if (systemUser != null) {
            String name = systemUser.getId();
            Integer id = (Integer) systemUser.getAttribute(USER_DATA_ID);
            userData.put(USER_DATA_NAME, name);
            userData.put(USER_DATA_ID, id);
        }
        return userData;
    }*/


    /*public static String checkLocale(Request request) {
        String languageCode = "";
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(LOCALE_TITLE);
        if (attribute != null && attribute.toString().length() > 0) {
            request.setAttribute(LOCALE_TITLE, attribute.toString());
            languageCode = attribute.toString();
        } else {
            Locale locale = request.getLocale();
            String localeStr = locale.getLanguage().toLowerCase();
            WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
            LanguageDao languageDao = (LanguageDao) currentWebApplicationContext.getBean("languageDao");
            List<Language> visibleLanguages = languageDao.getVisibleLanguages();
            if (visibleLanguages.size() > 0) {
                boolean correctLanguage = false;
                Language defaultLanguage = visibleLanguages.get(0);
                for (Language language : visibleLanguages) {
                    if (language.getIsDefaultLang()) {
                        defaultLanguage = language;
                    }
                    if (language.getCode().equalsIgnoreCase(localeStr)) {
                        correctLanguage = true;
                        break;
                    }
                }
                if (!correctLanguage) {
                    localeStr = defaultLanguage.getCode().toLowerCase();
                }
                request.setAttribute(LOCALE_TITLE, localeStr);
                session.setAttribute(LOCALE_TITLE, localeStr);
                languageCode = localeStr;
            }
        }
        return languageCode;
    }*/


    public static String checkLocaleWithLanguageCode(Request request) {
        System.out.println("checkLocaleWithLanguageCode ------------");
        String languageCode = "";
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(LOCALE_TITLE);
        WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
        LanguageDao languageDao = (LanguageDao) currentWebApplicationContext.getBean("languageDao");
        List<Language> visibleLanguages = languageDao.getVisibleLanguages();
        if (attribute != null && attribute.toString().length() > 0) {
            request.setAttribute(LOCALE_TITLE, attribute.toString());
            String RlanguageCode = attribute.toString();
            for (Language language : visibleLanguages) {
                if (language.getCode().equalsIgnoreCase(RlanguageCode)) {
                    languageCode = language.getCode();
                    request.setAttribute(LOCALE_TITLE, languageCode);
                    session.setAttribute(LOCALE_TITLE, languageCode);
//                    System.out.println("LOCALE_TITLE-Session 1"+ session.getAttribute(LOCALE_TITLE));
//                    System.out.println("LOCALE_TITLE-request 2"+ request.getAttribute(LOCALE_TITLE));
                    break;

                }
            }
        } else {
            Locale locale = request.getLocale();
            String localeStr = locale.getLanguage().toLowerCase();
            if (visibleLanguages.size() > 0) {
                boolean correctLanguage = false;
                Language defaultLanguage = visibleLanguages.get(0);
                for (Language language : visibleLanguages) {
                    if (language.getIsDefaultLang()) {
                        defaultLanguage = language;
                    }
                    if (language.getCode().equalsIgnoreCase(localeStr)) {
                        correctLanguage = true;
//                        languageId = language.getId();
                        languageCode = language.getCode();
                        break;
                    }
                }
                if (!correctLanguage) {
                    localeStr = defaultLanguage.getCode().toLowerCase();
//                    languageId = defaultLanguage.getId();
                    languageCode = defaultLanguage.getCode();
                }
                request.setAttribute(LOCALE_TITLE, localeStr);
                session.setAttribute(LOCALE_TITLE, localeStr);
//                System.out.println("LOCALE_TITLE-Session "+ session.getAttribute(LOCALE_TITLE));
//                System.out.println("LOCALE_TITLE-request "+ request.getAttribute(LOCALE_TITLE));

            }
        }
//        return languageId;
        return languageCode;
    }


    public static void updateLocaleFromParameter(Request request) {
        System.out.println("updateLocaleFromParameter----------------");
        String langParameter = request.getParameter(LANG_PARAMETER);
        if (langParameter != null && langParameter.trim().length() > 0) {
            langParameter = NON_WORD_PATTERN.matcher(langParameter).replaceAll("");
            WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
            LanguageDao languageDao = (LanguageDao) currentWebApplicationContext.getBean("languageDao");
            Language language = languageDao.getLanguageByCode(langParameter);
            if (language != null) {
                request.setAttribute(ServerTools.LOCALE_TITLE, language.getCode());
                HttpSession session = request.getSession();
                session.setAttribute(ServerTools.LOCALE_TITLE, language.getCode());
            }
        }
    }

}
