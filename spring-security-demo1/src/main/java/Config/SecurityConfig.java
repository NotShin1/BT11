package Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import Repository.UserInfoRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Chú ý: Hình ảnh của bạn inject trực tiếp Repository ở đây.
    // Cách làm này vẫn hoạt động, nhưng cách tốt hơn là inject UserInfoService.
    @Autowired
    UserInfoRepository repository;

    // Bean này sẽ cung cấp UserInfoService cho Spring Security.
    // Nó thay thế cho InMemoryUserDetailsManager trước đây.
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService(repository);
    }

    // Bean mã hóa mật khẩu, không thay đổi.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean quan trọng: Cấu hình AuthenticationProvider
    // Nó nói cho Spring Security biết cách lấy thông tin user (qua userDetailsService)
    // và cách kiểm tra mật khẩu (qua passwordEncoder).
    @SuppressWarnings("deprecation")
	@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Cấu hình các quy tắc cho HTTP requests, tương tự như trước đây
    // nhưng có bổ sung thêm 1 endpoint mới.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/new").permitAll() // Endpoint mới để tạo user, cho phép tất cả
                        .requestMatchers("/", "/hello").permitAll() // Cho phép truy cập trang chủ và /hello
                        .requestMatchers("/customer/**").authenticated() // Yêu cầu đăng nhập cho các trang customer
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }
}