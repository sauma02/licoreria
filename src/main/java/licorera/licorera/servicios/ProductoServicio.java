/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;
import licorera.licorera.repositorios.ProductoRepositorio;

/**
 * @author Admin
 */
@Service
public class ProductoServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    public Producto crearProducto(Producto producto) {
        productoRepositorio.save(producto);
        return producto;
    }

    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    public List<Producto> listarPorCategoria(Categoria categoria) {

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
    
    public Producto listarPorId(String id){
        Optional<Producto> res = productoRepositorio.findById(id);
        if(res.isPresent()){
            Producto pro = res.get();
            return pro;
        }else{
            return null;
        }
    }
    
    public Producto editarProducto(Producto producto){
       Optional<Producto> res = productoRepositorio.findById(producto.getId());
       if(res.isPresent()){
           Producto sol = res.get();
           sol.setCantidad(producto.getCantidad());
           sol.setCategoria(producto.getCategoria());
           sol.setImagen(producto.getImagen());
           sol.setNombre(producto.getNombre());
           sol.setPrecio(producto.getPrecio());
           productoRepositorio.save(sol);
           return sol;
       }else{
           return null;
       }
    }
    public void eliminarProducto(Producto producto){
        productoRepositorio.delete(producto);
    }

}
