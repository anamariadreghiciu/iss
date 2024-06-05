module biblioteca.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
//    requires org.hibernate.orm.core;
//    requires java.persistence;
//    requires java.naming;
//    requires com.fasterxml.jackson.databind;
    requires org.apache.logging.log4j;
//    requires sqlite.dialect;
    requires java.sql;

    opens biblioteca.biblioteca to javafx.fxml;
    opens biblioteca.biblioteca.controller to javafx.fxml;
//    opens biblioteca.biblioteca.repository to org.hibernate.orm.core;

    exports biblioteca.biblioteca;
    exports biblioteca.biblioteca.controller;
    exports biblioteca.biblioteca.repository;
}