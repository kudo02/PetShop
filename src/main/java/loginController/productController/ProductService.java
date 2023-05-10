package loginController.productController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> listALl(){
        return (List<Product>) repo.findAll();
    }

    public Product findById(Long id) {
        return repo.findById(Long.valueOf(id)).orElse(null);
    }

    public Page<Product> getProducts(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public void deleteById(Long id){
        repo.deleteById(id);
    }

    public void saveProduct(Product product){
        repo.save(product);
    }

}
