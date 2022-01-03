package ir.urmia.cloudservice.domain;


import ir.urmia.cloudservice.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = User.TABLE_NAME)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Long> {
    static final String TABLE_NAME = "user_table";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phone_number";

    @Column(name = USERNAME, unique = true, nullable = false)
    private String username;

    @Column(name = PASSWORD, nullable = false)
    private String password;

    @Column(name = FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = LASTNAME, nullable = false)
    private String lastName;

    @Column(name = EMAIL, unique = true, nullable = false)
    private String email;

    @Column(name = PHONE_NUMBER, unique = true, nullable = false)
    private String phoneNumber;

    public String getFullName() {
        return firstName.concat(" ").concat(lastName);
    }
}
