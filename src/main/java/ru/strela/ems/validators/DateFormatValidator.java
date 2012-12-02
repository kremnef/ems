package ru.strela.ems.validators;


import java.util.Calendar;
import java.text.SimpleDateFormat;


/**
 * User: andrejkremnev
 * Date: 26.07.11
 * Time: 0:50
 */
public class DateFormatValidator {


    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());

    }

}
