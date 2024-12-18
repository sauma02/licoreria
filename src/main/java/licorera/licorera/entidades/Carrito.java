/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.entidades;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Admin
 */
@Getter
@Setter
public class Carrito {
    private String productoId;
    private String nombre;
    private Double precio;
    private Integer cantidad;

    public Double getTotal() {
        return this.precio * this.cantidad;
    }


}
