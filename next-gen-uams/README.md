START KEYCLOAK DOCKER CONTAINER
===============================
docker run -p 127.0.0.1:8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin -v /Users/manikanta/Documents/keycloak_data:/opt/keycloak/data  quay.io/keycloak/keycloak:26.3.2 start-dev

KeyCloak URL's list
===============================
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

NextGenBank Users
===============================
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

NextGenBank Clients
====================
1)next-gen-uams(service-service communication)
  attached admin role used to create users,roles in keycloak using rest api
2)next-gen-ui
  client used to get the token by using authrization code flow

