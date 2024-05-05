.SECONDEXPANSION:

setup:
	ifeq ($(OS),Windows_NT)
		winget install Git.Git Docker.DockerDesktop
	else
		UNAME_S := $(shell uname -s)
			ifeq ($(UNAME_S), Linux)
					sudo apt update && sudo apt install apt-transport-https \
					ca-certificates curl software-properties-common git

					sudo apt-get update
					sudo apt-get install ca-certificates curl
					sudo install -m 0755 -d /etc/apt/keyrings
					sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
					sudo chmod a+r /etc/apt/keyrings/docker.asc

					echo \
						"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
						$(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
						sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
					sudo apt-get update
					sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
			endif
			ifeq ($(UNAME_S), Darwin)
					brew install git docker
			endif
	endif

up:
	sudo docker-compose up

build:
	sudo docker-compose build

down:
	sudo docker-compose down

stop:
	sudo docker-compose stop

clean:
	docker rm $(docker ps -qa)
	docker rmi $(docker images -a -q)
	docker volume rm $(docker volume ls -f dangling=true -q)