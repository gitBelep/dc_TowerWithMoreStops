# DC Tower-Elevators
##About the solution:
* The elevators going down can stop between the starting and the destination floors and taking passangers, elevators going up can stop and let passangers out (number of passangers is always available).
* I assumed travelling between floors takes 1 second time. 
* In every second will be searched for free elevators to the queueing requests.
* Always the closest free or useable elevator is called to the starting destination.

##Aufgabe
ein Java-Programm für das DC Tower Aufzug-System zu konzipieren und zu implementieren, welches die Aufzuganfragen verwaltet. Eine Anfrage kann sowohl der DC Tower Zutritt im Erdgeschoss, also auch die Rückfahrt vom Stockwerk ins Erdgeschoss sein.
Nachdem das System eine Anfrage bekommen hat, sucht es nach dem nächsten freien Aufzug. Ist keiner sofort frei, soll das System den nächsten einplanen, um die Passagiere abzuholen. Eine Anfrage besteht aus dem aktuellen Stock, dem Zielstock und der Fahrtrichtung als Input.
Für die Logik wie die Aufzüge sich bewegen können, kannst du aus zwei Varianten wählen:
1. Eine Aufzugfahrt kann nur zwischen zwei Stockwerken verkehren (z. B. 0. -> 35. oder 42. -> 0.).
2. Eine Aufzugfahrt kann auch Zwischenstopps haben (z. B. 0. -> 16. -> 24. -> 35. oder 48. -> 15. -> 0.).

##Tipps und Hilfe:
* Schreibe den Code objektorientiert.
* Wenn eine Anfrage ins System kommt, gilt es für das gesamte System und nicht nur
für einen Aufzug. Das liegt daran, dass die Aufzüge keine eigenen Anfrage-Buttons
haben.
* Mache dir keine Gedanken über die Türen der Aufzüge.
* Mache dir auch keine Gedanken über die Edge-Cases.
* Du kannst den Stand des Aufzugs mittels verschiedener Strategien simulieren. Eine
wäre mittels Java Thread, aber wenn du dich damit nicht auskennst, kannst du einfach
bei jeder Anfrage den Stand manuell aktualisieren.

