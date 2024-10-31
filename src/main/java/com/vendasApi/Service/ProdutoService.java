package com.vendasApi.Service;

import com.vendasApi.DTO.ProdutoDTO;
import java.util.List;

public interface ProdutoService {
    void cadastrarProduto(ProdutoDTO produto);
    List<ProdutoDTO> listarProdutos();
    ProdutoDTO buscarPorId(Long id);
    ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoAtualizado);
    void deletarProduto(Long id);
}
