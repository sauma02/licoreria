import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;


class listarProductos extends StatefulWidget{
  @override
  State<listarProductos> createState() => _listarProductosState();
}

class _listarProductosState extends State<listarProductos> {

  late List datos;

  Future<List> listaProductos() async {
    final response = await http.get("http://172.20.10.3:1010/listarProductos" as Uri);

    return json.decode(response.body);

  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    this.listaProductos();
  }


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container();
  }
}