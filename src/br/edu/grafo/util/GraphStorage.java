package br.edu.grafo.util;

import br.edu.grafo.model.DirectedGraph;
import java.io.*;

/**
 * Utilitário para persistência de grafos em arquivos binários.
 *
 * <h2>Descrição</h2>
 * Gerencia a serialização e desserialização de objetos DirectedGraph,
 * permitindo salvar grafos em disco e recuperá-los posteriormente.
 * Todos os grafos são armazenados no diretório {@code /data}.
 *
 * <h2>Formato de Arquivo</h2>
 * <ul>
 *   <li>Extensão: .bin (binário)</li>
 *   <li>Formato: Java Serialized Object Stream</li>
 *   <li>Localização: {@code data/} diretório na raiz do projeto</li>
 *   <li>Exemplo: {@code data/meu_grafo.bin}</li>
 * </ul>
 *
 * <h2>Características</h2>
 * <ul>
 *   <li>Cria diretório /data automaticamente se não existir</li>
 *   <li>Suporta nomes arbitrários para grafos</li>
 *   <li>Valida existência de arquivo antes de carregar</li>
 *   <li>Fornece listagem de grafos salvos</li>
 *   <li>Tratamento robusto de erros de I/O</li>
 * </ul>
 *
 * <h2>Exemplo de Uso</h2>
 * <pre>{@code
 * DirectedGraph grafo = new DirectedGraph(5);
 * // ... construir grafo ...
 *
 * // Salvar
 * GraphStorage.saveGraph(grafo, "meu_grafo");
 *
 * // Verificar se existe
 * if (GraphStorage.fileExists("meu_grafo")) {
 *     // Carregar
 *     DirectedGraph carregado = GraphStorage.loadGraph("meu_grafo");
 * }
 *
 * // Listar disponíveis
 * String[] nomes = GraphStorage.listGraphs();
 * }</pre>
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 2.0
 * @see DirectedGraph
 */
public class GraphStorage {
    private static final String DATA_DIR = "data";

    static {
        // Cria o diretório data se não existir
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Salva um grafo em arquivo binário
     * @param graph DirectedGraph a ser salvo
     * @param filename nome do arquivo (sem extensão .bin)
     * @return true se sucesso, false se falha
     */
    public static boolean saveGraph(DirectedGraph graph, String filename) {
        String fullPath = DATA_DIR + File.separator + filename + ".bin";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            oos.writeObject(graph);
            System.out.println("✓ Grafo salvo com sucesso em: " + fullPath);
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar grafo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Carrega um grafo de arquivo binário
     * @param filename nome do arquivo (sem extensão .bin)
     * @return DirectedGraph carregado ou null se falha
     */
    public static DirectedGraph loadGraph(String filename) {
        String fullPath = DATA_DIR + File.separator + filename + ".bin";

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            DirectedGraph graph = (DirectedGraph) ois.readObject();
            System.out.println("✓ Grafo carregado com sucesso de: " + fullPath);
            return graph;
        } catch (IOException e) {
            System.out.println("Erro ao carregar grafo: Arquivo não encontrado ou inválido");
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar grafo: Formato de arquivo inválido");
            return null;
        }
    }

    /**
     * Verifica se um arquivo .bin existe
     */
    public static boolean fileExists(String filename) {
        String fullPath = DATA_DIR + File.separator + filename + ".bin";
        return new File(fullPath).exists();
    }

    /**
     * Lista todos os grafos salvos
     */
    public static String[] listGraphs() {
        File dir = new File(DATA_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".bin"));

        if (files == null || files.length == 0) {
            return new String[0];
        }

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            // Remove a extensão .bin
            names[i] = files[i].getName().replace(".bin", "");
        }

        return names;
    }

    // ========== Métodos legados para compatibilidade ==========

    /**
     * @deprecated Use {@link #saveGraph(DirectedGraph, String)} instead
     */
    public static boolean salvarGrafo(DirectedGraph graph, String filename) {
        return saveGraph(graph, filename);
    }

    /**
     * @deprecated Use {@link #loadGraph(String)} instead
     */
    public static DirectedGraph carregarGrafo(String filename) {
        return loadGraph(filename);
    }

    /**
     * @deprecated Use {@link #fileExists(String)} instead
     */
    public static boolean arquivoExiste(String filename) {
        return fileExists(filename);
    }

    /**
     * @deprecated Use {@link #listGraphs()} instead
     */
    public static String[] listarGrafos() {
        return listGraphs();
    }
}
