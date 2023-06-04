#!/bin/sh
source .env
openapi -i $NEXT_PUBLIC_API_URL/api-docs/v3.yaml -o src/services