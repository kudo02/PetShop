package loginController.shoppingCartController;

import loginController.productController.Product;
import loginController.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUser(User user);

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUser_Id(Long customerId);
}
