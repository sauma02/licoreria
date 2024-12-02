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
    public List<Producto> listarPorCategoriaOtros(Categoria categoria){
        categoria.setNombre("Otros");
        List<Producto> otros = productoRepositorio.findAllByCategoria(categoria);
        return otros;
    }
    public List<Producto> listarPorCategoriaAlcholicas(Categoria categoria){
        categoria.setNombre("Alcoholicas");
        List<Producto> alcoholicas = productoRepositorio.findAllByCategoria(categoria);
        return alcoholicas;
        
    }
    public List<Producto> listarPorCategoriaNoAlcoholicas(Categoria categoria){
        categoria.setNombre("NoAlcoholicas");
        List<Producto> noAlcoholicas = productoRepositorio.findAllByCategoria(categoria);
        return noAlcoholicas;
    }
}
