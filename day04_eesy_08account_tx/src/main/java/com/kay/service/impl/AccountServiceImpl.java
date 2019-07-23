package com.kay.service.impl;

import com.kay.dao.IAccountDao;
import com.kay.domain.Account;
import com.kay.service.IAccountService;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 账户的业务层实现类
 *
 * 事务的控制都应该在业务层，而不应该在持久层
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    private TransactionTemplate transactionTemplate;

    public void setAccountDao(IAccountDao accoutDao) {
        this.accountDao = accoutDao;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public Account findAccountById(final Integer accountId) {
        return transactionTemplate.execute(new TransactionCallback<Account>() {
            public Account doInTransaction(TransactionStatus transactionStatus) {
                return accountDao.findAccountById(accountId);
            }
        });
        //return accountDao.findAccountById(accountId);
    }



    public void transfer(final String sourceName, final String targetName,final Float money){

        transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
                System.out.println("transfer........");
                //1.根据名称查询转出账户
                Account source = accountDao.findAccountByName(sourceName);
                //2.根据名称查询转入账户
                Account target = accountDao.findAccountByName(targetName);
                //3.转出账户减钱
                source.setMoney(source.getMoney() - money);
                //4.转入账户加钱
                target.setMoney(target.getMoney() + money);
                //5.更新转出账户
                accountDao.updateAccount(source);
                // int i = 5/0;
                //6.更新转出账户
                accountDao.updateAccount(target);
                return null;
            }
        });
    }
}
