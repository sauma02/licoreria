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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
        
    
    
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
        List<Producto> listaProductos = productoServicio.listarProductos();
        
        
        List<Producto> noAlcohol = new ArrayList<>();
        List<Producto> alcohol = new ArrayList<>();
        List<Producto> otros = new ArrayList<>();
        
        
        for (Producto producto : listaProductos) {
            for (Categoria cat : categorias) {
                switch (cat.getId()) {
                    case "2":
                        if(producto.getCategoria().getId().equals(cat.getId())){
                            alcohol.add(producto);
                        }
                            
                        break;
                    case "3":
                        if(producto.getCategoria().getId().equals(cat.getId())){
                            noAlcohol.add(producto);
                        }
                        break;
                    case "4":
                        if(producto.getCategoria().getId().equals(cat.getId())){
                            otros.add(producto);
                        }
                        break;    
                    default:
                          System.out.println("no hay productos registrados");
                       
                }
               
                
            }
        }
        model.addAttribute("alcohol", alcohol);
        model.addAttribute("noAlcohol", noAlcohol);
        model.addAttribute("otros", otros);
        model.addAttribute("productosCategorizados", categorias);
        model.addAttribute("productos", listaProductos);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", calcularTotal());
        return "index";
    }

   @GetMapping("/about")
   public String about(){
       return "about";
   }
   
   @GetMapping("/añadirProducto/{id}{cantidad}")
   @ResponseBody
   public ResponseEntity<Map<String, Object>> añadirProducto(@PathVariable("id") String id, @PathVariable("cantidad") Integer cantidad){
      Map<String, Object> response = new HashMap<>();
       try {
        Producto pro = productoServicio.listarPorId(id);
        Carrito carro = new Carrito();
        carro.setProductoId(pro.getId());
        carro.setNombre(pro.getNombre());
        carro.setCantidad(cantidad);
        carro.setPrecio(pro.getPrecio() * cantidad);
        carrito.add(carro);
        response.put("carrito", carrito);
        response.put("clase", "success");
        response.put("mensaje", "Producto añadido al carrito");
        return ResponseEntity.ok(response);
        
        
       
       } catch (Exception e) {
           logger.error("error al añadir producto", e);
           response.put("clase", "error");
           response.put("mensaje", "Ocurrio un error");
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       
       
       }
       
       
       
   }
    
   

}
