package biblioteca.biblioteca.model;

public class ImprumutDetails {
    private int idCarte;
    private String titlu;
    private String autor;
    private int idAbonat;
    private String nume;

    public ImprumutDetails(int idCarte, String titlu, String autor, int idAbonat, String nume) {
        this.idCarte = idCarte;
        this.titlu = titlu;
        this.autor = autor;
        this.idAbonat = idAbonat;
        this.nume = nume;
    }

    public int getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getIdAbonat() {
        return idAbonat;
    }

    public void setIdAbonat(int idAbonat) {
        this.idAbonat = idAbonat;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "ImprumutDetails{" +
                "idCarte=" + idCarte +
                ", titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", idAbonat=" + idAbonat +
                ", nume='" + nume + '\'' +
                '}';
    }
}
