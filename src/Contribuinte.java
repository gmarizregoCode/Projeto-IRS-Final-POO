import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contribuinte implements Imprimivel, Serializable {
    private static final long serialVersionUID = 1L;
    private String nif;
    private String nome;
    private int idade;
    private EstadoCivil estadoCivil;
    private int dependentes;
    private String senha;
    private List<Declaracao> historicoDeclaracoes;

    public Contribuinte(String nome, String nif, int idade, String senha, EstadoCivil estadoCivil, int dependentes) {
        this.nome = nome;
        this.nif = nif;
        this.senha = senha;
        this.idade = idade;
        this.estadoCivil = estadoCivil;
        this.dependentes = dependentes;
        this.historicoDeclaracoes = new ArrayList<>(); // Inicializa a memória da pessoa
    }

    public String getNif(){ return nif; }

    public String getNome(){ return nome; }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public int getDependentes(){ return dependentes; }

    public int getIdade() { return idade; }

    public void setDependentes(int dependentes){ this.dependentes = dependentes; }

    public String getSenha() { return senha; }

    public void setNome(String nome){ this.nome = nome; }

    public void setEstadoCivil(EstadoCivil estadoCivil){ this.estadoCivil = estadoCivil; }

    @Override
    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Contribuinte (nif: " + nif +
                ", nome: " + nome +
                ", Estado Civil: " + estadoCivil +
                ", Dependentes: " + dependentes +")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contribuinte)) return false;
        Contribuinte other = (Contribuinte) obj;
        return nif != null && nif.equals(other.nif);
    }

    public void adicionarDeclaracao(Declaracao d) {
        this.historicoDeclaracoes.add(d);
    }

    public List<Declaracao> getHistoricoDeclaracoes() {
        return Collections.unmodifiableList(historicoDeclaracoes);
    }
}
