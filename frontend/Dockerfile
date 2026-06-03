# Apache 기반 이미지 사용
FROM httpd:2.4

# API 요청을 backend로 프록시
COPY httpd.conf /usr/local/apache2/conf/extra/proxy.conf
RUN echo "Include conf/extra/proxy.conf" >> /usr/local/apache2/conf/httpd.conf

# 프로젝트의 정적 파일을 Apache DocumentRoot로 복사
COPY . /usr/local/apache2/htdocs/
