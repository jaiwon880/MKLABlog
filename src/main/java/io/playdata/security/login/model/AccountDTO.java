package io.playdata.security.login.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity  // JPA
@Data  // Lombok
@NoArgsConstructor
@Table(name="accounts")  // 이 엔티티에서 사용하는 DB의 이름
public class AccountDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    private String address;
    private String email;
    private String image;


    public String getImage() {
        return image;
    }

}

