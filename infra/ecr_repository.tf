resource "aws_ecr_repository" "repository" {
  name                 = "wehu001"

  image_scanning_configuration {
    scan_on_push = true
  }
}