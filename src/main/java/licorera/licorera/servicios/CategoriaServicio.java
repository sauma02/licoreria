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
import licorera.licorera.repositorios.CategoriaRepositorio;

/**
 * @author Admin
 */
@Service
public class CategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    public List<Categoria> listaCategorias() {
        return categoriaRepositorio.findAll();
    }
    
    public Categoria listarPorNombre(String categoria){
        Optional<Categoria> res = categoriaRepositorio.findFirstByNombre(categoria);
        if(res.isPresent()){
            Categoria cate = res.get();
            return cate;
        }else{
            return null;
        }
    }
}
