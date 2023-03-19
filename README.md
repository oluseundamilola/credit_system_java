# Build
mvn clean package && docker build -t com.credit/credit-system .

# RUN

docker rm -f credit-system || true && docker run -d -p 8080:8080 -p 4848:4848 --name credit-system com.credit/credit-system 