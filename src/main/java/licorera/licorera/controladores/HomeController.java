/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import licorera.licorera.entidades.Carrito;
import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;
import licorera.licorera.servicios.CategoriaServicio;
import licorera.licorera.servicios.ProductoServicio;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Admin
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;

    private List<Carrito> carrito = new ArrayList<>();

    private Double calcularTotal() {

        //los :: acceden directamente a la calse para poder buscar el metodo
        return carrito.stream().mapToDouble(Carrito::getTotal).sum();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    
    @GetMapping("/listaProductos")
    public String listaProductos(Model model){
        List<Producto> listaProductos = productoServicio.listarProductos();
        model.addAttribute("listaProductos", listaProductos);
        return "listaAdmin";
               
    }
    
    
    @GetMapping("/categorias")
    public List<Categoria> obtenerCategorias(){
        return categoriaServicio.listaCategorias();
    }


    @GetMapping("/home")
    public String home(Model model) {
        List<Categoria> categorias = categoriaServicio.listaCategorias();
        
        model.addAttribute("productosCategorizados", categorias);
        List<Producto> listaProductos = productoServicio.listarProductos();
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", calcularTotal());
        return "index";
    }

   @GetMapping("/about")
   public String about(){
       return "about";
   }
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@ModelAttribute Producto producto,RedirectAttributes flash, @RequestParam Integer cantidad, Model model) {
        for (Carrito item : carrito) {
            if (item.getProductoId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                flash.addFlashAttribute("status", "danger");
                flash.addFlashAttribute("mensaje", "Este producto ya esta en el carrito");
                return "redirect:/home";
            }
        }
        Carrito carro = new Carrito();
        carro.setProductoId(producto.getId());
        carro.setNombre(producto.getNombre());
        carro.setPrecio(producto.getPrecio());
        carro.setCantidad(cantidad);
        carrito.add(carro);
        model.addAttribute("carrito", carrito);
        return "redirect:/home";

    }
    
   

}
