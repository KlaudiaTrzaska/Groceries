
#!/bin/bash
set -e

cd "$(dirname "$0")"

if [ ! -f .env ]; then
    echo "Brak pliku .env"
    echo "Utwórz go na podstawie .env.example"
    exit 1
fi

set -a
source .env
set +a

mvn spring-boot:run -Dspring-boot.run.profiles=local
