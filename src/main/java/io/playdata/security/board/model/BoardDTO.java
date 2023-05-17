package io.playdata.security.board.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@Table(name="BoardDTO")
public class BoardDTO {
    @Id // DB와 소통할 때 단독으로 쓰일 기준값
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 이걸 추가로 생성할 때마다 1씩 증가
    private Long id;
    private String username;
    private String title;
    private String content;
//    private String image; // 파일 -> 별도의 스토리지(창고)에 업로드 되어야함 -> 업로드된 경로 또는 파일 이름
    private LocalDateTime createdAt; // 생성 당시의 시간을 기록

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Getter 및 Setter 생략

    public String getFormattedCreatedAt() {
        if (createdAt != null) {
            return createdAt.format(FORMATTER);
        }
        return null;
    }
}

