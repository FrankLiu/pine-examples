package io.pine.examples.petstore.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Data
public class Sequence  implements Serializable {
    private String name;
    private int nextId;

    public Sequence(String name, int nextId) {
        this.name = name;
        this.nextId = nextId;
    }
}
