output "s3_bucket_name" {
  value = aws_s3_bucket.backoffice_static.id
}

output "s3_website_address" {
  value = aws_s3_bucket.backoffice_static.website_endpoint
}

output "cloudfront_distribution_address" {
  value = aws_cloudfront_distribution.s3_distribution.domain_name
}