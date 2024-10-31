import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vendasApi.DTO.ProdutoDTO;
import com.vendasApi.Entity.ProdutosEntity;
import com.vendasApi.Repository.ProdutoRepository;
import com.vendasApi.Service.ProdutoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


package com.vendasApi.Service.Impl;



@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void cadastrarProduto(ProdutoDTO produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new RuntimeException("Nome do produto não pode ser vazio");
        }
        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Preço do produto não pode ser vazio ou negativo");
        }

        ProdutosEntity entity = new ProdutosEntity(produto.getNome(), produto.getPreco());
        produtoRepository.save(entity);
    }

    @Override
    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProdutoDTO buscarPorId(Long id) {
        Optional<ProdutosEntity> produto = produtoRepository.findById(id);
        return produto.map(this::convertToDTO).orElse(null);
    }

    @Override
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoAtualizado) {
        Optional<ProdutosEntity> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
            ProdutosEntity produto = produtoExistente.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            ProdutosEntity produtoAtualizadoEntity = produtoRepository.save(produto);
            return convertToDTO(produtoAtualizadoEntity);
        } else {
            throw new RuntimeException("Produto com ID " + id + " não encontrado para atualização.");
        }
    }

    @Override
    public void deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto com ID " + id + " não encontrado para exclusão.");
        }
    }

    // Métodos de conversão entre ProdutoDTO e ProdutosEntity
    private ProdutoDTO convertToDTO(ProdutosEntity produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco());
    }

    private ProdutosEntity convertToEntity(ProdutoDTO produtoDTO) {
        return new ProdutosEntity(produtoDTO.getNome(), produtoDTO.getPreco());
    }
}
