package Config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import Entity.UserInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(UserInfo userInfo) {
        name = userInfo.getName();
        password = userInfo.getPassword();
        // Lấy chuỗi roles từ userInfo (ví dụ: "ROLE_USER,ROLE_ADMIN"),
        // tách chuỗi bằng dấu phẩy, và chuyển đổi mỗi phần thành một đối tượng SimpleGrantedAuthority.
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Trả về true nghĩa là tài khoản không bao giờ hết hạn
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Trả về true nghĩa là tài khoản không bị khóa
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Trả về true nghĩa là mật khẩu/thông tin xác thực không bao giờ hết hạn
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Trả về true nghĩa là tài khoản được kích hoạt
        return true;
    }
}