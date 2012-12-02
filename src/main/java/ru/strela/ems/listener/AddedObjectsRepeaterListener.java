package ru.strela.ems.listener;


import org.apache.cocoon.forms.event.RepeaterEvent;
import org.apache.cocoon.forms.event.RepeaterListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * User: hobal
 * Date: 06.05.2010
 * Time: 11:25:47
 */
public class AddedObjectsRepeaterListener implements RepeaterListener {

     private final static Logger log = LoggerFactory.getLogger(AddedObjectsRepeaterListener.class);
    public void repeaterModified(RepeaterEvent event) {
        ////log.info("event = " + event);
    }

}
