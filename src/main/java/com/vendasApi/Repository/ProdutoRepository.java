package com.vendasApi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendasApi.Entity.ProdutosEntity;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutosEntity, Long> {
    
    // Método para buscar um produto pelo nome
    Optional<ProdutosEntity> findByNome(String nome);
}
