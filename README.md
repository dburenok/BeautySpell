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

I'm interested in this project because I want to learn how to implement a word suggestion algorithm. I also think this is a simple, robust way to get spellcheck and text beautification functionality without any hassle.

## User Stories

- As a user, I want to enter separate blocks of text (documents) and save them to a document library
- As a user, I want to select a previously saved document and check how many typos are present in it
- As a user, I want to remove and trim unnecessary whitespace in a document
- As a user, I want to make sure the periods and commas in a document are placed correctly

- As a user, I want to save the document library to file
- As a user, I want to open a previously saved document library and continue working on it

## Phase 4: Task 2
- Test and design a class in your model package that is robust.  You must have at least one method that throws a checked exception.  You must have one test for the case where the exception is expected and another where the exception is not expected.
- Make appropriate use of the Map interface somewhere in your code. 
- Make appropriate use of a bi-directional association somewhere in your code.  So, there must be a need for each class in the association to call methods on the other class. 

I added robustness to the PredictiveSpellchecker class, in both the constructor and cartesianProduct(). If a PC is constructed with a null dictionary, the PC throws a DictException. If PC.cartesianProduct() is passed two sets, and one of them is empty, it throws a CartesianProductException.

I also used a HashMap (specifically, HashMap<String, HashSet<String>>) to store the neighbour keys on a keyboard, which are used to generate the typing paths for the spellchecking algorithm.

I also implemented a bi-directional association between Document and DocumentLibrary, making sure that the proper mutual behaviour occurs. If you set a Document's DL, it calls the DL's addDocument method, and vice versa.

## Phase 4: Task 3
If I had more time to work on the project, I would refactor the Document and DocumentLibrary classes to reduce the coupling between them. I would also introduce a FileHandler class that took care of the reading and writing the files, to simplify the persistance implementation.