/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package licorera.licorera.servicios;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import licorera.licorera.entidades.Producto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class ExcelServicio {

    public ByteArrayInputStream exportarDatosExcel(List<Producto> productos){
        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Productos");
            
            String[] header = {"Producto", "Categoria", "Cantidad", "Precio"};
            Row headerRow = sheet.createRow(0);
            
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setAlignment(HorizontalAlignment.CENTER);
                cell.setCellStyle(headerStyle);
            }
            
            int rowIdx = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(producto.getNombre());
                row.createCell(1).setCellValue(producto.getCategoria().getNombre());
                row.createCell(2).setCellValue(producto.getCantidad());
                row.createCell(3).setCellValue(producto.getPrecio());
                
                
            }
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
