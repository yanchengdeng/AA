package apartment.wisdom.com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import apartment.wisdom.com.commons.Constants;
import apartment.wisdom.com.utils.Signature;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("username", "M000000004");
        data.put("pwd", "11111");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("function", "login");
        params.put("data", JSONObject.toJSON(data));
        String sign = Signature.getSign(params, Constants.Net.ACCESS_KEY);
        params.put("sign", sign);
        String jsonStr = JSON.toJSONString(params);
        System.out.println(jsonStr);
//        String encryptData = AESUtil.encrypt(jsonStr, Constants.Net.ACCESS_KEY);
//        System.out.println(encryptData);


//        System.out.println(AESUtil.decrypt(encryptData, Constants.Net.ACCESS_KEY));


//        String string = AESCipher.aesEncryptString(jsonStr, Constants.Net.ACCESS_KEY);

        String  string = "PY3SoeZOP+kRHXSb8HU8uXVGLvSX4FC0AadBVy7OBPRUPXyb5fCCUnm71oJrRpdUOEl/pJ7KxjYpTIvSaSfY3/8eK5s7l88sXICDIsibzfq+4F/iIjIq2LCzBFU4fDy2CKohwH/WpB9tLwB2XJVIF/ATGaaeBKKHDbnmTgPZrFtVAFLtaf1HevB8WzX7xn5jJ/83qHQvi0wUxG+vwCEWd2Rt1a4KPY2+NkirEvYXjumtVRsQ4jGjjCZlOjeVYq3oFW8ssNuTsQuLghB46Ll/llhdooBF3CNqUPYlXz7qmUUbturE7bD5Ob3Bjn0LO+1XInYVPYb6DfMjgpjuvIxnjDQuZEgZyP89H+tkZvo1OBLXkJfa7x101k+uw+6JZI67+iANtxeQ6/K+YODGq6EOCeOf069nbG0mh2hpAHWYLoZaTylcBQQ84WGJgmZla0/ccU37FN6C4czCGpEk2ZNlkkBfpkf7sll/K/O957+koo/BzICehXwsFI7/kdViXhibUFZcqEPx4aoV7Z3PUbek6Hw6AalfvlFnzbm2Il5XU8Lc/BpALFy6eIr/5DlflliCPEc2v/iFBimHqpb+VAB8gitGb2iHb/sVfh9OJpfrgIQiKwMq1I+OEz0kGRYw0JZeorA8Buu11TthnjG9ZyvP6tBMZUb38359TeZpoIdvxenwGk5V0rbH1D5kYzN28RIoyNK9D1mjMg09v4dq8KDFcgfKgCBoIDE8LWq5gjbEvRcKB1A5GFeM6PUDypG6KGm7";
        System.out.println(string);
        System.out.println(AESCipher.aesDecryptString(string,  Constants.Net.ACCESS_KEY));





        String  lat = "26.2323232";
      System.out.print(  Float.parseFloat(lat));
    }
}