package com.example.proyecto.services;


import com.example.proyecto.dto.CamposReporteSede;
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
    public List<CamposReporteSede> getVentas(int id) {
        return (List<CamposReporteSede>) ventaRepository.obtenerDatosParaReporteSede(id);
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

    @Override
    public List<ReporteConCamposOriginales> getReporteSede(Integer idsede) { return (List<ReporteConCamposOriginales>) ventaRepository.reporteSede(idsede); }


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
        }else{
            mesEspanish = mesEnEnglish;
        }
        return mesEspanish;
    }

    @Override
    public boolean createPDF(List<CamposReporteSede> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
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

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableheader = FontFactory.getFont("Arial",10,BaseColor.BLACK);
            Font tablebody = FontFactory.getFont("Arial",9,BaseColor.BLACK);

            float[] columnWidths = {2f,2f,2f,2f,2f,2f};
            table.setWidths(columnWidths);

            PdfPCell nombretienda = new PdfPCell(new Paragraph("Tienda",tableheader));
            nombretienda.setBorderColor(BaseColor.BLACK);
            nombretienda.setPaddingLeft(10);
            nombretienda.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombretienda.setVerticalAlignment(Element.ALIGN_CENTER);
            nombretienda.setBackgroundColor(BaseColor.GRAY);
            nombretienda.setExtraParagraphSpace(5f);
            table.addCell(nombretienda);

            PdfPCell firstname = new PdfPCell(new Paragraph("Cliente",tableheader));
            firstname.setBorderColor(BaseColor.BLACK);
            firstname.setPaddingLeft(10);
            firstname.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstname.setVerticalAlignment(Element.ALIGN_CENTER);
            firstname.setBackgroundColor(BaseColor.GRAY);
            firstname.setExtraParagraphSpace(5f);
            table.addCell(firstname);

            PdfPCell dni = new PdfPCell(new Paragraph("Documento de venta",tableheader));
            dni.setBorderColor(BaseColor.BLACK);
            dni.setPaddingLeft(10);
            dni.setHorizontalAlignment(Element.ALIGN_CENTER);
            dni.setVerticalAlignment(Element.ALIGN_CENTER);
            dni.setBackgroundColor(BaseColor.GRAY);
            dni.setExtraParagraphSpace(5f);
            table.addCell(dni);

            PdfPCell precio = new PdfPCell(new Paragraph("Precio de unitario de venta",tableheader));
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

            PdfPCell prectot = new PdfPCell(new Paragraph("Precio total por producto",tableheader));
            prectot.setBorderColor(BaseColor.BLACK);
            prectot.setPaddingLeft(10);
            prectot.setHorizontalAlignment(Element.ALIGN_CENTER);
            prectot.setVerticalAlignment(Element.ALIGN_CENTER);
            prectot.setBackgroundColor(BaseColor.GRAY);
            prectot.setExtraParagraphSpace(5f);
            table.addCell(prectot);

            for (CamposReporteSede venta1 : venta){

                PdfPCell tiendanamevalue = new PdfPCell(new Paragraph(venta1.getNombretienda(),tablebody));
                tiendanamevalue.setBorderColor(BaseColor.BLACK);
                tiendanamevalue.setPaddingLeft(10);
                tiendanamevalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                tiendanamevalue.setVerticalAlignment(Element.ALIGN_CENTER);
                tiendanamevalue.setBackgroundColor(BaseColor.WHITE);
                tiendanamevalue.setExtraParagraphSpace(5f);
                table.addCell(tiendanamevalue);

                PdfPCell firstnamevalue = new PdfPCell(new Paragraph(venta1.getCliente(),tablebody));
                firstnamevalue.setBorderColor(BaseColor.BLACK);
                firstnamevalue.setPaddingLeft(10);
                firstnamevalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                firstnamevalue.setVerticalAlignment(Element.ALIGN_CENTER);
                firstnamevalue.setBackgroundColor(BaseColor.WHITE);
                firstnamevalue.setExtraParagraphSpace(5f);
                table.addCell(firstnamevalue);

                PdfPCell docvalue = new PdfPCell(new Paragraph(venta1.getDoc(),tablebody));
                docvalue.setBorderColor(BaseColor.BLACK);
                docvalue.setPaddingLeft(10);
                docvalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                docvalue.setVerticalAlignment(Element.ALIGN_CENTER);
                docvalue.setBackgroundColor(BaseColor.WHITE);
                docvalue.setExtraParagraphSpace(5f);
                table.addCell(docvalue);

                String prec = venta1.getPreciounit() + "";
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

                String preciotot = venta1.getPreciotot() + "";
                PdfPCell preciototvalue = new PdfPCell(new Paragraph(preciotot,tablebody));
                preciototvalue.setBorderColor(BaseColor.BLACK);
                preciototvalue.setPaddingLeft(10);
                preciototvalue.setHorizontalAlignment(Element.ALIGN_CENTER);
                preciototvalue.setVerticalAlignment(Element.ALIGN_CENTER);
                preciototvalue.setBackgroundColor(BaseColor.WHITE);
                preciototvalue.setExtraParagraphSpace(5f);
                table.addCell(preciototvalue);
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
    public boolean createExcelSede(List<ReporteConCamposOriginales> ventaXCliente, String cliente, String periodo, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        String filepath = context.getRealPath("/resources/reports");
        File file = new File(filepath);
        boolean exists = new File(filepath).exists();
        if(!exists){
            new File(filepath).mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file + "/" + "ventas_sede" + ".xls");
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

            headerCellStyle1.setFillForegroundColor(HSSFColor.WHITE.index);
            headerCellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerCellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont cellFont = workbook.createFont();
            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerCellStyle1.setFont(cellFont);
            workSheet.addMergedRegion(new CellRangeAddress(0,0,0,10));

            HSSFRow headerRow = workSheet.createRow(1);

            HSSFCell fechaventa = headerRow.createCell(0);
            fechaventa.setCellValue("Fecha de venta");
            fechaventa.setCellStyle(headerCellStyle);

            HSSFCell tienda = headerRow.createCell(1);
            tienda.setCellValue("Tienda");
            tienda.setCellStyle(headerCellStyle);

            HSSFCell tipodocumento = headerRow.createCell(2);
            tipodocumento.setCellValue("Tipo de documento (factura/boleta)");
            tipodocumento.setCellStyle(headerCellStyle);

            HSSFCell numdocventa = headerRow.createCell(3);
            numdocventa.setCellValue("N° de documento de venta");
            numdocventa.setCellStyle(headerCellStyle);

            HSSFCell rucdni = headerRow.createCell(4);
            rucdni.setCellValue("RUC/DNI del Cliente");
            rucdni.setCellStyle(headerCellStyle);

            HSSFCell nombreclient = headerRow.createCell(5);
            nombreclient.setCellValue("Nombre del Cliente");
            nombreclient.setCellStyle(headerCellStyle);

            HSSFCell cantidad = headerRow.createCell(6);
            cantidad.setCellValue("Cantidad");
            cantidad.setCellStyle(headerCellStyle);

            HSSFCell codigo = headerRow.createCell(7);
            codigo.setCellValue("Código del producto");
            codigo.setCellStyle(headerCellStyle);

            HSSFCell nombreProducto = headerRow.createCell(8);
            nombreProducto.setCellValue("Nombre del producto");
            nombreProducto.setCellStyle(headerCellStyle);

            HSSFCell color = headerRow.createCell(9);
            color.setCellValue("Color del producto");
            color.setCellStyle(headerCellStyle);

            HSSFCell precioUnit = headerRow.createCell(10);
            precioUnit.setCellValue("Precio unitario");
            precioUnit.setCellStyle(headerCellStyle);

            HSSFCell totalxProducto = headerRow.createCell(11);
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

                HSSFCell fechaventavalue = bodyROW.createCell(0);
                fechaventavalue.setCellValue(venta1.getFecha());
                fechaventavalue.setCellStyle(bodyCellStyle);

                HSSFCell tiendavalue = bodyROW.createCell(1);
                tiendavalue.setCellValue(venta1.getTienda());
                tiendavalue.setCellStyle(bodyCellStyle);

                HSSFCell tipodocumentovalue = bodyROW.createCell(2);
                tipodocumentovalue.setCellValue(venta1.getTipodocventa());
                tipodocumentovalue.setCellStyle(bodyCellStyle);

                HSSFCell numdocventavalue = bodyROW.createCell(3);
                numdocventavalue.setCellValue(venta1.getNumerodocventa());
                numdocventavalue.setCellStyle(bodyCellStyle);

                HSSFCell rucdnivalue = bodyROW.createCell(4);
                rucdnivalue.setCellValue(venta1.getNumerodocid());
                rucdnivalue.setCellStyle(bodyCellStyle);

                HSSFCell nombreclientvalue = bodyROW.createCell(5);
                nombreclientvalue.setCellValue(venta1.getNombrecliente());
                nombreclientvalue.setCellStyle(bodyCellStyle);

                HSSFCell cantidadValue = bodyROW.createCell(6);
                cantidadValue.setCellValue(venta1.getCantidad());
                cantidadValue.setCellStyle(bodyCellStyle);

                HSSFCell codigovalue = bodyROW.createCell(7);
                codigovalue.setCellValue(venta1.getCodprod());
                codigovalue.setCellStyle(bodyCellStyle);

                HSSFCell nombreProductvalue = bodyROW.createCell(8);
                nombreProductvalue.setCellValue(venta1.getNombreproduct());
                nombreProductvalue.setCellStyle(bodyCellStyle);

                HSSFCell colorvalue = bodyROW.createCell(9);
                colorvalue.setCellValue(venta1.getColorprod());
                colorvalue.setCellStyle(bodyCellStyle);

                HSSFCell precioUnitvalue = bodyROW.createCell(10);
                precioUnitvalue.setCellValue(venta1.getPreciounit());
                precioUnitvalue.setCellStyle(bodyCellStyle);

                HSSFCell totalxProductovalue = bodyROW.createCell(11);
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

            HSSFCell totalxtotal = bodyROW.createCell(10);
            totalxtotal.setCellValue("Total");
            totalxtotal.setCellStyle(bodyCellStyle);

            HSSFCell totalxtotalvalue = bodyROW.createCell(11);
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
    public boolean createExcelXCliente(List<ReporteConCamposOriginales> ventaXCliente, String cliente, String periodo, ServletContext context, HttpServletRequest request, HttpServletResponse response) {
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

            headerCellStyle1.setFillForegroundColor(HSSFColor.WHITE.index);
            headerCellStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerCellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont cellFont = workbook.createFont();
            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerCellStyle1.setFont(cellFont);
            workSheet.addMergedRegion(new CellRangeAddress(0,0,0,10));

            HSSFRow headerRow = workSheet.createRow(1);

            HSSFCell fechaventa = headerRow.createCell(0);
            fechaventa.setCellValue("Fecha de venta");
            fechaventa.setCellStyle(headerCellStyle);

            HSSFCell tipodocumento = headerRow.createCell(1);
            tipodocumento.setCellValue("Tipo de documento (factura/boleta)");
            tipodocumento.setCellStyle(headerCellStyle);

            HSSFCell numdocventa = headerRow.createCell(2);
            numdocventa.setCellValue("N° de documento de venta");
            numdocventa.setCellStyle(headerCellStyle);

            HSSFCell rucdni = headerRow.createCell(3);
            rucdni.setCellValue("RUC/DNI del Cliente");
            rucdni.setCellStyle(headerCellStyle);

            HSSFCell nombreclient = headerRow.createCell(4);
            nombreclient.setCellValue("Nombre del Cliente");
            nombreclient.setCellStyle(headerCellStyle);

            HSSFCell cantidad = headerRow.createCell(5);
            cantidad.setCellValue("Cantidad");
            cantidad.setCellStyle(headerCellStyle);

            HSSFCell codigo = headerRow.createCell(6);
            codigo.setCellValue("Código del producto");
            codigo.setCellStyle(headerCellStyle);

            HSSFCell nombreProducto = headerRow.createCell(7);
            nombreProducto.setCellValue("Nombre del producto");
            nombreProducto.setCellStyle(headerCellStyle);

            HSSFCell color = headerRow.createCell(8);
            color.setCellValue("Color del producto");
            color.setCellStyle(headerCellStyle);

            HSSFCell precioUnit = headerRow.createCell(9);
            precioUnit.setCellValue("Precio unitario");
            precioUnit.setCellStyle(headerCellStyle);

            HSSFCell totalxProducto = headerRow.createCell(10);
            totalxProducto.setCellValue("Precio total por producto");
            totalxProducto.setCellStyle(headerCellStyle);

            if (periodo.equals("trimestre")){
                HSSFCell period = headerRow.createCell(11);
                period.setCellValue("Trimestre");
                period.setCellStyle(headerCellStyle);
            }

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

                HSSFCell fechaventavalue = bodyROW.createCell(0);
                fechaventavalue.setCellValue(venta1.getFecha());
                fechaventavalue.setCellStyle(bodyCellStyle);

                HSSFCell tipodocumentovalue = bodyROW.createCell(1);
                tipodocumentovalue.setCellValue(venta1.getTipodocventa());
                tipodocumentovalue.setCellStyle(bodyCellStyle);

                HSSFCell numdocventavalue = bodyROW.createCell(2);
                numdocventavalue.setCellValue(venta1.getNumerodocventa());
                numdocventavalue.setCellStyle(bodyCellStyle);

                HSSFCell rucdnivalue = bodyROW.createCell(3);
                rucdnivalue.setCellValue(venta1.getNumerodocid());
                rucdnivalue.setCellStyle(bodyCellStyle);

                HSSFCell nombreclientvalue = bodyROW.createCell(4);
                nombreclientvalue.setCellValue(venta1.getNombrecliente());
                nombreclientvalue.setCellStyle(bodyCellStyle);

                HSSFCell cantidadValue = bodyROW.createCell(5);
                cantidadValue.setCellValue(venta1.getCantidad());
                cantidadValue.setCellStyle(bodyCellStyle);

                HSSFCell codigovalue = bodyROW.createCell(6);
                codigovalue.setCellValue(venta1.getCodprod());
                codigovalue.setCellStyle(bodyCellStyle);

                HSSFCell nombreProductvalue = bodyROW.createCell(7);
                nombreProductvalue.setCellValue(venta1.getNombreproduct());
                nombreProductvalue.setCellStyle(bodyCellStyle);

                HSSFCell colorvalue = bodyROW.createCell(8);
                colorvalue.setCellValue(venta1.getColorprod());
                colorvalue.setCellStyle(bodyCellStyle);

                HSSFCell precioUnitvalue = bodyROW.createCell(9);
                precioUnitvalue.setCellValue(venta1.getPreciounit());
                precioUnitvalue.setCellStyle(bodyCellStyle);

                HSSFCell totalxProductovalue = bodyROW.createCell(10);
                totalxProductovalue.setCellValue(venta1.getTotalxproduct());
                totalxProductovalue.setCellStyle(bodyCellStyle);

                if (periodo.equals("trimestre")){
                    HSSFCell fechavalue = bodyROW.createCell(11);
                    fechavalue.setCellValue(traductorDeMeses(venta1.getFech()));
                    fechavalue.setCellStyle(bodyCellStyle);
                }

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

            HSSFCell totalxtotal = bodyROW.createCell(9);
            totalxtotal.setCellValue("Total");
            totalxtotal.setCellStyle(bodyCellStyle);

            HSSFCell totalxtotalvalue = bodyROW.createCell(10);
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
}
