package com.algorithmia.plugin.azurekeyvault;

import com.algorithmia.sdk.plugin.secrets.SecretIdentifier;

public class BasicSecretIdentifier implements SecretIdentifier
{
  private final String id;

  public BasicSecretIdentifier(String id)
  {
    this.id = id;
  }

  @Override
  public String getProviderKey()
  {
    return id;
  }
  
  @Override
  public String toString()
  {
    return id;
  }
}
