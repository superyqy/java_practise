package cn.yqy.apitest.PostmanEchoTest;


import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import java.util.HashMap;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import testData.EnvData;


public class retrieveDataFromServerTest {
    private String retrieveDataUrl = "/get";
    private String name = "QA";
    private String action = "queryData";

    @Before
    public void setUp(){
        baseURI = EnvData.postmanEchoURL;
    }

    @Test
    public void retrieveDataTest(){
        Response resp = given()
                .queryParam("name","QA")
                .queryParam("action","queryData")
                .log().all()
                .when()
                .get(retrieveDataUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("postmanEcho/retrieveDataFromServer.json"))
                .extract().response();

        String responseUrl = resp.jsonPath().getString("url");
        Map<String, String> args = new HashMap<>();
        args = resp.jsonPath().getMap("args");
        Long execTime = resp.getTime();


        assertThat(args.get("name"), is(name));
        assertThat(args.get("action"), is(action));
        assertThat(responseUrl.indexOf(EnvData.postmanEchoURL), greaterThan(-1));
        assertThat(10000l, greaterThan(execTime));



    }

}
