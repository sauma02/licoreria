import 'package:flutter/material.dart';

import 'package:image_picker/image_picker.dart';

class crearProducto extends StatefulWidget{
  @override
  State<crearProducto> createState() => _crearProductoState();
}

class _crearProductoState extends State<crearProducto> {
  final ImagePicker picker = ImagePicker();
  XFile? imagen;

  TextEditingController nombreController = TextEditingController();
  TextEditingController precioController = TextEditingController();
  TextEditingController cantidadController = TextEditingController();
  String? categoria;

//Metodo para seleccionar una imagen
//Se crea un future para decir que sera una accion que tomara tiempo, se hace de manera 
//asincorna para que no consuma muchos recursos, con el imagepicker se le indica
//que seleccionara una imagen de la galeria, despues con el setState() se le indica al programa
//que la pagina cambiara de estado

  Future<void> seleccionarImagen() async {
    final imagenSelec = await picker.pickImage(source: ImageSource.gallery);
    setState(() {
      imagen = imagenSelec;
    });
  }

//Metodo para enviar informacion al backend en springboot
Future<void> enviarForm()async {
  if(imagen == null){
    //Si no hay imagen seleccionada de le dice al programa que le indique
    //al usuario que seleccione una imagen antes de continuar;
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Por favor, selecciona una imagen")));
    return;
  }


  final uri = Uri.parse("http://localhost:1010/api/productos/registrar");
}


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    throw UnimplementedError();
  }
}