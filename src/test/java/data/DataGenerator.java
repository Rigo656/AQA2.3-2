package data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    // спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
    public static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest(InfoUser info) {
        //сам запрос
        given() //"дано"
                .spec(requestSpec)  //указываем, какую спецификацию указываем
                .body(info)  //передаём в теде объект, который бёдет преобразован в JSON
                .when() //когда
                .post("/api/system/users")  //на какой путь, относительно BaseUri отправляем запрос
                .then() //"тогда ожидаем"
                .statusCode(200);  //код 200 ОК
    }

    public static String getRandomLogin() {
        return faker.name().firstName();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static InfoUser getUser(String status) {
            return new InfoUser(getRandomLogin(), getRandomPassword(), status);
        }

        public static InfoUser getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class InfoUser {
        String login;
        String password;
        String status;
    }
}
