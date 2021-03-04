# BeautySpell
## Automatic spellcheck and text beautifier

BeautySpell will:
- Receive text from the user (either by input or file select)
- Run a spellcheck by comparing each word against a dictionary file
- When a grammatical error is found, suggest a replacement word (using a simple matching algorithm), or give the option to manually enter the correct word
- Perform whitespace correction (removing double whitespaces, adjusting period and comma positions)
- Save the session to continue working at a later time
- Run in hassle-free "automatic" mode which produces a quick (but rough) result

This project will be useful for drafting an essay as you won't have to click on each word individually (like in Word or Pages). The incorrect words will be served in a controlled way, letting you work systematically through your text, rather than haphazardly looking for the red lines in your text. The automatic word suggestions can be easily overwritten during the spellcheck process.

I'm interested in this project because I want to learn how to implement a word suggestion algorithm. I also think this is a simple, robust way to get spellcheck and text beautyfication functionality without any hassle.

## User Stories

- As a user, I want to enter separate blocks of text (documents) and save them to a document library
- As a user, I want to select a previously saved document and check how many typos are present in it
- As a user, I want to remove and trim unnecessary whitespace in a document
- As a user, I want to make sure the periods and commas in a document are placed correctly

- As a user, I want to save the document library to file
- As a user, I want to open a previously saved document library and continue working on it