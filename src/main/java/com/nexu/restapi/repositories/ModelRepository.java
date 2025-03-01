package com.nexu.restapi.repositories;

import com.nexu.restapi.entities.Brand;
import com.nexu.restapi.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query("SELECT m FROM Model m WHERE m.brandId = :id")
    List<Model> findByBrandId(@Param("id") Long id);

    @Query("SELECT m FROM Model m WHERE m.brandId = :id AND m.averagePrice >= :greater")
    List<Model> findByBrandIdAndGreater(@Param("id") Long id, @Param("greater") Long greater);

    @Query("SELECT m FROM Model m WHERE m.brandId = :id AND m.averagePrice <= :lower")
    List<Model> findByBrandIdAndLower(@Param("id") Long id, @Param("lower") Long lower);

    @Query("SELECT m FROM Model m WHERE m.brandId = :id AND m.averagePrice BETWEEN :greater AND :lower")
    List<Model> findByBrandIdAndFilters(@Param("id") Long id, @Param("greater") Long greater, @Param("lower") Long lower);

    @Query("SELECT m FROM Model m WHERE m.brandId = :id AND m.name = :name")
    List<Model> findByBrandIdAndName(@Param("id") Long id, @Param("name") String name);

    @Query("SELECT m FROM Model m WHERE m.averagePrice >= :greater")
    List<Model> findByGreater(@Param("greater") Long greater);

    @Query("SELECT m FROM Model m WHERE m.averagePrice <= :lower")
    List<Model> findByLower(@Param("lower") Long lower);

    @Query("SELECT m FROM Model m WHERE m.averagePrice BETWEEN :greater AND :lower")
    List<Model> findByFilters(@Param("greater") Long greater, @Param("lower") Long lower);
}
