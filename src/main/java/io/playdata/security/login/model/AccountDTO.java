package io.playdata.security.login.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import io.playdata.security.login.model.Role;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity  // JPA
@Data  // Lombok
@NoArgsConstructor
@Table(name = "accounts")  // 이 엔티티에서 사용하는 DB의 이름
public class AccountDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String address;
    private String email;
    private String image;

    @Builder
    public AccountDTO(Long id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public AccountDTO update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role != null ? this.role.getKey() : null;
    }


    public String getImage() {
        return image;
    }

}

