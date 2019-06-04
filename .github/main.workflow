workflow "Main workflow" {
  on = "push"
  resolves = [
    "Build Docker image",
#     "Delete old ECR image",
    "Tag image for ECR",
    "Push image to ECR",
    "AWS DEPLOY SERVICE",
  ]
}

# Build
action "Maven build" {
  uses = "LucaFeger/action-maven-cli@master"
  args = "clean install"
}

# Docker
action "Build Docker image" {
  uses = "actions/docker/cli@master"
  needs = ["Maven build"]
  args = ["build", "-t", "spring-esgi:latest", "."]
}

# AWS
action "Login to ECR" {
  uses = "actions/aws/cli@master"
  needs = ["Maven build"]
  secrets = ["AWS_SECRET_ACCESS_KEY", "AWS_ACCESS_KEY_ID", "AWS_DEFAULT_REGION"]
  args = "ecr get-login --no-include-email --region $AWS_DEFAULT_REGION | sh"
}

# action "Delete old ECR image" {
#   uses = "actions/aws/cli@master"
#   needs = [
#     "Login to ECR",
#     "Build Docker image",
#   ]
#   secrets = ["AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY", "AWS_DEFAULT_REGION"]
#   args = "ecr batch-delete-image --repository-name spring-esgi --image-ids imageTag=latest | sh"
# }

action "Tag image for ECR" {
  uses = "actions/docker/tag@master"
  needs = [
    "Build Docker image",
#     "Delete old ECR image"
    ]
  env = {
    CONTAINER_REGISTRY_PATH = "264868257155.dkr.ecr.eu-west-3.amazonaws.com"
    IMAGE_NAME = "spring-esgi"
  }
  args = ["$IMAGE_NAME:latest", "$CONTAINER_REGISTRY_PATH/$IMAGE_NAME"]
}

action "Push image to ECR" {
  uses = "actions/docker/cli@master"
  needs = [
    "Tag image for ECR",
#     "Delete old ECR image",
  ]
  env = {
    CONTAINER_REGISTRY_PATH = "264868257155.dkr.ecr.eu-west-3.amazonaws.com"
    IMAGE_NAME = "spring-esgi"
  }
  args = ["push", "$CONTAINER_REGISTRY_PATH/$IMAGE_NAME:latest"]
}

action "AWS DEPLOY SERVICE" {
  uses = "actions/aws/cli@master"
  needs = ["Push image to ECR"]
  secrets = ["AWS_DEFAULT_REGION", "AWS_SECRET_ACCESS_KEY", "AWS_ACCESS_KEY_ID"]
  args = "ecs update-service --force-new-deployment --cluster spring-project --service spring-api | sh"
}
