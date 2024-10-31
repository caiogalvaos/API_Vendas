package com.vendasApi.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendasApi.Entity.ProdutosEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutosEntity, Integer> {
    
}