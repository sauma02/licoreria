/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.servicios;

import java.util.List;
import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;
import licorera.licorera.repositorios.ProductoRepositorio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ProductoServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;
    
    public Producto crearProducto(Producto producto){
        productoRepositorio.save(producto);
        return producto;
    }
    public List<Producto> listarProductos(){
        return productoRepositorio.findAll();
    }
    public List<Producto> listarPorCategoria(Categoria categoria){
        
        switch (categoria.getNombre()) {
            case "Otros":
                List<Producto> otros = productoRepositorio.findAllByCategoria(categoria);
                return otros;
            case "Alcoholicos":
                List<Producto> alcoholicos = productoRepositorio.findAllByCategoria(categoria);
                return alcoholicos;
            case "NoAlcoholicos":
                List<Producto> noAlcoholicos = productoRepositorio.findAllByCategoria(categoria);
                return noAlcoholicos;
            default:
                throw new AssertionError();
        }
    }
    
}
