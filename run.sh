set -e

env=${1:-dev}
port=${2:-8080}

./mvnw package

java -jar target/examination-system-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active="${env}" \
  --server.port="${port}"
