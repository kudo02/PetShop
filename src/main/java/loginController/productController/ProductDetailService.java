package loginController.productController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductDetail> getProductDetails(Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo - 1, 10);
        return repo.findAll(pageable);
    }

}
