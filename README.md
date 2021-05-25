# secret-store-provider-azure-key-vault
Secret Store Provider for Azure Key Vault


## Integration tests


In Azure Portal - Under Active Directory:

* App Registrations
  * New Registration
  * New client secret (note client secret)
  * Note tenant_id and client_id and client secret

* Create Key Vault
* Note vault url
* Access Controls - add app created above with Key and Secret permissions

The integration tests expect the configurable parameters to be in the environment
at run time.

```
export vault_url="https://your_vault_name.vault.azure.net/"
export client_secret="1.something"

export tenant_id="" looks like a uuid
export client_id="" a different uuid

sbt it:test
```

Values are stored in Lastpass under Shared-Engineering folder as "secret-store-provider-azure-key-vault"


