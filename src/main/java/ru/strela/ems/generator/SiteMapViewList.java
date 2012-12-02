package ru.strela.ems.generator;


import ru.strela.ems.core.model.SiteMapView;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;


/**
 * User: hobal
 * Date: 07.03.11
 * Time: 2:47
 */
@XmlRootElement
@XmlSeeAlso( {SiteMapView.class} )
public class SiteMapViewList {


    private ArrayList<SiteMapView> siteMapViews;


    public SiteMapViewList() {
        siteMapViews = new ArrayList<SiteMapView>();
    }


    public ArrayList<SiteMapView> getSiteMapViews() {
        return siteMapViews;
    }


    public void setSiteMapViews(ArrayList<SiteMapView> siteMapViews) {
        this.siteMapViews = siteMapViews;
    }


    public void add(SiteMapView siteMapView) {
        siteMapViews.add(siteMapView);
    }


    @Override
    public String toString() {
        return siteMapViews.toString();
    }
}
