# ADR 0001: Adresse als Value Object statt Entity modellieren

## Status
✅ **Akzeptiert**  
**Datum:** 2025-05-29  
**Autor:** Vibe Code (mit lucweinbrecht)

---

## Kontext

Im aktuellen Domain Model wird die Klasse `Adresse` als **Entity** modelliert, indem sie von `io.github.domainprimitives.object.Entity` erbt. Dies impliziert, dass Adressen eine eigene Identität haben und als eigenständige Entitäten verwaltet werden.

Bei der Analyse des Domain Models und der Use Cases wurde jedoch festgestellt, dass:

1. **Adressen haben keine eigene Identität** – Sie werden vollständig durch ihre Attribute definiert (Strasse, Hausnummer, Postleitzahl, Ort)
2. **Adressen sind immutable** – Sie werden einmal erstellt und ändern sich nicht
3. **Adressen gehören zu Aggregaten** – Sie sind Teil einer Bestellung und haben keinen eigenen Lebenszyklus
4. **Gleichheit basiert auf Attributen** – Zwei Adressen mit den gleichen Attributen sollten als gleich betrachtet werden

## Entscheidung

**Adresse wird als Value Object modelliert** statt als Entity.

### Änderungen

- `Adresse` erbt von `ValueObject` statt `Entity`
- Implementierung von `getEqualityComponents()` für korrekte Gleichheitsprüfung
- Alle Felder (strasse, hausnummer, postleitzahl, ort) definieren die Identität

### Code-Beispiel

```java
@Getter
public class Adresse extends ValueObject {
    private final Strasse strasse;
    private final Hausnummer hausnummer;
    private final Postleitzahl postleitzahl;
    private final Ort ort;

    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(strasse, hausnummer, postleitzahl, ort);
    }
}
```

## Konsequenzen

### Positiv

✅ **Korrekte DDD-Semantik** – Value Objects haben keine Identität, nur Attribute  
✅ **Bessere Gleichheitsprüfung** – Zwei Adressen mit gleichen Attributen sind gleich  
✅ **Immutability** – Value Objects sind per Definition immutable  
✅ **Einfacheres Testing** – Keine ID-Verwaltung nötig  
✅ **Klarere Domain Language** – Adressen sind Werte, keine Entitäten  

### Negativ

⚠️ **Breaking Change** – Gleichheitsverhalten ändert sich (basierend auf Attributen statt ID)  
⚠️ **Anpassung nötig** – Alle Klassen, die Adresse als Entity behandeln, müssen angepasst werden  

### Neutral

🔄 **Performance** – Kein signifikanter Impact (Value Objects sind leichtgewichtig)  
🔄 **Speicher** – Kein Impact (keine zusätzliche ID mehr)  

## Alternativen

### Alternative 1: Adresse als Entity behalten
- **Vorteile:** Keine Änderungen nötig
- **Nachteile:** Falsche Semantik, Adressen haben keine echte Identität
- **Entscheidung:** ❌ Abgelehnt – Verletzt DDD-Prinzipien

### Alternative 2: Adresse als einfaches POJO (ohne Basis-Klasse)
- **Vorteile:** Maximale Flexibilität
- **Nachteile:** Keine Validierung, keine Domain Primitives Integration
- **Entscheidung:** ❌ Abgelehnt – Verlust von Validierung und Type Safety

### Alternative 3: Adresse als Embedded Value Object in Bestellung
- **Vorteile:** Starke Kopplung mit Aggregat
- **Nachteile:** Weniger wiederverwendbar
- **Entscheidung:** ❌ Abgelehnt – Adresse sollte wiederverwendbar sein

## Referenzen

- [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://domainlanguage.com/ddd/) – Eric Evans
- [Effective Aggregate Design](https://ddd-crew.github.io/) – Vaughn Vernon
- [Domain Primitives Library](https://github.com/domain-primitives/domain-primitives-java)

---

## Anhang

### Migration Guide

Für bestehende Codebasen, die von dieser Änderung betroffen sind:

1. **Gleichheitsprüfungen anpassen**
   ```java
   // Vorher (Entity-basiert)
   adresse1.getId().equals(adresse2.getId())
   
   // Nachher (Value Object-basiert)
   adresse1.equals(adresse2)  // Basierend auf Attributen
   ```

2. **Collections**
   - Sets und Maps funktionieren weiterhin, da `equals()` und `hashCode()` korrekt implementiert sind

3. **Datenbank-Mapping**
   - Keine ID-Spalte mehr nötig
   - Alle Attribute als Spalten speichern

---

*Dieses ADR dokumentiert eine architektonische Entscheidung und sollte nicht ohne Absprache geändert werden.*
