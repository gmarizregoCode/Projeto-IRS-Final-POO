import java.io.*;

public class GestorFicheiros {
    private static final String NOME_FICHEIRO = "declaracao_ativa.dat";

    public static boolean guardarDeclaracao(Declaracao d) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_FICHEIRO))) {
            oos.writeObject(d);
            return true; // Sucesso a gravar
        } catch (IOException e) {
            System.out.println("Erro ao guardar o ficheiro: " + e.getMessage());
            return false;
        }
    }

    public static Declaracao carregarDeclaracao() {
        File ficheiro = new File(NOME_FICHEIRO);
        if (!ficheiro.exists()) {
            return null; //ficheiro não exisste (primeira vez que o programa corre)
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (Declaracao) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o ficheiro: " + e.getMessage());
            return null;
        }
    }
}
