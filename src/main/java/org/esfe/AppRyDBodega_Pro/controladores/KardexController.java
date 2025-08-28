package org.esfe.AppRyDBodega_Pro.controladores;

import org.esfe.AppRyDBodega_Pro.dto.KardexDTO;
import org.esfe.AppRyDBodega_Pro.servicios.interfaces.IKardexService;
import org.esfe.AppRyDBodega_Pro.servicios.utilerias.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/kardex")
public class KardexController {

    @Autowired
    private IKardexService kardexService;

    /**
     * Mostrar el kardex en la vista HTML (tabla Thymeleaf)
     */
    @GetMapping
    public String verKardex(Model model) {
        List<KardexDTO> listaKardex = kardexService.obtenerKardex();
        if (listaKardex == null) {
            listaKardex = new ArrayList<>();
        }
        model.addAttribute("listaKardex", listaKardex);
        return "kardex/index";

    }

    /**
     * Generar PDF del kardex
     * Se puede ver inline en el navegador o forzar descarga cambiando Content-Disposition
     */
    @GetMapping("/pdf")
    public void generarKardexPDF(HttpServletResponse response) {
        try {
            // Tipo de contenido PDF
            response.setContentType("application/pdf");

            // Inline para ver en navegador / attachment para descarga directa
            response.setHeader("Content-Disposition", "inline; filename=kardex.pdf");

            response.setHeader("Content-Disposition", "attachment; filename=kardex.pdf");

            // Obtener datos del kardex
            List<KardexDTO> listaKardex = kardexService.obtenerKardex();

            // Generar PDF con la clase utilitaria
            PdfGenerator.generarKardexPDF(listaKardex, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //coso
    @GetMapping("/pdf/descargar")
    public void generarKardexPDFDescarga(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            // attachment = descarga
            response.setHeader("Content-Disposition", "attachment; filename=kardex.pdf");

            List<KardexDTO> listaKardex = kardexService.obtenerKardex();
            PdfGenerator.generarKardexPDF(listaKardex, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/pdf/vista")
    public void generarKardexPDFVista(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            // inline = vista previa
            response.setHeader("Content-Disposition", "inline; filename=kardex.pdf");

            List<KardexDTO> listaKardex = kardexService.obtenerKardex();
            PdfGenerator.generarKardexPDF(listaKardex, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
