package ru.strela.ems.validators;


import org.apache.cocoon.forms.formmodel.Field;
import org.apache.cocoon.forms.formmodel.Widget;
import org.apache.cocoon.forms.formmodel.WidgetState;
import org.apache.cocoon.forms.validation.ValidationError;
import org.apache.cocoon.forms.validation.WidgetValidator;
import ru.strela.ems.core.dao.SystemObjectDao;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.string.Translit;
import ru.tastika.tools.util.Utilities;


/**
 * @author hobal
 */
public class SystemNameUniquenessValidator implements WidgetValidator {


    public boolean validate(Widget widget) {
        Object value = widget.getValue();
        boolean success = false;
        if (value != null) {
            Widget checkSystemNameWidget = widget.lookupWidget("../checkSystemName");
//            System.out.println("---checkSystemNameWidget " + checkSystemNameWidget.getValue());

            Widget emsObjectIdWidget = widget.lookupWidget("../emsObjectId");
//            System.out.println("---emsObjectIdWidget " + emsObjectIdWidget.getValue());

            Widget parentIdWidget = widget.lookupWidget("../../parentId");
//            System.out.println("---parentIdWidget " + parentIdWidget.getValue());

            Widget entityWidget = widget.lookupWidget("../../entity");
//            System.out.println("---entityWidget " + entityWidget.getValue());

            Object entityWidgetValue = entityWidget.getValue();

            if (checkSystemNameWidget != null && emsObjectIdWidget != null && parentIdWidget != null && entityWidget != null) {
//            if (checkSystemNameUniqueness(widget, value, emsObjectIdWidget.getValue(), parentIdWidget.getValue(), entityWidgetValue)) {
                if (checkSystemNameUniqueness(widget, value, emsObjectIdWidget.getValue(), parentIdWidget.getValue(), entityWidgetValue)) {
                    success = true;
                    checkSystemNameWidget.setState(WidgetState.OUTPUT);
                } else {
                    checkSystemNameWidget.setState(WidgetState.INVISIBLE);
                }
            }
        }
//        String uniqueAttribute = widget.getAttribute("unique").toString();
//        boolean success = uniqueAttribute.equals("true");
        if (!success) {
            ((Field) widget).setValidationError(new ValidationError("error.notUnique"));
        }
        return success;
    }


    private boolean checkSystemNameUniqueness(Widget systemNameWidget, Object value, Object idObject, Object parentIdObject, Object entityObject) {
        String systemName = value.toString();
        String correctedSystemName = systemName;
        int id = 0;
        if (idObject != null) {
            id = Utilities.parseStringToInteger(idObject.toString());
        }

        int parentId = 0;
        if (parentIdObject != null) {
            parentId = Utilities.parseStringToInteger(parentIdObject.toString());
        }

        correctedSystemName = Translit.toTranslit(correctedSystemName).toLowerCase();
//        correctedSystemName = correctedSystemName.replaceAll("\\s", "-");
        correctedSystemName = correctedSystemName.replaceAll("[\\s_]", "-");
//        correctedSystemName = correctedSystemName.replaceAll("\\s", "_");
//        correctedSystemName = correctedSystemName.replaceAll("\\W", "");
        correctedSystemName = correctedSystemName.replaceAll("[^\\w-]", "");
//        correctedSystemName = correctedSystemName.replaceAll("_", "-");
        if (systemNameWidget != null && !correctedSystemName.equals(systemName)) {
            systemNameWidget.setValue(systemName);
        }
        SystemObjectDao systemObjectDao = (SystemObjectDao) ServerTools.getWebApplicationContext().getBean("systemObjectDao");
        int objectId = systemObjectDao.getIdBySystemName(systemName, parentId, entityObject != null ? entityObject.toString() : "");
        return objectId == 0 || objectId == id;
    }

}
