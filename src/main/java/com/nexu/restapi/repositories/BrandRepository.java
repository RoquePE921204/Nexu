package com.nexu.restapi.repositories;

import com.nexu.restapi.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(value = "SELECT b.id AS id, b.nombre AS nombre, COALESCE(AVG(m.average_price), 0.0) AS average_price " +
            "FROM brand b LEFT JOIN model m ON b.id = m.brand_id " +
            "GROUP BY b.id, b.nombre", nativeQuery = true)
    List<Brand> findAllBrandsWithAveragePrice();

    @Query("SELECT b FROM Brand b WHERE b.nombre = :nombre")
    Brand findByName(@Param("nombre") String nombre);
}
