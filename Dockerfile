FROM --platform=linux/amd64 ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive \
    LC_ALL=en_US.UTF-8 LANG=C.UTF-8 LANGUAGE=en_US.UTF-8 \
    TERM=xterm \
    PATH=/home/cdsw/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/opt/conda/bin \
    SHELL=/bin/bash \
    HADOOP_ROOT_LOGGER=WARN,console
    
RUN apt-get update && apt-get dist-upgrade -y && \
  apt-get install -y --no-install-recommends \
  locales \
  gpg \
  apt-transport-https \
  krb5-user \
  xz-utils \
  git \
  git-lfs \
  ssh \
  zip \
  unzip \
  gzip \
  curl \
  nano \
  emacs-nox \
  wget \
  less \
  ca-certificates ca-certificates-java \
  zlib1g-dev \
  libbz2-dev \
  liblzma-dev \
  libssl-dev \
  libsasl2-dev \
  libsasl2-2 \
  libsasl2-modules-gssapi-mit \
  libzmq3-dev \
  cpio \
  cmake \
  build-essential \
  patch autoconf automake \
  libgl-dev \
  libjpeg-dev \
  libpng-dev \
  ffmpeg \
  fonts-roboto \
  fonts-dejavu && \
  apt-get clean && \
  apt-get autoremove --purge && \
  rm -rf /var/lib/apt/lists/* && \
  rm -f /etc/ssh/ssh_host_ecdsa_key /etc/ssh/ssh_host_ed25519_key /etc/ssh/ssh_host_rsa_key && \
  echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && locale-gen && \
  addgroup --gid 8536 cdsw && \
  adduser --disabled-password --comment "CDSW User" --uid 8536 --gid 8536 cdsw && \
  for i in /etc /etc/alternatives; do \
    if [ -d ${i} ]; then chmod 777 ${i}; fi; \
  done && \
  chown cdsw / && \
  for i in /bin /etc /opt /sbin /usr; do \
    if [ -d ${i} ]; then \
      find ${i} -type d -exec chown cdsw {} +; \
    fi; \
  done && \
  ln -s /usr/lib/x86_64-linux-gnu/libsasl2.so.2 /usr/lib/x86_64-linux-gnu/libsasl2.so.3 && \
  mkdir -p /etc/pki/tls/certs && \
  ln -s /etc/ssl/certs/ca-certificates.crt /etc/pki/tls/certs/ca-bundle.crt



WORKDIR /build

ENV PYTHON3_VERSION=3.10.16 \
    ML_RUNTIME_KERNEL="Python 3.10"

RUN \
    apt-get update && \
    apt-get install -y --no-install-recommends \
    vim \
    libnss3 \
    libnspr4 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libxcomposite1 \
    libxdamage1 \
    libgbm1 \
    libatspi2.0-0 \
    libsqlite3-0 \
    media-types \
    libpq-dev \
    libkrb5-dev && \
    apt-get autoremove -y --purge && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

COPY etc/pip.conf /etc/pip.conf

ADD build/python-prebuilt-3.10.16-20241205-pkg.tar.gz /usr/local
COPY requirements/python-standard-packages/requirements-3.10.txt /build/requirements.txt

RUN \
    ldconfig && \
    pip3 config set install.user false && \
    pip3 install --no-cache-dir --no-warn-script-location -r requirements.txt && \
    rm -rf /build && \
    cd /root && \
    rm -rf .cache .ipython .ivy2 .sbt .npm .yarn && \
    rm -rf /tmp/*

ENV ML_RUNTIME_EDITOR="JupyterLab" \
    ML_RUNTIME_EDITION="Standard DE" \
    ML_RUNTIME_JUPYTER_KERNEL_GATEWAY_CMD="jupyter kernelgateway --config=/home/cdsw/.jupyter/jupyter_kernel_gateway_config.py" \
    JUPYTERLAB_WORKSPACES_DIR=/tmp

COPY requirements/pbj-workbench-base/requirements-3.10.txt /build/requirements.txt

COPY etc/cloudera.mplstyle /etc/cloudera.mplstyle

RUN pip3 install --no-cache-dir --no-warn-script-location -r /build/requirements.txt && \
    rm -rf /build

ENV ML_RUNTIME_JUPYTER_KERNEL_NAME="python3" \
    ML_RUNTIME_DESCRIPTION="PBJ Workbench Python runtime provided by Data Engineering Safra Team"
    
RUN \
  cd /root && \
  rm -rf .cache .ipython .ivy2 .sbt .npm .yarn && \
  apt-get autoremove -y --purge && \
  apt-get clean && rm -rf /var/lib/apt/lists/* && \
  rm -rf /tmp/*

ENV ML_RUNTIME_EDITOR="JupyterLab" \
    ML_RUNTIME_EDITION="DE Standard" \
    ML_RUNTIME_DESCRIPTION="Custom Runtime Workbench By Data Engineering Safra Team"

COPY requirements/pbj-jupyterlab/requirements-3.10.txt /build/requirements.txt
COPY etc/jupyterlab.sh /usr/local/bin/jupyterlab.sh
COPY etc/read_default_copilot_model.py /usr/local/bin/read_default_copilot_model.py
COPY etc/read_default_copilot_embedding_model.py /usr/local/bin/read_default_copilot_embedding_model.py

RUN \
  curl -s -o /tmp/nodejs.tar.xz https://nodejs.org/download/release/v20.8.1/node-v20.8.1-linux-x64.tar.xz && \
  tar xJ -f /tmp/nodejs.tar.xz -C /usr/local --strip-components 1 && \
  npm install -g npm@10.5.2 && \
  ln -sf /usr/local/bin/jupyterlab.sh /usr/local/bin/ml-runtime-editor && \
  chmod +x /usr/local/bin/jupyterlab.sh && \
  pip install --no-cache-dir -r /build/requirements.txt && \
  rm -rf /build && \
  cd /usr/local && \
  tar tJf /tmp/nodejs.tar.xz | \
  cut -d/ -f2- | \
  egrep -v '^(\s*|.*/)$' | \
  xargs rm -f && \
  rm -rf /usr/local/lib/node_modules /usr/local/include/node && \
  jupyter labextension disable "@jupyterlab/apputils-extension:announcements" && \
  cd /root && \
  rm -rf .cache .ipython .ivy2 .sbt .npm .yarn && \
  apt-get autoremove -y --purge && \
  apt-get clean && rm -rf /var/lib/apt/lists/* && \
  rm -rf /tmp/*

