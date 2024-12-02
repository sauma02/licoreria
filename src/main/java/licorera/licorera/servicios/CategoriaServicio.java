/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.servicios;

import java.util.List;
import licorera.licorera.entidades.Categoria;
import licorera.licorera.repositorios.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class CategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    
    public List<Categoria> listaCategorias(){
        return categoriaRepositorio.findAll();
    }
}
