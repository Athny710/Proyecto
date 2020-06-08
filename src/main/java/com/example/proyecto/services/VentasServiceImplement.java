package com.example.proyecto.services;


import com.example.proyecto.entity.Venta;
import com.example.proyecto.repository.VentaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.boot.jaxb.hbm.spi.Adapter4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
@Transactional
public class VentasServiceImplement implements VentasService {
    @Autowired
    VentaRepository ventaRepository;

    @Override
    public List<Venta> getVentas() {
        return (List<Venta>) ventaRepository.findAll();
    }

    @Override
    public boolean createPDF(List<Venta> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        Document document = new Document(PageSize.A4,15,15,45,30);
        try {
            String filepath = context.getRealPath("/resources/reports");
            File file = new File(filepath);
            boolean exists = new File(filepath).exists();
            if(!exists){
                new File(filepath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"ventas"+".pdf"));
            document.open();

            Font mainfont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Lista de ventas",mainfont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableheader = FontFactory.getFont("Arial",10,BaseColor.BLACK);
            Font tablebody = FontFactory.getFont("Arial",9,BaseColor.BLACK);

            float[] columnWidths = {2f,2f,2f,2f};
            table.setWidths(columnWidths);
            PdfPCell firstname = new PdfPCell(new Paragraph("Nombre del cliente",tableheader));
            firstname.setBorderColor(BaseColor.BLACK);
            firstname.setPaddingLeft(10);
            firstname.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstname.setVerticalAlignment(Element.ALIGN_CENTER);
            firstname.setBackgroundColor(BaseColor.GRAY);
            firstname.setExtraParagraphSpace(5f);
            table.addCell(firstname);

            PdfPCell dni = new PdfPCell(new Paragraph("Número de DNI",tableheader));
            dni.setBorderColor(BaseColor.BLACK);
            dni.setPaddingLeft(10);
            dni.setHorizontalAlignment(Element.ALIGN_CENTER);
            dni.setVerticalAlignment(Element.ALIGN_CENTER);
            dni.setBackgroundColor(BaseColor.GRAY);
            dni.setExtraParagraphSpace(5f);
            table.addCell(dni);

            PdfPCell precio = new PdfPCell(new Paragraph("Precio de venta",tableheader));
            precio.setBorderColor(BaseColor.BLACK);
            precio.setPaddingLeft(10);
            precio.setHorizontalAlignment(Element.ALIGN_CENTER);
            precio.setVerticalAlignment(Element.ALIGN_CENTER);
            precio.setBackgroundColor(BaseColor.GRAY);
            precio.setExtraParagraphSpace(5f);
            table.addCell(precio);

            PdfPCell cantidad = new PdfPCell(new Paragraph("Cantidad adquirida",tableheader));
            cantidad.setBorderColor(BaseColor.BLACK);
            cantidad.setPaddingLeft(10);
            cantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            cantidad.setVerticalAlignment(Element.ALIGN_CENTER);
            cantidad.setBackgroundColor(BaseColor.GRAY);
            cantidad.setExtraParagraphSpace(5f);
            table.addCell(cantidad);

            for (Venta venta1 : venta){
                PdfPCell firstnamevalue = new PdfPCell(new Paragraph(venta1.getNombrecliente(),tablebody));
                firstnamevalue.setBorderColor(BaseColor.BLACK);
                firstnamevalue.setPaddingLeft(10);
                firstnamevalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                firstnamevalue.setVerticalAlignment(Element.ALIGN_CENTER);
                firstnamevalue.setBackgroundColor(BaseColor.WHITE);
                firstnamevalue.setExtraParagraphSpace(5f);
                table.addCell(firstnamevalue);

                PdfPCell dnivalue = new PdfPCell(new Paragraph(venta1.getNumerodocumentoidentidad(),tablebody));
                dnivalue.setBorderColor(BaseColor.BLACK);
                dnivalue.setPaddingLeft(10);
                dnivalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                dnivalue.setVerticalAlignment(Element.ALIGN_CENTER);
                dnivalue.setBackgroundColor(BaseColor.WHITE);
                dnivalue.setExtraParagraphSpace(5f);
                table.addCell(dnivalue);

                String prec = venta1.getPreciounitarioventa() + "";
                PdfPCell preciovalue = new PdfPCell(new Paragraph(prec,tablebody));
                preciovalue.setBorderColor(BaseColor.BLACK);
                preciovalue.setPaddingLeft(10);
                preciovalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                preciovalue.setVerticalAlignment(Element.ALIGN_CENTER);
                preciovalue.setBackgroundColor(BaseColor.WHITE);
                preciovalue.setExtraParagraphSpace(5f);
                table.addCell(preciovalue);

                String canti = venta1.getCantidad() + "";
                PdfPCell cantidadvalue = new PdfPCell(new Paragraph(canti,tablebody));
                cantidadvalue.setBorderColor(BaseColor.BLACK);
                cantidadvalue.setPaddingLeft(10);
                cantidadvalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                cantidadvalue.setVerticalAlignment(Element.ALIGN_CENTER);
                cantidadvalue.setBackgroundColor(BaseColor.WHITE);
                cantidadvalue.setExtraParagraphSpace(5f);
                table.addCell(cantidadvalue);
            }
            document.add(table);
            document.close();
            writer.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean createExcel(List<Venta> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        String filepath = context.getRealPath("/resources/reports");
        File file = new File(filepath);
        boolean exists = new File(filepath).exists();
        if(!exists){
            new File(filepath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "ventas" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Ventas");
            workSheet.setDefaultColumnWidth(30);
            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell firstname = headerRow.createCell(0);
            firstname.setCellValue("Nombre del cliente");
            firstname.setCellStyle(headerCellStyle);

            HSSFCell dni = headerRow.createCell(1);
            dni.setCellValue("Número de DNI");
            dni.setCellStyle(headerCellStyle);

            HSSFCell precio = headerRow.createCell(2);
            precio.setCellValue("Precio unitario");
            precio.setCellStyle(headerCellStyle);

            HSSFCell cantid = headerRow.createCell(3);
            cantid.setCellValue("Cantidad");
            cantid.setCellStyle(headerCellStyle);

            int i = 1;
            for (Venta venta1 : venta){
                HSSFRow bodyROW = workSheet.createRow(i);
                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell firstnamevalue = bodyROW.createCell(0);
                firstnamevalue.setCellValue(venta1.getNombrecliente());
                firstnamevalue.setCellStyle(bodyCellStyle);

                HSSFCell dnivalue = bodyROW.createCell(1);
                dnivalue.setCellValue(venta1.getNumerodocumentoidentidad());
                dnivalue.setCellStyle(bodyCellStyle);

                HSSFCell precvalue = bodyROW.createCell(2);
                precvalue.setCellValue(venta1.getPreciounitarioventa());
                precvalue.setCellStyle(bodyCellStyle);

                HSSFCell cantvalue = bodyROW.createCell(3);
                cantvalue.setCellValue(venta1.getCantidad());
                cantvalue.setCellStyle(bodyCellStyle);

                i++;
            }
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }
}
