import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

# Configurações do servidor SMTP do Gmail
smtp_server = 'smtp.office365.com'
smtp_port = 587
smtp_username = 'guilhermetyszka@hotmail.com'
smtp_password = 'Tyszka0819'

# Destinatário e remetente
destinatario = 'guilhermetyszka@hotmail.com'
remetente = 'guilhermetyszka@hotmail.com'

# Crie uma mensagem de e-mail
mensagem = MIMEMultipart()
mensagem['From'] = remetente
mensagem['To'] = destinatario
mensagem['Subject'] = 'Assunto do e-mail'

# Corpo do e-mail
corpo = 'Este é o corpo do e-mail.'
mensagem.attach(MIMEText(corpo, 'plain'))

# Conecte-se ao servidor SMTP
try:
    server = smtplib.SMTP(smtp_server, smtp_port)
    server.starttls()
    server.login(smtp_username, smtp_password)

    # Envie o e-mail
    texto_email = mensagem.as_string()
    server.sendmail(remetente, destinatario, texto_email)
    server.quit()
    print('E-mail enviado com sucesso!')

except Exception as e:
    print('Erro ao enviar o e-mail:', str(e))
