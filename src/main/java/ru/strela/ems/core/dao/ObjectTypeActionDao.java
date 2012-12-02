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
package ru.strela.ems.core.dao;

import ru.strela.ems.core.model.ObjectTypeAction;
import ru.strela.ems.security.dao.CommonObjectDao;

import java.util.List;

public interface ObjectTypeActionDao extends CommonObjectDao{

	ObjectTypeAction getObjectTypeAction(Integer objectTypeActionId);
	List getObjectTypeActions();
	ObjectTypeAction saveObjectTypeAction(ObjectTypeAction objectTypeAction);
	void deleteObjectTypeAction(ObjectTypeAction objectTypeAction);
	List findObjectTypeActions(String[] descriptions);

}