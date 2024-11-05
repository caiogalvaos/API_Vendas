package com.vendasApi.Service;

import java.util.List;

import com.vendasApi.DTO.ProdutoDTO;

public interface ProdutoService {
    void cadastrarProduto(ProdutoDTO produto);
    List<ProdutoDTO> listarProdutos();
    ProdutoDTO buscarPorId(Long id);
    ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoAtualizado);
    void deletarProduto(Long id);
}