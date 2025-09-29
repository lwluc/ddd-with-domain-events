# DDD With Domain Events Demo

## 📌 Problemstellung

In vielen Fachdomänen – z. B. im E-Commerce – gibt es Objekte wie **Adressen**, die je nach Kontext unterschiedlich behandelt werden müssen:

* **Als Entity / Value Object**:
  Eine Adresse gehört nur zu einem Aggregat und hat keinen eigenen Lebenszyklus.
  Beispiel: Rechnungsadresse eines Kunden.

* **Als Referenz auf eine Entity in einem anderen Bounded Context**:
  Eine Adresse wird zentral verwaltet und wiederverwendet.
  Beispiel: Versand an eine Packstation oder einen Pickup Point aus dem Logistik-Kontext.

Das Projekt zeigt anhand eines einfachen **E-Commerce-Szenarios**, wie eine **Bestellung** sowohl eine **ad-hoc Adresse (Entity / Value Object)** als auch eine **Referenz auf eine Logistik-Entität (Abholhort)** nutzen kann.

👉 **Ziel:** Verdeutlichen, wie DDD mithilfe von **Domain Events** für die Kommunikation zwischen Domänen sowie durch **Value Objects, Entities und Bounded Contexts** sauber zwischen unterschiedlichen Anforderungen an „Adressen“ differenziert.
