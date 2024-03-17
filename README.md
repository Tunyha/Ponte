# Ponte
Ponte backend

Szia!

Az applikáció fútatásához szükséged lesz már feltelepített dockerre.
CMD-ben a következőt írd be és akkor létre jön az adott porton a konténer: 
docker run -d --name postgre --restart unless-stopped -e POSTGRES_PASSWORD=root -p 5433:5432 postgres:12

Amennyiben sikerült elindítani a konténert. Csatlakoztatjuk az applikációnkat az adatbázishoz:
port: 5433
username: postgres
password: root
Utána az applikációnak el lehet indítani a PonteApplication futtatásával. A swagger segítségével le tudjuk ellenőrizni a végpontokat.
Ajánlom a privát böngésző használatát, hogy ne mentsen el felesleges adatokat.

http://localhost:port/swagger-ui.html
vagy
http://localhost:8080/swagger-ui.html

Igyekeztem átláthatóan, stukátlan szét szedni a megfelelő osztályokat.
main:
config: third party libraries-ek konfigurációját tartalmazza.
domain: A létrejövő entitásokat tartalmazza.
controller: Frontend-el kommunikáló réteg.
service: Ahol az üzleti logika folyik.
repository: Az adatbázissal kommunikáló réteg.
dto: Az adatok átvitelére. adatátviteli objektumokat használok, amiket itt tárolok.
exception: bizonyos esetekben a lekérdezések hibákat eredményezhetnek, amiket igyekezem itt lekezelni.
resources: application.yaml tartalmazza az applikáció fútatásához szükséges paraméterek és beállításokat. Amennyiben szeretnél emailt küldeni szükséged lesz smtp szerverre. Én gmail-t használtam erre a célra, a jelszót szándékosan nem írtam bele.
test:
A tesztek fútatásához h2 adatbázist használtam.
IT: integrációs teszteket tartalmazz.
unit: unit teszteket tartalmaz.
resources: application.yaml tartalmazza a tesztek fútatásához szükséges paraméterek és beállításokat.

Sajnos nem úgy alakult a szabadidőm, ahogy terveztem így idő hiányában nem sikerült befejezni a teljes feladatot.
Köszönöm szépen, hogy elolvastad 😊

