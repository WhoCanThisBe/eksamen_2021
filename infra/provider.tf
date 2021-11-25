terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "3.56.0"
    }
  }
  backend "s3" {
    bucket = "pgr301-wehu001-terraform"
    key    = "pgr301-wehu001-terraform.state"
    region = "eu-west-1"
  }
}