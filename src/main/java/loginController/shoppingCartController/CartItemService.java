package loginController.shoppingCartController;

import loginController.productController.Product;
import loginController.productController.ProductService;
import loginController.login.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository repo;

    @Autowired
    private ProductService pro;

    public List<CartItem> findByUser(User user) {
        return repo.findByUser(user);
    }

    public CartItem save(CartItem cartItem) {
        return repo.save(cartItem);
    }

    public CartItem findCartItemByUserAndProduct(User user, Product product) {
        CartItem cartItem = repo.findByUserAndProduct(user, product);
        return cartItem;
    }

    public Integer addProduct(Long productId, Integer quantity, User user){
        Integer addedQuantity = quantity;

        Product product = pro.findById(productId);

        CartItem cartItem = repo.findByUserAndProduct(user, product);

        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setUser(user);
            cartItem.setProduct(product);
        }

        repo.save(cartItem);

        return addedQuantity;
    }

    @Transactional
    public void deleteCartItemsByUserId(Long userId) {
        repo.deleteByUser_Id(userId);
    }

}
