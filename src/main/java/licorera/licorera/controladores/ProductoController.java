/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;
import licorera.licorera.servicios.CategoriaServicio;
import licorera.licorera.servicios.ProductoServicio;
import licorera.licorera.utilidades.archivosUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Admin
 */
@Controller
@CrossOrigin(origins = "/api/productos/**")
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private CategoriaServicio categoriaServicio;
    @Value("${valor.ruta}")
    private String ruta;

    @GetMapping("/registrar")
    public String crearProducto(Model model) {
        List<Categoria> categorias = categoriaServicio.listaCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", new Producto());
        return "formularios/registrarProducto";
    }
    
    @GetMapping("/editarProducto/{id}")
    public String editarProducto(@PathVariable("id") String id, Model model){
        Producto producto = productoServicio.listarPorId(id);
        List<Categoria> categorias = categoriaServicio.listaCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", producto);
        System.out.println("Producto id: "+ producto.getId());
        return "formularios/editarProducto";
    }
    
    @GetMapping("/eliminarProducto/{id}")
    public ResponseEntity<Map<String, String>> eliminarCandidato(@PathVariable("id") String id){
        Map<String, String> response = new HashMap<>();
        try {
            Producto producto = productoServicio.listarPorId(id);
            productoServicio.eliminarProducto(producto);
            response.put("clase", "success");
            response.put("mensaje", "Éxito al eliminar");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("clase", "error");
            response.put("mensaje", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    
    
@PostMapping("/editarProducto")
public String editar(
        @RequestParam("id") String id,
        @RequestParam("nombre") String nombre,
        @RequestParam("cantidad") Integer cantidad,
        @RequestParam("precio") double precio,
        @RequestParam("categoria") String categoria,
        @RequestParam("imagenes") MultipartFile imagen,
        RedirectAttributes flash) {
    try {
        // Validar existencia del producto
        Producto producto = productoServicio.listarPorId(id);
        System.out.println("Producto" + producto.getId());
        if (producto == null) {
            flash.addFlashAttribute("class", "error");
            flash.addFlashAttribute("mensaje", "Producto no encontrado.");
            return "redirect:/home/listaProductos";
        }
        System.out.println("Categoria: "+categoria);
        // Validar existencia de la categoría
        Categoria cate = categoriaServicio.listarPorId(categoria);
        if (cate == null) {
            flash.addFlashAttribute("class", "error");
            flash.addFlashAttribute("mensaje", "Categoría no válida.");
            return "redirect:/api/productos/editarProducto/" + id;
        }

        // Guardar archivo de imagen (si se proporciona)
        if (!imagen.isEmpty()) {
            String rutaArchivo = this.ruta + "archivos/" + nombre + "/";
            String nombreArchivo = archivosUpload.guardarArchivo(imagen, rutaArchivo);
            producto.setImagen(nombreArchivo);
        }

        // Actualizar los datos del producto
        producto.setCantidad(cantidad);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCategoria(cate);

        // Guardar cambios
        productoServicio.editarProducto(producto);

        // Flash Attributes para feedback
        flash.addFlashAttribute("class", "success");
        flash.addFlashAttribute("mensaje", "Producto editado exitosamente.");
        return "redirect:/api/productos/editarProducto/" + producto.getId();

    } catch (Exception e) {
        // Manejo de excepciones
        e.printStackTrace(); // Opcional: usa un logger
        flash.addFlashAttribute("class", "error");
        flash.addFlashAttribute("mensaje", "Error al editar: " + e.getMessage());
        return "redirect:/api/productos/editarProducto/" + id;
    }
}

    
    
    
    @PostMapping("/registrar")
    public String crearProducto(@RequestBody @ModelAttribute Producto producto, @RequestParam("imagenes") MultipartFile imagen,
            RedirectAttributes flash) {
        try {
            if (imagen == null || imagen.isEmpty()) {
                flash.addFlashAttribute("class", "danger");
                flash.addFlashAttribute("mensaje", "No se ha cargado ningún archivo");
                return "redirect:/api/productos/registrar";
            }
            
            // Crear el objeto Producto
            
           
           

            // Guardar la imagen
            String rutaArchivo = this.ruta + "archivos/" + producto.getNombre() + "/";
            String nombreArchivo = archivosUpload.guardarArchivo(imagen, rutaArchivo);

            if ("no".equals(nombreArchivo)) {
                flash.addFlashAttribute("clase", "danger");
                flash.addFlashAttribute("mensaje", "El formato ingresado no es válido. Solo imágenes (PNG, JPEG, JPG)");
                return "redirect:/api/productos/registrar";
            }

            producto.setImagen(nombreArchivo);

            // Guardar el producto
            productoServicio.crearProducto(producto);

            flash.addFlashAttribute("clase", "success");
            flash.addFlashAttribute("mensaje", "Éxito al registrar producto");
            return "redirect:/api/productos/registrar";

        } catch (Exception e) {
            e.printStackTrace();
            flash.addFlashAttribute("clase", "error");
            flash.addFlashAttribute("mensaje", "Error al registrar el producto: " + e.getMessage());
            return "redirect:/api/productos/registrar";
        }
    }

}
