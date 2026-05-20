public class Contribuinte implements Imprimivel{
    private String nif;
    private String nome;
    private EstadoCivil estadoCivil;
    private int dependentes;

    public Contribuinte(String nif, String nome, EstadoCivil estadoCivil, int dependentes){
        this.nif = nif;
        this.nome = nome;
        this.estadoCivil = estadoCivil;
        this.dependentes = dependentes;
    }
    public String getNif(){ return nif; }

    public String getNome(){ return nome; }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public int getDependentes(){ return dependentes; }

    public void setDependentes(int dependentes){ this.dependentes = dependentes; }

    public void setNome(String nome){ this.nome = nome; }

    public void setNif(String nif){ this.nif = nif; }

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
}
