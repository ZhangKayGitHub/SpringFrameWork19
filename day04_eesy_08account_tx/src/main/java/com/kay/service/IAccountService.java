package com.kay.service;

import com.kay.domain.Account;

/**
 * 账户的业务层接口
 */
public interface IAccountService {
    /**
     * 根据 id 查询账户的信息
     * @param accountId
     * @return
     */
    Account findAccountById(Integer accountId);

    /**
     * 转账
     * @param sourceName    转出账户名称
     * @param targetName    转入账户名称
     * @param money         转帐金额
     */
    void transfer(String sourceName, String targetName,Float money);
}
