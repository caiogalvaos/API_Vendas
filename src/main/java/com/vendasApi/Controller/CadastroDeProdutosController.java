package com.vendasApi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vendasApi.Service.ProdutoService;
import com.vendasApi.DTO.ProdutoDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class CadastroDeProdutosController {

    private static final Logger logger = LoggerFactory.getLogger(CadastroDeProdutosController.class);

    @Autowired
    private ProdutoService produtoService;

    // Cadastrar novo produto
    @PostMapping
    public ResponseEntity<?> cadastrarNovoProduto(@Valid @RequestBody ProdutoDTO produto) {
        try {
            ProdutoDTO novoProduto = produtoService.cadastrarProduto(produto);
            logger.info("Produto cadastrado com sucesso: {}", novoProduto);
            return ResponseEntity.ok(novoProduto);
        } catch (Exception e) {
            logger.error("Erro ao cadastrar produto: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar todos os produtos
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        try {
            List<ProdutoDTO> produtos = produtoService.listarProdutos();
            logger.info("Listando todos os produtos.");
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            logger.error("Erro ao listar produtos: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        try {
            ProdutoDTO produto = produtoService.buscarPorId(id);
            if (produto != null) {
                logger.info("Produto encontrado: {}", produto);
                return ResponseEntity.ok(produto);
            } else {
                logger.warn("Produto com ID {} não encontrado.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar produto por ID: {}", e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // Atualizar produto
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoAtualizado) {
        try {
            ProdutoDTO produto = produtoService.atualizarProduto(id, produtoAtualizado);
            if (produto != null) {
                logger.info("Produto atualizado com sucesso: {}", produto);
                return ResponseEntity.ok(produto);
            } else {
                logger.warn("Produto com ID {} não encontrado para atualização.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar produto: {}", e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // Deletar produto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        try {
            produtoService.deletarProduto(id);
            logger.info("Produto com ID {} deletado com sucesso.", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar produto: {}", e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
