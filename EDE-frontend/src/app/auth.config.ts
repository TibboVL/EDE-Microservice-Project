import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  // issuer: 'https://accounts.google.com', // Replace with your issuer URL
  issuer: 'https://api-tibbovl.cloud.okteto.net', // Replace with your issuer URL
  strictDiscoveryDocumentValidation: false, // Set to true if needed, based on your requirements
  clientId:
    '466655062537-ocs19qdjbpdkupnpi0ff86lq3gofngis.apps.googleusercontent.com', // Replace with your client ID
  responseType: 'code',
  redirectUri: window.location.origin + '/home',
  scope: 'openid profile email', // Ask offline_access to support refresh token refreshes

  showDebugInformation: true,
};
