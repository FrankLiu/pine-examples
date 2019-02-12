package io.pine.examples.petstore.domain;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Data
public class Order implements Serializable {
    private int orderId;
    private String username;
    private Date orderDate;

    private String shipAddress1;
    private String shipAddress2;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;
    private String shipToFirstName;
    private String shipToLastName;

    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZip;
    private String billCountry;
    private String billToFirstName;
    private String billToLastName;

    private String courier;
    private BigDecimal totalPrice;
    private String creditCard;
    private String expiryDate;
    private String cardType;
    private String locale;
    private String status;

    private boolean ShippingAddressRequired;

    private List<LineItem> lineItems = new ArrayList<LineItem>();

    public void initOrder(Account account, Cart cart) {

        username = account.getUsername();
        orderDate = new Date(System.currentTimeMillis());

        shipToFirstName = account.getFirstName();
        shipToLastName = account.getLastName();
        shipAddress1 = account.getAddress1();
        shipAddress2 = account.getAddress2();
        shipCity = account.getCity();
        shipState = account.getState();
        shipZip = account.getZip();
        shipCountry = account.getCountry();

        billToFirstName = account.getFirstName();
        billToLastName = account.getLastName();
        billAddress1 = account.getAddress1();
        billAddress2 = account.getAddress2();
        billCity = account.getCity();
        billState = account.getState();
        billZip = account.getZip();
        billCountry = account.getCountry();

        totalPrice = cart.getSubTotal();

        creditCard = "999 9999 9999 9999";
        expiryDate = "12/03";
        cardType = "Visa";
        courier = "UPS";
        locale = "CA";
        status = "P";

        Iterator<CartItem> i = cart.getAllCartItems();
        while (i.hasNext()) {
            CartItem cartItem = (CartItem) i.next();
            addLineItem(cartItem);
        }

    }

    public void addLineItem(CartItem cartItem) {
        LineItem lineItem = new LineItem(lineItems.size() + 1, cartItem);
        addLineItem(lineItem);
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }
}
