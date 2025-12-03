# Eliminate current PostgresSQL instance
sudo docker compose down
# Relaunch postgres server in the background, clearing the data
sudo docker compose up -d