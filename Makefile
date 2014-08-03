all: original

original: model
	java -cp "lib/*:src" maui.main.MauiTopicExtractor -l data/mytest -m test -v wikipedia -c ~/Downloads/wikipedia-miner-1.2.0/configs/en.xml

model: compile
	java -cp "lib/*:src" maui.main.MauiModelBuilder -l data/wikipedia_indexing/train/ -m test -v wikipedia -c ~/Downloads/wikipedia-miner-1.2.0/configs/en.xml
	cp test /data/tmp/maui/

compile:
	javac -cp "lib/*:src" src/maui/stopwords/StopwordsEnglish.java
	javac -cp "lib/*:src" src/maui/filters/MauiFilter.java
	javac -cp "lib/*:src" src/maui/main/MauiModelBuilder.java
	javac -cp "lib/*:src" src/maui/main/MauiTopicExtractor.java
