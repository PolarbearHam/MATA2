#!/usr/bin/env bash

# Util setup
cd ~ && \
sudo apt-get update && \
sudo apt-get install net-tools vim wget curl -y && \
sudo apt-get install ssh -y && \
sudo apt-get install build-essential gcc g++ make -y

# Build essentials setup
sudo apt-get install zlib1g-dev libncurses5-dev libgdbm-dev libnss3-dev libssl-dev libreadline-dev libffi-dev libbz2-dev libsasl2-dev -y && \
sudo apt-get install sqlite3 libsqlite3-dev -y && \
sudo DEBIAN_FRONTEND=noninteractive apt-get install -y tzdata

# Java installation
sudo mkdir -p /usr/lib/jvm && \
wget https://github.com/AdoptOpenJDK/openjdk8-upstream-binaries/releases/download/jdk8u342-b07/OpenJDK8U-jdk_x64_linux_8u342b07.tar.gz && \
tar -xvf OpenJDK8U-jdk_x64_linux_8u342b07.tar.gz && \
sudo mv openjdk-8u342-b07 /usr/lib/jvm/java-1.8.0-openjdk-8u342-b07 && \
sudo mkdir -p /usr/lib/jvm/java-1.8.0-openjdk-8u342-b07/lib/ext && \
echo 'export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-8u342-b07' >> ~/.bashrc && \
echo 'export PATH=$PATH:$JAVA_HOME/bin' >> ~/.bashrc

# Scala Installation
wget https://downloads.lightbend.com/scala/2.12.17/scala-2.12.17.tgz && \
tar -xvf scala-2.12.17.tgz && \
sudo mv scala-2.12.17 /usr/local/lib/scala-2.12.17 && \
echo 'export SCALA_HOME=/usr/local/lib/scala-2.12.17' >> ~/.bashrc && \
echo 'export PATH=$PATH:$SCALA_HOME/bin' >> ~/.bashrc

# Python3 installation
wget https://www.python.org/ftp/python/3.9.5/Python-3.9.5.tgz && \
tar -xzf Python-3.9.5.tgz && \
sudo mv Python-3.9.5 /usr/local/lib/python-3.9.5 && \
cd /usr/local/lib/python-3.9.5 && \
./configure --enable-optimizations && \
make -j 12 && \
sudo make altinstall && \
sudo python3.9 -m pip install pip --upgrade && \
sudo pip install --upgrade setuptools && \
cd ~ && \
sudo apt install python3-dev -y

# Hadoop installation
wget https://dlcdn.apache.org/hadoop/common/hadoop-3.3.4/hadoop-3.3.4.tar.gz && \
tar xvzf hadoop-3.3.4.tar.gz && \
sudo mv hadoop-3.3.4 /usr/local/lib/hadoop-3.3.4 && \
echo 'export HADOOP_HOME=/usr/local/lib/hadoop-3.3.4' >> ~/.bashrc && \
echo 'export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin' >> ~/.bashrc

# Hadoop env settings
echo 'HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop' >> ~/.bashrc
cp lib/hadoop-3.3.4/etc/hadoop/hadoop-env.sh /usr/local/lib/hadoop-3.3.4/etc/hadoop/hadoop-env.sh

# Zookeeper installation
wget https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz &&\
tar xvfz apache-zookeeper-3.7.1-bin.tar.gz && \
sudo mv apache-zookeeper-3.7.1-bin /usr/local/lib/apache-zookeeper-3.7.1-bin && \
mkdir /usr/local/lib/apache-zookeeper-3.7.1-bin/data && \
echo 'export ZOOKEEPER_HOME=/usr/local/lib/apache-zookeeper-3.7.1-bin' >> ~/.bashrc && \
echo 'export PATH=$PATH:$ZOOKEEPER_HOME/bin' >> ~/.bashrc

# Zookeeper env settings
cp lib/apache-zookeeper-3.7.1-bin/conf/zoo.cfg /usr/local/lib/apache-zookeeper-3.7.1-bin/conf/zoo.cfg

# Hadoop-Zookeeper HA env settings
cp lib/hadoop-3.3.4/etc/hadoop/core-site.xml /usr/local/lib/hadoop-3.3.4/etc/hadoop/core-site.xml && \
cp lib/hadoop-3.3.4/etc/hadoop/hdfs-site.xml /usr/local/lib/hadoop-3.3.4/etc/hadoop/hdfs-site.xml && \
cp lib/hadoop-3.3.4/etc/hadoop/yarn-site.xml /usr/local/lib/hadoop-3.3.4/etc/hadoop/yarn-site.xml && \
cp lib/hadoop-3.3.4/etc/hadoop/mapred-site.xml /usr/local/lib/hadoop-3.3.4/etc/hadoop/mapred-site.xml && \
cp lib/hadoop-3.3.4/etc/hadoop/capacity-scheduler.xml /usr/local/lib/hadoop-3.3.4/etc/hadoop/capacity-scheduler.xml && \
cp lib/hadoop-3.3.4/etc/hadoop/workers /usr/local/lib/hadoop-3.3.4/etc/hadoop/workers

# MySQL Installation
#########################
# ONLY IN master01 NODE #
#########################
sudo apt-get install mysql-server -y
CREATE DATABASE hive;
GRANT ALL PRIVILEGES ON hive.* TO 'hive'@'%';

# Hive installation
wget https://dlcdn.apache.org/hive/hive-3.1.3/apache-hive-3.1.3-bin.tar.gz && \
tar -xzvf apache-hive-3.1.3-bin.tar.gz && \
sudo mv apache-hive-3.1.3-bin /usr/local/lib/apache-hive-3.1.3-bin && \
echo 'export HIVE_HOME=/usr/local/lib/apache-hive-3.1.3-bin' >> ~/.bashrc && \
echo 'export PATH=$PATH:$HIVE_HOME/bin' >> ~/.bashrc

# Hive env settings
wget https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-j-8.0.32.tar.gz && \
tar -xvf mysql-connector-j-8.0.32.tar.gz && \
sudo mv mysql-connector-j-8.0.32/mysql-connector-j-8.0.32.jar /usr/lib/jvm/java-1.8.0-openjdk-8u342-b07/jre/lib/ext && \
cp lib/apache-hive-3.1.3-bin/conf/hive-site.xml /usr/local/lib/apache-hive-3.1.3-bin/conf/hive-site.xml

# Cassandra Installation
wget https://dlcdn.apache.org/cassandra/4.0.9/apache-cassandra-4.0.9-bin.tar.gz && \
tar xzvf apache-cassandra-4.0.9-bin.tar.gz && \
sudo mv apache-cassandra-4.0.9 /usr/local/lib/apache-cassandra-4.0.9 && \
echo 'export CASSANDRA_HOME=/usr/local/lib/apache-cassandra-4.0.9' >> ~/.bashrc && \
echo 'export PATH=$PATH:$CASSANDRA_HOME/bin' >> ~/.bashrc

# Cassandra env settings
cp lib/apache-cassandra-4.0.8/conf/cassandra.yaml /usr/local/lib/apache-cassandra-4.0.9/conf/cassandra.yaml