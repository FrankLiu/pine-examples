package io.pine.examples.payment.domain;

import java.util.Date;

/**
 * 支付方式
 *
 * @author Frank
 * @sinace 2018/10/25 0025.
 */
public class PaymentMethod {

    private String paymentMethodId;

    private PaymentMethodType paymentMethodType;

    // 机构Id
    private String partyId;

    // 结算账户
    private String glAccountId;

    private String description;

    private Date fromDate;
    private Date thruDate;

    private Date createdTime;
    private Date lastUpdatedTime;

}
