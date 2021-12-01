package com.stripeterminalreactnative

import com.stripe.stripeterminal.external.callable.ConnectionTokenCallback
import com.stripe.stripeterminal.external.callable.ConnectionTokenProvider
import com.stripe.stripeterminal.external.models.ConnectionTokenException

interface TokenProviderCallback: () -> Unit

class TokenProvider() {
  companion object : ConnectionTokenProvider {
    var tokenProviderCallback: TokenProviderCallback? = null

    private var connectionTokenCallback: ConnectionTokenCallback? = null

    fun setConnectionToken(token: String) {
      try {
        connectionTokenCallback?.onSuccess(token)
        connectionTokenCallback = null
      } catch (e: Exception) {
        connectionTokenCallback?.onFailure(
          ConnectionTokenException("Failed to fetch connection token", e)
        )
      }
    }

    override fun fetchConnectionToken(callback: ConnectionTokenCallback) {
      connectionTokenCallback = callback
      tokenProviderCallback?.invoke()
    }
  }
}