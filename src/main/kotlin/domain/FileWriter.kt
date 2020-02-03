package domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.FileWriter

suspend fun writeToFile(fileName: String, fileContent: String) = coroutineScope {
    launch(Dispatchers.IO) {
        FileWriter(fileName).use { fileWriter ->
            BufferedWriter(fileWriter).use {
                it.write(fileContent)
            }
        }
    }
}
