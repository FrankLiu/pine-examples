package io.pine.examples.petstore.application;

import io.pine.examples.petstore.domain.Item;
import io.pine.examples.petstore.domain.LineItem;
import io.pine.examples.petstore.domain.Order;
import io.pine.examples.petstore.domain.Sequence;
import io.pine.examples.petstore.infrastructure.persistence.ItemMapper;
import io.pine.examples.petstore.infrastructure.persistence.LineItemMapper;
import io.pine.examples.petstore.infrastructure.persistence.OrderMapper;
import io.pine.examples.petstore.infrastructure.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private LineItemMapper lineItemMapper;

    public void insertOrder(Order order) {
        order.setOrderId(getNextId("ordernum"));
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            Integer increment = new Integer(lineItem.getQuantity());

            Item item = itemMapper.getItem(itemId);
            item.setQuantity(itemMapper.getInventoryQuantity(itemId));
            lineItem.setItem(item);

            Map<String, Object> param = new HashMap<String, Object>(2);
            param.put("itemId", itemId);
            param.put("increment", increment);

            itemMapper.updateInventoryQuantity(param);
        }

        orderMapper.insertOrder(order);
        orderMapper.insertOrderStatus(order);
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insertLineItem(lineItem);
        }
    }

    public Order getOrder(int orderId) {
        Order order = orderMapper.getOrder(orderId);
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));

        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        }

        return order;
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    public int getNextId(String name) {
        Sequence sequence = new Sequence(name, -1);
        sequence = (Sequence) sequenceMapper.getSequence(sequence);
        if (sequence == null) {
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name
                    + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        if(sequenceMapper.updateSequence(parameterObject)){
            return parameterObject.getNextId();
        }else {
            throw new RuntimeException("Can't updateSequence!");
        }
    }
}
