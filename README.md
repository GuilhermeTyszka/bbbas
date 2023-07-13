**KUDU_QUICKSTART_IP = ((Get-NetIPAddress | Where-Object { $_.AddressFamily -eq "IPv4" -and $_.InterfaceAlias -ne "Loopback Pseudo-Interface 1" } | Sort-Object -Property InterfaceIndex)[-1]).IPAddress**version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:6.1.1
    hostname: zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      - ZOOKEEPER_CLIENT_PORT=2181
      - ZOOKEEPER_TICK_TIME=2000

  kafka:
    image: confluentinc/cp-kafka:6.1.1
    hostname: kafka
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      - KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181
      - KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092
      - KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1

  kudu-master:
    image: apache/kudu:1.14.0
    hostname: kudu-master
    container_name: kudu-master
    ports:
      - "8051:8051"
      - "7051:7051"
      - "8080:8080"
    command: master --logtostderr

  kudu-tserver:
    image: apache/kudu:1.14.0
    hostname: kudu-tserver
    container_name: kudu-tserver
    depends_on:
      - kudu-master
    ports:
      - "8050:8050"
      - "7050:7050"
    command: tserver --logtostderr --master_addresses=kudu-master:7051
