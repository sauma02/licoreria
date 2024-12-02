/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.controladores;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
            List<Producto> otros = productoServicio.listarPorCategoriaOtros();
            response.put("otros", otros);
            List<Producto> alcoholicos = productoServicio.listarPorCategoriaAlcholicas();
             response.put("alcholicos", alcoholicos);
            List<Producto> noAlcoholicos = productoServicio.listarPorCategoriaNoAlcoholicas();
             response.put("noAlcoholicos", noAlcoholicos);
            }
            
             return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
           e.getMessage();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "error al traer los productos"));
                             
        }
        
    }
    
    
    
}
