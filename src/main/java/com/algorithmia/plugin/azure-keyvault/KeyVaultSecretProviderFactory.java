package com.algorithmia.plugin.azurekeyvault;

import com.algorithmia.sdk.plugin.secrets.SecretProvider;
import com.algorithmia.sdk.plugin.secrets.SecretProviderFactory;
import java.util.Map;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.ClientSecretCredential;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.SecretClient;


public class KeyVaultSecretProviderFactory implements SecretProviderFactory {


    @Override
    public SecretProvider create(Map<String, String> config) {
      require(config, "vault_url"); 
      require(config, "client_secret");
      require(config, "tenant_id");
      require(config, "client_id");


      ClientSecretCredential cred = 
      new ClientSecretCredentialBuilder()
        .clientSecret(config.get("client_secret"))
        .tenantId(config.get("tenant_id"))
        .clientId(config.get("client_id"))
        .build();

			 SecretClient secret_client = new SecretClientBuilder()
     		.credential(cred)
     .vaultUrl(config.get("vault_url"))
     .buildClient();

      return new KeyVaultSecretProvider(secret_client);
    }

    private void require(Map<String, String> config, String field)
    {
      if (!config.containsKey(field)) throw new RuntimeException("KeyVaultSecretProviderFactory Missing required config field: " + field);
    }
}
