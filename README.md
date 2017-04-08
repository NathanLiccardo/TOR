# TOR

Ce projet a pour but de créer un réseau simulant le fonctionnement de Tor. Il utilisera plusieurs technologies différentes explicitées au sein du code présent.

## Développement

    Ce projet a été développé en java à l'aide de NetBeans

## Exécution

    Ouvrir les 3 projets dans Netbeans ou les exécuter à l'aide de java -jar "nom"

## Génération clés RSA

    Generate a 2048-bit RSA private key

        $ openssl genrsa -out private_key.pem 2048

    Convert private Key to PKCS#8 format (so Java can read it)

        $ openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt

    Output public key portion in DER format (so Java can read it)

        $ openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
