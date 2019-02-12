package io.pine.examples.petstore.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Data
public class Product  implements Serializable {
    private String productId;
    private String categoryId;
    private String name;
    private String description;
}
