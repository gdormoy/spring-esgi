workflow "Main workflow" {
  on = "push"
  resolves = [
    "Build Docker image",
    "Push image to ECR",
    "Delete old ECR image",
  ]
}

action "Maven build" {
  uses = "LucaFeger/action-maven-cli@master"
  args = "clean install"
}

action "Build Docker image" {
  uses = "actions/docker/cli@master"
  needs = ["Maven build"]
  args = ["build", "-t", "spring-esgi:latest", "."]
}

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
  args = "ecr batch-delete-image --repository-name 264868257155.dkr.ecr.eu-west-3.amazonaws.com/spring-esgi --image-ids imageTag=latest | sh"
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

