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
package ru.strela.ems.ecommerce.service.spring;


import ru.strela.ems.ecommerce.dao.ProductDao;
import ru.strela.ems.ecommerce.model.Product;
import ru.strela.ems.security.dao.hibernate.CommonObjectDaoImpl;

import java.util.List;


public class ProductServiceImpl extends CommonObjectDaoImpl implements ProductDao {


    private ProductDao productDao = null;
    Product product = new Product();

    public ProductDao getProductDao() {
        return productDao;
    }
    Class entityClass = product.getClass();

    public int getChildrenCount(int id) {
        return productDao.getChildrenCount(entityClass, id);
    }
    
    public List<Product> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<Product> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return productDao.getObjects(entityClass, start, quantity, sortName, desc);
    }
    

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }


    public Product getProduct(Integer itemId) {
        return productDao.getProduct(itemId);
    }


    public Product saveProduct(Product item) {
        return productDao.saveProduct(item);
    }


    public List getProducts() {
        return productDao.getProducts();
    }


    public void deleteProduct(Product item) {
        productDao.deleteProduct(item);
    }


    public List findProducts(String[] descriptions) {
        return productDao.findProducts(descriptions);
    }
/*
    public List<Product> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<Product> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return ProductDao.getObjects(Product.class, start, quantity, sortName, desc);
    }*/
}
