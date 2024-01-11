resource "aws_acm_certificate" "auth_pivot_jun_codes" {
  domain_name       = "auth.pivot.jun.codes"
  validation_method = "DNS"

  provider = aws.us-east-1
}

resource "aws_acm_certificate_validation" "auth_pivot_jun_codes" {
  certificate_arn         = aws_acm_certificate.auth_pivot_jun_codes.arn
  validation_record_fqdns = [for record in aws_route53_record.auth_pivot_jun_codes : record.fqdn]

  provider = aws.us-east-1
}
