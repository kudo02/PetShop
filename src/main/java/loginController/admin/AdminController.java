package loginController.admin;

import loginController.login.User;
import loginController.login.UserRepository;
import loginController.productController.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService service;

    @Autowired
    private UserRepository user;

    @Autowired
    private ProductDetailRepository pro;

    @Autowired
    private ProductDetailService productDetailService;


    @RequestMapping(value = "/products")
    public String listProduct(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer page){
        Page<Product> products = service.getProducts(page);

        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("products", products);
        return "admin/list-product";
    }

    @GetMapping("/user")
    public String getUsers(Model model){
        List<User> list = user.findAll();
        model.addAttribute("list", list);
        return "admin/list-user";
    }

    @GetMapping("/detail")
    public String getDetail(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer page){
        Page<ProductDetail> detailList = productDetailService.getProductDetails(page);

        model.addAttribute("totalPage", detailList.getTotalPages());
        model.addAttribute("currentPage", page);
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

    @GetMapping("/update")
    public String update(){
        return "admin/updateProduct";
    }

    @PostMapping("/products/update")
    public String updateProduct(@RequestParam("id") Long id, @RequestParam("name") String name,
    @RequestParam("price") String price, @RequestParam("type") String type, @RequestParam("age") Integer age){
        Product product = service.findById(id);
        product.setName(name);
        product.setPrice(Integer.valueOf(price));
        product.setAge(age);
        product.setType(type);
        service.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/update2")
    public String update2(){
        return "admin/updateProductDetail";
    }

    @PostMapping("/detail/update")
    public String updateProductDetail(@RequestParam("id") Long id, @RequestParam("name") String name,
                                @RequestParam("price") String price, @RequestParam("type1") String type, @RequestParam("descript") String descript){
        Optional<ProductDetail> product = pro.findById(id);
        product.get().setName(name);
        product.get().setPrice(price);
        product.get().setType1(type);
        product.get().setDescript(descript);
        productDetailService.save(product);
        return "redirect:/admin/detail";
    }


}
