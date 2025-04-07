# Kata FooBarQuix
 Cette application implémente le kata FooBarQuix avec les règles suivantes:

- Si le nombre est divisible par 3 ou s'il contient 3, la chaîne de caractères contient "FOO"
- Si le nombre est divisible par 5 ou s'il contient 5, la chaîne de caractères contient "BAR"
- Si le nombre contient 7, la chaîne de caractères contient "QUIX"
- La règle "divisible par" est prioritaire sur la règle "contient"
- Le contenu est analysé de gauche à droite
- Si aucune règle n'est vérifiée, le nombre d'entrée est retourné sous forme de chaîne

### Prérequis

Java 17
Maven 3.6+

### Compilation
mvn clean install

### Utilisation
#### L'application expose un endpoint REST:
- GET /api/foobarquix/{number}
#### Où {number} est un nombre entre 0 et 100.

### Exemple:
curl http://localhost:8080/api/foobarquix/3
### Réponse:
"FOOFOO"

### Batch
#### Pour traiter un fichier en mode batch:
java -jar target/kata-0.0.1-SNAPSHOT.jar --batch kata/src/data/input.txt
- Le fichier d'entrée doit contenir un nombre par ligne (entre 0 et 100).
- Le résultat sera écrit dans un fichier avec le suffixe "_output" dans le même répertoire.

### Tests
- mvn test

