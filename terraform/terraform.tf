terraform {
  backend "s3" {
    bucket = "junstack"
    key    = "pivot"
    region = "eu-north-1"
  }

  required_version = "~> 1.9.5"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.35"
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
