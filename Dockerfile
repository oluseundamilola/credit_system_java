FROM airhacks/glassfish
COPY ./target/credit-system.war ${DEPLOYMENT_DIR}
