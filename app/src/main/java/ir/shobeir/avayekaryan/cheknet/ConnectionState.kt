package ir.shobeir.baladomapp.cheknet

sealed class ConnectionState{
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
