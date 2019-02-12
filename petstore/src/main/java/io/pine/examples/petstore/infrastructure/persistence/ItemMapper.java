package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface ItemMapper {

    /**
     * 更新库存
     */
    void updateInventoryQuantity(Map<String, Object> param);

    /**
     * 得到商品库存
     */
    int getInventoryQuantity(String itemId);

    /**
     * 根据productId得到对应的所有的商品
     */
    List<Item> getItemListByProduct(String productId);

    /**
     * 根据itemId 得到对应的某个商品
     */
    Item getItem(String itemId);
}
