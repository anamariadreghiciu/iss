package biblioteca.biblioteca.model;

//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "abonat")
//public class Abonat {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "nume")
//    private String nume;
//
//    @Column(name = "cnp")
//    private String cnp;
//
//    @Column(name = "adresa")
//    private String adresa;
//
//    @Column(name = "telefon")
//    private String telefon;
public class Abonat {
    private Integer id;
    private String nume;
    private String cnp;
    private String adresa;
    private String telefon;
    public Abonat() {
    }

    public Abonat(String cnp, String nume, String adresa, String telefon) {
        this.cnp = cnp;
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

//    public StringProperty numeProperty() {
//        return new SimpleStringProperty(nume);
//    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Abonat{" +
                "id=" + id +
                ", cnp='" + cnp + '\'' +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
