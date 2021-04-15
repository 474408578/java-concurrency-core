package com.xschen.juc.synchronizedcase;

/**
 * 保护没有关联关系的多个资源
 * @author xschen
 */
public class SynchronizedNoRelation {

    /**
     * 保护账户余额的锁
     */
    private final Object balanceLock = new Object();

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 保护密码的锁
     */
    private final Object pwdLock = new Object();

    /**
     * 密码
     */
    private String password;


    /**
     * 取款
     * @param amount
     */
    public void withdraw(Integer amount) {
        synchronized (balanceLock) {
            if (this.balance > amount) {
                balance -= amount;
            }
        }
    }

    /**
     * 查看余额
     * @return
     */
    public Integer getBalance() {
        synchronized (balanceLock) {
            return balance;
        }
    }

    /**
     * 更改密码
     * @param password
     */
    public void updatePassword(String password) {
        synchronized (pwdLock) {
            this.password = password;
        }
    }

    /**
     * 查看密码
     * @return
     */
    public String getPassword() {
        return this.password;
    }
}
