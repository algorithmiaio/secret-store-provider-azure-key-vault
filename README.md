# secret-store-provider-azure-key-vault

This module is the example of an Azure Key Vault-based secret provider implementation used by the
Algorithmia platform.

This plugin is unsupported by Algorithmia and intended as an example only.

## Getting started

This secret provider module can be added using Algorithmia's [admin functionality for managing secret providers](https://training.algorithmia.com/exploring-the-admin-panel/842511).

## Requirements

To build this plugin the following must be installed:
* sbt 1.3.13 or later
* Java 1.8 or later

As an example, see `Dockerfile.build`, which can be executed with:

`docker build . -f Dockerfile.build -t plugin-dev && docker run -it --rm plugin-dev`

## Building

To build, run:
`sbt assembly`

This will produce a JAR file at:
`target/secret-store-provider-azure-key-vault-assembly-<GIT_SHA>.jar`

This JAR file can then be [uploaded to Algorithmia as a secret provider module](https://training.algorithmia.com/exploring-the-admin-panel/842511) in the admin UI.

Rather than building, files from the [Releases](https://github.com/algorithmiaio/secret-store-provider-azure-key-vault/releases) may also be used.

## Configuration

This plugin requires the following configuration settings (see [Azure setup](#azure-setup), below):
* `vault_url` - Azure Key Vault URL
* `client_id` - Azure Client ID (UUID)
* `client_secret` - Azure token to authenticate to Azure
* `tenant_id` - Azure tenant ID (UUID)

## Azure setup

In the Azure Portal:

*Register an app:*

* Go to the **Azure Active Directory** service and then **App registrations**
* Click on **+ New registration**
  * Provide a **Name** for your app so that it's identifiable
  * Click **Register** at the bottom
* In the details page for the newly registered app:
  * Click **Certificates & secrets** and **+ New client secret**
    * Click **Add** and note the new client secret's **Value** (`client_secret`), which won't be available once you navigate away from the page
  * Note the **Application (client) ID** (`client_id`) and **Directory (tenant) ID** (`tenant_id`) values

*Create a key vault:*

* Go to the **Key Vaults** service and click **+ Create**
* Create a key vault
  * Under **Access policy** click **+ Add Access Policy** and add the app registered above, with **Key Permissions** and **Secret Permissions** selected
  * Configure the vault as desired, and note the vault URL (`vault_url`) once created (this'll likely be called **DNS Name** on the vault details page)

## Testing

### Integration testing

The integration tests expect the configurable parameters to be available in the environment
at run time. To set the variables and run the tests, substitue your values with the PLACEHOLDER_VALUES and run the following:

```
export vault_url="https://YOUR_VAULT_NAME.vault.azure.net/"
export client_secret="CLIENT_SECRET"

export tenant_id="TENANT_ID"  # looks like a uuid
export client_id="CLIENT_ID"  # a different uuid

sbt it:test
```

## How to contribute

If you have proposed changes, feel free to open PRs. However, only submit PRs with
code that can be freely released under the MIT License of this package.
