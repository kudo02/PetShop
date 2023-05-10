package loginController.shoppingCartController;

import loginController.login.User;
import loginController.login.UserRepository;
import loginController.productController.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @RequestMapping("/view")
    public String viewCart(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());
        List<CartItem> cartItems = cartItemService.findByUser(user);

        float total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        model.addAttribute("total", total);
        model.addAttribute("cartItems", cartItems);
        return "shoppingCart";
    }



//    @DeleteMapping("/remove")
//    public ResponseEntity<String> removeFromCart(@RequestParam("cartItemId") Long cartItemId, Authentication authentication) {
//        User user = userRepository.findByUsername(authentication.getName());
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
//        if (user == null || cartItem == null || !cartItem.getUser().equals(user)) {
//            return ResponseEntity.badRequest().body("Invalid cart item");
//        }
//        cartItemRepository.delete(cartItem);
//        return ResponseEntity.ok("Cart item removed");
}
