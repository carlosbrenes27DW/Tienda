package com.tienda.tienda.repository;

import com.tienda.tienda.domain.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();
    
    //Consulta derivada que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(BigDecimal precioInf, BigDecimal precioSup);

    //Consulta JPQL que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(BigDecimal precioInf, BigDecimal precioSup);

    //Consulta SQL que recupera los producto de un rango de precio y los ordena por precio ascendentemente
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(BigDecimal precioInf, BigDecimal precioSup);

    //Práctica #2: Consulta ampliada (JPQL) que recupera los productos ACTIVOS
    //de un rango de precio, con existencias mayores o iguales a un mínimo y,
    //opcionalmente, de una categoría específica (si idCategoria es null trae todas).
    //Ordena por categoría y luego por precio descendentemente.
    @Query(value = "SELECT p FROM Producto p "
            + "WHERE p.activo = true "
            + "AND p.precio BETWEEN :precioInf AND :precioSup "
            + "AND p.existencias >= :existenciasMin "
            + "AND (:idCategoria IS NULL OR p.categoria.idCategoria = :idCategoria) "
            + "ORDER BY p.categoria.descripcion ASC, p.precio DESC")
    public List<Producto> consultaAmpliada(@Param("precioInf") BigDecimal precioInf,
            @Param("precioSup") BigDecimal precioSup,
            @Param("existenciasMin") Integer existenciasMin,
            @Param("idCategoria") Integer idCategoria);

}