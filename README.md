# Secret Santa
_Alternatively: Yeet42.java the game_

Let me say this upfront, I'm sorry. This _thing_ should take you about an hour, but that 
really depends on how quick you are with figuring out what the code is doing.

This small challenge revolves around a password checker (I know, very original). 
The password checker takes in a password, obviously, and it passes it through four password
verifiers, which all check parts of the password. If all four verifiers accept the password, 
it is considered correct.

Your task is to find the password (or anything for that matter), which the password accepts.

To make sure you aren't completely in the dark: The password is something that 
you and all _spoorknorren_ can agree on.

If you get stuck at any point, I put some comments in the code that might be able 
to guide you a bit. If this doesn't help and you're still stuck, you can ask me for hints.
The first three verifiers all have four possible hints (where the last one just gives the answer), 
and the final verifier has a single hint (you'll find out why).

Good luck,

Secret Santa


### Running the checker
#### Advised
The easiest way to run the checker is by using docker:
* Pull the image (finitum/secret-santa:dany)
* Run the container with an interactive terminal (docker run -i -t ...)

#### Alternative
If you don't have docker, or cannot use it you can also run the jars directly. 
Make sure the jars are in the same folder, or it will not work. You need to run the ss-wrapper.jar  
Warning: Due to the way the password checker works, it might break on different versions 
of Java (that's why docker is advised).

The jars can also be found in the .zip, in /jars/