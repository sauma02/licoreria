import 'package:http/http.dart';
import 'dart:convert';

Producto productoModeloJson(String srt) => 
            Producto.fromJson(json.decode(srt));

String productoModeloToJson(Producto data) => json.encode(data.toJson());
class Producto{
  
  String nombre;
  int cantidad;
  String categoria;
 
  double precio;


  Producto({required this.nombre, required this.categoria, required this.precio, 
  required this.cantidad});


  factory Producto.fromJson(Map<String, dynamic> json) => Producto(
    nombre: json["nombre"], 
    categoria: json["categoria"], 
    precio: json["precio"], 
    cantidad: json["cantidad"] 
    );

  Map<String, dynamic> toJson() =>{
    "nombre" : nombre,
    "categoria" : categoria,
    "precio" : precio,
    "cantidad" : cantidad,
    
  }; 

  String get getNombre => nombre;
  int get getCantidad => cantidad;
  String get getCategoria => categoria;
 
  double get getPrecio => precio;



}