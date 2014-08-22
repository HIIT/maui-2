import maui.main.MauiTopicExtractor

val opts = Array("-v", "wikipedia", "-m", "/path/to/model-file", "-c", "/path/to/wikipedia-miner/config.xml")

val extractor = new MauiTopicExtractor()
val text = "The Liber Eliensis (\"Book of Ely\") is a 12th-century English chronicle and history, written in Latin. Composed in three books, it was written at Ely Abbey on the island of Ely in the fenlands of eastern Cambridgeshire. Ely Abbey became the cathedral of a newly formed bishopric in 1109."

extractor.setOptions(opts)
extractor.loadModel()

extractor.configMauiFilter()

println("Keyphrases are: " + extractor.extractKeyphrasesFromText(text).toList.toString)
