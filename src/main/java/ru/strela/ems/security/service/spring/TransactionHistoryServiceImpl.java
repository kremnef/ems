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
package ru.strela.ems.security.service.spring;


import ru.strela.ems.security.dao.TransactionHistoryDao;
import ru.strela.ems.security.model.TransactionHistory;
import ru.strela.ems.security.service.TransactionHistoryService;

import java.util.List;


public class TransactionHistoryServiceImpl implements TransactionHistoryService {


    private TransactionHistoryDao transactionHistoryDao = null;


    public int getChildrenCount(int id) {
        return transactionHistoryDao.getChildrenCount(TransactionHistory.class, id);
    }

    public List<TransactionHistory> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<TransactionHistory> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return transactionHistoryDao.getObjects(TransactionHistory.class, start, quantity, sortName, desc);
    }


    public TransactionHistoryDao getTransactionHistoryDao() {
        return transactionHistoryDao;
    }


    public void setTransactionHistoryDao(TransactionHistoryDao transactionHistoryDao) {
        this.transactionHistoryDao = transactionHistoryDao;
    }


    public TransactionHistory getTransaction(Integer transactionId) {
        return transactionHistoryDao.getTransaction(transactionId);
    }


    public TransactionHistory saveTransaction(TransactionHistory transaction) {
        return transactionHistoryDao.saveTransaction(transaction);
    }


    public List<TransactionHistory> getTransactions() {
        return transactionHistoryDao.getTransactions();
    }

    public List<TransactionHistory> getEntities() {

        return transactionHistoryDao.getTransactions();
    }


    public void deleteTransaction(TransactionHistory transaction) {
        transactionHistoryDao.deleteTransaction(transaction);
    }


    public List findTransactions(String[] descriptions) {
        return transactionHistoryDao.findTransactions(descriptions);
    }

}
