import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class OAuthHttpClientExampleWithObjectMapper {

    public static void main(String[] args) throws IOException {
        String clientId = "your_client_id";
        String clientSecret = "your_client_secret";
        String tokenUrl = "https://example.com/oauth/token"; // URL para obter o token

        // Criar um HttpClient
        HttpClient httpClient = HttpClients.createDefault();

        // Criar um objeto ObjectMapper (do Jackson)
        ObjectMapper objectMapper = new ObjectMapper();

        // Criar um objeto JSON para o corpo da requisição de obtenção de token
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("client_id", clientId)
                .put("client_secret", clientSecret)
                .put("grant_type", "client_credentials");

        // Criar uma requisição HTTP POST para obter o token
        HttpPost httpPost = new HttpPost(tokenUrl);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // Configurar o corpo da requisição
        StringEntity requestEntity = new StringEntity(objectMapper.writeValueAsString(requestBody));
        httpPost.setEntity(requestEntity);

        // Executar a requisição HTTP POST para obter o token
        HttpResponse response = httpClient.execute(httpPost);

        // Extrair e processar a resposta
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String responseString = EntityUtils.toString(responseEntity);

            // Converter a resposta JSON para um objeto JsonNode usando ObjectMapper
            JsonNode jsonResponse = objectMapper.readTree(responseString);

            // Extrair o token de acesso (Bearer token) da resposta
            String accessToken = jsonResponse.get("access_token").asText();

            // Usar o token de acesso para autenticar outra requisição HTTP POST
            String postData = "{ \"key\": \"value\" }"; // Corpo da requisição POST
            String postUrl = "https://example.com/api/endpoint"; // URL do endpoint POST

            HttpPost httpPostWithData = new HttpPost(postUrl);
            httpPostWithData.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            httpPostWithData.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            // Configurar o corpo da requisição POST
            StringEntity postDataEntity = new StringEntity(postData);
            httpPostWithData.setEntity(postDataEntity);

            // Executar a requisição HTTP POST autenticada
            HttpResponse postResponse = httpClient.execute(httpPostWithData);
            // Processar a resposta, se necessário
        }
    }
}
