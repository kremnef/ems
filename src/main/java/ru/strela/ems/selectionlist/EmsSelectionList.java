package ru.strela.ems.selectionlist;


import org.apache.cocoon.forms.datatype.AbstractJavaSelectionList;

import java.util.List;
import java.util.Map;


/**
 * User: hobal
 * Date: 08.07.2010
 * Time: 19:22:45
 */
public class EmsSelectionList extends AbstractJavaSelectionList {


    private List<Map<String, Object>> typeActionsData;


    public EmsSelectionList() {
        super();
    }

    @Override
    protected boolean build() throws Exception {
        for (Map<String, Object> typeAction : typeActionsData) {
            addItem(typeAction.get("value"), (String) typeAction.get("label"));
        }
        return false;
    }


    public void setItems(List<Map<String, Object>> typeActionsData) {
        this.typeActionsData = typeActionsData;
    }

}
