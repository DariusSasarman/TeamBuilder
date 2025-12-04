# Eliminate current PostgresSQL instance
sudo docker compose down -v
# Relaunch postgres server in the background, clearing the data
sudo docker compose up -d
# Check docker containers
docker ps -a :
# Restart docker service
systemctl restart docker
# See what's listening on post 5433
sudo lsof -i :5433
# Remove teambuilder-db container from the
docker rm -f teambuilder-db
# Kill background processes by Id's
sudo kill PROCESSID