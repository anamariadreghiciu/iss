package biblioteca.biblioteca.model;

//import javax.persistence.*;
//
//@Entity
//@Table(name = "bibliotecar")
//public class Bibliotecar {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "nume")
//    private String nume;
//
//    @Column(name = "parola")
//    private String parola;
public class Bibliotecar {
    private Integer id;
    private String nume;
    private String parola;
    public Bibliotecar() {
    }

    public Bibliotecar(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Bibliotecar{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
