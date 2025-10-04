package Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import Entity.UserInfo;
import Repository.UserInfoRepository;

@Service
public record UserService(UserInfoRepository repository,
                          PasswordEncoder passwordEncoder) {

    public String addUser(UserInfo userInfo) {
        // Lấy mật khẩu người dùng gửi lên và mã hóa nó trước khi lưu
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        
        // Lưu đối tượng userInfo đã được mã hóa mật khẩu vào database
        repository.save(userInfo);
        
        return "Thêm user thành công!";
    }
}