package com.algorithmia.plugin.azurekeyvault;

import com.algorithmia.sdk.plugin.secrets.Secret;
import com.algorithmia.sdk.plugin.secrets.SecretIdentifier;
import com.algorithmia.sdk.plugin.secrets.SecretNotFoundException;
import com.algorithmia.sdk.plugin.secrets.SecretProvider;
import com.algorithmia.sdk.plugin.secrets.SimpleSecret;
import java.time.Instant;
import java.util.UUID;
import java.util.Map;
import java.util.TreeMap;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.security.keyvault.secrets.models.SecretProperties;
import com.google.common.collect.ImmutableMap;

public class KeyVaultSecretProvider implements SecretProvider {

  private final SecretClient secret_client;

  public KeyVaultSecretProvider(SecretClient secret_client)
  {
    this.secret_client = secret_client;

  }


  private KeyVaultSecret getSecretInternal(SecretIdentifier id) 
    throws SecretNotFoundException
  {
    try
    {
      return secret_client.getSecret(id.getProviderKey());
    }
    catch(com.azure.core.exception.ResourceNotFoundException e)
    {
      throw new SecretNotFoundException(e.toString());
    }
  }

  @Override
  public Secret getSecret(SecretIdentifier id) throws SecretNotFoundException 
  {
    KeyVaultSecret kv = getSecretInternal(id);
    return new SimpleSecret(kv.getValue(), getTtl());
  
  }

  @Override
  public String getSecretVersion(SecretIdentifier id) throws SecretNotFoundException
  {
    KeyVaultSecret kv = getSecretInternal(id);
    return kv.getProperties().getVersion();
  }

  @Override
  public SecretIdentifier createSecret(String value)
  {
    UUID secret_id_uuid = UUID.randomUUID();
    SecretIdentifier id = new BasicSecretIdentifier(secret_id_uuid.toString());
    KeyVaultSecret kv = new KeyVaultSecret(id.getProviderKey(), value);
    secret_client.setSecret(kv);
    return id;
  }

  @Override
  public String updateSecret(SecretIdentifier id, String value) throws SecretNotFoundException 
  {
    getSecretInternal(id);

    KeyVaultSecret kv = new KeyVaultSecret(id.getProviderKey(), value);
    secret_client.setSecret(kv);

    return getSecretInternal(id).getProperties().getVersion();

  }

  @Override
  public boolean deleteSecret(SecretIdentifier id) throws SecretNotFoundException 
  {
    try
    {
      secret_client.beginDeleteSecret(id.getProviderKey()).waitForCompletion();

      return true;
    }
    catch(com.azure.core.exception.ResourceNotFoundException e)
    {
      return false;
    }

  }

  @Override
  public Instant secretLastUpdated(SecretIdentifier id) throws SecretNotFoundException
  {
    KeyVaultSecret kv = getSecretInternal(id);

    return kv.getProperties().getUpdatedOn().toInstant();
  }

  @Override
  public int getTtl() {
    return 0;
  }

  @Override
  public boolean allowsCreate() {
    return true;
  }

  @Override
  public boolean allowsUpdate() {
    return true;
  }

  @Override
  public boolean allowsDelete() {
    return true;
  }

}
