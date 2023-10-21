# JavaServer
Repo is being used to learn more about java and CI pipelines



Launch docker container with the following command:
// d stands for daemon
// p stands for specific port (host port):(container port)
docker run -dp 8000:8000 "image" 

// verify port is mapped via 'docker ps -a' command
// connect to server via 'java client localhost 8000' command