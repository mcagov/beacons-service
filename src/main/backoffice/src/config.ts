export const applicationConfig = {
  apiUrl: "http://localhost:8080/spring-api",
  azureADClientId: process.env.REACT_APP_AAD_CLIENT_ID,
  azureADTenantId: process.env.REACT_APP_AAD_TENANT_ID,
  azureADAPIScopeURI: `api://${process.env.REACT_APP_AAD_CLIENT_ID}/access_as_user`,
  apiTimeoutMs: 30000,
};
