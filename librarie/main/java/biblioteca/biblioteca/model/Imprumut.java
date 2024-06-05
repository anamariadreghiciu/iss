package biblioteca.biblioteca.model;

//import javax.persistence.*;
//
//@Entity
//@Table(name = "imprumut")
//public class Imprumut {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Integer id;
//
//    @Column(name = "id_abonat")
//    private Integer idAbonat;
//
//    @Column(name = "id_carte")
//    private Integer idCarte;
public class Imprumut {
    private Integer id;
    private Integer idAbonat;
    private Integer idCarte;

    public Imprumut() {
        this.id = 0;
        this.idAbonat = 0;
        this.idCarte = 0;
    }
    public Imprumut(Integer id, Integer idAbonat, Integer idCarte) {
        this.id = id;
        this.idAbonat = idAbonat;
        this.idCarte = idCarte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAbonat() {
        return idAbonat;
    }

    public void setIdAbonat(Integer idAbonat) {
        this.idAbonat = idAbonat;
    }

    public Integer getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(Integer idCarte) {
        this.idCarte = idCarte;
    }
}
