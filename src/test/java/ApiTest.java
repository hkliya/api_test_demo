import com.jayway.jsonassert.JsonAssert;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.jayway.jsonassert.JsonAssert.with;
import static com.jayway.jsonpath.internal.Utils.isTrue;
import static org.junit.Assert.*;

public class ApiTest {
    @Test public void should_login_with_valid_user() throws IOException {
        String response = loginWith("test1", "Passw0rd123");

        with(response).assertEquals("success", true);
        with(response).assertEquals("message", "");
    }

    @Test public void should_show_message_when_login_with_invalid_user() throws IOException {
        String response = loginWith("test1", "wrong_password");

        with(response).assertEquals("success", false);
        with(response).assertEquals("message", "Invalid username or password.");
    }

    @Test public void should_show_message_when_user_is_blocked() throws IOException {
        String response = loginWith("test2", "Passw0rd123");

        with(response).assertEquals("success", false);
        with(response).assertEquals("message", "User is blocked.");
    }

    private String loginWith(String username, String password) throws IOException {
        return Request.Post("http://localhost:8080/session")
                .bodyForm(Form.form().add("username", username).add("password", password).build())
                .execute().returnContent().asString();
    }
}
