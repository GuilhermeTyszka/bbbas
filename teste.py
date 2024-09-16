import msal
import requests

client_id = 'SEU_CLIENT_ID'
client_secret = 'SEU_CLIENT_SECRET'
tenant_id = 'common'
authority = f"https://login.microsoftonline.com/{tenant_id}"
scope = ['Files.ReadWrite.All', 'Sites.ReadWrite.All']
redirect_uri = 'https://localhost'

# Crie uma instância do aplicativo MSAL
app = msal.ConfidentialClientApplication(
    client_id, authority=authority, client_credential=client_secret
)

# Primeira autenticação interativa para obter o token
result = app.acquire_token_interactive(scopes=scope)

# Armazene o token de atualização e o token de acesso
if 'access_token' in result:
    access_token = result['access_token']
    refresh_token = result['refresh_token']  # Guarde o refresh token
    print(f"Access Token: {access_token}")
    print(f"Refresh Token: {refresh_token}")
else:
    print(f"Erro na autenticação: {result.get('error_description')}")

# Função para obter um novo token de acesso usando o refresh token
def get_new_access_token(refresh_token):
    result = app.acquire_token_by_refresh_token(refresh_token, scopes=scope)
    if 'access_token' in result:
        return result['access_token']
    else:
        print(f"Erro ao renovar o token: {result.get('error_description')}")
        return None

# Tente renovar o token de acesso
new_access_token = get_new_access_token(refresh_token)

if new_access_token:
    # Fazer o upload do arquivo para o OneDrive
    upload_url = "https://graph.microsoft.com/v1.0/me/drive/root:/meu_arquivo.txt:/content"
    headers = {
        'Authorization': f'Bearer {new_access_token}',
        'Content-Type': 'text/plain',
    }

    file_content = 'Conteúdo do arquivo a ser enviado.'

    response = requests.put(upload_url, headers=headers, data=file_content)

    if response.status_code == 201:
        print('Arquivo enviado com sucesso!')
    else:
        print(f"Erro ao enviar arquivo: {response.status_code} - {response.text}")
else:
    print('Não foi possível obter um novo token de acesso.')
