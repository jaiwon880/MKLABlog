package io.playdata.security.login.service;

import io.playdata.security.board.model.BoardDTO;
import io.playdata.security.board.repository.BoardRepository;
import io.playdata.security.login.model.AccountDTO;
import io.playdata.security.login.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;

@Service
public class LoginService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // register
    public AccountDTO register(AccountDTO accountDTO) throws Exception {
        // username 중복?
        if (accountRepository.findByUsername(accountDTO.getUsername()) != null) {
            return null;
        }
        String newPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(newPassword);
        // TODO : Spring Security 관련
        // 로그인 할땐 bcrypt, 가입할땐 X
        return accountRepository.save(accountDTO);
    }

    @Autowired
    private S3Client s3Client;

    // 이미지 파일 업로드 기능이 추가된 서비스 메소드
    public AccountDTO register(AccountDTO accountDTO, MultipartFile imageFile) throws Exception {
        if (accountRepository.findByUsername(accountDTO.getUsername()) != null) {
            throw new Exception("이미 사용중인 ID입니다");
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            // contentType 검사를 통해 jpeg, png 타입만 들어가게
            boolean isJPEG = contentType.contains("image/jpeg");
            boolean isPNG = contentType.contains("image/png");
            if (!isJPEG && !isPNG) {
                throw new Exception("잘못된 이미지 타입입니다. (JPG, PNG)"); // throw new 에러 발생
            }
            String newFileName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();

            // 객체 스토리지로 저장하는 코드
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("profile-image")
                    .key(newFileName) // 파일명
                    .build(); // 요청을 작Q성 (어느 버켓에, 어떤 이름으로 저장할지)
            // 객체 스토리지 클라이언트에게 요청과 파일(바이트)을 보내는 명령
            s3Client.putObject(request, RequestBody.fromBytes(imageFile.getBytes()));
            accountDTO.setImage(newFileName);
        }
        String newPassword = passwordEncoder.encode(accountDTO.getPassword());
        accountDTO.setPassword(newPassword);
        return accountRepository.save(accountDTO);
    }

    public AccountDTO getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public void deleteAccountByUsername(String username) throws Exception {
        AccountDTO account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new Exception("유저를 찾을 수 없습니다");
        }
        accountRepository.delete(account);
    }

    public void updateAccount(AccountDTO updatedAccount) throws Exception {
        AccountDTO existingAccount = accountRepository.findByUsername(updatedAccount.getUsername());
        if (existingAccount == null) {
            throw new Exception("유효하지 않은 사용자입니다.");
        }

        existingAccount.setName(updatedAccount.getName());
        existingAccount.setAddress(updatedAccount.getAddress());
        existingAccount.setEmail(updatedAccount.getEmail());

        accountRepository.save(existingAccount);
    }

    public byte[] loadFile(String fileName) throws IOException { // fileName을 주면 upload 폴더 경로에 있는 파일을 읽어서 전달
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("profile-image")
                .key(fileName)
                .build(); // 파일을 받아오기 위한 요청 설정
        return s3Client.getObject(request).readAllBytes(); // 바이트 형태로 변환
    }
}