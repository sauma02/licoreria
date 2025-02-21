/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.controladores;

import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import licorera.licorera.servicios.ExcelServicio;
import licorera.licorera.servicios.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Autowired
    private ExcelServicio excelServicio;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/listaProductos")
    public String listaProductos(Model model) {
        List<Producto> listaProductos = productoServicio.listarProductos();
        model.addAttribute("listaProductos", listaProductos);
        return "listaAdmin";
    }

    @GetMapping("/categorias")
    public List<Categoria> obtenerCategorias() {
        return categoriaServicio.listaCategorias();
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        List<Categoria> categorias = categoriaServicio.listaCategorias();
        List<Producto> listaProductos = productoServicio.listarProductos();

        // Obtener el carrito de la sesión o inicializarlo si no existe
        List<Carrito> carrito = (List<Carrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        List<Producto> noAlcohol = new ArrayList<>();
        List<Producto> alcohol = new ArrayList<>();
        List<Producto> otros = new ArrayList<>();

        for (Producto producto : listaProductos) {
            for (Categoria cat : categorias) {
                switch (cat.getId()) {
                    case "2":
                        if (producto.getCategoria().getId().equals(cat.getId())) {
                            alcohol.add(producto);
                        }
                        break;
                    case "3":
                        if (producto.getCategoria().getId().equals(cat.getId())) {
                            noAlcohol.add(producto);
                        }
                        break;
                    case "4":
                        if (producto.getCategoria().getId().equals(cat.getId())) {
                            otros.add(producto);
                        }
                        break;
                    default:
                        System.out.println("No hay productos registrados");
                }
            }
        }
        model.addAttribute("alcohol", alcohol);
        model.addAttribute("noAlcohol", noAlcohol);
        model.addAttribute("otros", otros);
        model.addAttribute("productosCategorizados", categorias);
        model.addAttribute("productos", listaProductos);
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", calcularTotal(carrito));
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/añadirProducto/{id}/{cantidad}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> añadirProducto(@PathVariable("id") String id, @PathVariable("cantidad") Integer cantidad, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Obtener carrito de la sesión
            List<Carrito> carrito = (List<Carrito>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
                session.setAttribute("carrito", carrito);
            }

            // Buscar producto y añadirlo al carrito
            Producto pro = productoServicio.listarPorId(id);
            Carrito carro = new Carrito();
            carro.setProductoId(pro.getId());
            carro.setNombre(pro.getNombre());
            carro.setCantidad(cantidad);
            carro.setPrecio(pro.getPrecio() * cantidad);
            carrito.add(carro);

            session.setAttribute("carrito", carrito); // Actualizar el carrito en la sesión

            double total = carrito.stream()
                    .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                    .sum();

            response.put("carrito", carrito);
            response.put("totalProductos", carrito.size());
            response.put("total", total);
            response.put("clase", "success");
            response.put("mensaje", "Producto añadido al carrito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al añadir producto", e);
            response.put("clase", "error");
            response.put("mensaje", "Ocurrió un error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/eliminarProducto/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable("id") String id, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Obtener carrito de la sesión
            List<Carrito> carrito = (List<Carrito>) session.getAttribute("carrito");
            if (carrito != null) {
                // Eliminar producto del carrito
                boolean eliminado = carrito.removeIf(carro -> carro.getProductoId().equals(id));
                if (eliminado) {
                    session.setAttribute("carrito", carrito); // Actualizar el carrito en la sesión
                    double total = carrito.stream()
                            .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                            .sum();

                    response.put("carrito", carrito);
                    response.put("totalProductos", carrito.size());
                    response.put("total", total);
                    response.put("clase", "success");
                    response.put("mensaje", "Producto eliminado del carrito");
                    return ResponseEntity.ok(response);
                }
            }
            response.put("clase", "error");
            response.put("mensaje", "Producto no encontrado en el carrito");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            logger.error("Error al eliminar producto", e);
            response.put("clase", "error");
            response.put("mensaje", "Ocurrió un error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private double calcularTotal(List<Carrito> carrito) {
        return carrito.stream().mapToDouble(item -> item.getPrecio() * item.getCantidad()).sum();
    }

    @GetMapping("/realizarCompra")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> realizarCompra(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Carrito> carrito = (List<Carrito>) session.getAttribute("carrito");
            if (carrito == null || carrito.isEmpty()) {
                response.put("success", false);
                response.put("mensaje", "El carrito esta vacio");
                return ResponseEntity.badRequest().body(response);
            }

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("[*] PEDIDO EN LICOYA [*]").append("\n\n");

            double total = 0;

            for (Carrito item : carrito) {
                mensaje.append("> Producto: ").append(item.getNombre()).append("\n")
                        .append("> Cantidad: ").append(item.getCantidad()).append("\n")
                        .append("> Precio unitario: $").append(String.format("%.2f", item.getPrecio())).append("\n")
                        .append("> Subtotal: $").append(String.format("%.2f", item.getPrecio() * item.getCantidad())).append("\n\n");
                total += item.getPrecio() * item.getCantidad();
            }

            mensaje.append("==== TOTAL: $").append(String.format("%.2f", total)).append(" ====").append("\n\n");
            mensaje.append("[>] Método de pago: Transferencia contraentrega / Efectivo contraentrega.");

            String numeroWp = "573242780208";
            String mensajeCodificado = URLEncoder.encode(mensaje.toString(), StandardCharsets.UTF_8);

            String url = "https://wa.me/+" + numeroWp + "?text=" + mensajeCodificado;
            System.out.println(url);

            response.put("success", true);
            response.put("mensaje", "Redirigiendo a WhatsApp...");
            response.put("url", url);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", e.getCause());
            response.put("mensaje", "Error al procesar la compra.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }
    @GetMapping("/productos")
    public ResponseEntity<InputStreamResource> descargarExcel() {
        List<Producto> listaProductos = productoServicio.listarProductos();
        for (Producto producto : listaProductos) {
            System.out.println(producto.getNombre());
        }

        ByteArrayInputStream bis = excelServicio.exportarDatosExcel(listaProductos);

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment; filename=productos.xlsx");
        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(bis));
    }
}
