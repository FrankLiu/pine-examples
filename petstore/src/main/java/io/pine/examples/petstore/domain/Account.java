package io.pine.examples.petstore.domain;

import lombok.Data;
import java.io.Serializable;

/**
 * @author frank.liu
 * @since 2019/2/11 0011.
 */
@Data
public class Account  implements Serializable {
    private String username;
    private String password;
    private String repeatedPassword;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;
    private String favouriteCategoryId;
    private String languagePreference;
    private boolean listOption;
    private boolean bannerOption;
    private String bannerName;

}
