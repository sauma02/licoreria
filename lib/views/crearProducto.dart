import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'package:image_picker/image_picker.dart';
import 'package:mime/mime.dart';
import 'package:path/path.dart';
import 'package:flutter/foundation.dart';
import 'dart:io';

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
    ScaffoldMessenger.of(context as BuildContext).showSnackBar(const SnackBar(content: Text("Por favor, selecciona una imagen")));
    return;
  }
    if (categoria == null) {
  ScaffoldMessenger.of(this as BuildContext).showSnackBar( const SnackBar(content: Text("Por favor, selecciona una categoría")));
  return;
}
  
  

  //Prepara los datos para el form
  final uri = Uri.parse("http://172.20.10.3:1010/api/productos/registrar");
  var request = http.MultipartRequest('POST', uri);

  //
  request.fields['nombre'] = nombreController.text;
  request.fields['precio'] = precioController.text;
  request.fields['cantidad'] = cantidadController.text;
  request.fields['categoria'] = categoria!;


  var file = await http.MultipartFile.fromPath('imagen', imagen!.path,
        contentType: MediaType('image', lookupMimeType(imagen!.path)!));
  request.files.add(file);


  var response = await request.send();

  if(response.statusCode == 200){
    ScaffoldMessenger.of(this.context).showSnackBar(const SnackBar(content: Text("Producto registrado corretamente")));
  }else{
        ScaffoldMessenger.of(this.context ).showSnackBar(const SnackBar(content: Text("Error al registrar")));

  }
  

} 
Widget imagenPreview(){
  if(imagen == null) return SizedBox();
  if(kIsWeb){
    //Si se ejecuta en un navegador
    return Image.network(imagen!.path, height: 100, fit: BoxFit.cover);
  }else{

    return Image.file(File(imagen!.path), height: 100, fit: BoxFit.cover);
  }
}


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Registrar Producto"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [ 
            TextField(
              controller: nombreController,
              decoration: const InputDecoration(labelText: "Nombre del producto"),
            ),
            TextField(
              controller: precioController,
              decoration:const InputDecoration(labelText: "Precio"),
              keyboardType: TextInputType.number,
            ),
            TextField(
              controller: cantidadController,
              decoration: const InputDecoration(labelText: "Cantidad"),
              keyboardType: TextInputType.number,
            ),
            DropdownButton<String>(
              hint: const Text("Seleccione una categoria"),
              value: categoria,
              onChanged: (newValue){
                setState(() {
                  categoria = newValue;
                });
              },
              items: ['Categoria 1', 'Categoria 2', 'Categoria 3'].map((categoria){
                return DropdownMenuItem(
                  value: categoria,
                  child: Text(categoria),
                );
              }).toList(),

            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: seleccionarImagen,
              child: const  Text("Seleccionar imagen"),
            ),
            imagenPreview(),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: enviarForm,
              child: const  Text("Registrar Producto")
              ),
            
          ],
          )
      
      ),
    );
  }
}