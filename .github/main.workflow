workflow "Main workflow" {
  on = "push"
  resolves = [
    "Build Docker image",
    "Login to ECR",
    "Push image to ECR",
  ]
}

action "Maven build" {
  uses = "LucaFeger/action-maven-cli@master"
  args = "clean install"
}

action "Build Docker image" {
  uses = "actions/docker/cli@latest"
  needs = ["Maven build"]
  args = ["build", "-t", "spring-esgi", "."]
}

action "Login to ECR" {
  uses = "actions/aws/cli@latest"
  needs = ["Maven build"]
  secrets = ["AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY"]
  env = {
    AWS_DEFAULT_REGION = "eu-west-3"
  }
  args = "ecr get-login --no-include-email --region $AWS_DEFAULT_REGION | sh"
}

action "Tag image for ECR" {
  uses = "actions/docker/tag@latest"
  needs = ["Login to ECR", "Build Docker image"]
  env = {
    CONTAINER_REGISTRY_PATH = "264868257155.dkr.ecr.eu-west-3.amazonaws.com"
    IMAGE_NAME = "spring-esgi"
  }
  args = ["$IMAGE_NAME", "$CONTAINER_REGISTRY_PATH/$IMAGE_NAME"]
}

action "Push image to ECR" {
  uses = "actions/docker/cli@latest"
  needs = ["Tag image for ECR"]
  env = {
    CONTAINER_REGISTRY_PATH = "264868257155.dkr.ecr.eu-west-3.amazonaws.com"
    IMAGE_NAME = "spring-esgi"
  }
  args = ["push", "$CONTAINER_REGISTRY_PATH/$IMAGE_NAME"]
}
