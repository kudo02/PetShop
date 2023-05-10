package loginController.admin;

import loginController.login.User;
import loginController.login.UserRepository;
import loginController.productController.Product;
import loginController.productController.ProductDetail;
import loginController.productController.ProductDetailRepository;
import loginController.productController.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService service;

    @Autowired
    private UserRepository user;

    @Autowired
    private ProductDetailRepository pro;


//    @RequestMapping(value = "/product")
//    public String listProduct(Model model){
//        List<Product> products = service.listALl();
//        model.addAttribute("listProducts", products);
//        return "admin/list-product";
//    }

    @GetMapping("/products")
    public String getProducts(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        Page<Product> productPage = service.getProducts(PageRequest.of(page, size));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "admin/list-product";
    }

    @GetMapping("/user")
    public String getUsers(Model model){
        List<User> list = user.findAll();
        model.addAttribute("list", list);
        return "admin/list-user";
    }

    @GetMapping("/detail")
    public String getDetail(Model model){
        List<ProductDetail> detailList = pro.findAll();
        model.addAttribute("list", detailList);
        return "admin/list-product-detail";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes ra){
        service.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra){
        user.deleteById(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/detail/delete/{id}")
    public String deleteDetail(@PathVariable("id") Long id, RedirectAttributes ra){
        pro.deleteById(id);
        return "redirect:/admin/detail";
    }

    @GetMapping("/newProduct")
    public String newProduct(){
        return "admin/newProduct";
    }

    @GetMapping("/newProductDetail")
    public String newProductDetail(){
        return "admin/newProductDetail";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes ra){
        service.saveProduct(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/detail/save")
    public String saveProductDetail(ProductDetail product, RedirectAttributes ra){
        pro.save(product);
        return "redirect:/admin/detail";
    }
}
