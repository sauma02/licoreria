import 'package:flutter/material.dart';
import 'package:licoya1/views/crearProducto.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'LicoYa',
      theme: ThemeData(
      
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const Inicio(),
        '/registrarProducto': (context) => crearProducto(),

      },
      
    );
  }
}

class Inicio extends StatelessWidget{
  const Inicio({super.key});

  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(title: const Text('Inicio'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: (){
            Navigator.pushNamed(context, '/registrarProducto');
        },
        child: const Text('Registrar producto'),
        ),
      ),
    );

  }
}



  