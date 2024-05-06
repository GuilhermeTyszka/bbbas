from jinja2 import Environment, FileSystemLoader
from xhtml2pdf import pisa

# Função para converter HTML em PDF
def convert_html_to_pdf(html_content, output_filename):
    with open(output_filename, "w+b") as output_file:
        pisa_status = pisa.CreatePDF(html_content, dest=output_file)
        
# Carregar o template HTML
env = Environment(loader=FileSystemLoader('.'))
template = env.get_template('example.html')

# Definir os dados que serão inseridos no template
data = {'estrategia': 'ACORDO', 'id': '12345'}

# Renderizar o template com os dados
html_out = template.render(data)

# Salvar o HTML renderizado em um arquivo temporário
with open('example_out.html', 'w') as f:
    f.write(html_out)

# Converter HTML para PDF
convert_html_to_pdf(html_out, 'output.pdf')

# Remover o arquivo HTML temporário
import os
#os.remove('output.html')

print("PDF criado com sucesso!")
