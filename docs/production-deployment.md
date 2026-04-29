# Despliegue en produccion: Cafe de Barrio Backend

Esta guia cubre el flujo:

`git push -> Coolify -> Docker build -> Docker Compose -> Cloudflare Tunnel -> dominio`

Reemplaza `api.tu-dominio.com` por tu dominio real.

## Archivos incluidos

- `Dockerfile`: build multi-stage con Maven y runtime `eclipse-temurin:21-jre-alpine`.
- `docker-compose.yml`: backend Spring Boot + PostgreSQL + healthchecks.
- `.env.example`: variables necesarias para local/Coolify.
- `scripts/deploy-compose.sh`: despliegue manual opcional sin Coolify.

## Variables requeridas

Configuralas en Coolify como Runtime Environment Variables:

```env
DB_NAME=cafe_de_barrio
DB_USER=postgres
DB_PASSWORD=usa_un_password_largo
APP_ADMIN_USERNAME=admin
APP_ADMIN_PASSWORD=usa_un_password_largo
JWT_SECRET=usa_un_secret_largo_minimo_32_caracteres
```

Opcionales:

```env
PGADMIN_EMAIL=admin@cafedebarrio.com
PGADMIN_PASSWORD=solo_si_activas_el_perfil_tools
```

## Verificacion local con Docker Compose

```bash
cd backend
cp .env.example .env
# Edita .env con secretos reales
docker compose up --build -d
docker compose logs -f backend
curl http://localhost:8080/actuator/health
```

Para levantar pgAdmin localmente:

```bash
docker compose --profile tools up -d pgadmin
```

## Coolify

1. Sube el backend a un repositorio Git.
2. En Coolify crea un nuevo recurso desde Git Repository.
3. Selecciona el repo y la rama `main` o `master`.
4. Elige Docker Compose como tipo de despliegue.
5. Define:
   - Base Directory: `backend` si el repositorio contiene tambien `frontend`.
   - Compose file: `docker-compose.yml`.
6. En Environment Variables crea las variables requeridas del bloque anterior.
7. Asigna dominio al servicio `backend`, por ejemplo `api.tu-dominio.com`.
8. En Advanced habilita Auto Deploy para que cada push despliegue automaticamente.
9. Guarda y ejecuta Deploy.

Notas para Coolify:

- En Docker Compose, el archivo `docker-compose.yml` es la fuente de verdad del stack.
- Coolify detecta variables escritas como `${VARIABLE}` o `${VARIABLE:?}` y permite configurarlas en la UI.
- Los healthchecks estan definidos en `Dockerfile` y `docker-compose.yml`; Coolify puede leer el estado del contenedor.
- Los logs se ven desde el recurso desplegado, en la pestana Logs. Para fallos de build/deploy, revisa Deployments.

## Auto-deployment por GitHub

Opcion recomendada:

1. Conecta Coolify con GitHub App.
2. Importa el repositorio.
3. Verifica que Auto Deploy este activo en Advanced.
4. Cada `git push` a la rama configurada dispara build y deploy.

Opcion webhook:

1. En Coolify, abre el recurso y ve a Advanced.
2. Activa Auto Deploy.
3. Genera/copia el webhook URL y define un Webhook Secret.
4. En GitHub: Repository Settings -> Webhooks -> Add webhook.
5. Payload URL: URL de Coolify.
6. Secret: el mismo secret configurado en Coolify.
7. Event: Just the push event.
8. Activa SSL verification.

## Cloudflare Tunnel

Puedes usar Tunnel con routing hacia el puerto donde Coolify expone el backend.

### Instalacion de cloudflared en Ubuntu

```bash
curl -L https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64.deb -o cloudflared.deb
sudo dpkg -i cloudflared.deb
cloudflared --version
```

### Tunnel localmente administrado

```bash
cloudflared tunnel login
cloudflared tunnel create cafe-de-barrio-api
cloudflared tunnel list
```

Crea `~/.cloudflared/config.yml` usando el UUID que devuelve `cloudflared tunnel list`:

```yaml
tunnel: TUNNEL_UUID
credentials-file: /home/USUARIO/.cloudflared/TUNNEL_UUID.json

ingress:
  - hostname: api.tu-dominio.com
    service: http://localhost:8080
  - service: http_status:404
```

Crea el DNS del tunnel:

```bash
cloudflared tunnel route dns cafe-de-barrio-api api.tu-dominio.com
```

Prueba el tunnel:

```bash
cloudflared tunnel run cafe-de-barrio-api
```

Instala como servicio systemd:

```bash
sudo cloudflared --config /home/USUARIO/.cloudflared/config.yml service install
sudo systemctl enable --now cloudflared
sudo systemctl status cloudflared
```

Cuando cambies `config.yml`:

```bash
sudo systemctl restart cloudflared
journalctl -u cloudflared -f
```

## Cloudflare DNS y SSL/TLS

1. En Cloudflare, el dominio debe usar nameservers de Cloudflare.
2. El comando `cloudflared tunnel route dns` crea un CNAME a `<UUID>.cfargotunnel.com`.
3. En SSL/TLS usa modo `Full` si el trafico llega por proxy/tunnel.
4. Valida:

```bash
curl -I https://api.tu-dominio.com/actuator/health
```

Resultado esperado:

```text
HTTP/2 200
```

## Monitoreo y logs

Coolify:

- Logs en tiempo real: recurso -> Logs.
- Historial de despliegues: recurso -> Deployments.
- Alertas: configura notificaciones en Coolify para fallos de deployment.

Servidor:

```bash
docker compose logs -f backend
docker compose ps
journalctl -u cloudflared -f
```

Healthcheck:

```bash
curl https://api.tu-dominio.com/actuator/health
```

## Fuentes oficiales consultadas

- Coolify Docker Compose: https://coolify.io/docs/knowledge-base/docker/compose
- Coolify Auto Deploy con GitHub: https://coolify.io/docs/applications/ci-cd/github/auto-deploy
- Coolify Environment Variables: https://coolify.io/docs/knowledge-base/environment-variables
- Coolify Health Checks: https://coolify.io/docs/knowledge-base/health-checks
- Cloudflare Tunnel routing: https://developers.cloudflare.com/tunnel/routing/
- Cloudflare DNS records para Tunnel: https://developers.cloudflare.com/cloudflare-one/networks/connectors/cloudflare-tunnel/routing-to-tunnel/dns/
- Cloudflare Tunnel como servicio Linux: https://developers.cloudflare.com/tunnel/advanced/local-management/as-a-service/linux/
