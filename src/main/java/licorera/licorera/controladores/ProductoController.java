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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Admin
 */

@RequestMapping("/api/productos")
public class ProductoController{
    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> obtenerProductosCategorizados(){
        Map<String, List> response = new HashMap<>();
        try {
            List<Categoria> categorias = categoriaServicio.listaCategorias();
            
            for (Categoria categoria : categorias) {
                
                switch (categoria.getNombre()) {
                    case "Otros":
                        List<Producto> otro = productoServicio.listarPorCategoria(categoria);
                        response.put("otro", otro);
                        break;
                    case "Alcholicas":
                        List<Producto> alcoholicas = productoServicio.listarPorCategoria(categoria);
                        response.put("alcoholicas", alcoholicas);
                        break;
                    case "NoAlcoholicas":
                        List<Producto> noAlcoholicas = productoServicio.listarPorCategoria(categoria);
                        response.put("noAlcoholicas", noAlcoholicas);
                        break;
                    default:
                       
                        throw new AssertionError();
                }
            }
            
             return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
           e.getMessage();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "error al traer los productos"));
                             
        }
        
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
