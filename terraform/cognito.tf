resource "aws_cognito_user_pool" "cognito" {
  name                     = "pivot"
  mfa_configuration        = "OFF"
  auto_verified_attributes = ["email"]
}

resource "aws_cognito_identity_provider" "google" {
  provider_name = "Google"
  provider_type = "Google"
  user_pool_id  = aws_cognito_user_pool.cognito.id

  provider_details = {
    authorize_scopes              = "email profile openid"
    client_id                     = data.aws_secretsmanager_secret_version.google_client_id.secret_string
    client_secret                 = data.aws_secretsmanager_secret_version.google_client_secret.secret_string
    token_url                     = "https://www.googleapis.com/oauth2/v4/token"
    token_request_method          = "POST"
    oidc_issuer                   = "https://accounts.google.com"
    authorize_url                 = "https://accounts.google.com/o/oauth2/v2/auth"
    attributes_url                = "https://people.googleapis.com/v1/people/me?personFields="
    attributes_url_add_attributes = "true"
  }

  attribute_mapping = {
    email    = "email"
    username = "sub"
  }
}

resource "aws_cognito_user_pool_domain" "domain" {
  user_pool_id    = aws_cognito_user_pool.cognito.id
  domain          = "auth.pivot.jun.codes"
  certificate_arn = aws_acm_certificate.auth_pivot_jun_codes.arn

  depends_on = [
    aws_acm_certificate_validation.auth_pivot_jun_codes,
  ]
}

resource "aws_cognito_user_pool_client" "pivot" {
  name         = "pivot"
  user_pool_id = aws_cognito_user_pool.cognito.id

  supported_identity_providers = ["Google"]

  access_token_validity                = 1
  allowed_oauth_flows_user_pool_client = true
  allowed_oauth_flows                  = ["code"]
  allowed_oauth_scopes                 = ["email", "openid", "profile"]

  generate_secret = true
  callback_urls   = ["http://localhost:3000/api/auth/callback/cognito"]
}
