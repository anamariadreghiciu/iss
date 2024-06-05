package biblioteca.biblioteca.model;

//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "carte")
//public class Carte {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "titlu")
//    private String titlu;
//
//    @Column(name = "autor")
//    private String autor;

public class Carte {
    private Integer id;
    private String titlu;
    private String autor;
    public Carte() {
    }

    public Carte(String titlu, String autor) {
        this.titlu = titlu;
        this.autor = autor;
    }

//    public IntegerProperty idProperty() {
//        return new SimpleIntegerProperty(id);
//    }
//
//    public StringProperty titluProperty() {
//        return new SimpleStringProperty(titlu);
//    }
//
//    public StringProperty autorProperty() {
//        return new SimpleStringProperty(autor);
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Carte{" +
                "id=" + id +
                ", titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }
}
