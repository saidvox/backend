#!/usr/bin/env sh
set -eu

APP_DIR="${APP_DIR:-/opt/cafe-de-barrio/backend}"
BRANCH="${BRANCH:-main}"

echo "Deploying Cafe de Barrio backend from ${APP_DIR} on branch ${BRANCH}"

cd "$APP_DIR"

git checkout "$BRANCH"
git pull --ff-only origin "$BRANCH"

docker compose pull postgres
docker compose up --build -d
docker compose ps
docker compose logs --tail=120 backend

echo "Deployment finished. Health endpoint: http://localhost:8080/actuator/health"
