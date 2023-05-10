package loginController.shoppingCartController;

import loginController.login.User;
import loginController.login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class ShoppingCartRestController {
    @Autowired
    private CartItemService cartService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") Integer quantity,
                            @AuthenticationPrincipal Authentication authentication,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            redirectAttributes.addFlashAttribute("cartMessage", "You must login to add this product to your shopping cart");
        } else {
            User user = userRepository.findByEmail(authentication.getName());

            Integer addedQuantity = cartService.addProduct(productId, quantity, user);
            redirectAttributes.addFlashAttribute("cartMessage", addedQuantity + " item(s) of this product were added to your shopping cart");
        }

        // Lưu trữ cartMessage trong session
        session.setAttribute("cartMessage", redirectAttributes.getFlashAttributes().get("cartMessage"));

        return "redirect:/product/" + productId;
    }

    @PostMapping("/checkout")
    public String checkout(Model model, Authentication authentication,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        User user = userRepository.findByEmail(authentication.getName());
        Long userIdId = user.getId();

        cartService.deleteCartItemsByUserId(userIdId);
//        String mess = "Đặt hàng thành công";
//        session.setAttribute("cartMess", mess);
        return "redirect:/view";
    }
}
