package loginController.productController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository repo;

    public Optional<ProductDetail> findById(Long id){
        return repo.findById(id);
    }

    public ProductDetail save(Optional<ProductDetail> productDetail1){
        return repo.save(productDetail1.get());
    }

}
