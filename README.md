Maui-2.0
======

Maui 2.0 is a **keyword extraction tool**, based on [Maui][maui], a topic indexing tool. 

##Improvement

Major improvement over original Maui:

- Upgraded one of its dependent libraries, [Wikipedia Miner][wm] from 1.1 to 1.2, resulting in much faster program execution speed(almost 10 times faster).
- Augmented with keyword extraction Java API. Original one has only commandline tool.


##Usage

###Preparing Wikipedia Miner
Maui 2.0 depends [Wikipeida Miner 1.2][wm], which requires the user to build the Wikipedia summary database. 

Instruction on how to set up Wikipedia Miner 1.2 is located at [wm-setup]. 

If you need to Wikipedia Dump summary file of July, 2014, please contact me(see the address at the very bottom).

###Buidling the model

Run

`> java -cp "lib/*:src" maui.main.MauiTopicExtractor -l path-to-trainig-data -m model-name -v wikipedia -c path-to-wikipedia-miner-configuration-file`

- For *wikipedia miner configuration file*, refer to [here](https://github.com/dnmilne/wikipediaminer/wiki/Installing-the-java-api#create-configuration-file)
- A typical *trianing data path* would be `data/wikipedia_indexing/train/`


###Keyword extraction(Command line)

Given a list of text files for which you would the keywords to be extracted, an example usage is:

`> java -cp "lib/*:src" maui.main.MauiTopicExtractor -l path-to-test-data -m model-name -v wikipedia -c path-to-wikipedia-miner-configuration-file`

- *test data path* is the directory containing text files each being a document you would its keywords to be extracted. 
- Note text files in *test data path* should have extension `.txt`


###Keyword extraction(Java API)

The function can also be called through Java API. An Scala example is:

    import maui.main.MauiTopicExtractor

    val opts = Array("-v", "wikipedia", "-m", "test", "-c", "/data/mmbrain/model/en.xml")
    val extractor = new MauiTopicExtractor()
    extractor.setOptions(opts)
    extractor.loadModel()
    
    extractor.configMauiFilter()

    println("Keyphrases are: " + extractor.extractKeyphrasesFromText(text).toList.toString)


##Contact

[Han Xiao](mailto:xiaohan2012@gmail.com)


[maui]: https://code.google.com/p/maui-indexer/
[wm]: http://wikipedia-miner.cms.waikato.ac.nz/
[wm-setup]: https://github.com/dnmilne/wikipediaminer/wiki/Installing-the-java-api