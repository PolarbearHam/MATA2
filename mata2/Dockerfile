FROM ubuntu:22.04

MAINTAINER malachai<prussian1933@naver.com>
USER root
WORKDIR /home

# Nginx installation
RUN \
    apt update -y && \
    apt install wget vim -y && \
    apt install nginx -y && \
    rm /etc/nginx/sites-available/default
COPY nginx/sites-available/default /etc/nginx/sites-available/default

# Build files
COPY build/ /home/build/
COPY nginx/ /home/nginx/

ENTRYPOINT ["/etc/init.d/nginx", "start"]