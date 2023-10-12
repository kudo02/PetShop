package loginController.productController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    Page<ProductDetail> findAll(Pageable pageable);
}
