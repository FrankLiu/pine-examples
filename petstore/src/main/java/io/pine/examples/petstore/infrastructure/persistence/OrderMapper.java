package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface OrderMapper {

    // 根据用户名得到订单
    List<Order> getOrdersByUsername(String username);

    // 根据订单ID得到订单
    Order getOrder(int orderId);

    // 插入新订单
    void insertOrder(Order order);

    // 插入新订单状态
    void insertOrderStatus(Order order);

}
