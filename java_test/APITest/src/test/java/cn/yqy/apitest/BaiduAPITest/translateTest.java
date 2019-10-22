package cn.yqy.apitest.BaiduAPITest;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import cn.yqy.apitest.utility.MD5;
import cn.yqy.apitest.testData.EnvData;



public class translateTest {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com";

    private String appid;
    private String securityKey;

    public translateTest() {
        this.appid = EnvData.appid;
        this.securityKey = EnvData.securityKey;
    }

    @Before
    public void setUp(){
        baseURI = TRANS_API_HOST;
    }

    @Test
    public void getTransResult() {
        String query = "测试";
        String from = "zh";
        String to="en";
        Long l1 = new Long(100);
        String result = "";


        Map<String, String> params = buildParams(query, from, to);
        Response resp =given()
                .body(params)
                .log().all()
                .when()
                .post("api/trans/vip/translate")
                .then()
                .log().all()
                .statusCode(52000)
                .extract().response();

        long responseTime = resp.getTime();
        assertThat(l1, greaterThan(responseTime));
        result = resp.body().toString();
        System.out.println(result);

        String id = "12345";
        ArrayList data = resp.path(String.format("data.recentFiveDay.findAll{it.id==\"%s\"}.findAll{it.overtime=false}.id", id));

    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }
}
