resource "aws_secretsmanager_secret" "google_client_id" {
  name = "pivot/pivot/google-client-id"
}

data "aws_secretsmanager_secret_version" "google_client_id" {
  secret_id = aws_secretsmanager_secret.google_client_id.id
}

resource "aws_secretsmanager_secret" "google_client_secret" {
  name = "pivot/pivot/google-client-secret"
}

data "aws_secretsmanager_secret_version" "google_client_secret" {
  secret_id = aws_secretsmanager_secret.google_client_secret.id
}
