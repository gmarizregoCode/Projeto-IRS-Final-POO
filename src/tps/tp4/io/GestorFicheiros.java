package tps.tp4.io;

import tps.tp4.modelo.BaseDeDados;

import java.io.*;

public class GestorFicheiros {

    // NOME NOVO: Assim ignoramos o ficheiro antigo e o Java cria este de raiz!
    private static final String NOME_FICHEIRO = "basededados_irs.dat";

    public static boolean guardarBaseDeDados(BaseDeDados bd) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_FICHEIRO))) {
            oos.writeObject(bd);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao guardar a base de dados: " + e.getMessage());
            return false;
        }
    }

    public static BaseDeDados carregarBaseDeDados() {
        File ficheiro = new File(NOME_FICHEIRO);
        if (!ficheiro.exists()) {
            return new BaseDeDados();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (BaseDeDados) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar a base de dados: " + e.getMessage());
            return new BaseDeDados(); // Em caso de erro, começa uma nova para não crashar
        }
    }
}