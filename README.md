# Promise.kt

[![Twitter: @Kidmar](https://img.shields.io/badge/contact-@Kidmar-blue.svg?style=flat)](https://twitter.com/Kidmar)
[![GitHub license](https://img.shields.io/github/license/kimar/Promise.kt.svg)](https://github.com/kimar/Promise.kt/blob/master/LICENSE.md)
[![Build Status](https://travis-ci.org/kimar/Promise.kt.svg?branch=master)](https://travis-ci.org/kimar/Promise.kt)
![Kotlin](https://img.shields.io/badge/language-Kotlin-orange.svg)

## tl;dr
A very simple Kotlin Promise library I've written, after coming up with the [same library for Swift on my iPad years ago](https://github.com/kimar/Promise.swift).

Writte in just 26 lines of code.

## Usage

Rather simple and more for reference than actually to be used is this very simple implementation of a *Promise* library.

**Succeeding**

```kotlin
Promise<String> { resolve, reject ->
	resolve("Bazingaaaa!")
}.then { result ->
	System.out.print(result) // Bazingaaaa!
}
```

**Failing** 

```kotlin
Promise<String> { resolve, reject -> 
	reject(AnError())
}.fail { error ->
	System.out.print("An error occured $error")
}
```

**Chaining**

```kotlin
Promise<String> { resolve, reject -> 
	resolve(1)
}.then<Int> { result -> 
	return Promise<Int> { resolve, reject -> 
		resolve(result + 1)
	}
}.then<Int> { result -> 
	return Promise<Int> { resolve, reject -> 
		resolve(result + 1)
	}
}.then { result -> 
	System.out.print(result) // 3
}
```

## Contributing

Please take a moment to read and understand the [Berlin Code of Conduct](CODE_OF_CONDUCT.md).

## License

See [LICENSE.md](LICENSE.md)
