# Connect4_bot

Il s'agit du bot de connexion et de jeu automatique sur le site BGA ou Board Game Arena. 

Pour exécuter le code source, il faut au préalable vous créer un compte sur Board Game Arena et créer deux variables d'environnement : BGA_ID qui contient votre email de connexion et BGA_PASSWORD qui contient votre mot de passe. 

Il vous faudra ensuite exécuter successivement ces deux commandes dans votre terminal : 
javac -cp "lib/*" -d bin src/*.java => Pour compiler le projet.
java -cp "bin;lib/*" MainBot => Pour exécuter le projet.

Attention, la connexion automatique à Board Game Arena et l'utilisation d'un bot pour jouer des parties est proscrit, il faudra donc que vous jouiez contre vous-même ou contre une personne ayant donné son accord pour jouer conre un bot.