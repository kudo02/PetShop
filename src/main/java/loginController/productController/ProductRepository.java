package loginController.productController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByTypeContainingIgnoreCase(String type);

    List<Product> findByAge(Integer age);

    Page<Product> findAll(Pageable pageable);

}
