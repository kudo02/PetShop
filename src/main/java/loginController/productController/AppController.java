package loginController.productController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private ProductRepository pro;
    @Autowired
    private ProductService service;

    @Autowired
    private ProductDetailRepository repo;

    @RequestMapping(value = "/home")
    public String show(){
        return "home";
    }

    @RequestMapping(value = "/about")
    public String show1(){
        return "about";
    }

    @RequestMapping(value = "/service")
    public String show2(){
        return "service";
    }


    @RequestMapping(value = "/product")
    public String listProduct(Model model){
        List<Product> products = service.listALl();
        model.addAttribute("products", products);
        return "product";
    }

//    @RequestMapping("/list")
//    public String list(Model model){
//        List<Product> products = service.listALl();
//        model.addAttribute("products", products);
//        return "listProduct";
//    }

    @RequestMapping("/product/{id}")
    public String getProductById(@PathVariable Long id, Model model, HttpSession session){
        Optional<ProductDetail> product = repo.findById(id);
        model.addAttribute("product", product);

        List<Product> products = service.listALl();
        model.addAttribute("products", products);

        // Lấy cartMessage từ session
        String cartMessage = (String) session.getAttribute("cartMessage");

        // Xóa cartMessage khỏi session
        session.removeAttribute("cartMessage");

        model.addAttribute("cartMessage", cartMessage);

        return "productCart";
    }

    @GetMapping("/list")
    public String search(@RequestParam(name = "query", required = false)String query,
                         @RequestParam(name = "species", required = false) String species,
                         @RequestParam(name = "type", required = false) String type,
                         @RequestParam(name = "age", required = false) Integer age,
                         @RequestParam(name = "price", required = false) String price,
                         Model model){
        List<Product> products;
        if(query != null && !query.isEmpty()){
            products = pro.findByNameContainingIgnoreCase(query);
            System.out.println(1);
        } else if (species != null && age != null && price != null && type != null) {
            // Tìm kiếm theo tất cả các thuộc tính
            String[] priceRange = price.split("-");
            int minPrice = Integer.parseInt(priceRange[0]);
            int maxPrice = Integer.parseInt(priceRange[1]);
            products = service.searchProductsByTypeAndAgeAndPrice(species,type,age,minPrice,maxPrice);
            System.out.println(2);
        }else if (species != null && !species.isEmpty() && type != null && !type.isEmpty()) {
            products = pro.findBySpeciesAndType(species, type);
            System.out.println(3);
        }else if (species != null && !species.isEmpty() && age > 0 && age != null) {
            products = pro.findBySpeciesAndAge(species, age);
            System.out.println(species);
            System.out.println(age);
            System.out.println(4);
        }else if (species != null && !species.isEmpty() && price != null) {
            String[] priceRange = price.split("-");
            int minPrice = Integer.parseInt(priceRange[0]);
            int maxPrice = Integer.parseInt(priceRange[1]);
            products = pro.findBySpeciesAndPriceBetween(species, minPrice, maxPrice);
            System.out.println(5);
        }else if (type != null && !type.isEmpty() && age != null && age > 0) {
            products = pro.findByTypeAndAge(type, age);
            System.out.println(6);
        }else if (type != null && !type.isEmpty() && price != null) {
            String[] priceRange = price.split("-");
            int minPrice = Integer.parseInt(priceRange[0]);
            int maxPrice = Integer.parseInt(priceRange[1]);
            products = pro.findByTypeAndPriceBetween(type, minPrice, maxPrice);
            System.out.println(7);
        }else if (age != null && age > 0 && price != null) {
            String[] priceRange = price.split("-");
            int minPrice = Integer.parseInt(priceRange[0]);
            int maxPrice = Integer.parseInt(priceRange[1]);
            products = pro.findByAgeAndPriceBetween(age, minPrice, maxPrice);
            System.out.println(8);
        }
        else if (species != null && !species.isEmpty()) {
            products = pro.findBySpeciesContainingIgnoreCase(species);
            System.out.println(9);
        }else if (type != null && !type.isEmpty()) {
            products = pro.findByTypeContainingIgnoreCase(type);
            System.out.println(10);
        }else if (age != null && age > 0) {
            products = pro.findByAge(age);
            System.out.println(11);
        }else if (price != null) {
            String[] priceRange = price.split("-");
            int minPrice = Integer.parseInt(priceRange[0]);
            int maxPrice = Integer.parseInt(priceRange[1]);
            products = pro.findByPriceBetween(minPrice, maxPrice);
            System.out.println(12);
        }else{
            products = pro.findAll();
            System.out.println(13);
        }
        model.addAttribute("products", products);
        return "listProduct";
    }

}
