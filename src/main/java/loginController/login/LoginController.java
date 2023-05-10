package loginController.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String viewHomePage() {
		return "login1";
	}

	@PostMapping("/login")
	public String processRegister() {
		return "redirect:/home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// Hủy phiên đăng nhập hiện tại
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Chuyển hướng người dùng đến trang đăng nhập
		return "redirect:/login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "register";
	}

	@PostMapping("/register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepo.save(user);

		return "redirect:/login";
	}


}
