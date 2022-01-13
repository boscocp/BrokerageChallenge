# Brokerage Challenge
Hello, I implemented this project using visual studio code, for the unit tests I used Maven and JUnity.

I would like to start by explaining that I built the code thinking a lot about what I've been studying for some time about Clean Code and SOLID according to Uncle Bob's (Robert C. Martin) vision and books. Thinking about the DIP (Dependency Inversion) I created the interfaces to both protect methods that should not be accessed, and to facilitate the testing in JUnity, since it needs to be “public” to have access to the test directly and let the test and code run as simple and clean as possible. Another advantage of this would be being able to change implementation details, refactor, optimize, etc. and whoever is accessing through the interface will not be affected, leaving the code unbound. 

I tried to use the SRP in the best way possible, leaving each class and each method with only one responsibility. I also thought about using the interface to follow the ISP as best as possible to avoid dependencies. So, in testing as much as possible, I tried to make the methods less dependent as possible. I thought of the OCP thinking of components that can be easily inserted into a current account, or even the components present in the current account example can change their behavior without affecting it as long as they respect the interface. The code was made following TDD principles. I drew the diagram in the figure below to illustrate how I thought of the architecture for the given problem. 

I tried to separate the “Rules of Business” from the implementation of the technical details. In this way, there is an interface with the communication functions with the database, and a separate technical implementation that would communicate with the database. In this way, all code is protected from changes in technical rules, database changes in case there is a need to change databases, frameworks, etc.

I tried as much as possible to handle all possible exceptions that operations could cause and check in the unit tests. Thinking about data structures, I assumed some questions such as: 1) in asset control, I assumed that “get” operations are more frequent than addition, I used ArrayList because the complexity is O(1) for get and add O(n) ; 2) I used a Hashtable structure to be able to access an asset by String name in O(1) time; 3) in the Movement class, assuming that add is more frequent than get, I used LinkedList because add is O(1). I also used a moving hashtable to get the amount of assets per name in O(1) time.

I used an Enum class to handle the Asset Type and avoid passing an incorrect value. I created a Use Case test that simulates and prints all the records created to verify the system as a whole.

As we are dealing with monetary values, I took care to implement the BigDecimal in the calculations to maximize the precision of the calculation as far as possible, since, for example, when calculating 34.19f multiplied by 10 in float or double it would give an approximate and not an exact value. .

![Corretora](https://github.com/boscocp/CorretoraDesafio/blob/master/Imagem/Corretora.png)


