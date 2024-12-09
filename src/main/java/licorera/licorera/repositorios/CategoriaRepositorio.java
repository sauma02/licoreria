/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package licorera.licorera.repositorios;

import java.util.Optional;
import licorera.licorera.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, String> {
    public Optional<Categoria> findFirstByNombre(String name);
}
