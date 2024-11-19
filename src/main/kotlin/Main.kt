package pl.wnasilowski


import java.util.*
import java.util.Arrays.copyOfRange
import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


suspend fun main() {
  //functional.collections.toparticles.main()
  val message = "2E9odo4dC++NFNysdVAFe3aXgqt82JLeGS089fHyQIlO4UHCAnASS2TxQlB3rMSnoe3YEXckixqUCXbDiSrPmQ=="
  val baseKey = "bF/YjTC1yjEUZVfhM1aL2aLMb4WyAvxI0EfXQsaprck="
  val baseHmacKey = "h+Z9ymdMA1TxBAY9cD2EsvdybzsXlqmwwT5L1vqQ2MU="

  val decodedEncryptedMessage = Base64.getDecoder()!!.decode(message)
  val hmac = copyOfRange(decodedEncryptedMessage, decodedEncryptedMessage.size - 32, decodedEncryptedMessage.size)

  val iv = copyOfRange(decodedEncryptedMessage, 0, 16)
  val ivParameter = IvParameterSpec(iv)
  val encryptedMessageAndIv = copyOfRange(decodedEncryptedMessage, 0, decodedEncryptedMessage.size - hmac.size)
  val encryptedMessage = copyOfRange(decodedEncryptedMessage, 16, decodedEncryptedMessage.size - hmac.size)
  val baKey = Base64.getDecoder().decode(baseKey)

  println("keyHex: ${baKey.toHexString()}")
  println("iv: ${iv.toHexString()}")
  println("encryptedMessage: ${encryptedMessage.toHexString()}")
  println("received hmac: ${hmac.toHexString()}")
  val key = SecretKeySpec(baKey, ENCRYPTION_KEY_ALGORITHM)
  val decrypted = decrypt(encryptedMessage, ivParameter, key)
  val result = decrypted.decodeToString()
  val byteHmacKey = Base64.getDecoder().decode(baseHmacKey)
  val hmacKey = SecretKeySpec(byteHmacKey, HMAC_KEY_ALGORITHM)
  val generatedHmac = calculateHmac(encryptedMessageAndIv, hmacKey).toHexString()
  /*val ivParameter = IvParameterSpec(Base64.getDecoder().decode("k3tbMRS4U2FHEd66BwD19A=="))
  val encryptedMessage = Base64.getDecoder().decode("zMCQ3JGAFDXSY57QvyQxPA==")
  val key = SecretKeySpec(Base64.getDecoder().decode("NtnfYJ+ZXSNOkL5+c/ciPO7N0Nm3MOxgPy6RZca6nfs="), ENCRYPTION_KEY_ALGORITHM )
  val result = decrypt(encryptedMessage, ivParameter, key)*/
  println("generated hmac: $generatedHmac")
  println("Decrypted: $result")
}

fun ByteArray.toHexString(): String {
  return joinToString("") { byte -> String.format("%02X", byte) }
}

fun decrypt(encryptedMessage: ByteArray, iv: IvParameterSpec, encryptionKey: SecretKey): ByteArray {
  val cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding")
  cipherDecrypt.init(DECRYPT_MODE, encryptionKey, iv)
  val decrypted = cipherDecrypt.doFinal(encryptedMessage)
  return decrypted
}

val ENCRYPTION_KEY_ALGORITHM = "AES"
val HMAC_KEY_ALGORITHM = "HmacSHA256"

private fun calculateHmac(encryptedMessage: ByteArray, hmacKey: SecretKey) : ByteArray {
  val mac = Mac.getInstance(HMAC_KEY_ALGORITHM)
  mac.init(hmacKey)
  return mac.doFinal(encryptedMessage)
}