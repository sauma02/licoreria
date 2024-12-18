/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import licorera.licorera.entidades.Categoria;
import licorera.licorera.entidades.Producto;

/**
 * @author Admin
 */
@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, String> {
    public List<Producto> findAllByCategoria(Categoria categoria);
}
