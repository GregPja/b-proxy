release:
    ./gradlew bootJar
	docker build -t web .
	heroku container:push web -a b-proxy-foxy
	heroku container:release web -a b-proxy-foxy