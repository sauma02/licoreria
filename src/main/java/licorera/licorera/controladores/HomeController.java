/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.controladores;

import java.util.ArrayList;
import java.util.List;
import licorera.licorera.entidades.Carrito;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    
    
    private List<Carrito> carrito = new ArrayList<>();
    private Double calcularTotal(){
        
        //los :: acceden directamente a la calse para poder buscar el metodo
        return carrito.stream().mapToDouble(Carrito::getTotal).sum();
    }
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", calcularTotal());
        return "home";
    }
    
    
}
