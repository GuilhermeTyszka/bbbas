import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class HttpClientDisableSSLVerificationExample {

    public static void main(String[] args) throws Exception {
        // Cria um HttpClient customizado com verificação SSL desabilitada
        HttpClient httpClient = createCustomHttpClientWithSSLDisabled();

        // URL da requisição HTTPS que será feita
        String url = "https://exemplo.com/api";

        // Cria uma requisição HTTP GET para a URL especificada
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        // Envia a requisição e recebe a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Imprime a resposta da requisição
        System.out.println("Status da resposta: " + response.statusCode());
        System.out.println("Corpo da resposta: " + response.body());
    }

    private static HttpClient createCustomHttpClientWithSSLDisabled() throws Exception {
        // Cria um TrustManager que não verifica certificados SSL
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };

        // Cria um SSLContext que utiliza o TrustManager personalizado
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        // Cria um HttpClient com verificação SSL desabilitada usando o SSLContext customizado
        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();

        return httpClient;
    }
}
