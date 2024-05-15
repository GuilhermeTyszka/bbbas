import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientDisableSSLVerificationExample {

    public static void main(String[] args) throws Exception {
        // Cria um CloseableHttpClient com verificação SSL desabilitada
        CloseableHttpClient httpClient = createCustomHttpClientWithSSLDisabled();

        // URL da requisição HTTPS que será feita
        String url = "https://exemplo.com/api";

        // Cria um objeto HttpGet para a URL especificada
        HttpGet httpGet = new HttpGet(url);

        // Executa a requisição e recebe a resposta
        HttpResponse response = httpClient.execute(httpGet);

        // Lê e imprime o conteúdo da resposta
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
            String line;
            StringBuilder responseBody = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            System.out.println("Status da resposta: " + response.getStatusLine().getStatusCode());
            System.out.println("Corpo da resposta: " + responseBody.toString());
        } finally {
            // Fecha o HttpClient após a conclusão da requisição
            httpClient.close();
        }
    }

    private static CloseableHttpClient createCustomHttpClientWithSSLDisabled() throws Exception {
        // Cria um SSLContext que utiliza o TrustManager personalizado para aceitar todos os certificados SSL
        SSLContext sslContext = SSLContextBuilder.create()
                .loadTrustMaterial((chain, authType) -> true) // Aceita todos os certificados
                .build();

        // Cria um SSLConnectionSocketFactory com verificação SSL desabilitada
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        // Cria um CloseableHttpClient com o SSLConnectionSocketFactory personalizado
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();

        return httpClient;
    }
}
