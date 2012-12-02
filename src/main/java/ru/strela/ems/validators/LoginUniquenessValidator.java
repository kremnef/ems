package ru.strela.ems.validators;


import org.apache.cocoon.forms.formmodel.Field;
import org.apache.cocoon.forms.formmodel.Widget;
import org.apache.cocoon.forms.validation.ValidationError;
import org.apache.cocoon.forms.validation.WidgetValidator;


/**
 *
 * @author kremnef
 */
public class LoginUniquenessValidator implements WidgetValidator {


    public boolean validate(Widget widget) {
        String uniqueAttribute = widget.getAttribute("unique").toString();
        boolean success = uniqueAttribute.equals("true");
        if (!success) {
            ((Field) widget).setValidationError(new ValidationError("error.notUnique"));
        }
        return success;
    }

}