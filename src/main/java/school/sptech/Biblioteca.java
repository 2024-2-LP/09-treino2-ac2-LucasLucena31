package school.sptech;

import school.sptech.exception.ArgumentoInvalidoException;
import school.sptech.exception.LivroNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private String nome;
    private List<Livro> livros = new ArrayList<>();

    public Biblioteca() {
    }

    public Biblioteca(String nome) {
        this.nome = nome;
    }

    public void adicionarLivro (Livro livro){
        if((livro == null)){
            throw new ArgumentoInvalidoException("Livro nulo");
        } else if ((livro.getTitulo() == null) || livro.getTitulo().isBlank()){
            throw new ArgumentoInvalidoException("Titulo inválido");
        } else if ((livro.getAutor() == null) || livro.getAutor().isBlank()){
            throw new ArgumentoInvalidoException("Autor inválido");
        } else if ((livro.getDataPublicacao() == null)){
            throw new ArgumentoInvalidoException("Data inválida");
        } else {
            livros.add(livro);
        }
    }

    public void removerLivroPorTitulo(String titulo){
        if ((titulo == null) || titulo.isBlank()) {
            throw new ArgumentoInvalidoException("Titulo inválido");
        }
        Boolean existe = false;

        for (int i = 0; i < livros.size(); i++) {
            Livro livorDaVez = livros.get(i);

            if(livorDaVez.getTitulo().equalsIgnoreCase(titulo)){
                livros.remove(livorDaVez);
                existe = true;
            }
        }
        if (existe == false){
            throw new LivroNaoEncontradoException("Livro não encontrado");
        }
    }

    public Livro buscarLivroPorTitulo(String titulo){
        if ((titulo == null) || titulo.isBlank()) {
            throw new ArgumentoInvalidoException("Titulo inválido");
        }

        for (int i = 0; i < livros.size(); i++) {
            Livro livorDaVez = livros.get(i);

            if(livorDaVez.getTitulo().equalsIgnoreCase(titulo)){
                return livorDaVez;
            }
        }
        throw new LivroNaoEncontradoException("Livro não encontrado");
    }

    public Integer contarLivros(){
        return livros.size();
    }

    public List<Livro> obterLivrosAteAno(Integer ano){
        List<Livro> busca = new ArrayList<>();

        for (int i = 0; i < livros.size(); i++) {
            Livro livorDaVez = livros.get(i);
            Integer anoDaVez = livorDaVez.getDataPublicacao().getYear();

            if(anoDaVez <= ano){
                busca.add(livorDaVez);
            }
        }
        return busca;
    }

    public List<Livro> retornarTopCincoLivros() {
        List<Livro> busca = new ArrayList<>();

        if (livros.isEmpty()) {
            return busca;
        }

        List<Livro> topLivros = new ArrayList<>();

        for (Livro livroDaVez : livros) {
            double mediaAtual = livroDaVez.calcularMediaAvaliacoes();

            for (int i = 0; i < topLivros.size(); i++) {
                if (mediaAtual > topLivros.get(i).calcularMediaAvaliacoes()) {
                    topLivros.add(i, livroDaVez);
                    break;
                }
            }

            if (!topLivros.contains(livroDaVez) && topLivros.size() < 5) {
                topLivros.add(livroDaVez);
            }

            if (topLivros.size() > 5) {
                topLivros.remove(5);
            }
        }

        return topLivros;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
