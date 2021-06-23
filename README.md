# secret-store-provider-azure-key-vault

Algorithmia Secret Store Provider for Azure Key Vault

This module is the example of an Azure Key Vault based secret provider implementation used by the
Algorithmia platform.

This plugin is unsupported by Algorithmia and intended as an example only.

## Getting started

This secret provider modules can be added using the admin functionality for managing secret providers.

[Algorithmia Developers - Algorithm Secrets](https://algorithmia.com/developers/platform/algorithm-secrets)

## Requirements

To build this plugin the following must be installed:
* sbt 1.3.13 or later
* java 1.8 or later

As an example, see: Dockerfile.build

This can be executed as:
`docker build . -f Dockerfile.build -t plugin-dev && docker run -it --rm plugin-dev`

## Building

`sbt assembly`

This will produce a JAR file at:
target/secret-store-provider-azure-key-vault-assembly-<GIT_SHA>.jar

This can be uploaded to the Algorithmia Admin console as a secret provider.

Rather than building, files from the releases may be used:
[Releases](https://github.com/algorithmiaio/secret-store-provider-azure-key-vault/releases)

## Configuration

This plugin requires the follow configuration settings:
* vault_url - URL of the Azure Vault to use
* client_id - Azure Client ID (UUID)
* client_secret - Azure token to authenticate to Azure
* tenant_id - Azure Tenant ID (UUID)

## Azure Setup

In Azure Portal - Under Active Directory:

* App Registrations
  * New Registration
  * New client secret (note client secret)
  * Note tenant_id and client_id

* Create Key Vault
* Note vault url
* Access Controls - add app created above with Key and Secret permissions

## Integration tests

The integration tests expect the configurable parameters to be in the environment
at run time.

```
export vault_url="https://your_vault_name.vault.azure.net/"
export client_secret="1.something"

export tenant_id="" looks like a uuid
export client_id="" a different uuid

sbt it:test
```

