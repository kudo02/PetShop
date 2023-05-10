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
                         @RequestParam(name = "type", required = false) String type,
                         @RequestParam(name = "age", required = false) Integer age,
                         Model model){
        List<Product> products;
        if(query != null && !query.isEmpty()){
            products = pro.findByNameContainingIgnoreCase(query);
        } else if (type != null && !type.isEmpty()) {
            products = pro.findByTypeContainingIgnoreCase(type);
        } else if (age != null && age > 0) {
            products = pro.findByAge(age);
        }else{
            products = pro.findAll();
        }
        model.addAttribute("products", products);
        return "listProduct";
    }

}
