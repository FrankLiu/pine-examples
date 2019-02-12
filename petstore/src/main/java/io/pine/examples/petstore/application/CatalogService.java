package io.pine.examples.petstore.application;

import io.pine.examples.petstore.domain.Category;
import io.pine.examples.petstore.domain.Item;
import io.pine.examples.petstore.domain.Product;
import io.pine.examples.petstore.infrastructure.persistence.CategoryMapper;
import io.pine.examples.petstore.infrastructure.persistence.ItemMapper;
import io.pine.examples.petstore.infrastructure.persistence.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Service
public class CatalogService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    public Category getCategory(String categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
    }

    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList("%"+keyword.toLowerCase()+"%");
    }

    public List<Item> getItemListByProduct(String productId) {
        return itemMapper.getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return itemMapper.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }
}
