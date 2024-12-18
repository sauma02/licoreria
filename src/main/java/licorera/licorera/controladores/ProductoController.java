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
import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;
import licorera.licorera.servicios.CategoriaServicio;
import licorera.licorera.servicios.ProductoServicio;
import licorera.licorera.utilidades.archivosUpload;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Admin
 */
@Controller
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

    @PostMapping("/registrar")
    public String crearProducto(
            @RequestParam("categoria") String categoria,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("cantidad") Integer cantidad,
            @RequestParam("imagen") MultipartFile imagen,
            RedirectAttributes flash) {
        try {
            if (imagen == null || imagen.isEmpty()) {
                flash.addFlashAttribute("class", "danger");
                flash.addFlashAttribute("mensaje", "No se ha cargado ningún archivo");
                return "redirect:/api/productos/registrar";
            }
            
            // Crear el objeto Producto
            Producto producto = new Producto();
            producto.setCategoria(categoriaServicio.listarPorNombre(categoria));
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);

            // Guardar la imagen
            String rutaArchivo = this.ruta + "archivos/" + nombre + "/";
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
            flash.addFlashAttribute("clase", "danger");
            flash.addFlashAttribute("mensaje", "Error al registrar el producto: " + e.getMessage());
            return "redirect:/api/productos/registrar";
        }
    }

}
