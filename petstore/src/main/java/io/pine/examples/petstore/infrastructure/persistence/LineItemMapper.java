package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.LineItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface LineItemMapper {
    // 根据订单ID得到订单中的商品项
    List<LineItem> getLineItemsByOrderId(int orderId);

    // 插入商品项
    boolean insertLineItem(LineItem lineItem);
}
