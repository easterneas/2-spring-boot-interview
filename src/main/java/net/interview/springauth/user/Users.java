package net.interview.springauth.user;

import lombok.Data;
import lombok.experimental.Accessors;
import net.interview.springauth.helper.HashHelper;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "mobile_phone")
        }
)
@Data
@Accessors(chain = true)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Username cannot be empty!")
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank(message = "Mobile phone cannot be empty!")
    @Pattern(
            regexp = "^\\d*",
            message = "Mobile phone must be in number format!"
    )
    @Column(name = "mobile_phone")
    private String mobile_phone;

    @NotBlank(message = "Password cannot be empty!")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}",
            message = "Password should contain lowercase, uppercase, numeric, and special characters."
    )
    @Size(min = 8, message = "Password minimum length is 8 characters.")
    @Column(name = "password")
    private String password;

    public Users() throws NoSuchAlgorithmException {}

    public Users(
            @NotBlank(message = "Username cannot be empty!")
            String username,
            @NotBlank(message = "Mobile phone cannot be empty!")
            @Pattern(
                    regexp = "^\\d*",
                    message = "Mobile phone must be in number format!"
            )
            String mobile_phone,
            @NotBlank(message = "Password cannot be empty!")
            @Pattern(
                    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}",
                    message = "Password should contain lowercase, uppercase, numeric, and special characters."
            )
            @Size(min = 8, message = "Password minimum length is 8 characters.")
            String password
    ) throws NoSuchAlgorithmException {
        this.username = username;
        this.mobile_phone = mobile_phone;
        this.password = password;
    }
}
