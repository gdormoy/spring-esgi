workflow "Main workflow" {
  on = "push"
  resolves = [
    "Build Docker image",
    "Push image to ECR",
    "Delete old ECR image",
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
  secrets = ["AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY"]
  env = {
    AWS_DEFAULT_REGION = "eu-west-3"
  }
  args = "ecr get-login --no-include-email --region $AWS_DEFAULT_REGION | sh"
}

action "Delete old ECR image" {
  uses = "actions/aws/cli@master"
  needs = ["Login to ECR"]
  env = {
    AWS_REPOSITORY_NAME = "spring-esgi"
    VERSION = "latest"
  }
  args = "ecr batch-delete-image --repository-name $AWS_DEFAULT_REGION --image-ids imageTag=$VERSION | sh"
}

action "Tag image for ECR" {
  uses = "actions/docker/tag@master"
  needs = ["Build Docker image", "Delete old ECR image"]
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
    "Delete old ECR image",
  ]
  env = {
    CONTAINER_REGISTRY_PATH = "264868257155.dkr.ecr.eu-west-3.amazonaws.com"
    IMAGE_NAME = "spring-esgi"
  }
  args = ["push", "$CONTAINER_REGISTRY_PATH/$IMAGE_NAME:latest"]
}

