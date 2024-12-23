/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Entity
@Getter
@Setter
public class Ordenes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private Integer id;
    private String nombre;
    @OneToOne
    private Carrito carrito;
    private String contacto;
    private String direccion;
    private String detalles;

    public Ordenes() {
    }

    public Ordenes(Integer id, String nombre, Carrito carrito, String contacto, String direccion, String detalles) {
        this.id = id;
        this.nombre = nombre;
        this.carrito = carrito;
        this.contacto = contacto;
        this.direccion = direccion;
        this.detalles = detalles;
    }
    
    
    
    
    
    
}
