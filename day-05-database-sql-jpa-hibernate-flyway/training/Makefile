.PHONY: build run test clean docker-build docker-up docker-down db-create

# === Maven ===
build:
	mvn clean package -DskipTests

run:
	mvn spring-boot:run

test:
	mvn test

clean:
	mvn clean

# === Docker ===
docker-build:
	docker build -t finance-training:latest .

docker-up:
	docker-compose up -d

docker-down:
	docker-compose down -v

docker-logs:
	docker-compose logs -f app

# === Database ===
db-create:
	@echo "Creating database finance_training..."
	@docker exec -it finance-postgres psql -U postgres -c "CREATE DATABASE finance_training;" 2>/dev/null || echo "Database may already exist"

db-migrate:
	mvn flyway:migrate

db-reset:
	mvn flyway:clean flyway:migrate

# === Helpers ===
fmt:
	mvn spotless:apply
