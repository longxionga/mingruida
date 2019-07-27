package com.acl.platform.service;

import java.io.File;

public interface IPlatformExcelSerivce {

    /**
     *
     * @param brandId
     * @param file
     */
    void shuaBaoMerchant(String brandId, File file);


    /**
     *
     * @param brandId
     * @param file
     */
    void shuaBaoTradeOrder(String brandId, File file);
    /**
     *
     * @param brandId
     * @param file
     */
    void shuaBaoMachine(String brandId, File file);

    /**
     * 海科员工信息导入
     * @param file
     */
    void haikeStaffImport(String brandId,File file) throws Exception;

    /**
     * 海科机具信息导入
     * @param brandId
     * @param file
     */
    void haikeMachine(String brandId, File file);

    /**
     * 海科交易流水信息导入
     * @param brandId
     * @param file
     */
    void haiKeTradeOrder(String brandId, File file);

    /**
     *  海科商户信息导入
     * @param brandId
     * @param file
     */
    void HKMerchant(String brandId, File file);
}
