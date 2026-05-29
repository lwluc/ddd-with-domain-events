# ADR 0002: Optional<T> für optionale Felder in Domain Events

## Status
✅ **Akzeptiert**  
**Datum:** 2025-05-29  
**Autor:** Vibe Code (mit lucweinbrecht)

---

## Kontext

In den Domain Event-Klassen (`BestellungEvent`, `BestellungsaufgabeEvent`, etc.) wurden optionale Felder wie `adresse` und `abholortReferenz` als normale Attribute mit `null`-Werten modelliert:

```java
// Vorher
private final Adresse adresse;        // kann null sein
private final AbholortReferenz abholortReferenz;  // kann null sein
```

Dies führt zu mehreren Problemen:

1. **Implizite Null-Werte** – Es ist nicht klar, ob `null` ein gültiger Zustand ist
2. **Fehlende Type Safety** – Der Compiler erzwingt keine bewusste Behandlung von `null`
3. **Schlechte API-Dokumentation** – Nicht offensichtlich, welche Felder optional sind
4. **NullPointerException-Risiko** – Unbewusster Zugriff auf `null`-Felder

## Entscheidung

**Verwendung von `Optional<T>` für alle optionalen Felder in Domain Events.**

### Änderungen

- `BestellungEvent`: `adresse` und `abholortReferenz` als `Optional<T>`
- `BestellungsaufgabeEvent`: `isBestellungsspezifischeAdresse()` verwendet `isPresent()`
- Alle Zugriffe auf diese Felder müssen `Optional`-Methoden verwenden

### Code-Beispiel

```java
@Getter
public abstract class BestellungEvent extends DomainObject implements DomainEvent {
    private final Kundennummer kundennummer;
    private final Optional<Adresse> adresse;           // Explizit optional
    private final Optional<AbholortReferenz> abholortReferenz;  // Explizit optional

    protected BestellungEvent(Kundennummer kundennummer, Adresse adresse) {
        this.kundennummer = kundennummer;
        this.adresse = Optional.ofNullable(adresse);
        this.abholortReferenz = Optional.empty();
        // ...
    }
}
```

## Konsequenzen

### Positiv

✅ **Explizite Null-Safety** – Es ist klar, welche Felder optional sind  
✅ **Type Safety** – Compiler erzwingt bewusste Behandlung von Optional-Werten  
✅ **Bessere API-Dokumentation** – `Optional<T>` dokumentiert die Optionalität  
✅ **Keine NullPointerExceptions** – Bewusster Umgang mit fehlenden Werten  
✅ **Selbst-dokumentierender Code** – Keine zusätzliche Dokumentation nötig  

### Negativ

⚠️ **Etwas mehr Boilerplate** – `Optional.of()`, `orElse()`, `isPresent()` etc.  
⚠️ **Anpassung nötig** – Alle Zugriffe auf diese Felder müssen angepasst werden  

### Neutral

🔄 **Performance** – Minimaler Overhead durch Optional (vernachlässigbar)  
🔄 **Speicher** – Minimaler Speicheroverhead (ein Optional-Objekt pro Feld)  

## Alternativen

### Alternative 1: Null-Werte behalten
- **Vorteile:** Weniger Boilerplate
- **Nachteile:** Keine Type Safety, implizite Null-Werte
- **Entscheidung:** ❌ Abgelehnt – Verletzt Null-Safety-Prinzipien

### Alternative 2: @Nullable Annotation
- **Vorteile:** Weniger Boilerplate als Optional
- **Nachteile:** Keine Compiler-Unterstützung, nur Dokumentation
- **Entscheidung:** ❌ Abgelehnt – Keine echte Type Safety

### Alternative 3: Separate Subklassen für jeden Fall
- **Vorteile:** Keine Optional nötig, Type Safety durch Vererbung
- **Nachteile:** Klassen-Explosion, komplexere Hierarchie
- **Entscheidung:** ❌ Abgelehnt – Zu komplex für dieses Szenario

## Design-Rationale

### Warum Optional<T>?

