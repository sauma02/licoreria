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
import licorera.licorera.utilidades.archivosUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
    @Value("${valor.ruta}")
    private String ruta;
    
    @GetMapping("/api/productos/registrar")
    public String crearProducto(Model model){
        List<Categoria> categorias = categoriaServicio.listaCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", new Producto());
        return "registrarProducto";
    }
    @PostMapping("/api/productos/registrar")
    public String crearProducto(@Valid Producto producto, @RequestParam MultipartFile imagen, RedirectAttributes flash, Model model){
        try {
            if(imagen == null || imagen.isEmpty()){
                flash.addFlashAttribute("class", "danger");
                flash.addFlashAttribute("mensaje", "No se ha cargado ningun archivo");
                return "redirect:/api/productos";
            } else{
                
                
                String rutaArchivo = this.ruta + "archivos/" + producto.getNombre() + "/";
                String nombreArchivo = archivosUpload.guardarArchivo(imagen, ruta);
                if("no".equals(nombreArchivo)){
                    flash.addAttribute("clase", "danger");
                    flash.addAttribute("mensaje", "El formato ingresado no es valido, por favor ingresa solo imagenes(PNG, JPEG, JPG)");
                    return "redirect:/api/productos";
                }
                if(nombreArchivo != null){
                    producto.setImagen(nombreArchivo);
                }
                productoServicio.crearProducto(producto);
                flash.addAttribute("clase", "success");
                flash.addAttribute("mensaje", "Exito al registrar producto");
                return "redirect:/api/productos";
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getCause() != null){
                System.err.println("Error: "+ e.getMessage());
                flash.addAttribute("clase", "danger");
                flash.addAttribute("mensaje", e.getMessage());
            }
            flash.addAttribute("clase", "danger");
            flash.addAttribute("mensaje", e.getLocalizedMessage());
            return "redirect:/api/productos";
        }
    }
    
    
}
