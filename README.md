# Blockchain API library (Java, v2.1.0)

A fork from the official Java library for interacting with the Blockchain.info API (Java 12 required).

Link: https://github.com/blockchain/api-v1-client-java

The library consists of the following packages:

* `info.blockchain.api.blockexplorer` ([docs](docs/blockexplorer.md)) ([api/blockchain_api][api1])
* `info.blockchain.api.createwallet` ([docs](docs/createwallet.md)) ([api/create_wallet][api2])
* `info.blockchain.api.exchangerates` ([docs](docs/exchangerates.md)) ([api/exchange\_rates\_api][api3])
* `info.blockchain.api.pushtx` ([docs](docs/pushtx.md)) ([pushtx][api7])
* `info.blockchain.api.receive` ([docs](docs/receive.md)) ([api/api_receive][api4])
* `info.blockchain.api.statistics` ([docs](docs/statistics.md)) ([api/charts_api][api5])
* `info.blockchain.api.wallet` ([docs](docs/wallet.md)) ([api/blockchain\_wallet\_api][api6])

In order to use `createwallet` and `wallet` you need to run an instance of [service-my-wallet-v3](https://github.com/blockchain/service-my-wallet-v3).

### Error handling

All methods may throw exceptions caused by incorrectly passed parameters or other problems. If a call is rejected server-side, the `APIException` exception will be thrown. In case of a network error, the `IOException` exception will be thrown.


### Request limits and API keys

In order to prevent abuse some API methods require an API key approved with some basic contact information and a description of its intended use. Please request an API key [here](https://blockchain.info/api/api_create_code).

The same API key can be used to bypass the request limiter.

### Installing and implementing locally

In order to compile/install this library successfully using Java 12 is recommended.

<b>Example: Java Spring Boot project using Gradle</b>

```
repositories {
	mavenLocal()
	...
}

dependencies {
    implementation 'info.blockchain:api:2.1.0'
}

```



[api1]: https://blockchain.info/api/blockchain_api
[api2]: https://blockchain.info/api/create_wallet
[api3]: https://blockchain.info/api/exchange_rates_api
[api4]: https://blockchain.info/api/api_receive
[api5]: https://blockchain.info/api/charts_api
[api6]: https://blockchain.info/api/blockchain_wallet_api
[api7]: https://blockchain.info/pushtx
