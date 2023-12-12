<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iframe com Cookie</title>
</head>
<body>
    <script>
        // Configuração do cookie
        document.cookie = "authCookie=seu_valor; SameSite=None; Secure";

        // Exemplo de como você pode fazer uma requisição usando o cookie configurado
        fetch('sua_url_de_requisicao', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + getCookie('authCookie')
            }
        }).then(response => {
            // Lidere com a resposta da requisição
        });

        // Função para obter um cookie pelo nome
        function getCookie(name) {
            const value = `; ${document.cookie}`;
            const parts = value.split(`; ${name}=`);
            if (parts.length === 2) return parts.pop().split(';').shift();
        }
    </script>
</body>
</html>
