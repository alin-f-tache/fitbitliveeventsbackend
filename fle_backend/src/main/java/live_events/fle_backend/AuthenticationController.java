package live_events.fle_backend;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.mashape.unirest.http.Unirest;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthenticationController {
    private final JdbcTemplate jdbcTemplate;

    @Value("${auth0.client_id}")
    private String client_id;

    @Value("${auth0.client_secret}")
    private String client_secret;

    @Value("${auth0.audience}")
    private String audience;

    @Value("${auth0.token_url}")
    private String token_url;

    public AuthenticationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password) {
        if (this.jdbcTemplate.queryForList("SELECT * FROM Users WHERE Username = ? AND Password = ?",
                username, password).size() != 0) {

            Map<String,String> jsonMap = new HashMap<>();
            jsonMap.put("client_id", client_id);
            jsonMap.put("client_secret", client_secret);
            jsonMap.put("audience", audience);
            jsonMap.put("grant_type", "client_credentials");
            JSONObject body = new JSONObject(jsonMap);

            HttpResponse<JsonNode> response;
            try {
                response = Unirest.post(token_url)
                        .header("Content-Type", "application/json")
                        .body(body.toJSONString())
                        .asJson();
            } catch (UnirestException e) {
                return "Invalid login";
            }

            org.json.JSONObject result = response.getBody().getObject();
            try {
                return result.getString("access_token");
            } catch (JSONException e) {
                return "Invalid login";
            }
        }
        return "Invalid login";
    }
}
