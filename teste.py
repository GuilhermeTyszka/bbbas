import msal
import requests

# Detalhes de autenticação
client_id = 'SEU_CLIENT_ID'
tenant_id = 'common'  # Use 'common' para contas corporativas ou seu tenant específico
authority = f"https://login.microsoftonline.com/{tenant_id}"

# Defina o escopo para o Microsoft Graph (sem criar um aplicativo próprio)
scope = ['Sites.ReadWrite.All', 'Files.ReadWrite.All']

# URL de redirecionamento após login (pode ser algo simples como localhost)
redirect_uri = 'http://localhost'

# Autenticação interativa usando MSAL para obter o token
app = msal.PublicClientApplication(client_id=client_id, authority=authority)

# Solicita que o usuário faça login
result = app.acquire_token_interactive(scopes=scope, redirect_uri=redirect_uri)

if 'access_token' in result:
    access_token = result['access_token']
    
    # Detalhes do SharePoint
    site_id = 'SEU_SITE_ID'  # Identificador do site no SharePoint
    drive_id = 'SEU_DRIVE_ID'  # ID da biblioteca de documentos do SharePoint
    file_path = '/Documentos/meu_arquivo.txt'  # Caminho do arquivo no SharePoint
    file_content = 'Conteúdo do arquivo a ser enviado.'

    # URL da API Graph para upload de arquivo no SharePoint
    upload_url = f"https://graph.microsoft.com/v1.0/sites/{site_id}/drives/{drive_id}/root:{file_path}:/content"

    # Cabeçalhos da requisição
    headers = {
        'Authorization': f'Bearer {access_token}',
        'Content-Type': 'text/plain',
    }

    # Enviando o arquivo para o SharePoint
    response = requests.put(upload_url, headers=headers, data=file_content)

    if response.status_code == 201:
        print("Arquivo enviado com sucesso!")
    else:
        print(f"Erro ao enviar arquivo: {response.status_code} - {response.text}")
else:
    print(f"Erro na autenticação: {result.get('error_description')}")
