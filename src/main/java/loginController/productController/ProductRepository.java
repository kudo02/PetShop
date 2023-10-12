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

    List<Product> findByPriceBetween(int minPrice, int maxPrice);


    List<Product> findBySpeciesAndTypeAndAgeAndPriceBetween(String species, String type, Integer age, int minPrice, int maxPrice);

    List<Product> findBySpeciesContainingIgnoreCase(String species);

    List<Product> findBySpeciesAndType(String species, String type);

    List<Product> findBySpeciesAndAge(String species, Integer age);

    List<Product> findBySpeciesAndPriceBetween(String species, int minPrice, int maxPrice);

    List<Product> findByTypeAndAge(String type, Integer age);

    List<Product> findByTypeAndPriceBetween(String type, int minPrice, int maxPrice);

    List<Product> findByAgeAndPriceBetween(Integer age, int minPrice, int maxPrice);
}
