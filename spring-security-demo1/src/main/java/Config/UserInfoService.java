package Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import Entity.UserInfo;
import Repository.UserInfoRepository;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    // Sử dụng final để đảm bảo repository được khởi tạo và không thay đổi
    private final UserInfoRepository repository;

    // *** CONSTRUCTOR BỊ THIẾU NẰM Ở ĐÂY ***
    // Spring sẽ tự động "tiêm" (inject) UserInfoRepository vào service này thông qua constructor.
    @Autowired
    public UserInfoService(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByName(username);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}