package io.playdata.security.login.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity  // JPA
@Data  // Lombok
@Table(name="accounts")  // 이 엔티티에서 사용하는 DB의 이름
public class AccountDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String image;
    private String passwordConfirm;
    private String name;
    private String address;
    private String email;
}