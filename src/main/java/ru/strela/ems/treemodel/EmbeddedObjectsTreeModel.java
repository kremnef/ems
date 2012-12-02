package ru.strela.ems.treemodel;


import org.apache.cocoon.forms.formmodel.tree.DefaultTreeModel;
import org.apache.cocoon.spring.configurator.WebAppContextUtils;
import org.springframework.web.context.WebApplicationContext;
import ru.strela.ems.core.dao.ObjectTypeDao;
import ru.strela.ems.core.dao.SystemObjectDao;
import ru.strela.ems.core.model.ObjectType;
import ru.strela.ems.core.model.SystemObject;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.Collection;
import java.util.List;


/**
 *
 * @author hobal
 */
public class EmbeddedObjectsTreeModel extends DefaultTreeModel {


    private SystemObjectDao systemObjectDao;
    private ObjectTypeDao objectTypeDao;



    public EmbeddedObjectsTreeModel() {
        super(new DefaultTreeNode("Objects"));
        WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
        systemObjectDao = (SystemObjectDao) currentWebApplicationContext.getBean("systemObjectDao");
        objectTypeDao = (ObjectTypeDao) currentWebApplicationContext.getBean("objectTypeDao");
        List types = systemObjectDao.getObjectTypes(true);
        addTypes((DefaultTreeNode) getRoot(), types);
    }


    private void addTypes(DefaultTreeNode root, List types) {
        for (Object typeObject : types) {
            ObjectType type = (ObjectType) typeObject;
            root.add(type.getName(), new DefaultTreeNode(type));
        }
    }


    @Override
    public boolean isLeaf(Object node) {
        DefaultTreeNode defaultTreeNode = (DefaultTreeNode) node;
        if (defaultTreeNode.equals(getRoot())) {
            return super.isLeaf(node);
        }
        else if (defaultTreeNode.getData() instanceof ObjectType) {
            return objectTypeDao.getRootObjectsForTypeCount((ObjectType) defaultTreeNode.getData()) == 0;
        }
        else
            return !(defaultTreeNode.getData() instanceof SystemObject) || ((SystemObject) defaultTreeNode.getData()).getEmsObject().getChildrenCount() == 0;
    }


    @Override
    public Collection getChildren(Object parent) {
        DefaultTreeNode parentTreeNode = (DefaultTreeNode) parent;
        if (parentTreeNode.equals(getRoot())) {
        }
        else if (parentTreeNode.getData() instanceof ObjectType) {
            List list = objectTypeDao.getRootObjectsForType((ObjectType) parentTreeNode.getData());
            for (Object object : list) {
                TypifiedObject typifiedObject = (TypifiedObject) object;
                parentTreeNode.add(typifiedObject.getName(), new DefaultTreeNode(typifiedObject));
            }
        }
        else {
            int parentId = ((TypifiedObject) ((DefaultTreeNode) parentTreeNode).getData()).getId();
            List<TypifiedObject> children = systemObjectDao.getChildren(parentId);
            for (TypifiedObject typifiedObject : children) {
                parentTreeNode.add(typifiedObject.getName(), new DefaultTreeNode(typifiedObject));
            }
        }
        return parentTreeNode.getChildren();
    }

}
