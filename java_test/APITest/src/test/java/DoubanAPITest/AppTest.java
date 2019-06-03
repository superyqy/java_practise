/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package DoubanAPITest;

import java.io.*;
import java.util.Map;
import java.util.List;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;

import testData.EnvData;

public class AppTest {

    public String topFilm = "/v2/movie/top250";

    @Before
    public void setup(){
        baseURI = EnvData.doubanURL;
    }

//    @Test
//    public void testAppHasAGreeting() {
//        App classUnderTest = new App();
//        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
//    }

    @Test
    public void testDoubanApi() throws IOException{
        String title = "not found";

        Response resp = given()
//                .header()
                .queryParam("start",'0')
                .queryParam("count",'1')
                .accept(ContentType.JSON)
                .when()
                .log().all()
                .get(topFilm)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().response();
//        MAP<String, String> cookie = resp.cookies();

        List<Map<String, String>> body = resp.getBody().jsonPath().getList("subjects");
        assertThat(body.size(),greaterThan(0));
        for(int i=0;i<body.size();i++){
            Map<String, String> content = body.get(i);
            if(content.containsKey("title")) {
                title = content.get("title");
                break;
            }

        }
        assertThat(title,is("肖申克的救赎"));

//        int statusCode= resp.getStatusCode();
//        writeFile(statusCode,topFilm);
    }

    public void writeFile(int statusCode, String caseUrl) throws IOException{

        FileWriter outFile = new FileWriter(EnvData.outoutFile);
        BufferedWriter output = new BufferedWriter(outFile);
        output.write(caseUrl+" 's statusCode is: "+statusCode);
        output.close();
    }

    public void testValueType(){
        int a = 50;
        assertThat(a,allOf(notNullValue(),instanceOf(Integer.class)));
        assertThat(a,notNullValue());
    }

}