package Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Entity.UserInfo;
import Service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // @RequiredArgsConstructor của Lombok sẽ tự động tạo constructor
    // để inject (tiêm) bean UserService này vào.
    // Đây là cách làm hiện đại và được khuyến khích thay cho @Autowired.
    private final UserService userService;

    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }
}
