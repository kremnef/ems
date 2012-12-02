/*
 *  Eberom: a CRM web application 
 *  Copyright (C) 2006  Luk Morbee
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.TemplateDao;
import ru.strela.ems.core.model.Template;
import ru.strela.ems.core.service.TemplateService;

import java.util.List;


public class TemplateServiceImpl implements TemplateService {


    private TemplateDao templateDao = null;
    Template template = new Template();
    Class entityClass = template.getClass();

    public int getChildrenCount(int id) {
        return templateDao.getChildrenCount(entityClass, id);
    }

    public List<Template> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<Template> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return templateDao.getObjects(entityClass, start, quantity, sortName, desc);
    }

    public List<Template> getEntities() {
        return templateDao.getTemplates();
    }

    public TemplateDao getTemplateDao() {
        return templateDao;
    }


    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }


    public Template getTemplate(Integer templateId) {
        return templateDao.getTemplate(templateId);
    }


    public Template saveTemplate(Template template) {
        return templateDao.saveTemplate(template);
    }


    public List getTemplates() {
        return templateDao.getTemplates();
    }


    public void deleteTemplate(Template template) {
        templateDao.deleteTemplate(template);
    }


    public List findTemplates(String[] descriptions) {
        return templateDao.findTemplates(descriptions);
    }

}
