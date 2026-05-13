package br.edu.grafo.util;

import br.edu.grafo.model.DirectedGraph;
import java.io.*;
import java.util.Optional;

/**
 * Implementacao de {@link GraphRepository} usando serializacao Java em arquivos {@code .bin}.
 *
 * <h2>Descricao</h2>
 * Gerencia a serializacao e desserializacao de objetos {@link DirectedGraph}.
 * Todos os grafos sao armazenados no diretorio {@code /data}.
 *
 * <h2>Seguranca</h2>
 * {@code ObjectInputStream} e usado para carregar arquivos binarios. Trate os
 * arquivos {@code .bin} como entrada confiavel/local.
 *
 * @author Jafte Carneiro Fagundes da Silva
 * @version 3.0
 * @see GraphRepository
 */
public class GraphStorage implements GraphRepository {

    private static final String DATA_DIR = "data";

    public GraphStorage() {
        new File(DATA_DIR).mkdirs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(DirectedGraph graph, String name) {
        String fullPath = DATA_DIR + File.separator + name + ".bin";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
            oos.writeObject(graph);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DirectedGraph> load(String name) {
        String fullPath = DATA_DIR + File.separator + name + ".bin";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
            DirectedGraph graph = (DirectedGraph) ois.readObject();
            return Optional.of(graph);
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(String name) {
        return new File(DATA_DIR + File.separator + name + ".bin").exists();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] listAll() {
        File dir = new File(DATA_DIR);
        File[] files = dir.listFiles((d, n) -> n.endsWith(".bin"));

        if (files == null || files.length == 0) {
            return new String[0];
        }

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName().replace(".bin", "");
        }
        return names;
    }
}
