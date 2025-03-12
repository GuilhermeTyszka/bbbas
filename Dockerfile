# Primeira etapa: Utiliza uma imagem oficial do Python completa
FROM python:3.9-bullseye as python-base

# Segunda etapa: Utiliza a imagem oficial do Maven
FROM maven:3.8.4-eclipse-temurin-11

# Copia todos os arquivos essenciais do Python da primeira etapa
COPY --from=python-base /usr/local/bin/python3 /usr/local/bin/
COPY --from=python-base /usr/local/bin/python3.9 /usr/local/bin/
COPY --from=python-base /usr/local/bin/pip3 /usr/local/bin/
COPY --from=python-base /usr/local/bin/pip3.9 /usr/local/bin/
COPY --from=python-base /usr/local/lib/python3.9 /usr/local/lib/python3.9
COPY --from=python-base /usr/local/include/python3.9 /usr/local/include/python3.9

# Copia a biblioteca compartilhada do Python do local correto
COPY --from=python-base /usr/local/lib/libpython3.9.so.1.0 /usr/local/lib/

# Cria os symlinks para comandos padrão
RUN ln -s /usr/local/bin/python3 /usr/local/bin/python && \
    ln -s /usr/local/bin/pip3 /usr/local/bin/pip

# Verifica a instalação
RUN python --version && pip --version

RUN pip install pandas

RUN mvn install
