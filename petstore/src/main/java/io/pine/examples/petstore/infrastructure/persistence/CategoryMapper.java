package io.pine.examples.petstore.infrastructure.persistence;

import io.pine.examples.petstore.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Repository
public interface CategoryMapper {

    /**
     * 得到所有商品大类
     */
    List<Category> getCategoryList();

    /**
     * 根据货物ID得到商品大类
     */
    Category getCategory(String categoryId);
}
