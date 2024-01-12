terraform {
  backend "s3" {
    bucket = "junstack"
    key    = "pivot"
    region = "eu-north-1"
  }

  required_version = "~> 1.6.6"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }

    vercel = {
      source  = "vercel/vercel"
      version = "~> 1"
    }
  }
}

provider "aws" {
  region = "eu-north-1"

  default_tags {
    tags = {
      Project = "pivot"
    }
  }
}

provider "aws" {
  alias  = "us-east-1"
  region = "us-east-1"

  default_tags {
    tags = {
      Project = "pivot"
    }
  }
}
