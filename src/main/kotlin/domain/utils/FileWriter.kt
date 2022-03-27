package domain.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.FileWriter

suspend inline fun writeToFile(fileName: String, fileContent: String) = coroutineScope {
    launch(Dispatchers.IO) {
        FileWriter(fileName).use { fileWriter ->
            BufferedWriter(fileWriter).use { bufferedWriter ->
                bufferedWriter.write(fileContent)
            }
        }
    }
}
