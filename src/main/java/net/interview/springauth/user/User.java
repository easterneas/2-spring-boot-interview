package net.interview.springauth.user;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Entity
@Table(
        name = "Users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "mobile_phone")
        }
)
public class User {
    private SecureRandom rand = new SecureRandom();
    private byte[] salt = new byte[14];

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Username cannot be empty!")
    @Column(name = "username")
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

    public User () throws NoSuchAlgorithmException {}

    public User (
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

    public String getUsername() {
        return username;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public void setPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        String encodedHash = Base64.getEncoder().encodeToString(hash);

        this.password = encodedHash;
    }

    public void setId(long id) {
        this.id = id;
    }
}
