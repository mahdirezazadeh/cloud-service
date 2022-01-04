package ir.urmia.cloudservice.service.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDTO {

    @NotBlank(message = "Username can not be blank!")
    @Length(min = 4, message = "length must be more than 4")
    private String username;

    @NotBlank(message = "Password can not be blank!")
    @Length(min = 8, message = "length must be more than 8")
    private String password;

    @NotBlank(message = "Email can not be blank!")
    @Length(min = 6, message = "length must be more than 6")
    private String email;

    @NotBlank(message = "First name can not be blank!")
    private String firstName;

    @NotBlank(message = "Last name can not be blank!")
    private String lastName;

    private String phoneNumber;
}