1. **Explizit vs. Implizit**
   - `Optional<Adresse>` sagt klar: "Dieses Feld kann fehlend sein"
   - `Adresse` (nullable) sagt: "Weiß nicht, ob null erlaubt ist"

2. **Compiler-Unterstützung**
   - Der Compiler zwingt Entwickler, Optional-Werte bewusst zu behandeln
   - Verhindert versehentliche NullPointerExceptions

3. **Selbst-dokumentierend**
   - Die API dokumentiert sich selbst
   - Keine zusätzliche JavaDoc nötig

4. **Funktionale Programmierung**
   - Ermöglicht flüssige Verarbeitung mit `map()`, `filter()`, `flatMap()`
   - Bessere Komposition von Operationen

### Wann Optional<T> verwenden?

✅ **Return-Typen** – Wenn eine Methode keinen Wert zurückgeben kann  
✅ **Optionale Attribute** – Wenn ein Feld in einer Klasse optional ist  
✅ **Domain Events** – Wenn ein Event nur bestimmte Felder hat  

❌ **Methoden-Parameter** – Optional als Parameter ist umstritten (besser: Überladung)  
❌ **Collections** – Leere Collection statt Optional<Collection>  
❌ **Primitive Typen** – OptionalInt, OptionalLong etc. für Primitives  

## Migration Guide

### Für bestehende Codebasen

1. **Feld-Deklaration ändern**
   ```java
   // Vorher
   private final Adresse adresse;
   
   // Nachher
   private final Optional<Adresse> adresse;
   ```

2. **Konstruktoren anpassen**
   ```java
   // Vorher
   this.adresse = adresse;  // kann null sein
   
   // Nachher
   this.adresse = Optional.ofNullable(adresse);
   ```

3. **Zugriffe anpassen**
   ```java
   // Vorher
   if (event.getAdresse() != null) {
       // ...
   }
   
   // Nachher
   if (event.getAdresse().isPresent()) {
       Adresse adresse = event.getAdresse().get();
       // oder besser:
       event.getAdresse().ifPresent(adresse -> { ... });
   }
   ```

4. **Default-Werte**
   ```java
   // Vorher
   Adresse adresse = event.getAdresse() != null ? event.getAdresse() : defaultAdresse;
   
   // Nachher
   Adresse adresse = event.getAdresse().orElse(defaultAdresse);
   ```

## Best Practices

### ✅ Empfohlen

```java
// 1. ifPresent für Side Effects
event.getAdresse().ifPresent(adresse -> sendToAddress(adresse));

// 2. orElse für Default-Werte
Adresse adresse = event.getAdresse().orElse(DEFAULT_ADRESSE);

// 3. map für Transformationen
String strasse = event.getAdresse()
    .map(Adresse::getStrasse)
    .map(Strasse::getValue)
    .orElse("unbekannt");

// 4. filter für Bedingte Verarbeitung
event.getAdresse()
    .filter(adresse -> adresse.isValid())
    .ifPresent(this::processValidAddress);
```

### ❌ Nicht empfohlen

```java
// 1. get() ohne isPresent() Check
Adresse adresse = event.getAdresse().get();  // NoSuchElementException!

// 2. isPresent() + get() (redundant)
if (event.getAdresse().isPresent()) {
    Adresse adresse = event.getAdresse().get();  // Redundant
}
// Besser:
event.getAdresse().ifPresent(adresse -> { ... });
```

---

## Referenzen

- [Java 8 Optional – The Best Practices](https://dzone.com/articles/java-8-optional-the-best-practices)
- [Using Optional in Java – Baeldung](https://www.baeldung.com/java-optional)
- [Effective Java – Item 55: Return Optionals Judiciously](https://www.oreilly.com/library/view/effective-java-3rd/9780134685991/) – Joshua Bloch
- [Google Guava: Using and avoiding Optional](https://github.com/google/guava/wiki/Using-and-avoiding-Optional)

---

*Dieses ADR dokumentiert eine architektonische Entscheidung und sollte nicht ohne Absprache geändert werden.*
