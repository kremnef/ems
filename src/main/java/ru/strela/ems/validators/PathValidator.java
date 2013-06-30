package ru.strela.ems.validators;

import org.apache.cocoon.forms.formmodel.Field;
import org.apache.cocoon.forms.formmodel.Widget;
import org.apache.cocoon.forms.validation.ValidationError;
import org.apache.cocoon.forms.validation.WidgetValidator;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 26.08.12
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public class PathValidator implements WidgetValidator  {

    public boolean validate(Widget widget) {

        Object value = widget.getValue();
        boolean success = false;
        if (value != null) {
            success = true;
            Widget pathWidget = widget.lookupWidget("../path");
//            System.out.println("DEBUG---pathWidget " + pathWidget.getValue());
            Widget systemNameWidget = widget.lookupWidget("../systemURL/systemName");
//            System.out.println("DEBUG---systemNameWidget " + pathWidget.getValue());
            if (pathWidget != null) {
                Object pathObject = pathWidget.getValue();
                if (pathObject != null) {
                    String path = pathObject.toString();
                    int lastIndex = path.substring(0, path.length() - 1).lastIndexOf("/");
                    String parentPath = path.substring(0, lastIndex + 1);
                    pathWidget.setValue(parentPath + widget.getValue().toString() + "/");
                }
            }
            Widget fullURLWidget = widget.lookupWidget("../fullURL");
            if (fullURLWidget != null) {
                Object fullURL = fullURLWidget.getValue();
                if (fullURL != null) {
                    String fullURLStr = fullURL.toString();
//                    String systemName = widget.getValue().toString();
                    String systemName = systemNameWidget.getValue().toString();
                    if (fullURLStr.length() > 0) {
                        int lastIndex = fullURLStr.lastIndexOf("/");
                        fullURLWidget.setValue(lastIndex > -1 ? fullURLStr.substring(0, lastIndex + 1) + systemName : systemName);
                    } else {
                        fullURLWidget.setValue(systemName);
                    }
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

}
