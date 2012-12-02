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
package ru.strela.ems.ecommerce.service;

import ru.strela.ems.ecommerce.dao.ProductDao;
import ru.strela.ems.ecommerce.model.Product;

import java.util.List;

public interface ProductService {


    ProductDao getProductDao();
    int getChildrenCount(int id);
    List<Product> getChildren(int parentId);
    List<Product> getChildren(int parentId, int start, int quantity, String sortName, boolean desc);
	void setProductDao(ProductDao productDao);
	Product getProduct(Integer productId);
	List getProducts();
	Product saveProduct(Product product);
	void deleteProduct(Product product);
	List findProducts(String[] descriptions);
}