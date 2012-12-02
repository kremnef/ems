package ru.strela.ems.selectionlist;

/**
 * Created by IntelliJ IDEA.
 * User: andreykremnev
 * Date: 25.03.11
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cocoon.forms.datatype.AbstractJavaSelectionList;
import org.springframework.web.context.WebApplicationContext;
import ru.strela.ems.ecommerce.model.Country;
import ru.strela.ems.ecommerce.service.spring.CountryServiceImpl;


public class CountryNameSelectionList extends AbstractJavaSelectionList {


    private List<Map<String, Object>> countryNamesData;
    private WebApplicationContext springbean;
    private CountryServiceImpl countryService;

    /*public void service(ServiceManager manager) throws ServiceException {
        //super.service(manager);
        springbean = (WebApplicationContext) context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        this.countryService = (CountryServiceImpl) springbean.getBean("countryService");
    }*/
    public CountryNameSelectionList() {
        super();
    }

    @Override
    protected boolean build() throws Exception {

        /*for (Map<String, Object> countryName : countryNamesData) {
            addItem(countryName.get("value"), (String) countryName.get("label"));
        }
        return false;*/
        Iterator countryNames = countryService.getCountryNames().iterator();
		while(countryNames.hasNext()){
			Country country = (Country)countryNames.next();
			this.addItem(country.getName(), (String)null);
		}
		return false;
    }


    public void setItems(List<Map<String, Object>> countryNamesData) {
        this.countryNamesData = countryNamesData;
    }

}
/*
public class CountryNameSelectionList implements JavaSelectionList, FilterableSelectionList {

    public void generateSaxFragment(ContentHandler out, Locale arg1, String filter) throws SAXException {
        try {
            Context context;
            context = new InitialContext();
            Country country = (Country) context.lookup("triplebase-ejb3/UserBean/remote");
            List list = country.getCou(filter + "%");
            Attributes attr = new AttributesImpl();
            out.startElement(FormsConstants.INSTANCE_NS, SELECTION_LIST_EL, SELECTION_LIST_EL, attr);
            for (int i = 0; i < list.size(); i++) {
                out.startElement(FormsConstants.INSTANCE_NS, ITEM_EL, ITEM_EL, attr);
                out.startElement(FormsConstants.INSTANCE_NS, LABEL_EL, LABEL_EL, attr);
                Object[] row = (Object[]) list.get(i);
                out.characters((row[0].toString() + " (" + row[1] + ")").toCharArray(), 0, (row[0].toString() + " ("
                        + row[1] + ")").length());
                out.endElement(FormsConstants.INSTANCE_NS, LABEL_EL, LABEL_EL);
                out.endElement(FormsConstants.INSTANCE_NS, ITEM_EL, ITEM_EL);
            }
            out.endElement(FormsConstants.INSTANCE_NS, SELECTION_LIST_EL, SELECTION_LIST_EL);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}*/
