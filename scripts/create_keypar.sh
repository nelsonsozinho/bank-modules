#!/bin/bash

keytool -genkeypair \
-alias loan \
-keyalg RSA \
-keysize 2048 \
-storetype PKCS12 \
-keystore api-keystore.p12 \
-validity 3050