# Apache 기반 이미지 사용
FROM httpd:2.4

# 프로젝트의 정적 파일을 Apache DocumentRoot로 복사
COPY . /usr/local/apache2/htdocs/
