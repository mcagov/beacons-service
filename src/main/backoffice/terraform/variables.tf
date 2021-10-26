variable "aws_region" {
  type        = string
  description = "The AWS region in which to create resources"
}

variable "cloudfront_domain_name" {
  type        = string
  description = "The domain name where CloudFront will be presented from"
}
variable "acm_certificate_arn" {
  type        = string
  description = "The certificate arn from AWS"
  default     = "arn:aws:acm:us-east-1:232705206979:certificate/91e17b33-5a3b-4ac9-a386-68711286c363"
}
