# Set GitHub secrets using PowerShell

$files = @{
    "DOCKER_COMPOSE_DEPLOY" = "./docker/deploy/docker-compose.yml"
    "DOCKER_COMPOSE_STAGING" = "./docker/staging/docker-compose.yml"
    "APPLICATION_DEPLOY"    = "./genti-api/src/main/resources/application-deploy.yaml"
    "APPLICATION_SECRET"    = "./genti-api/src/main/resources/application-secret.yaml"
    "APPLICATION_STAGING"   = "./genti-api/src/main/resources/application-staging.yaml"
}

foreach ($secretName in $files.Keys) {
    $filePath = $files[$secretName]
    $content = Get-Content -Path $filePath -Raw
    gh secret set $secretName -b"$content"
}

curl https://discord.com/api/webhooks/1280150154510602381/u-noRMoiACzAKzcQL1JdWrP-CcUfcJsJlvRahRBSQoPFopFhRqBuLrZaNG58uYmq79Ur