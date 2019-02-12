package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface ProductMapper {

    /**
     * 根据大类categoryId 来查询属于该类的所有Product
     */
    List<Product> getProductListByCategory(String categoryId);

    /**
     * 根据小类 productId 来查询该product对象
     */
    Product getProduct(String productId);

    /**
     * 根据关键字 keywords 查询所有符合条件的Product
     */
    List<Product> searchProductList(String keywords);
}
