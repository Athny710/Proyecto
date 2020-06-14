package com.example.proyecto.services;


import com.example.proyecto.dto.ReporteConCamposOriginales;
import com.example.proyecto.dto.VentaPorCodigo;
import com.example.proyecto.entity.Venta;
import com.example.proyecto.repository.VentaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
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
    public List<VentaPorCodigo> getVentasPorCodigo(String codigo) { return (List<VentaPorCodigo>) ventaRepository.listarVentasXCodigo(codigo); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorCliente(String mes, String año, String cliente) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteMensualCliente(mes,año,cliente); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorClienteAnual(String año, String cliente) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteAnualCliente(año,cliente); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorClienteTrimestral(String año, String cliente) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteTrimestralCliente(año,cliente); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorSede(String mes, String año, String idsede) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteMensualSede(mes,año,idsede); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorSedeTrimestral(String año, String idsede) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteTrimestralSede(año,idsede); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorSedeAnual(String año, String idsede) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteAnualSede(año,idsede); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorArticuloAnual(String año, String articulo) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteAnualArticulo(año,articulo); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorArticuloTrimestral(String año, String articulo) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteTrimestralArticulo(año,articulo); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorArticuloMensual(String mes, String año, String articulo) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteMensualArticulo(mes,año,articulo); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorComunidadAnual(String año, String comunidad) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteAnualComunidad(año,comunidad); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorComunidadTrimestral(String año, String comunidad) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteTrimestralComunidad(año,comunidad); }

    @Override
    public List<ReporteConCamposOriginales> getVentasPorComunidadMensual(String mes, String año, String comunidad) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteMensualComunidad(mes,año,comunidad); }

    @Override
    public List<ReporteConCamposOriginales> getVentaAnual(String año) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteAnualTotal(año); }

    @Override
    public List<ReporteConCamposOriginales> getVentaTrimestral(String año) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteTrimestralTotal(año); }

    @Override
    public List<ReporteConCamposOriginales> getVentaMensual(String mes, String año) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteMensualTotal(mes,año); }


    public String traductorDeMeses(String mesEnEnglish){
        String mesEspanish = null;
        if (mesEnEnglish.equals("January")){
            mesEspanish = "enero";
        }else if (mesEnEnglish.equals("February")){
            mesEspanish = "febrero";
        }else if (mesEnEnglish.equals("March")){
            mesEspanish = "marzo";
        }else if (mesEnEnglish.equals("April")){
            mesEspanish = "abril";
        }else if (mesEnEnglish.equals("May")){
            mesEspanish = "mayo";
        }else if (mesEnEnglish.equals("June")){
            mesEspanish = "junio";
        }else if (mesEnEnglish.equals("July")){
            mesEspanish = "julio";
        }else if (mesEnEnglish.equals("August")){
            mesEspanish = "agosto";
        }else if (mesEnEnglish.equals("September")){
            mesEspanish = "setiembre";
        }else if (mesEnEnglish.equals("October")){
            mesEspanish = "octubre";
        }else if (mesEnEnglish.equals("November")){
            mesEspanish = "noviembre";
        }else if (mesEnEnglish.equals("December")){
            mesEspanish = "diciembre";
        }
        return mesEspanish;
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

    @Override
    public boolean createExcelXCodigo(List<VentaPorCodigo> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        String filepath = context.getRealPath("/resources/reports");
        File file = new File(filepath);
        boolean exists = new File(filepath).exists();
        if(!exists){
            new File(filepath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "ventas_por_codigo" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Ventas totales por codigo de producto");
            workSheet.setDefaultColumnWidth(30);
            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFRow headerRow = workSheet.createRow(0);

            HSSFCell dnioruc = headerRow.createCell(0);
            dnioruc.setCellValue("DNI o RUC");
            dnioruc.setCellStyle(headerCellStyle);

            HSSFCell cliente = headerRow.createCell(1);
            cliente.setCellValue("Nombre del cliente");
            cliente.setCellStyle(headerCellStyle);

            HSSFCell lugarventa = headerRow.createCell(2);
            lugarventa.setCellValue("Lugar de venta");
            lugarventa.setCellStyle(headerCellStyle);

            HSSFCell fecha = headerRow.createCell(3);
            fecha.setCellValue("Fecha");
            fecha.setCellStyle(headerCellStyle);

            int i = 1;
            for (VentaPorCodigo venta1 : venta){
                HSSFRow bodyROW = workSheet.createRow(i);
                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);

                HSSFCell dniorucvalue = bodyROW.createCell(0);
                dniorucvalue.setCellValue(venta1.getDnioruc());
                dniorucvalue.setCellStyle(bodyCellStyle);

                HSSFCell clientevalue = bodyROW.createCell(1);
                clientevalue.setCellValue(venta1.getCliente());
                clientevalue.setCellStyle(bodyCellStyle);

                HSSFCell lugarvalue = bodyROW.createCell(2);
                lugarvalue.setCellValue(venta1.getLugar());
                lugarvalue.setCellStyle(bodyCellStyle);

                HSSFCell fechavalue = bodyROW.createCell(3);
                fechavalue.setCellValue(venta1.getCodgen());
                fechavalue.setCellStyle(bodyCellStyle);

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

    @Override
    public boolean createExcelXCliente(List<ReporteConCamposOriginales> ventaXCliente, String cliente, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        String filepath = context.getRealPath("/resources/reports");
        File file = new File(filepath);
        boolean exists = new File(filepath).exists();
        if(!exists){
            new File(filepath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "ventas_por_cliente" + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet workSheet = workbook.createSheet("Ventas Mosqoy");
            workSheet.setDefaultColumnWidth(30);
            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            HSSFCellStyle headerCellStyle1 = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
            headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

            headerCellStyle1.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            headerCellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerCellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont cellFont = workbook.createFont();
            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerCellStyle1.setFont(cellFont);
            workSheet.addMergedRegion(new CellRangeAddress(0,0,0,5));

            HSSFRow headerRow = workSheet.createRow(1);

            HSSFCell codigo = headerRow.createCell(0);
            codigo.setCellValue("Código del producto");
            codigo.setCellStyle(headerCellStyle);

            HSSFCell nombreProducto = headerRow.createCell(1);
            nombreProducto.setCellValue("Nombre del producto");
            nombreProducto.setCellStyle(headerCellStyle);

            HSSFCell cantidad = headerRow.createCell(2);
            cantidad.setCellValue("Cantidad");
            cantidad.setCellStyle(headerCellStyle);

            HSSFCell precioUnit = headerRow.createCell(3);
            precioUnit.setCellValue("Precio unitario");
            precioUnit.setCellStyle(headerCellStyle);

            HSSFCell fecha = headerRow.createCell(4);
            fecha.setCellValue("Fecha");
            fecha.setCellStyle(headerCellStyle);

            HSSFCell totalxProducto = headerRow.createCell(5);
            totalxProducto.setCellValue("Precio total por producto");
            totalxProducto.setCellStyle(headerCellStyle);

            HSSFRow tituRow1 = workSheet.createRow(0);
            HSSFCell titulo = tituRow1.createCell(0);
            titulo.setCellValue(cliente);
            titulo.setCellStyle(headerCellStyle1);

            int i = 2;
            float totalTotal = 0;
            for (ReporteConCamposOriginales venta1 : ventaXCliente){
                HSSFRow bodyROW = workSheet.createRow(i);
                HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
                bodyCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
                bodyCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                bodyCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
                bodyCellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
                bodyCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
                bodyCellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

                HSSFCell codigovalue = bodyROW.createCell(0);
                codigovalue.setCellValue(venta1.getCodgen());
                codigovalue.setCellStyle(bodyCellStyle);

                HSSFCell nombreProductvalue = bodyROW.createCell(1);
                nombreProductvalue.setCellValue(venta1.getNombreproduct());
                nombreProductvalue.setCellStyle(bodyCellStyle);

                HSSFCell cantidadValue = bodyROW.createCell(2);
                cantidadValue.setCellValue(venta1.getCantidad());
                cantidadValue.setCellStyle(bodyCellStyle);

                HSSFCell precioUnitvalue = bodyROW.createCell(3);
                precioUnitvalue.setCellValue(venta1.getPreciounit());
                precioUnitvalue.setCellStyle(bodyCellStyle);

                HSSFCell fechavalue = bodyROW.createCell(4);
                fechavalue.setCellValue(traductorDeMeses(venta1.getFech()));
                fechavalue.setCellStyle(bodyCellStyle);

                HSSFCell totalxProductovalue = bodyROW.createCell(5);
                totalxProductovalue.setCellValue(venta1.getTotalxproduct());
                totalxProductovalue.setCellStyle(bodyCellStyle);

                totalTotal += venta1.getTotalxproduct();
                i++;
            }

            HSSFRow bodyROW = workSheet.createRow(i);
            HSSFCellStyle bodyCellStyle = workbook.createCellStyle();
            bodyCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            //bodyCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            bodyCellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            bodyCellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            bodyCellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            bodyCellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

            HSSFCell totalxtotal = bodyROW.createCell(4);
            totalxtotal.setCellValue("Total");
            totalxtotal.setCellStyle(bodyCellStyle);

            HSSFCell totalxtotalvalue = bodyROW.createCell(5);
            totalxtotalvalue.setCellValue(totalTotal);
            totalxtotalvalue.setCellStyle(bodyCellStyle);

            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean createExcelXSede(List<ReporteConCamposOriginales> ventaXSedeAnual, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        return false;
    }
}
