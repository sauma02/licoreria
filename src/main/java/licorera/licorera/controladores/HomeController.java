/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.controladores;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import licorera.licorera.entidades.Carrito;
import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;
import licorera.licorera.servicios.CategoriaServicio;
import licorera.licorera.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;
    
    private List<Carrito> carrito = new ArrayList<>();
    private Double calcularTotal(){
        
        //los :: acceden directamente a la calse para poder buscar el metodo
        return carrito.stream().mapToDouble(Carrito::getTotal).sum();
    }
  
    @GetMapping("/home")
    public String home(Model model){
        List<Categoria> categorias = categoriaServicio.listaCategorias();
        Map<String, List<Producto>> productosCategorizados = new HashMap<>();
        for (Categoria categoria : categorias) {
             
            switch (categoria.getNombre()) {
                case "Alcoholicas":
                    List<Producto> alcoholicas = productoServicio.listarPorCategoria(categoria);
                    productosCategorizados.put("alcoholicas", alcoholicas);
                    model.addAttribute("alcoholicas", alcoholicas);
                    break;
                case "NoAlcoholicas":
                    List<Producto> noAlcoholicas = productoServicio.listarPorCategoria(categoria);
                    productosCategorizados.put("noAlcoholicas", noAlcoholicas);
                    model.addAttribute("noAlcoholicas", noAlcoholicas);
                    break;
                case "Otros":
                    List<Producto> otros = productoServicio.listarPorCategoria(categoria);
                    productosCategorizados.put("otros", otros);
                    model.addAttribute("otros", otros);
                    break;
                default:
                    throw new AssertionError();
            }
        }
        model.addAttribute("productosCategorizados", productosCategorizados);
        List<Producto> listaProductos = productoServicio.listarProductos();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", calcularTotal());
        return "home";
    }
    
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@ModelAttribute Producto producto, @RequestParam Integer cantidad){
        for (Carrito item : carrito) {
            if(item.getProductoId().equals(producto.getId())){
                item.setCantidad(item.getCantidad()+cantidad);
                return "redirect:/home";
            }
        }
        Carrito carro =  new Carrito();
        carro.setProductoId(producto.getId());
        carro.setNombre(producto.getNombre());
        carro.setPrecio(producto.getPrecio());
        carro.setCantidad(cantidad);
        carrito.add(carro);
        return "redirect:/home";
        
    }
     
    @GetMapping("/api/productos/registrarProducto")
    public String registrar(RedirectAttributes redirect, Model model){
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaServicio.listaCategorias());
        return "formularios/registrarProducto";
    }
    @PostMapping("/api/productos/registrarProducto")
    public String registrarProductoForm(@Valid Producto producto, BindingResult result, RedirectAttributes flash, Model model) throws Exception{
        try {
            productoServicio.crearProducto(producto);
            flash.addAttribute("Status", "success");
            flash.addAttribute("mensaje", "Exito al crear producto");
            return "/api/productos/registrarProducto";
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("Error,", e.getCause());
        }
    }
    
}
