# next-gen-uams

UAMS_DB_HOST
UAMS_DB_USER
UAMS_DB_PASSWORD
EUREKA_HOST
NEXT_GEN_UAMS_CLIENT_SECRET
KEYCLOACK_TOKEN_URL
KEYCLOACK_USER_URL
KAFKA_BOOTSTRAP_SERVERS

# next-gen-accounts

NEXT_GEN_ACCOUNTS_DB_HOST
NEXT_GEN_ACCOUNTS_DB_USER
NEXT_GEN_ACCOUNTS_DB_PASSWORD
EUREKA_HOST
KAFKA_BOOTSTRAP_SERVERS

# next-gen-gateway

KEYCLOACK_JWK_SET_URL
EUREKA_HOST

# next-gen-configserver

# next-gen-eureka

docker run -p 127.0.0.1:8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin -v /Users/manikanta/Documents/keycloak_data/:/opt/keycloak/data/h2 quay.io/keycloak/keycloak:26.3.2 start-dev

# START KEYCLOAK DOCKER CONTAINER

docker run -p 127.0.0.1:8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin -v /Users/manikanta/Documents/keycloak_data/:/opt/keycloak/data/h2 quay.io/keycloak/keycloak:26.3.2 start-dev

# KeyCloak URL's list

http://localhost:8080/realms/next-gen-bank/.well-known/openid-configuration
"issuer": "http://0.0.0.0:8080/realms/next-gen-bank",
"authorization_endpoint": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/auth",
"token_endpoint": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/token",
"introspection_endpoint": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/token/introspect",
"userinfo_endpoint": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/userinfo",
"end_session_endpoint": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/logout",
"frontchannel_logout_session_supported": true,
"frontchannel_logout_supported": true,
"jwks_uri": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/certs",
"check_session_iframe": "http://0.0.0.0:8080/realms/next-gen-bank/protocol/openid-connect/login-status-iframe.html",

# NextGenBank Users

{
"attributes": {
"attribute_key": "test_value"
},
"credentials": [
{
"temporary": false,
"type": "password",
"value": "Ritesh@2027"
}
],
"username": "manikanta92",
"firstName": "manikanta",
"lastName": "mutyala",
"email": "suryamutyala757@gmail.com",
"emailVerified": false,
"enabled": true
}

# NextGenBank Clients

1)next-gen-uams(service-service communication)
admin role used to create users,roles in keycloak using rest api
2)next-gen-ui
client used to get the token by using authrization code flow

# PORTS

Keycloak:8080
Gateway:8081
Uams:8082
Accounts:8083
configserver:8084

client_id: next-gen-uams
client_secret:
jwk-set-uri: http://localhost:8080/realms/next-gen-bank/protocol/openid-connect/certs

services:
kafka(need to start zookeeper and three brokers setup) 
keycloak(need to start keycloak)
mysql(need to start mysql)
next-gen-configserver(config microservice)
next-gen-eureka(eureka microservice)
next-gen-gateway(gateway microservice)(Load balancer)
next-gen-uams(uams microservice)
next-gen-accounts(accounts microservice)

minikube service keycloak --url  
http://127.0.0.1:60688
❗  Because you are using a Docker driver on darwin, the terminal needs to be open to run it.

Local
====
client_id:next-gen-uams
client_sec:p3sqTHth08sx8UN5WGmVXkOic83HCyuV
minikube
===
client_id:next-gen-uams
client_sec:RFymUaVdJJkPN1yhueVnJ4paSx1eTB3s