ENV \
    ML_RUNTIME_METADATA_VERSION=2 \ 
    ML_RUNTIME_FULL_VERSION=2025.03-Tyszka.2 \
    ML_RUNTIME_SHORT_VERSION=2025.03-Tyszka \
    ML_RUNTIME_MAINTENANCE_VERSION=2 \
    ML_RUNTIME_GIT_HASH=fb1e31ce5d426a950deb2d19e56f4a82e12ef5b5 \
    ML_RUNTIME_GBN=63562532

LABEL \
    com.cloudera.ml.runtime.runtime-metadata-version=$ML_RUNTIME_METADATA_VERSION \
    com.cloudera.ml.runtime.editor=$ML_RUNTIME_EDITOR \
    com.cloudera.ml.runtime.edition=$ML_RUNTIME_EDITION \
    com.cloudera.ml.runtime.description=$ML_RUNTIME_DESCRIPTION \
    com.cloudera.ml.runtime.kernel=$ML_RUNTIME_KERNEL \
    com.cloudera.ml.runtime.full-version=$ML_RUNTIME_FULL_VERSION \
    com.cloudera.ml.runtime.short-version=$ML_RUNTIME_SHORT_VERSION \
    com.cloudera.ml.runtime.maintenance-version=$ML_RUNTIME_MAINTENANCE_VERSION \
    com.cloudera.ml.runtime.git-hash=$ML_RUNTIME_GIT_HASH \
    com.cloudera.ml.runtime.gbn=$ML_RUNTIME_GBN \
    com.cloudera.ml.runtime.cuda-version=$ML_RUNTIME_CUDA_VERSION

RUN pip config set global.index http://nexus-alm.safra.com.br/repository/pypi-repo/pypi
RUN pip config set global.index-url http://nexus-alm.safra.com.br/repository/pypi-repo/pypi
RUN pip config set global.trusted-host nexus-alm.safra.com.br

WORKDIR /home/cdsw
