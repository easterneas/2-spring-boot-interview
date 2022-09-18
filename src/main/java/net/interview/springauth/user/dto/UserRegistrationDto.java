package net.interview.springauth.user.dto;

import lombok.Data;
import net.interview.springauth.helper.HashHelper;
import net.interview.springauth.user.Users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Username cannot be empty!")
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}",
            message = "Password should contain lowercase, uppercase, numeric, and special characters."
    )
    @Size(min = 8, message = "Password minimum length is 8 characters.")
    private String password;

    @NotBlank(message = "Mobile phone cannot be empty!")
    @Pattern(
            regexp = "^\\d*",
            message = "Mobile phone must be in number format!"
    )
    private String mobile_phone;

    public Users toUser () throws NoSuchAlgorithmException, InvalidKeySpecException {
        HashHelper hh = new HashHelper();

        return new Users().setUsername(username).setPassword(hh.hash(password.toCharArray())).setMobile_phone(mobile_phone);
    }
}
