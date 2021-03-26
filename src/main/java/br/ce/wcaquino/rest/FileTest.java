package br.ce.wcaquino.rest;

import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FileTest {

    @Test
    public void deveObrigarEnviarArquivo() {
        given()
                .log().all()
                .when()
                .post("http://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(404) // Deveria ser 400
                .body("error", is("Arquivo não enviado"));
    }

    @Test
    public void deveFazerUploadArquivo() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))
                .when()
                .post("http://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("users.pdf"));
    }

    @Test
    public void deveFazerUploadArquivoGrande() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/Arquivo Comprimido.zip"))
                .when()
                .post("http://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .time(lessThan(3000L))
                .statusCode(413)
        ;
    }
}
