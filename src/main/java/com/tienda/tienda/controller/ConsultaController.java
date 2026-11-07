package com.tienda.tienda.controller;

import com.tienda.tienda.domain.Categoria;
import com.tienda.tienda.service.CategoriaService;
import com.tienda.tienda.service.ProductoService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public ConsultaController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    //Práctica #2: se agrega la lista de categorías activas a todas las vistas
    //de este controlador, para llenar el <select> de la consulta ampliada.
    @ModelAttribute("categoriasConsulta")
    public List<Categoria> categoriasConsulta() {
        return categoriaService.getCategorias(true);
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "/consultas/listado";
    }

    @PostMapping("/consultaDerivada")
    public String consultaDerivida(@RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.consultaDerivada(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }

    @PostMapping("/consultaJPQL")
    public String consultaJPQL(@RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.consultaJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }

    @PostMapping("/consultaSQL")
    public String consultaSQL(@RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.consultaSQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }

    //Práctica #2: consulta ampliada por rango de precio, existencias mínimas y categoría
    @PostMapping("/consultaAmpliada")
    public String consultaAmpliada(@RequestParam() BigDecimal precioInfA,
            @RequestParam() BigDecimal precioSupA,
            @RequestParam(defaultValue = "0") Integer existenciasMin,
            @RequestParam(required = false) Integer idCategoria,
            Model model) {
        var productos = productoService.consultaAmpliada(precioInfA, precioSupA, existenciasMin, idCategoria);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInfA", precioInfA);
        model.addAttribute("precioSupA", precioSupA);
        model.addAttribute("existenciasMin", existenciasMin);
        model.addAttribute("idCategoria", idCategoria);
        model.addAttribute("totalAmpliada", productos.size());
        return "/consultas/listado";
    }
}