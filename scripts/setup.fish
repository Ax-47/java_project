#!/usr/bin/env fish

echo "Setting up git hooks..."

git config core.hooksPath hooks

chmod +x hooks/pre-commit
chmod +x hooks/pre-push
chmod +x hooks/commit-msg

echo "Git hooks installed!"

echo "Generating RSA keys..."

mkdir -p src/main/resources/keys

# generate private key
openssl genpkey -algorithm RSA -out src/main/resources/private_key_pkcs8.pem -pkeyopt rsa_keygen_bits:2048

# generate public key
openssl rsa -pubout -in src/main/resources/private_key_pkcs8.pem -out src/main/resources/public_key.pem

echo "Keys generated in src/main/resources/keys"