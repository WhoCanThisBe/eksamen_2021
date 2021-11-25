terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "2.49.0"
    }
  }
  backend "s3" {
    bucket = "pgr301-wehu001-terraform"
    key    = "terraform.state"
    region = "eu-west-1"
  }

}