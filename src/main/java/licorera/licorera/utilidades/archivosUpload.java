/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.utilidades;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Admin
 */
public class archivosUpload {

    public static String guardarArchivo(MultipartFile file, String ruta) {
        if (!archivosUpload.validarArchivo(file.getContentType())) {
            return "no";
        }
        String nombreOriginal = file.getOriginalFilename();
        String extension = archivosUpload.getExtension(file.getContentType());

        if (nombreOriginal != null && nombreOriginal.endsWith(extension)) {
            extension = "";
        }
        nombreOriginal = nombreOriginal.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); // Reemplazar caracteres especiales por guiones bajos

        // Crear el nombre final del archivo
        String nombre = nombreOriginal + extension.trim();

        ruta = ruta.trim();

        if (!ruta.endsWith(File.separator)) {
            ruta += File.separator;
        }
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        try {
            File imageFile = new File(ruta + nombre);
            file.transferTo(imageFile);
            return nombre;


        } catch (IOException e) {

            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static boolean validarArchivo(String mime) {
        boolean retorno = false;
        switch (mime) {
            case "image/jpg":
                retorno = true;

                break;
            case "image/jpeg":
                retorno = true;

                break;
            case "image/png":
                retorno = true;

                break;
            default:
                retorno = false;
                break;
        }
        return retorno;

    }

    public static String getExtension(String mime) {
        String retorno = "";
        switch (mime) {
            case "image/jpg":
                retorno = " .jpg";

                break;
            case "image/jpeg":
                retorno = " .jpeg";

                break;
            case "image/png":
                retorno = " .png";

                break;
            default:
                retorno = "Error, archivo no admitido";
                break;
        }
        return retorno;
    }
}
