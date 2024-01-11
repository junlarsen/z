data "aws_route53_zone" "jun_codes" {
  name = "jun.codes"
}

resource "aws_route53_record" "null_record" {
  name    = "pivot.jun.codes"
  type    = "A"
  zone_id = data.aws_route53_zone.jun_codes.zone_id
  ttl     = 3600
  records = ["127.0.0.1"]
}

resource "aws_route53_record" "auth_pivot_jun_codes_cname" {
  name    = "auth.pivot.jun.codes"
  type    = "CNAME"
  zone_id = data.aws_route53_zone.jun_codes.zone_id
  ttl     = 3600
  records = [aws_cognito_user_pool_domain.domain.cloudfront_distribution]
}

resource "aws_route53_record" "auth_pivot_jun_codes" {
  for_each = {
    for dvo in aws_acm_certificate.auth_pivot_jun_codes.domain_validation_options : dvo.domain_name => {
      name    = dvo.resource_record_name
      record  = dvo.resource_record_value
      type    = dvo.resource_record_type
      zone_id = data.aws_route53_zone.jun_codes.zone_id
    }
  }

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = each.value.zone_id
}
