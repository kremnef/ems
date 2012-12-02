package ru.strela.ems.treemodel;


public class ParentsTreeModel extends EmsObjectsTreeModel {


    public ParentsTreeModel() {
        super();
    }


    public void setLimitingObjectId(int id) {
        removeFilters();
        addExcludeFilter("id", id);
    }
    

}
