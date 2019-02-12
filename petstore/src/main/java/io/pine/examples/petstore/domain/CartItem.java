package io.pine.examples.petstore.domain;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Data
public class CartItem implements Serializable {
    private Item item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public void setItem(Item item) {
        this.item = item;
        calculateTotal();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }

    private void calculateTotal() {
        if (item != null && item.getListPrice() != null) {
            total = item.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }
}
